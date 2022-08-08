package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.footer.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageInputPedido extends PageBase implements PageFromFooter {
	
	private static final String XPATH_INPUT_PEDIDO = "//input[@id[contains(.,'pedidoId')]]";
	private static final String XPATH_INPUT_EMAIL_USR = "//input[@id[contains(.,'mailPedido')]]";
	private static final String XPATH_LINK_RECUPERAR_DATOS = "//*[@onclick[contains(.,'FPedidoMail')]]";
	
	@Override
	public String getName() {
		return "Consulta de pedidos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, By.xpath(XPATH_INPUT_PEDIDO)).wait(maxSeconds).check());
	}
	
	public boolean isVisibleInputPedido() {
		return (state(Visible, By.xpath(XPATH_INPUT_PEDIDO)).check());
	}
	
	public void inputPedido(String codPedido) {
		driver.findElement(By.xpath(XPATH_INPUT_PEDIDO)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_PEDIDO)).sendKeys(codPedido);
	}
	
	public void inputEmailUsr(String emailUsr) {
		driver.findElement(By.xpath(XPATH_INPUT_EMAIL_USR)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_EMAIL_USR)).sendKeys(emailUsr);
	}
	
	public void clickRecuperarDatos() {
		click(By.xpath(XPATH_LINK_RECUPERAR_DATOS)).exec();
	}
}
