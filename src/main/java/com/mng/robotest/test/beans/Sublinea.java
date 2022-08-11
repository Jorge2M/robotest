package com.mng.robotest.test.beans;

import com.mng.robotest.conftestmaker.AppEcom;


public class Sublinea extends Linea {

	private static final long serialVersionUID = 1L;

	public static enum SublineaType { 
		teen_nina("teenA", "teenA", "nina", LineaType.teen),
		teen_nino("teenO", "teenO", "nino", LineaType.teen),
		nina_nina("nina", "nina", "nina", LineaType.nina), 
		nina_bebe("babyNina", "babyNina", "bebe", LineaType.nina),
		nino_nino("nino", "nino", "nino", LineaType.nino), 
		nino_bebe("babyNino", "babyNino", "bebe", LineaType.nino); 
		
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