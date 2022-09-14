package com.mng.robotest.domains.micuenta.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDetalleArticuloDesktop extends ModalDetalleArticulo {

	private static final String XPATH_MODAL_INFO_ARTICULO = "//*[@aria-describedBy='modal-content-description']";
	private static final String XPATH_ASPA_FOR_CLOSE = "//*[@data-testid[contains(.,'modal.close')]]";
	private static final String XPATH_CONTAINER_DESCRIPTION = "//*[@data-testid[contains(.,'modal.content')]]";
	private static final String XPATH_REFERENCIA = XPATH_CONTAINER_DESCRIPTION + "//div/div[3]/div";
	private static final String XPATH_NOMBRE = XPATH_CONTAINER_DESCRIPTION + "//div[@class[contains(.,'sg-headline')]]";
	private static final String XPATH_PRECIO = XPATH_CONTAINER_DESCRIPTION + "//*[@data-testid[contains(.,'product.paidPrice')]]";
	
	@Override
	public boolean isVisible(int seconds) {
		return state(Visible, XPATH_MODAL_INFO_ARTICULO).wait(seconds).check();
	}
	@Override
	public void clickAspaForClose() {
		getElement(XPATH_ASPA_FOR_CLOSE).click();
	}
	@Override
	public String getReferencia() {
		return getElement(XPATH_REFERENCIA).getText().replaceAll("\\D+","");
	}
	@Override
	public String getNombre() {
		return getElement(XPATH_NOMBRE).getText();
	}
	@Override
	public String getPrecio() {
		return getElement(XPATH_PRECIO).getText();
	}
	@Override
	public boolean isReferenciaValidaModal(String idArticulo) {
		String idArticuloModal = getElement(XPATH_REFERENCIA).getText();
		idArticuloModal = idArticuloModal.replace(" ", "");
		return idArticulo.replace(" ", "").equals(idArticuloModal);
	}

	public boolean isInvisible(int seconds) {
		return state(Invisible, XPATH_MODAL_INFO_ARTICULO).wait(seconds).check();
	}
	
}
