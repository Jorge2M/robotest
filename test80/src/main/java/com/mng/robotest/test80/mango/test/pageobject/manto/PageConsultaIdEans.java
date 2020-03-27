package com.mng.robotest.test80.mango.test.pageobject.manto;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

import static com.mng.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class PageConsultaIdEans {
    
	static String XPathTituloPagina = "//form[@id='formTempl']//td[text()[contains(.,'s / EANS')]]";
    static String XPathDivBusquedaExcel = "//div[@id='ExcelContent']";
    static String XPathDivBusquedaRapida = "//div[@id='busquedaRapidaContent']";
    
    static String XPathBotonBuscarDatosContacto = "//input[@value[contains(.,'Buscar datos de contacto')]]";
    static String XPathBotonBuscarIdentificadoresPedido = "//input[@value[contains(.,'Buscar identificadores de pedidos')]]";
    static String XPathBotonBuscarTrackings = "//input[@value[contains(.,'Buscar trackings')]]";
    static String XPathBotonBuscarDatosEan = "//input[@value[contains(.,'Buscar datos EAN')]]";
    
    static String XPathTextAreaBusquedaRapida = "//textarea[@name='form:j_id_1u']";
    
    static String XPathHeaderTablaID = "//table/thead/tr[1]/th[text()[contains(.,'ID')]]";
    //static String iniXPathPedidosTabla = "//tbody[@id='form:j_id_30:tbody_element']/tr/td[text()[contains(.,'";
    static String XPathLineaPedidoTablaID = XPathHeaderTablaID + "/../../../tbody/tr"; 
    
    
  //######################  PARTE DATOS CONTACTO ###################### 
    
    public static String getXPathLineaPedido(String pedido) {
        return (XPathLineaPedidoTablaID + "/td//self::*[text()[contains(.,'" + pedido + "')]]");
    }

	public static boolean isVisibleTituloPagina(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTituloPagina), driver).check());
	}

	public static boolean isVisibleDivBusquedaExcel(WebDriver driver) {	
		return (state(Visible, By.xpath(XPathDivBusquedaExcel), driver).check());
	}

	public static boolean isVisibleDivBusquedaRapida(WebDriver driver) {
		return (state(Visible, By.xpath(XPathDivBusquedaRapida), driver).check());
	}

	public static boolean isVisibleTablaInformacionUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathHeaderTablaID), driver)
				.wait(maxSeconds).check());
	}

	public static void inputPedidosAndClickBuscarDatos(List<String> pedidosPrueba, WebDriver driver) {
	    inputPedidos(pedidosPrueba, driver);
	    clickBuscarDatosContactoButton(driver);
	}
	
	public static void inputPedidos(List<String> pedidosPrueba, WebDriver driver) {
	    driver.findElement(By.xpath(XPathTextAreaBusquedaRapida)).clear();
	    for (String pedido : pedidosPrueba) {
	    	driver.findElement(By.xpath(XPathTextAreaBusquedaRapida)).sendKeys(pedido);
	        driver.findElement(By.xpath(XPathTextAreaBusquedaRapida)).sendKeys(Keys.ENTER);
	    }
	}
	
	public static void clickBuscarDatosContactoButton(WebDriver driver) {
            driver.findElement(By.xpath(XPathBotonBuscarDatosContacto)).click();
	}

	public static int getLineasPedido(WebDriver driver) {
	    return (driver.findElements(By.xpath(XPathLineaPedidoTablaID)).size());
	}

	public static boolean isPedidosTablaCorrecto(List<String> pedidosPrueba, WebDriver driver) {
		for (String pedido : pedidosPrueba) {
			String xpathLineaPedido = getXPathLineaPedido(pedido);
			if (!state(Visible, By.xpath(xpathLineaPedido), driver).check()) {
				return false;
			}
		}
		return true;
	}
	
	
	//######################  PARTE IDENTIFICADORES ###################### 

	public static void inputPedidosAndClickBuscarIdentificadores (List<String> pedidosPrueba, WebDriver driver) {
		inputPedidos(pedidosPrueba, driver);
	    clickBuscarIdentificadoresPedidoButton(driver);
	}
	
	private static void clickBuscarIdentificadoresPedidoButton(WebDriver driver) {
		driver.findElement(By.xpath(XPathBotonBuscarIdentificadoresPedido)).click();
	}
	

	//######################  PARTE TRACKINGS ###################### 

	public static void inputPedidosAndClickBuscarTrackings(List<String> pedidosPrueba, WebDriver driver) {
		inputPedidos(pedidosPrueba, driver);
	    clickBuscarTrackingsButton(driver);
	}
	
	private static void clickBuscarTrackingsButton(WebDriver driver) {
		driver.findElement(By.xpath(XPathBotonBuscarTrackings)).click();
	}

	
	//######################  PARTE EANS ######################
	
	public static void inputArticulosAndClickBuscarDatosEan(List<String> articulosPrueba, WebDriver driver) {
		inputPedidos(articulosPrueba, driver);
		clickBuscarDatosEanButton(driver);
	}

	private static void clickBuscarDatosEanButton(WebDriver driver) {
		driver.findElement(By.xpath(XPathBotonBuscarDatosEan)).click();
	}

	public static boolean isArticulosTablaCorrecto(List<String> articulosPrueba, WebDriver driver) {
		for (String articulo : articulosPrueba) {
			String xpathLineaPedido = getXPathLineaPedido(articulo);
			if (!state(Visible, By.xpath(xpathLineaPedido), driver).check()) {
				return false;
			}
		}
		return true;
	}
	
}
