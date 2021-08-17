package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;


public class Sublinea extends Linea {

    public static enum SublineaNinosType { 
    	teen_nina("teenA", "teenA", LineaType.teen),
    	nina("nina", "outletA", LineaType.nina), 
    	bebe_nina("babyNina", "outletBA", LineaType.nina),
    	teen_nino("teenO", "teenO", LineaType.teen),
    	nino("nino", "outletO", LineaType.nino), 
    	bebe_nino("babyNino", "outletBO", LineaType.nino); 
    	
    	String idShop = "";
    	String idOutlet = "";
    	LineaType parentLine;
    	private SublineaNinosType(String idShop, String idOutlet, LineaType parentLine) {
    		this.idShop = idShop;
    		this.idOutlet = idOutlet;
    		this.parentLine = parentLine;
    	}
    	
    	public String getId(AppEcom app) {
    		switch (app) {
    		case outlet:
    			return idOutlet;
    		default:
    			return idShop;
    		}
    	}
    	
    	public String getNameUpper() {
    		return name().toUpperCase();
    	}
    	
    	public LineaType getParentLine() {
    		return this.parentLine;
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