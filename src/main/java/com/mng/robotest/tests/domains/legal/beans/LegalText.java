package com.mng.robotest.tests.domains.legal.beans;

public class LegalText {

	private final String code;
	private final String text;
	private final String xpath;
	private final String cssShadowDom;
	
	public LegalText(String code, String text, String xpath) {
		this.code = code;
		this.text = text;
		this.xpath = xpath;
		this.cssShadowDom = null;
	}
	public LegalText(String code, String text, String xpath, String cssShadowDom) {
		this.code = code;
		this.text = text;
		this.xpath = xpath;
		this.cssShadowDom = cssShadowDom;
	}	

	public String getCode() {
		return code;
	}

	public String getText() {
		return text;
	}
	
	public boolean isShadowDom() {
		return cssShadowDom!=null;
	}
	
	public String getXPath() {
		return xpath;
	}

	public String getXPathWithLiteral() {
		return xpath + "//self::*[text()[contains(.,'" + text + "')]]";
	}
	public String getCssShadowDom() {
		return cssShadowDom;
	}

}
