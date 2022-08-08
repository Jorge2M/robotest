package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloMobile extends ModalDetalleArticulo {

	private static String XPATH_ASPA_FOR_CLOSE = "//i[@class[contains(.,'icon-outline-close')]]";
	private static String XPATH_REFERENCIA = "//*[@data-testid='myPurchases.detail.reference']";
	private static String XPATH_NOMBRE = "//div[@class='sg-subtitle']";
	private static String XPATH_PRECIO = "//*[@data-testid[contains(.,'product.paidPrice')]]"; 
	
	@Override
	public boolean isVisible(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_REFERENCIA)).wait(maxSeconds).check());
	}
	@Override
	public boolean isInvisible(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_REFERENCIA)).wait(maxSeconds).check());
	}
	@Override
	public void clickAspaForClose() {
		driver.findElement(By.xpath(XPATH_ASPA_FOR_CLOSE)).click();
	}
	@Override
	public String getReferencia() {
		return (driver.findElement(By.xpath(XPATH_REFERENCIA)).getText().replaceAll("\\D+",""));
	}
	@Override
	public String getNombre() {
		state(State.Visible, By.xpath(XPATH_NOMBRE)).wait(1).check();
		return (driver.findElement(By.xpath(XPATH_NOMBRE)).getText());
	}
	@Override
	public String getPrecio() {
		return (driver.findElement(By.xpath(XPATH_PRECIO)).getText());
	}
	@Override
	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = driver.findElement(By.xpath(XPATH_REFERENCIA)).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}
}
