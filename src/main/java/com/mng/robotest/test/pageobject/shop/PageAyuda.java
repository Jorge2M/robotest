package com.mng.robotest.test.pageobject.shop;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageAyuda extends PageBase {
	
	private static final String XPATH_CAB_PREGUNTAS_FREQ = "//h1[text()[contains(.,'Preguntas frecuentes')]]";
	private static final String XPATH_FAQ_MOBILE = "//li[@class='leaf']//a[text()[contains(.,'Preguntas frecuentes')]]";

	private String getXPathTelefono(String telefono) {
		return ("//*[@class='text_container']/p[text()[contains(.,'" + telefono + "')]]");
	}

	public boolean isPresentCabPreguntasFreq() {
		if (channel.isDevice()) {
			return state(Present, XPATH_FAQ_MOBILE).check();
		}
		return state(Present, XPATH_CAB_PREGUNTAS_FREQ).check();
	}

	public boolean isPresentTelefono(String telefono) {
		return state(Present, getXPathTelefono(telefono)).check();
	}
}
