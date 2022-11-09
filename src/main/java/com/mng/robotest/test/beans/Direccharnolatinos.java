package com.mng.robotest.test.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.*;

public class Direccharnolatinos implements Serializable {

	private static final long serialVersionUID = -6285735947183892226L;
	
	String check;
	String text;
	
	public String getCheck() {
		return this.check;
	}
	
	@XmlAttribute(name="check")
	public void setCheck(String check) {
		this.check = check;
	}
	
	public String getText() {
		return this.text;
	}
	
	@XmlAttribute(name="text")
	public void setText(String text) {
		this.text = text;
	}
	
	public boolean check() {
		return (this.getCheck()!=null && 
				this.getCheck().compareTo("s")==0);
	}
		
	@Override
	public String toString() {
		return "Checkchar [check="+ this.check + ", text=" + this.text + 
				", toString()=" + super.toString() + "]";
	}
}