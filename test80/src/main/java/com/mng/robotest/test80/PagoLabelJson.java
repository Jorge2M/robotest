package com.mng.robotest.test80;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class PagoLabelJson {
	private String label;
	public PagoLabelJson() {}
	public PagoLabelJson(String label) {
		this.label = label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getLabel() {
		return label;
	}
}