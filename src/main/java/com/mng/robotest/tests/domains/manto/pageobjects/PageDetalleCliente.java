package com.mng.robotest.tests.domains.manto.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageDetalleCliente extends PageBase {
	
	private static final String XP_USER_DNI = "//td[text()[contains(.,'DNI')]]/following::td/span";
	private static final String XP_USER_EMAIL = "//td[text()[contains(.,'MAIL:')]]/following::td/span";
	private static final String XP_VOLVER_PEDIDOS = "//a[text()='volver a pedidos']";

	public String getUserDniText() {
		return getElement(XP_USER_DNI).getText();
	}

	public String getUserEmailText() {
		return getElement(XP_USER_EMAIL).getText();
	}

	public void clickLinkVolverPedidos() {
		click(XP_VOLVER_PEDIDOS).waitLoadPage(20).exec();
		while (!new PagePedidos().isPage()) {
			waitMillis(200);
		}
	}

}
