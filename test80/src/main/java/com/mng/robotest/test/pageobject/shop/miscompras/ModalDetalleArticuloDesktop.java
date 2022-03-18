package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloDesktop extends ModalDetalleArticulo {

	//private static String XPathModalInfoArticulo = "//*[@data-testid[contains(.,'modal.overlay')]]";
	private static String XPathModalInfoArticulo = "//*[@aria-describedBy='modal-content-description']";
	
	private static String XPathAspaForClose = "//*[@data-testid[contains(.,'modal.close')]]";
	//private static String XPathBuscarTallaTiendaButton = "//div[@id='findInShop']";
	//private static String XPathContainerItem = "//*[@data-testid[contains(.,'modal.container')]]";
	private static String XPathContainerDescription = "//*[@data-testid[contains(.,'modal.content')]]";
	private static String XPathReferencia = XPathContainerDescription + "//div/div[3]";
	private static String XPathNombre = XPathContainerDescription + "//div[@class[contains(.,'sg-headline')]]";
	private static String XPathPrecio = XPathContainerDescription + "//*[@data-testid[contains(.,'product.paidPrice')]]";
	
	public ModalDetalleArticuloDesktop(WebDriver driver) {
		super(driver);
	}
	
	@Override
	public boolean isVisible(int maxSeconds) {
		return (state(Visible, By.xpath(XPathModalInfoArticulo)).wait(maxSeconds).check());
	}
	@Override
	public void clickAspaForClose() {
		driver.findElement(By.xpath(XPathAspaForClose)).click();
	}
	@Override
	public String getReferencia() {
		return (driver.findElement(By.xpath(XPathReferencia)).getText().replaceAll("\\D+",""));
	}
	@Override
	public String getNombre() {
		return (driver.findElement(By.xpath(XPathNombre)).getText());
	}
	@Override
	public String getPrecio() {
		return (driver.findElement(By.xpath(XPathPrecio)).getText());
	}
	@Override
	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = driver.findElement(By.xpath(XPathReferencia)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}

	public boolean isInvisible(int maxSeconds) {
		return (state(Invisible, By.xpath(XPathModalInfoArticulo)).wait(maxSeconds).check());
	}
	

//	public void clickBuscarTallaTiendaButton() {
//		click(By.xpath(XPathBuscarTallaTiendaButton)).waitLoadPage(3).exec();
//	}
}
