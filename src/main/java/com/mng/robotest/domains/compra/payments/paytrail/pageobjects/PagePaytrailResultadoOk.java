package com.mng.robotest.domains.compra.payments.paytrail.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

public class PagePaytrailResultadoOk extends PageBase {
	
	private static final String ACEPTADO_EN_FINLANDES = "hyväksytty";
	private static final String VOLVER_AL_SERVICIO_DEL_VENDEDOR_EN_FINLANDES = "Palaa myyjän palveluun";
	
	private String getXPathVolverAMangoButton() {
		return "//input[@class='button' and @value[contains(.,'" + VOLVER_AL_SERVICIO_DEL_VENDEDOR_EN_FINLANDES + "')]]";
	}
	
	public boolean isPage() {
		return driver.getTitle().toLowerCase().contains(ACEPTADO_EN_FINLANDES);
	}

	public void clickVolverAMangoButton() {
		click(getXPathVolverAMangoButton()).exec();
	}
}
