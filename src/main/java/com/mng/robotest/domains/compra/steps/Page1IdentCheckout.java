package com.mng.robotest.domains.compra.steps;

import com.mng.robotest.domains.compra.pageobjects.SecSoyNuevo;
import com.mng.robotest.domains.transversal.PageBase;


public class Page1IdentCheckout extends PageBase {
	
	private final SecSoyNuevo secSoyNuevo = new SecSoyNuevo();

	public SecSoyNuevo getSecSoyNuevo() {
		return secSoyNuevo;
	}
	
}
