package com.mng.robotest.tests.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;

public class PageInputPedido extends PageBase implements PageFromFooter {
	
	private static final String XP_INPUT_PEDIDO = "//input[@id[contains(.,'pedidoId')]]";
	private static final String XP_INPUT_EMAIL_USR = "//input[@id[contains(.,'mailPedido')]]";
	private static final String XP_LINK_RECUPERAR_DATOS = "//*[@onclick[contains(.,'FPedidoMail')]]";
	
	@Override
	public String getName() {
		return "Consulta de pedidos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(PRESENT, XP_INPUT_PEDIDO).wait(seconds).check();
	}
	
	public boolean isVisibleInputPedido() {
		return state(VISIBLE, XP_INPUT_PEDIDO).check();
	}
	
	public void inputPedido(String codPedido) {
		getElement(XP_INPUT_PEDIDO).clear();
		getElement(XP_INPUT_PEDIDO).sendKeys(codPedido);
	}
	
	public void inputEmailUsr(String emailUsr) {
		getElement(XP_INPUT_EMAIL_USR).clear();
		getElement(XP_INPUT_EMAIL_USR).sendKeys(emailUsr);
	}
	
	public void clickRecuperarDatos() {
		click(XP_LINK_RECUPERAR_DATOS).exec();
	}
}
