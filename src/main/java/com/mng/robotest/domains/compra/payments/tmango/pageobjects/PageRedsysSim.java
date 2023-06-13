package com.mng.robotest.domains.compra.payments.tmango.pageobjects;

import com.mng.robotest.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.domains.compra.payments.tmango.pageobjects.PageRedsysSim.OptionRedSys.*;

public class PageRedsysSim extends PageBase {

	public enum OptionRedSys {
		AUTENTICACION_CON_EXITO("Y"),
		DENEGAR_AUTENTICACION("N"),
		ATTEMPT("A");
		
		private String value;
		private OptionRedSys(String value) {
			this.value = value;
		}
		public String getXPath() {
			return "//input[@value='" + value + "']";
		}
		public String getNombre() {
			return name().replace("_", "");
		}
	}
	
	private static final String XPATH_BUTTON_ENVIAR = "//input[@id='boton']";
	
	public boolean isPage(int seconds) {
		return state(Present, AUTENTICACION_CON_EXITO.getXPath()).wait(seconds).check();
	}
	
	public void selectOption(OptionRedSys option) {
		click(option.getXPath()).exec();
	}
	
	public void clickEnviar() {
		click(XPATH_BUTTON_ENVIAR).exec();
	}
	
}
