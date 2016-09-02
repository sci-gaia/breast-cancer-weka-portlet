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
@JsonPropertyOrder({ "status", "application", "date", "description",
		"output_files", "_links", "user", "input_files", "id", "arguments" })
public class Task {

	@JsonProperty("status")
	private String status;
	@JsonProperty("application")
	private String application;
	@JsonProperty("date")
	private String date;
	@JsonProperty("description")
	private String description;
	@JsonProperty("output_files")
	private List<OutputFile> outputFiles = new ArrayList<OutputFile>();
	@JsonProperty("_links")
	private List<Link> links = new ArrayList<Link>();
	@JsonProperty("user")
	private String user;
	@JsonProperty("input_files")
	private List<InputFile> inputFiles = new ArrayList<InputFile>();
	@JsonProperty("id")
	private String id;
	@JsonProperty("arguments")
	private List<String> arguments = new ArrayList<String>();
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
	 * @return The date
	 */
	@JsonProperty("date")
	public String getDate() {
		return date;
	}

	/**
	 * 
	 * @param date
	 *            The date
	 */
	@JsonProperty("date")
	public void setDate(String date) {
		this.date = date;
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
	 * @return The links
	 */
	@JsonProperty("_links")
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * 
	 * @param links
	 *            The _links
	 */
	@JsonProperty("_links")
	public void setLinks(List<Link> links) {
		this.links = links;
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
		return "Task [status=" + status + ", application=" + application
				+ ", date=" + date + ", description=" + description
				+ ", outputFiles=" + outputFiles + ", links=" + links
				+ ", user=" + user + ", inputFiles=" + inputFiles + ", id="
				+ id + ", arguments=" + arguments + ", additionalProperties="
				+ additionalProperties + "]";
	}

}
