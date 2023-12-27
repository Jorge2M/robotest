package com.mng.robotest.tests.domains.menus.beans;

import java.io.Serializable;

import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.SublineaType;

public class Sublinea extends Linea implements Serializable {

	private static final long serialVersionUID = 1L;
	
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