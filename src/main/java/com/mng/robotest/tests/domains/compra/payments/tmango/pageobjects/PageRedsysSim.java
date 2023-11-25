package com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.compra.payments.tmango.pageobjects.PageRedsysSim.OptionRedSys.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class PageRedsysSim extends PageBase {

	public enum OptionRedSys {
		AUTENTICACION_CON_EXITO("1"),
		DENEGAR_AUTENTICACION("2"),
		ATTEMPT("3");
		
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
	
	private static final String XP_BUTTON_ENVIAR = "//input[@id='boton']";
	
	public boolean isPage(int seconds) {
		return state(Present, AUTENTICACION_CON_EXITO.getXPath()).wait(seconds).check();
	}
	
	public void selectOption(OptionRedSys option) {
		click(option.getXPath()).exec();
	}
	
	public void clickEnviar() {
		click(XP_BUTTON_ENVIAR).exec();
	}
	
}
