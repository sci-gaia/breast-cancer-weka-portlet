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
@JsonPropertyOrder({ "application", "description", "arguments", "input_files",
		"output_files" })
public class AppInput {

	@JsonProperty("application")
	private int application;
	@JsonProperty("description")
	private String description;
	@JsonProperty("arguments")
	private List<String> arguments = new ArrayList<String>();
	@JsonProperty("input_files")
	private List<InputFile> inputFiles = new ArrayList<InputFile>();
	@JsonProperty("output_files")
	private List<OutputFile> outputFiles = new ArrayList<OutputFile>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();

	/**
	 * 
	 * @return The application
	 */
	@JsonProperty("application")
	public int getApplication() {
		return application;
	}

	/**
	 * 
	 * @param i
	 *            The application
	 */
	@JsonProperty("application")
	public void setApplication(int i) {
		this.application = i;
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
		return "AppInput [application=" + application + ", description="
				+ description + ", arguments=" + arguments + ", inputFiles="
				+ inputFiles + ", outputFiles=" + outputFiles
				+ ", additionalProperties=" + additionalProperties + "]";
	}

}
