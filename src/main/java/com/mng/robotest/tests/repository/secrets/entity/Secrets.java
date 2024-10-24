package com.mng.robotest.tests.repository.secrets.entity;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement (name = "secrets")
public class Secrets {
	
	@XmlElement(name = "secret")
	private List<Secret> list;
	
	public List<Secret> getSecrets() {
		return list;	
	}
	
	public void setSecrets(List<Secret> secrets) {
		this.list = secrets;
	}
	
}
