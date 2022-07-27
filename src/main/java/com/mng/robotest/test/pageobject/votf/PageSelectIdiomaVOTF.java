package com.mng.robotest.test.pageobject.votf;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.data.CodIdioma;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageSelectIdiomaVOTF extends PageObjTM {

	private static final String XPATH_SELECT_IDIOMA = "//select[@name[contains(.,'country')]]";
	private static final String XPATH_BUTTON_ACEPTAR = "//span[@class[contains(.,'button submit')]]";
	
	public PageSelectIdiomaVOTF(WebDriver driver) {
		super(driver);
	}
	
	public void selectIdioma(CodIdioma codigoIdioma) {
		new Select(driver.findElement(By.xpath(XPATH_SELECT_IDIOMA))).selectByValue(codigoIdioma.toString());
	}

	public void clickButtonAceptar() {
		click(By.xpath(XPATH_BUTTON_ACEPTAR)).exec();
		
		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleButtonAceptar()) {
			click(By.xpath(XPATH_BUTTON_ACEPTAR)).exec();
		}
	}
	
	public boolean isVisibleButtonAceptar() {
		return (state(Visible, By.xpath(XPATH_BUTTON_ACEPTAR)).check());
	}
}
