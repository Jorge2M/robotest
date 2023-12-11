package com.mng.robotest.tests.domains.compra.steps;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo;

public class Page1IdentCheckout extends PageBase {
	
	private final SecSoyNuevo secSoyNuevo = SecSoyNuevo.make(channel);

	public SecSoyNuevo getSecSoyNuevo() {
		return secSoyNuevo;
	}
	
}
