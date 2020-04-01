package com.mng.robotest.test80.mango.test.pageobject.manto;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;


public class PageDetalleCliente extends PageObjTM {
	
	private final static String XPathUserDni = "//td[text()[contains(.,'DNI')]]/following::td/span";
	private final static String XPathUserEmail = "//td[text()[contains(.,'MAIL:')]]/following::td/span";
	private final static String XPathVolverPedidos = "//a[text()='volver a pedidos']";

	public PageDetalleCliente(WebDriver driver) {
		super(driver);
	}
	
	public String getUserDniText() {
		return driver.findElement(By.xpath(XPathUserDni)).getText();
	}

	public String getUserEmailText() {
		return driver.findElement(By.xpath(XPathUserEmail)).getText();
	}

	public void clickLinkVolverPedidos() {
		click(By.xpath(XPathVolverPedidos)).waitLoadPage(20).exec();
		while (!PagePedidos.isPage(driver)) {
			PageObjTM.waitMillis(200);
		}
	}

}
