package it.dfa.unict;

import it.dfa.unict.pojo.AppInput;
import it.dfa.unict.pojo.Task;
import it.dfa.unict.util.Constants;

import java.io.File;
import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.multipart.FormDataMultiPart;
import com.sun.jersey.multipart.file.FileDataBodyPart;

public class FutureGatewayClient {
	
	private final Log _log = LogFactoryUtil.getLog(FutureGatewayClient.class);

	private String fgEndpoint;
	private String fgApiversion;
	private WebResource apiResource;
	private Client client;
		
	public FutureGatewayClient(String fgHost, int fgPort, String fgAPIVersion) {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		this.client = Client.create(clientConfig);
		this.setFgEndpoint(fgHost, fgPort);
		this.fgApiversion = fgAPIVersion;
		this.apiResource = this.client
				.resource(this.fgEndpoint + "/" + this.fgApiversion);
	}

	public Client getClient() {
		return client;
	}

	public void setApiResource(WebResource apiResource) {
		this.apiResource = apiResource;
	}

	public String getFgEndpoint() {
		return fgEndpoint;
	}

	public void setFgEndpoint(String fgEndpoint) {
		this.setFgEndpoint(fgEndpoint, Constants.HTTP_PORT);
	}

	public void setFgEndpoint(String fgEndpoint, int fgPort) {
		this.fgEndpoint = "http://" + fgEndpoint + ":" + fgPort;
	}
	
	public String getFgApiversion() {
		return fgApiversion;
	}

	public void setFgApiversion(String fgApiversion) {
		this.fgApiversion = fgApiversion;
	}

	public Task createTask(AppInput appInput, String user)
			throws JsonGenerationException, JsonMappingException,
			UniformInterfaceException, IOException {

		ClientResponse response = apiResource.path("tasks")
				.queryParam("user", user).accept(MediaType.APPLICATION_JSON)
				.type(MediaType.APPLICATION_JSON)
				.post(ClientResponse.class, appInput);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}
		
		return response.getEntity(Task.class);

	}

	public Task getTask(int id) {

		ClientResponse response = apiResource.path("tasks/" + id)
				.accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

		if (response.getStatus() != 200) {
			_log.debug(apiResource);
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		return response.getEntity(Task.class);
	}

	public String uploadFile(String uploadPath, String[] inputSandbox) {
		FormDataMultiPart multiPart = new FormDataMultiPart();
		for (String path : inputSandbox) {
			File fileToUpload = new File(path);
			if (fileToUpload != null) {
				multiPart.bodyPart(new FileDataBodyPart("file[]", fileToUpload,
						MediaType.APPLICATION_OCTET_STREAM_TYPE));
			}
		}
		
		_log.debug(uploadPath.replace("/v1.0", ""));
		
		ClientResponse response = apiResource.path(uploadPath.replace("/v1.0", ""))
				.accept(MediaType.APPLICATION_JSON)
				.type(MediaType.MULTIPART_FORM_DATA_TYPE)
				.post(ClientResponse.class, multiPart);
		if (response.getStatus() != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		return response.getEntity(String.class);
	}
}
