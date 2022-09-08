package com.mng.robotest.test.pageobject.shop.checkout.tmango;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

public class PageRedsysSim extends PageBase {

	public enum OptionRedSys {
		Autenticacion_con_exito("AUTENTICADA"),
		Denegar_autenticacion("NO_AUTENTICADA"),
		Tarjeta_no_registrada_Finanet("NO_FINANET");
		
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
		String xpathOption = getXPathOption(OptionRedSys.Autenticacion_con_exito);
		return state(State.Present, xpathOption).check();
	}
	
	public void selectOption(OptionRedSys option) {
		click(getXPathOption(option)).exec();
	}
	
	public void clickEnviar() {
		click(XPATH_BUTTON_ENVIAR).exec();
	}
	
}
