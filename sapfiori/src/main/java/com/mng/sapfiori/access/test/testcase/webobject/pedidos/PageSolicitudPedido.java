package com.mng.sapfiori.access.test.testcase.webobject.pedidos;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.sapfiori.access.test.testcase.generic.webobject.elements.inputs.select.SelectEstandard;
import com.mng.sapfiori.access.test.testcase.generic.webobject.utils.PageObject;

public class PageSolicitudPedido extends PageObject {

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
		super(driver);
		this.selectClaseDocumento = elementsMaker.getSelectEstandard("Clase de documento de la solicitud de pedido");
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
	
	
	private final static String XPath1rstLinePedido = "//table//tr[@id[contains(.,'PurchaseReqnItem')]]";
	private final static String XPathInput1rstPedido = XPath1rstLinePedido + "//input";
	private String getXPathInput1rstPedido(InputFieldPedido inputPage) {
		return 
			XPathInput1rstPedido + 
			"//self::*[@aria-labelledby[contains(.,'Table-" + inputPage.idInTable + "')]]";
	}
	
	private String getXPathInput1rstPedidoWithValue(InputFieldPedido inputPage, String value) {
		return 
			getXPathInput1rstPedido(inputPage) + 
			"//self::*[@value='" + value + "']";
	}
	
	public boolean checkFieldIn1rstLineaPedidos(InputFieldPedido inputPage, String value, int maxSeconds) 
	throws Exception {
		waitForPageFinished();
		String xpathInput = getXPathInput1rstPedidoWithValue(inputPage, value);
		return PageObject.isElementPresentUntil(driver, By.xpath(xpathInput), maxSeconds);
	}
	
	public void inputFielValuedIn1rstLinePedidos(InputFieldPedido inputPage, String value) throws Exception {
		String xpathInput = getXPathInput1rstPedido(inputPage);
		WebElement inputElem = driver.findElement(By.xpath(xpathInput));
		inputElem.sendKeys(Keys.HOME, Keys.chord(Keys.SHIFT, Keys.END), value);
		//SeleniumUtils.sendKeysWithRetry(2, inputElem, value);
	}
	
	private final static String XPathButtonGuardar = "//button[@id[contains(.,'-activate')]]";
	public void clickButtonGuardar() throws Exception {
		clickAndWaitLoad(By.xpath(XPathButtonGuardar));
	}
}
