package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageConsultaTienda extends PageBase {
	
	private static final String XPATH_INPUT_TIENDA = "//input[@id[contains(.,'ID_TIENDA')]]";
	private static final String XPATH_BOTON_BUSCAR = "//input[@value[contains(.,'Buscar tienda')]]";
	private static final String XPATH_TIENDA_NO_EXISTE = "//li[text()[contains(.,'La tienda no existe')]]";
	private static final String XPATH_TIENDA_EXISTE = "//span[text()[contains(.,'ID Tienda')]]";

	public boolean isVisibleInputTienda() {
		return state(Visible, By.xpath(XPATH_INPUT_TIENDA)).check();
	}

	public void introducirTienda(String tiendaNoExistente) {
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA)).click();
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA)).clear();
		driver.findElement(By.xpath(XPATH_INPUT_TIENDA)).sendKeys(tiendaNoExistente);
		driver.findElement(By.xpath(XPATH_BOTON_BUSCAR)).click();
	}

	public boolean apareceMensajeTiendaNoExiste() {
		return (state(Present, By.xpath(XPATH_TIENDA_NO_EXISTE)).check());
	}

	public boolean apareceInformacionTienda() {
		return state(Present, By.xpath(XPATH_TIENDA_EXISTE)).check();
	}

}
