package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;

@SuppressWarnings("javadoc")
public class Sublinea extends Linea {

    public static enum SublineaNinosType { 
    	nina("nina", "outletA"), 
    	bebe_nina("babyNina", "outletBA"), 
    	nino("nino", "outletO"), 
    	bebe_nino("babyNino", "outletBO"); 
    	
    	String idShop = "";
    	String idOutlet = "";
    	private SublineaNinosType(String idShop, String idOutlet) {
    		this.idShop = idShop;
    		this.idOutlet = idOutlet;
    	}
    	
    	public String getId(AppEcom app) {
    		switch (app) {
    		case outlet:
    			return idOutlet;
    		default:
    			return idShop;
    		}
    	}
    }
    
    public SublineaNinosType getTypeSublinea() {
        return (SublineaNinosType.valueOf(super.getId()));
    }
        
    @Override
    public Sublinea getSublineaNinos(SublineaNinosType sublineaType) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Sublinea [id="+ this.id + ", existe=" + this.shop + ", contentDesk=" + this.contentDesk + ", contentMovil=" + this.contentMovil + ", carrusels=" + this.carrusels + ", panoramicas=" + this.panoramicas +
                ", toString()=" + super.toString() + "]";
	}

}