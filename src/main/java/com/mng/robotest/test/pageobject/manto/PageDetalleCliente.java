package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.manto.pedido.PagePedidos;


public class PageDetalleCliente extends PageBase {
	
	private static final String XPATH_USER_DNI = "//td[text()[contains(.,'DNI')]]/following::td/span";
	private static final String XPATH_USER_EMAIL = "//td[text()[contains(.,'MAIL:')]]/following::td/span";
	private static final String XPATH_VOLVER_PEDIDOS = "//a[text()='volver a pedidos']";

	public String getUserDniText() {
		return driver.findElement(By.xpath(XPATH_USER_DNI)).getText();
	}

	public String getUserEmailText() {
		return driver.findElement(By.xpath(XPATH_USER_EMAIL)).getText();
	}

	public void clickLinkVolverPedidos() {
		click(By.xpath(XPATH_VOLVER_PEDIDOS)).waitLoadPage(20).exec();
		while (!new PagePedidos().isPage()) {
			waitMillis(200);
		}
	}

}
