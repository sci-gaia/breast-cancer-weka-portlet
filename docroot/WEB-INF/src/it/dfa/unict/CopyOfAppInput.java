package it.dfa.unict;

/**
 *
 * @author mario
 */
public class CopyOfAppInput {
	// Application inputs

	private String inputFileName; // Filename for application input file
	private String jobLabel; // User' given job identifier

	// Each inputSandobox file must be declared below
	// This variable contains the content of an uploaded file
	private String inputSandbox;

	// Some user level information
	// must be stored as well
	private String username;
	private String timestamp;

	/**
	 * Standard constructor just initialize empty values
	 */
	public CopyOfAppInput() {
		this.inputFileName = "";
		this.jobLabel = "";
		this.inputSandbox = "";
		this.username = "";
		this.timestamp = "";
	}

	public String getInputFileName() {
		return inputFileName;
	}

	public void setInputFileName(String inputFileName) {
		this.inputFileName = inputFileName;
	}

	public String getJobLabel() {
		return jobLabel;
	}

	public void setJobLabel(String joblabel) {
		this.jobLabel = joblabel;
	}

	public String getInputSandbox() {
		return inputSandbox;
	}

	public void setInputSandbox(String inputFile) {
		this.inputSandbox += (this.inputSandbox.equals("") ? "" : ",")
				+ inputFile;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	@Override
	public String toString() {
		return "AppInput{" + "inputFileName=" + inputFileName + ", jobLabel="
				+ jobLabel + ", inputSandbox=" + inputSandbox + ", username="
				+ username + ", timestamp=" + timestamp + '}';
	}

}
