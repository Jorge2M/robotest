package com.mng.sapfiori.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageSolicitudPedido extends WebdrvWrapp {

	private final WebDriver driver;
	
	public final static String TitlePage = "Solicitud de pedido";
	private final static String XPathTitle = "//h1[text()[contains(.,'" + TitlePage + "')]]";
	
	private final static String XPathIconAddArticulo = "//button[@id[contains(.,'-iCreateItembutton')]]";
	private final static String XPathModalIconAddArticulo = "//div[@class[contains(.,'sapMPopup-CTX')]]";
	private final static String XPathOptionModalAddArticulo = XPathModalIconAddArticulo + "//button";
	
	private PageSolicitudPedido(WebDriver driver) {
		this.driver = driver;
	}
	public static PageSolicitudPedido getNew(WebDriver driver) {
		return new PageSolicitudPedido(driver);
	}
	
	private String getXPathOptionModalAddArt(String concepto) {
		return (XPathOptionModalAddArticulo + "//self::*[text()[contains(.,'" + concepto + "')]]");
	}
	
	public boolean checkIsPage(int maxSeconds) {
		return (isElementVisibleUntil(driver, By.xpath(XPathTitle), maxSeconds));
	}
	
	public PagePosSolicitudPedido añadirArticulo(String tipoArticulo) {
		driver.findElement(By.xpath(XPathIconAddArticulo)).click();
		String xpathOption = getXPathOptionModalAddArt(tipoArticulo);
		driver.findElement(By.xpath(xpathOption)).click();
		return PagePosSolicitudPedido.getNew(driver);
	}
	
}
