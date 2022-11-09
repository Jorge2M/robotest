package com.mng.robotest.test.pageobject.manto;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

public class PageOrdenacionDePrendas {
	
	public enum Orden implements ElementPage {
		TITULO("ORDENADOR DE PRENDAS"),
		INITIAL_TITULO("//td[@class='txt11B' and text()[contains(.,'ORDENADOR DE PRENDAS')]]"),
		DESPLEGABLE_TIENDAS("//select[@id[contains(.,'host')]]"),
		VER_TIENDAS("//input[@id[contains(.,'j_id_2f')]]"),
		SELECTOR_ORDENACION("//select[@name[contains(.,'secciones')]]"),
		SELECTOR_PRENDAS("//select[@id[contains(.,'menus')]]"),
		VER_PRENDAS("//input[@value[contains(.,'Ver Prendas')]]"),
		PRUEBA_IMAGEN("//ul[@id='grupo_0']"),
		PRUEBA_CAMISA("//p[@class='mensaje10' and text()[contains(.,'camisas_she')]]"),
		BAJAR_PRENDA("//a[@id='idDos']"),
		PRIMERA_PRENDA("//ul[@id='grupo_0']//li[1]"),
		SEGUNDA_PRENDA("//ul[@id='grupo_0']//li[2]"),
		APLICAR_ORDEN("//button[@onclick[contains(.,'aplicarOrden')]]");
		
		String xpath;
		By by;
		Orden(String xPath) {
			xpath = xPath;
			by = By.xpath(xPath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
		public String getXPath() {
			return xpath;
		}
	}
	
	public enum Modal implements ElementPage {
		CONTAINER("//div[@id='aplicarOrden']"),
		APPLY_GENERIC("//input[@value='Aplicar orden genérico']"),
		APPLY_COUNTRY("//input[@value='Aplicar orden país']"),
		CANCEL("//button[@id='cancelarAplicar']");
		
		By by;
		Modal(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}
	
	public enum Section implements ElementPage {
		She("//input[@src[contains(.,'cabecera_mango_she')]]"),
		He("//input[@src[contains(.,'cabecera_mango_he')]]"),
		Nina("//input[@src[contains(.,'cabecera_mango_nina')]]"),
		Nino("//input[@src[contains(.,'cabecera_mango_nino')]]"),
		Teen("//input[@src[contains(.,'cabecera_mango_teen')]]");
		
		By by;
		Section(String xpath) {
			by = By.xpath(xpath);
		}
		
		@Override
		public By getBy() {
			return by;
		}
	}

}
