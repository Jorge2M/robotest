package com.mng.sapfiori.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.test.testcase.generic.webobject.elements.inputs.select.SelectEstandard;
import com.mng.sapfiori.test.testcase.generic.webobject.makers.StandarElementsMaker;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class PageSolicitudPedido extends WebdrvWrapp {

	private final WebDriver driver;

	
	public final static String TitlePage = "Solicitud de pedido";
	private final static String XPathTitle = "//h1[text()[contains(.,'" + TitlePage + "')]]";
	
	//Info General
	private final static String XPathInputDescrSolPedido = "//input[@id[contains(.,'PurReqnDescription::Field-input-inner')]]";
	private final SelectEstandard selectClaseDocumento;
	private final static String XPathRadioDetFuenteAprov = "//input[@id[contains(.,'SourceDetermination')]]";
	private final static String XPathRadioDetFuenteAprovToClick = XPathRadioDetFuenteAprov + "/..";
	
	private final static String XPathIconAddArticulo = "//button[@id[contains(.,'-iCreateItembutton')]]";
	private final static String XPathModalIconAddArticulo = "//div[@class[contains(.,'sapMPopup-CTX')]]";
	private final static String XPathOptionModalAddArticulo = XPathModalIconAddArticulo + "//button";
	
	private PageSolicitudPedido(WebDriver driver) {
		this.driver = driver;
		StandarElementsMaker standarMaker = StandarElementsMaker.getNew(driver);
		this.selectClaseDocumento = standarMaker.getSelectEstandard("Clase de documento de la solicitud de pedido");
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
	
	public void inputInfoGeneral(InfoGeneralSolPedido infoGeneral) {
		WebElement inputDescr = driver.findElement(By.xpath(XPathInputDescrSolPedido));
		inputDescr.clear();
		inputDescr.sendKeys(infoGeneral.descrSolPedido);
		selectClaseDocumento.selectByValue(infoGeneral.claseDocumento);
		inputRadioDetFuenteAprov(infoGeneral.detFuenteAprov);
	}
	
	private void inputRadioDetFuenteAprov(boolean toCheck) {	
		WebElement radio = driver.findElement(By.xpath(XPathRadioDetFuenteAprovToClick));
		boolean checked = (radio.getAttribute("checked")!=null);
		if (checked!=toCheck) {
			radio.click();
		}
	}
	
	public PagePosSolicitudPedido añadirArticulo(String tipoArticulo) {
		driver.findElement(By.xpath(XPathIconAddArticulo)).click();
		String xpathOption = getXPathOptionModalAddArt(tipoArticulo);
		driver.findElement(By.xpath(xpathOption)).click();
		return PagePosSolicitudPedido.getNew(driver);
	}
	
}
