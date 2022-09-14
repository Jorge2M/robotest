package com.mng.robotest.domains.micuenta.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class ModalDetalleArticuloMobile extends ModalDetalleArticulo {

	private static final String XPATH_ASPA_FOR_CLOSE = "//i[@class[contains(.,'icon-outline-close')]]";
	private static final String XPATH_REFERENCIA = "//*[@data-testid='myPurchases.detail.reference']";
	private static final String XPATH_NOMBRE = "//div[@class='sg-subtitle']";
	private static final String XPATH_PRECIO = "//*[@data-testid[contains(.,'product.paidPrice')]]"; 
	
	@Override
	public boolean isVisible(int seconds) {
		return state(Visible, XPATH_REFERENCIA).wait(seconds).check();
	}
	@Override
	public boolean isInvisible(int seconds) {
		return state(Invisible, XPATH_REFERENCIA).wait(seconds).check();
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
		state(State.Visible, XPATH_NOMBRE).wait(1).check();
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
}
