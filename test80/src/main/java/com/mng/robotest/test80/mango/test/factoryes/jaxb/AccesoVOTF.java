package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import javax.xml.bind.annotation.*;

@SuppressWarnings("javadoc")
public class AccesoVOTF {

    String usuario;
    String password;
    String entregadomic = "S";
	
    public String getUsuario() {
        return this.usuario;
    }
	
    @XmlAttribute(name="usuario")
    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
	
    public String getPassword() {
        return this.password;
    }
	
    @XmlAttribute(name="password")
    public void setPassword(String password) {
        this.password = password;
    }
	
    public String getEntregadomic() {
        return this.entregadomic;
    }

    @XmlAttribute(name="entregadomic")
    public void setEntregadomic(String entregadomic) {
        this.entregadomic = entregadomic;
    }
        
    @Override
    public String toString() {
        return "AccesoVOTF [usuario="+ this.usuario + ", password=" + this.password +
                ", toString()=" + super.toString() + "]";
    }
}