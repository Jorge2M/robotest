package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;


public class Sublinea extends Linea {

    public static enum SublineaType { 
    	teen_nina("teenA", "teenA", LineaType.teen),
    	teen_nino("teenO", "teenO", LineaType.teen),
    	nina_nina("nina", "outletA", LineaType.nina), 
    	nina_bebe("babyNina", "outletBA", LineaType.nina),
    	nino_nino("nino", "outletO", LineaType.nino), 
    	nino_bebe("babyNino", "outletBO", LineaType.nino); 
    	
    	String idShop = "";
    	String idOutlet = "";
    	LineaType parentLine;
    	private SublineaType(String idShop, String idOutlet, LineaType parentLine) {
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
    
    public SublineaType getTypeSublinea() {
        return (SublineaType.valueOf(super.getId()));
    }
        
    @Override
    public Sublinea getSublineaNinos(SublineaType sublineaType) {
        return null;
    }
    
    @Override
    public String toString() {
        return "Sublinea [id="+ this.id + ", existe=" + this.shop + ", contentDesk=" + this.contentDesk + ", contentMovil=" + this.contentMovil + ", carrusels=" + this.carrusels + ", panoramicas=" + this.panoramicas +
                ", toString()=" + super.toString() + "]";
	}

}