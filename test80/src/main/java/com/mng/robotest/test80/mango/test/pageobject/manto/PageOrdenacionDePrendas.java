package com.mng.robotest.test80.mango.test.pageobject.manto;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.testmaker.service.webdriver.pageobject.WebdrvWrapp;

public class PageOrdenacionDePrendas extends WebdrvWrapp {
	
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
    	
    	Orden(String xpath) {
    		this.xpath = xpath;
    	}
    	
    	public String getXPath() {
    		return this.xpath;
    	}

		public String getXPath(Channel channel) {
			return xpath;
		}
	}
    
	public enum Modal implements ElementPage {
		container("//div[@id='aplicarOrden']"),
		applyGeneric("//input[@value='Aplicar orden genérico']"),
		applyCountry("//input[@value='Aplicar orden país']"),
		cancel("//button[@id='cancelarAplicar']");
		
		String element;
		
		Modal(String element) {
			this.element = element;
		}
		
		public String getXPath() {
			return this.element;
		}

		public String getXPath(Channel channel) {
			return element;
		}
	}
	
	public enum Section implements ElementPage {
		She("//input[@src[contains(.,'cabecera_mango_she')]]"),
		He("//input[@src[contains(.,'cabecera_mango_he')]]"),
		Nina("//input[@src[contains(.,'cabecera_mango_nina')]]"),
		Nino("//input[@src[contains(.,'cabecera_mango_nino')]]"),
		Violeta("//input[@src[contains(.,'cabecera_mango_violeta')]]");
		
		String element;
		
		Section(String element) {
			this.element = element;
		}
		
		public String getXPath() {
			return this.element;
		}

		public String getXPath(Channel channel) {
			return element;
		}
	}

}
