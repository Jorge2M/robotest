package com.mng.robotest.test.pageobject.shop.miscompras;

import org.openqa.selenium.By;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class ModalDetalleArticuloDesktop extends ModalDetalleArticulo {

	private static final String XPATH_MODAL_INFO_ARTICULO = "//*[@aria-describedBy='modal-content-description']";
	private static final String XPATH_ASPA_FOR_CLOSE = "//*[@data-testid[contains(.,'modal.close')]]";
	private static final String XPATH_CONTAINER_DESCRIPTION = "//*[@data-testid[contains(.,'modal.content')]]";
	private static final String XPATH_REFERENCIA = XPATH_CONTAINER_DESCRIPTION + "//div/div[3]/div";
	private static final String XPATH_NOMBRE = XPATH_CONTAINER_DESCRIPTION + "//div[@class[contains(.,'sg-headline')]]";
	private static final String XPATH_PRECIO = XPATH_CONTAINER_DESCRIPTION + "//*[@data-testid[contains(.,'product.paidPrice')]]";
	
	@Override
	public boolean isVisible(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_MODAL_INFO_ARTICULO)).wait(maxSeconds).check());
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

	public boolean isInvisible(int maxSeconds) {
		return (state(Invisible, By.xpath(XPATH_MODAL_INFO_ARTICULO)).wait(maxSeconds).check());
	}
	
}
