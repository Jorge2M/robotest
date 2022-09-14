package com.mng.robotest.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.domains.transversal.PageBase;

public class PageInputPedido extends PageBase implements PageFromFooter {
	
	private static final String XPATH_INPUT_PEDIDO = "//input[@id[contains(.,'pedidoId')]]";
	private static final String XPATH_INPUT_EMAIL_USR = "//input[@id[contains(.,'mailPedido')]]";
	private static final String XPATH_LINK_RECUPERAR_DATOS = "//*[@onclick[contains(.,'FPedidoMail')]]";
	
	@Override
	public String getName() {
		return "Consulta de pedidos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return state(Present, XPATH_INPUT_PEDIDO).wait(seconds).check();
	}
	
	public boolean isVisibleInputPedido() {
		return state(Visible, XPATH_INPUT_PEDIDO).check();
	}
	
	public void inputPedido(String codPedido) {
		getElement(XPATH_INPUT_PEDIDO).clear();
		getElement(XPATH_INPUT_PEDIDO).sendKeys(codPedido);
	}
	
	public void inputEmailUsr(String emailUsr) {
		getElement(XPATH_INPUT_EMAIL_USR).clear();
		getElement(XPATH_INPUT_EMAIL_USR).sendKeys(emailUsr);
	}
	
	public void clickRecuperarDatos() {
		click(XPATH_LINK_RECUPERAR_DATOS).exec();
	}
}
