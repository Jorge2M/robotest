package com.mng.robotest.testslegacy.beans;

import javax.xml.bind.annotation.*;

/**
 * @author jorge.munoz
 *
 */

public class Checkchar {

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
	
	public boolean checkChar() {
		return (this.getCheck()!=null && 
				this.getCheck().compareTo("s")==0);
	}
		
	@Override
	public String toString() {
		return "Checkchar [check="+ this.check + ", text=" + this.text + 
				", toString()=" + super.toString() + "]";
	}
}