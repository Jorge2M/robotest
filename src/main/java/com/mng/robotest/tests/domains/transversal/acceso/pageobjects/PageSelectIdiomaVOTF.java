package com.mng.robotest.tests.domains.transversal.acceso.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.CodIdioma;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageSelectIdiomaVOTF extends PageBase {

	private static final String XP_SELECT_IDIOMA = "//select[@name[contains(.,'country')]]";
	private static final String XP_BUTTON_ACEPTAR = "//span[@class[contains(.,'button submit')]]";
	
	public void selectIdioma(CodIdioma codigoIdioma) {
		new Select(getElement(XP_SELECT_IDIOMA)).selectByValue(codigoIdioma.toString());
	}

	public void clickButtonAceptar() {
		click(XP_BUTTON_ACEPTAR).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonAceptar()) {
			click(XP_BUTTON_ACEPTAR).exec();
		}
	}
	
	public boolean isVisibleButtonAceptar() {
		return state(VISIBLE, XP_BUTTON_ACEPTAR).check();
	}
}
