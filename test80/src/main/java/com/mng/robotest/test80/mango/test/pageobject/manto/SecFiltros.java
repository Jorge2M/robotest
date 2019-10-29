package com.mng.robotest.test80.mango.test.pageobject.manto;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.webdriverwrapper.WebdrvWrapp;


public class SecFiltros extends WebdrvWrapp {

    final static String XPathFiltroCodPedido = "//input[@id[contains(.,':id')]]";
    final static String XPathFiltroCodPais = "//input[@id[contains(.,'pais')]]";
    final static String XPathFiltroFDesde = "//input[@id[contains(.,'desde')]]";
    final static String XPathFiltroFHasta = "//input[@id[contains(.,'hasta')]]";
    final static String XPathFiltroImporteTotal = "//input[@id[contains(.,':total')]]";
    final static String XPathButtonBuscar = "//input[@value='Buscar']";
    
	final static DateTimeFormatter formatDate = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public static void setFiltroCodPedido(String codigoPedidoManto, WebDriver driver) {
        driver.findElement(By.xpath(XPathFiltroCodPedido)).clear();
        driver.findElement(By.xpath(XPathFiltroCodPedido)).sendKeys(codigoPedidoManto);
    }
    
    public static void setFiltroFDesde(LocalDate fechaDesde, WebDriver driver) {
    	String fechaDesdeInput = fechaDesde.format(formatDate);
        driver.findElement(By.xpath(XPathFiltroFDesde)).clear();
        driver.findElement(By.xpath(XPathFiltroFDesde)).sendKeys(fechaDesdeInput);
    }
    
    public static void setFiltroImporteTotal(String importeTotal, WebDriver driver) {
        driver.findElement(By.xpath(XPathFiltroImporteTotal)).clear();
        driver.findElement(By.xpath(XPathFiltroImporteTotal)).sendKeys(importeTotal);
    }
    
    public static void setFiltroFHasta(WebDriver driver, String fechaHasta) {
        driver.findElement(By.xpath(XPathFiltroFHasta)).clear();
        driver.findElement(By.xpath(XPathFiltroFHasta)).sendKeys(fechaHasta);
    }
    
    public static void setFiltroCodPaisIfExists(WebDriver driver, String codigoPais) {
        if (isElementPresent(driver, By.xpath(XPathFiltroCodPais))) {
            driver.findElement(By.xpath(XPathFiltroCodPais)).clear();
            driver.findElement(By.xpath(XPathFiltroCodPais)).sendKeys(codigoPais);
        }
    }
    
    public static String getFechaDesdeValue(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathFiltroFDesde)).getAttribute("value"));
    }
    
    public static LocalDate getFechaHastaValue(WebDriver driver) {
    	String fechaHastaScreen = driver.findElement(By.xpath(XPathFiltroFHasta)).getAttribute("value");
    	return LocalDate.parse(fechaHastaScreen, formatDate);
    }
    
    public static void clickButtonBuscar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonBuscar));
    }
}
