package com.mng.robotest.domains.compra.steps;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo;

public class Page1IdentCheckout extends PageBase {
	
	private final SecSoyNuevo secSoyNuevo = SecSoyNuevo.make(channel, app);

	public SecSoyNuevo getSecSoyNuevo() {
		return secSoyNuevo;
	}
	
}
