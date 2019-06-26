package com.mng.robotest.test80.mango.test.pageobject.shop.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;


public class PageInputPedido extends WebdrvWrapp implements PageFromFooter {
    
    private static final String XPathInputPedido = "//input[@id[contains(.,'pedidoId')]]";
    private static final String XPathInputEmailUsr = "//input[@id[contains(.,'mailPedido')]]";
    private static final String XPathLinkRecuperarDatos = "//*[@onclick[contains(.,'FPedidoMail')]]";
    
	@Override
	public String getName() {
		return "Consulta de pedidos";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait, WebDriver driver) {
		return (isElementPresentUntil(driver, By.xpath(XPathInputPedido), maxSecondsWait));
	}
    
    public static boolean isVisibleInputPedido(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputPedido)));
    }
    
    public static void inputPedido(String codPedido, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputPedido)).clear();
        driver.findElement(By.xpath(XPathInputPedido)).sendKeys(codPedido);
    }
    
    public static void inputEmailUsr(String emailUsr, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputEmailUsr)).clear();
        driver.findElement(By.xpath(XPathInputEmailUsr)).sendKeys(emailUsr);
    }
    
    public static void clickRecuperarDatos(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathLinkRecuperarDatos));
    }
}
