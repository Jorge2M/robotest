package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

public class PageOrdenacionDePrendas {
	
    public enum Orden implements ElementPage {
    	titulo("ORDENADOR DE PRENDAS"),
    	initialTitulo("//td[@class='txt11B' and text()[contains(.,'ORDENADOR DE PRENDAS')]]"),
    	desplegableTiendas("//select[@id[contains(.,'host')]]"),
    	verTiendas("//input[@id[contains(.,'j_id_2f')]]"),
    	selectorOrdenacion("//select[@name[contains(.,'secciones')]]"),
    	selectorPrendas("//select[@id[contains(.,'menus')]]"),
    	verPrendas("//input[@value[contains(.,'Ver Prendas')]]"),
    	pruebaImagen("//ul[@id='grupo_0']"),
    	pruebaCamisa("//p[@class='mensaje10' and text()[contains(.,'camisas_she')]]"),
    	bajarPrenda("//a[@id='idDos']"),
    	primeraPrenda("//ul[@id='grupo_0']//li[1]"),
    	segundaPrenda("//ul[@id='grupo_0']//li[2]"),
    	aplicarOrden("//button[@onclick[contains(.,'aplicarOrden')]]");
    	
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
		container("//div[@id='aplicarOrden']"),
		applyGeneric("//input[@value='Aplicar orden genérico']"),
		applyCountry("//input[@value='Aplicar orden país']"),
		cancel("//button[@id='cancelarAplicar']");
		
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
		Violeta("//input[@src[contains(.,'cabecera_mango_violeta')]]");
		
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
