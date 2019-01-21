package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import javax.xml.bind.annotation.*;

/**
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class Direccharnolatinos {
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
        if (this.getCheck()!=null && this.getCheck().compareTo("s")==0)
            return true;
         
        return false;
    }
		
    @Override
    public String toString() {
        return "Checkchar [check="+ this.check + ", text=" + this.text + 
                ", toString()=" + super.toString() + "]";
    }
}