package it.dfa.unict.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.codehaus.jackson.annotate.JsonAnyGetter;
import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
@Generated("org.jsonschema2pojo")
@JsonPropertyOrder({ "status", "description", "creation", "iosandbox", "user",
		"id", "output_files", "application", "arguments", "runtime_data",
		"input_files", "last_change" })
public class Task {

	@JsonProperty("status")
	private String status;
	@JsonProperty("description")
	private String description;
	@JsonProperty("creation")
	private String creation;
	@JsonProperty("iosandbox")
	private String iosandbox;
	@JsonProperty("user")
	private String user;
	@JsonProperty("id")
	private String id;
	@JsonProperty("output_files")
	private List<OutputFile> outputFiles = new ArrayList<OutputFile>();
	@JsonProperty("application")
	private String application;
	@JsonProperty("arguments")
	private List<String> arguments = new ArrayList<String>();
	@JsonProperty("runtime_data")
	private List<Object> runtimeData = new ArrayList<Object>();
	@JsonProperty("input_files")
	private List<InputFile> inputFiles = new ArrayList<InputFile>();
	@JsonProperty("last_change")
	private String lastChange;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The status
	 */
	@JsonProperty("status")
	public String getStatus() {
		return status;
	}

	/**
	 * 
	 * @param status
	 *            The status
	 */
	@JsonProperty("status")
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * 
	 * @return The description
	 */
	@JsonProperty("description")
	public String getDescription() {
		return description;
	}

	/**
	 * 
	 * @param description
	 *            The description
	 */
	@JsonProperty("description")
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * 
	 * @return The creation
	 */
	@JsonProperty("creation")
	public String getCreation() {
		return creation;
	}

	/**
	 * 
	 * @param creation
	 *            The creation
	 */
	@JsonProperty("creation")
	public void setCreation(String creation) {
		this.creation = creation;
	}

	/**
	 * 
	 * @return The iosandbox
	 */
	@JsonProperty("iosandbox")
	public String getIosandbox() {
		return iosandbox;
	}

	/**
	 * 
	 * @param iosandbox
	 *            The iosandbox
	 */
	@JsonProperty("iosandbox")
	public void setIosandbox(String iosandbox) {
		this.iosandbox = iosandbox;
	}

	/**
	 * 
	 * @return The user
	 */
	@JsonProperty("user")
	public String getUser() {
		return user;
	}

	/**
	 * 
	 * @param user
	 *            The user
	 */
	@JsonProperty("user")
	public void setUser(String user) {
		this.user = user;
	}

	/**
	 * 
	 * @return The id
	 */
	@JsonProperty("id")
	public String getId() {
		return id;
	}

	/**
	 * 
	 * @param id
	 *            The id
	 */
	@JsonProperty("id")
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 
	 * @return The outputFiles
	 */
	@JsonProperty("output_files")
	public List<OutputFile> getOutputFiles() {
		return outputFiles;
	}

	/**
	 * 
	 * @param outputFiles
	 *            The output_files
	 */
	@JsonProperty("output_files")
	public void setOutputFiles(List<OutputFile> outputFiles) {
		this.outputFiles = outputFiles;
	}

	/**
	 * 
	 * @return The application
	 */
	@JsonProperty("application")
	public String getApplication() {
		return application;
	}

	/**
	 * 
	 * @param application
	 *            The application
	 */
	@JsonProperty("application")
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * 
	 * @return The arguments
	 */
	@JsonProperty("arguments")
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * 
	 * @param arguments
	 *            The arguments
	 */
	@JsonProperty("arguments")
	public void setArguments(List<String> arguments) {
		this.arguments = arguments;
	}

	/**
	 * 
	 * @return The runtimeData
	 */
	@JsonProperty("runtime_data")
	public List<Object> getRuntimeData() {
		return runtimeData;
	}

	/**
	 * 
	 * @param runtimeData
	 *            The runtime_data
	 */
	@JsonProperty("runtime_data")
	public void setRuntimeData(List<Object> runtimeData) {
		this.runtimeData = runtimeData;
	}

	/**
	 * 
	 * @return The inputFiles
	 */
	@JsonProperty("input_files")
	public List<InputFile> getInputFiles() {
		return inputFiles;
	}

	/**
	 * 
	 * @param inputFiles
	 *            The input_files
	 */
	@JsonProperty("input_files")
	public void setInputFiles(List<InputFile> inputFiles) {
		this.inputFiles = inputFiles;
	}

	/**
	 * 
	 * @return The lastChange
	 */
	@JsonProperty("last_change")
	public String getLastChange() {
		return lastChange;
	}

	/**
	 * 
	 * @param lastChange
	 *            The last_change
	 */
	@JsonProperty("last_change")
	public void setLastChange(String lastChange) {
		this.lastChange = lastChange;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

	@Override
	public String toString() {
		return "Task [status=" + status + ", description=" + description
				+ ", creation=" + creation + ", iosandbox=" + iosandbox
				+ ", user=" + user + ", id=" + id + ", outputFiles="
				+ outputFiles + ", application=" + application + ", arguments="
				+ arguments + ", runtimeData=" + runtimeData + ", inputFiles="
				+ inputFiles + ", lastChange=" + lastChange
				+ ", additionalProperties=" + additionalProperties + "]";
	}
	
}
