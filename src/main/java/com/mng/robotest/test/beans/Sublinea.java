package com.mng.robotest.test.beans;

import com.mng.robotest.conftestmaker.AppEcom;


public class Sublinea extends Linea {

	private static final long serialVersionUID = 1L;

	public static enum SublineaType { 
		TEEN_NINA("teenA", "teenA", "nina", LineaType.teen),
		TEEN_NINO("teenO", "teenO", "nino", LineaType.teen),
		NINA_NINA("nina", "outletA", "nina", LineaType.nina), 
		NINA_BEBE("babyNina", "outletBA", "bebe", LineaType.nina),
		NINO_NINO("nino", "outletO", "nino", LineaType.nino), 
		NINO_BEBE("babyNino", "outletBO", "bebe", LineaType.nino); 
		
		String idShop = "";
		String idOutlet = "";
		String text;
		LineaType parentLine;
		private SublineaType(String idShop, String idOutlet, String text, LineaType parentLine) {
			this.idShop = idShop;
			this.idOutlet = idOutlet;
			this.text = text;
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
		
		public String getText() {
			return text;
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