package com.mng.robotest.test80.mango.test.beans;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name="Response")
public class Response {

	List<Continente> Response;	
		
	public List<Continente> getResponse() {
		return this.Response;
	}

	@XmlElement(name="continente")
	public void setResponse(List<Continente> Response) {
		this.Response = Response;
	}

	@Override
	public String toString() {
		return "Response [Response=" + this.Response + ", toString()="
				+ super.toString() + "]";
	}
}
