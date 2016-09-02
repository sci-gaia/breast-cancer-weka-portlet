package it.dfa.unict;

import it.dfa.unict.pojo.AppInput;
import it.dfa.unict.pojo.Task;

import java.io.IOException;

import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.UniformInterfaceException;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.json.JSONConfiguration;

public class FutureGatewayClient {

	private String fgEndpoint = "http://151.97.41.48:8888";
	private String fgApiversion = "v1.0";
	private WebResource apiResource;
	private Client client;

	public FutureGatewayClient() {

		ClientConfig clientConfig = new DefaultClientConfig();
		clientConfig.getFeatures().put(JSONConfiguration.FEATURE_POJO_MAPPING,
				Boolean.TRUE);
		this.client = Client.create(clientConfig);
		this.apiResource = this.client
				.resource(fgEndpoint + "/" + fgApiversion);
	}

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public void setApiResource(WebResource apiResource) {
		this.apiResource = apiResource;
	}

	public static void main(String[] args) {
		try {

			Client client = Client.create();

			WebResource webResource = client
					.resource("http://151.97.41.48:8888/v1.0/tasks?user=mtorrisi");

			ClientResponse response = webResource.accept("application/json")
					.get(ClientResponse.class);

			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatus());
			}

			String output = response.getEntity(String.class);

			System.out.println("Output from Server .... \n");
			System.out.println(output);

		} catch (Exception e) {

			e.printStackTrace();

		}

	}

	public String getFgEndpoint() {
		return fgEndpoint;
	}

	public void setFgEndpoint(String fgEndpoint) {
		this.fgEndpoint = fgEndpoint;
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
			throw new RuntimeException("Failed : HTTP error code : "
					+ response.getStatus());
		}

		return response.getEntity(Task.class);
	}
}
