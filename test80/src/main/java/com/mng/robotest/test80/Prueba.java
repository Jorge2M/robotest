package com.mng.robotest.test80;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="prueba")
public class Prueba {
	private String variable="0";
	public Prueba() {}
	public Prueba(String variable) {
		this.variable = variable;
	}
	public String getVariable() {
		return variable;
	}
	public void setVariable(String variable) {
		this.variable = variable;
	}

}
