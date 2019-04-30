package com.mng.robotest.test80.mango.test.pageobject.manto;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;


public class SecFiltros extends WebdrvWrapp {

    final static String XPathFiltroCodPedido = "//input[@id[contains(.,':id')]]";
    final static String XPathFiltroCodPais = "//input[@id[contains(.,'pais')]]";
    final static String XPathFiltroFDesde = "//input[@id[contains(.,'desde')]]";
    final static String XPathFiltroFHasta = "//input[@id[contains(.,'hasta')]]";
    final static String XPathFiltroImporteTotal = "//input[@id[contains(.,':total')]]";
    final static String XPathButtonBuscar = "//input[@value='Buscar']";

    /**
     * Seteo del código de pedido
     */
    public static void setFiltroCodPedido(String codigoPedidoManto, WebDriver driver) {
        driver.findElement(By.xpath(XPathFiltroCodPedido)).clear();
        driver.findElement(By.xpath(XPathFiltroCodPedido)).sendKeys(codigoPedidoManto);
    }
    
    /**
     * Seteo del filtro correspondiente a la fecha desde
     */
    public static void setFiltroFDesde(String fechaDesde, WebDriver driver) {
        driver.findElement(By.xpath(XPathFiltroFDesde)).clear();
        driver.findElement(By.xpath(XPathFiltroFDesde)).sendKeys(fechaDesde);
    }
    
    public static void setFiltroImporteTotal(String importeTotal, WebDriver driver) {
        driver.findElement(By.xpath(XPathFiltroImporteTotal)).clear();
        driver.findElement(By.xpath(XPathFiltroImporteTotal)).sendKeys(importeTotal);
    }
    
    /**
     * Seteo del filtro correspondiente a la fecha hasta
     */
    public static void setFiltroFHasta(WebDriver driver, String fechaHasta) {
        driver.findElement(By.xpath(XPathFiltroFHasta)).clear();
        driver.findElement(By.xpath(XPathFiltroFHasta)).sendKeys(fechaHasta);
    }
    
    /**
     * Seteo del filtro correspondiente al código de país
     */
    public static void setFiltroCodPaisIfExists(WebDriver driver, String codigoPais) {
        if (isElementPresent(driver, By.xpath(XPathFiltroCodPais))) {
            driver.findElement(By.xpath(XPathFiltroCodPais)).clear();
            driver.findElement(By.xpath(XPathFiltroCodPais)).sendKeys(codigoPais);
        }
    }
    
    public static String getFechaDesdeValue(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathFiltroFDesde)).getAttribute("value"));
    }
    
    public static String getFechaHastaValue(WebDriver driver) {
        return (driver.findElement(By.xpath(XPathFiltroFHasta)).getAttribute("value"));
    }
    
    /**
     * Selección del botón "Buscar"
     */
    public static void clickButtonBuscar(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonBuscar));
    }
}
