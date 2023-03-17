package com.mng.robotest.domains.compra.payments.tmango.pageobjects;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.base.PageBase;

public class PageRedsysSim extends PageBase {

	public enum OptionRedSys {
		AUTENTICACION_CON_EXITO("AUTENTICADA"),
		DENEGAR_AUTENTICACION("NO_AUTENTICADA"),
		TARJETA_NO_REGISTRADA_FINANET("NO_FINANET");
		
		private String value;
		private OptionRedSys(String value) {
			this.value = value;
		}
		public String getValue() {
			return value;
		}
		public String getNombre() {
			return name().replace("_", "");
		}
	}
	
	private static final String XPATH_BUTTON_ENVIAR = "//input[@id='boton']";
	
	private String getXPathOption(OptionRedSys option) {
		return "//input[@value='" + option.getValue() + "']";
	}
	
	public boolean isPage() {
		String xpathOption = getXPathOption(OptionRedSys.AUTENTICACION_CON_EXITO);
		return state(State.Present, xpathOption).check();
	}
	
	public void selectOption(OptionRedSys option) {
		click(getXPathOption(option)).exec();
	}
	
	public void clickEnviar() {
		click(XPATH_BUTTON_ENVIAR).exec();
	}
	
}
