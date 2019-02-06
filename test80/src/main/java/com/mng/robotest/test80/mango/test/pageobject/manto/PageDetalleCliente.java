package com.mng.robotest.test80.mango.test.pageobject.manto;


import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.manto.pedido.PagePedidos;


@SuppressWarnings("javadoc")
public class PageDetalleCliente extends WebdrvWrapp {


    static String XPathUserDni = "//td[text()[contains(.,'DNI')]]/following::td/span";
    static String XPathUserEmail = "//td[text()[contains(.,'MAIL:')]]/following::td/span";
    static String XPathVolverPedidos = "//a[text()='volver a pedidos']";
    

	/**
	 * @param driver
	 * @return el DNI del cliente
	 */ 
	public static String getUserDniText(WebDriver driver) {
		return driver.findElement(By.xpath(XPathUserDni)).getText();
	}

	/**
	 * @param driver
	 * @return el mail del cliente
	 */
	public static String getUserEmailText(WebDriver driver) {
		return driver.findElement(By.xpath(XPathUserEmail)).getText();
	}

	/**
	 * @param driver
	 * @throws Exception
	 */
	public static void clickLinkVolverPedidos(WebDriver driver) throws Exception {
		clickAndWaitLoad(driver, By.xpath(XPathVolverPedidos), 20);
		while (!PagePedidos.isPage(driver))
			Thread.sleep(200);
	}

}
