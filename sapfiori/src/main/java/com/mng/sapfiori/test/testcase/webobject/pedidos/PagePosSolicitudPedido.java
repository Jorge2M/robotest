package com.mng.sapfiori.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PagePosSolicitudPedido extends WebdrvWrapp {

	private final WebDriver driver;
	public static final String TitlePage = "Posición de solicitud de pedido";
	private static final String XPathTitle = "//h1[text()[contains(.,'" + TitlePage + "')]]";
	
	private PagePosSolicitudPedido(WebDriver driver) {
		this.driver = driver;
	}
	public static PagePosSolicitudPedido getNew(WebDriver driver) {
		return new PagePosSolicitudPedido(driver);
	}
	
	public boolean checkIsPage(int maxSeconds) throws Exception {
		return isElementVisibleUntil(driver, By.xpath(XPathTitle), maxSeconds);
	}
}
