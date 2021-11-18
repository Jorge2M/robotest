package com.mng.robotest.test80.mango.test.pageobject.shop.registro;


public class InputDataXPath {
	String XPath;
	String XPathDivError;
	String msgErrorPrevRegistro;
	
	public InputDataXPath(String XPath, String XPathDivError, String msgErrorPrevRegistro) {
		this.XPath = XPath;
		this.XPathDivError = XPathDivError;
		this.msgErrorPrevRegistro = msgErrorPrevRegistro;
	}
	
	public String getXPah() {
		return this.XPath;
	}
	
	public String getXPathDivError() {
		return this.XPathDivError;
	}
	
	public String getMsgErrorPrevRegistro() {
		return this.msgErrorPrevRegistro;
	}
}
