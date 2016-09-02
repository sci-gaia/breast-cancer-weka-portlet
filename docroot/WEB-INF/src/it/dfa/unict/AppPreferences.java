package it.dfa.unict;

import it.dfa.unict.util.Constants;

public class AppPreferences {
	private String fgHost = "";
	private int fgPort;
	private String fgAPIVersion = "";
	private int applicationId;
	private String pilotScript = "";

	public String getFgHost() {
		return fgHost;
	}

	public void setFgHost(String fgHost) {
		this.fgHost = fgHost;
	}

	public String getFgAPIVersion() {
		return fgAPIVersion;
	}

	public void setFgAPIVersion(String fgAPIVersion) {
		this.fgAPIVersion = fgAPIVersion;
	}

	public int getFgPort() {
		return fgPort;
	}

	public void setFgPort(int fgPort) {
		this.fgPort = fgPort;
	}

	public int getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(int applicationId) {
		this.applicationId = applicationId;
	}

	public String getPilotScript() {
		return pilotScript;
	}

	public void setPilotScript(String pilotScript) {
		this.pilotScript = pilotScript;
	}

	/**
	 * Returns a text string containing the AppPreferences values dump
	 * 
	 * @return String with HTML statements inside a 'table' block
	 * 
	 * @see AppPreferences
	 */
	public String dump() {
		String dump = Constants.NEW_LINE + "Preference values:"
				+ Constants.NEW_LINE + "-----------------" + Constants.NEW_LINE
				+ "gridOperationId              : '" + applicationId + "'"
				+ Constants.NEW_LINE + "FgHost            : '"
				+ fgHost + "'" + Constants.NEW_LINE
				+ "pilotScript                  : '" + pilotScript + "'";
		return dump;
	} // dumpPreferences

}
