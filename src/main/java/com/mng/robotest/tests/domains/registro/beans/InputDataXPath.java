package com.mng.robotest.tests.domains.registro.beans;


public class InputDataXPath {
	private final String xPath;
	private final String xPathDivError;
	private final String msgErrorPrevRegistro;
	
	public InputDataXPath(String xPath, String xPathDivError, String msgErrorPrevRegistro) {
		this.xPath = xPath;
		this.xPathDivError = xPathDivError;
		this.msgErrorPrevRegistro = msgErrorPrevRegistro;
	}
	
	public String getXPah() {
		return this.xPath;
	}
	
	public String getXPathDivError() {
		return this.xPathDivError;
	}
	
	public String getMsgErrorPrevRegistro() {
		return this.msgErrorPrevRegistro;
	}
}
