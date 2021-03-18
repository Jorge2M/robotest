package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * SectionObject de la ficha nueva correspondiente al bloque al que se scrolla cuando se selecciona el link "Detalle del producto"
 * y que contiene los apartados "Descripción" y "Composición y lavado"
 * @author jorge.munoz
 *
 */

public class SecDetalleProductNew {

    public enum ItemBreadcrumb {linea, grupo, galeria}
    
    static String XPathWrapper = "//div[@class='product-info-wrapper']";
    static String XPathCapaDescription = "//div[@class='product-info-block'][1]";
    static String XPathBreadCrumbs = XPathCapaDescription + "//ol[@class='breadcrumbs']";
    static String XPathBreadCrumbItemLink = XPathBreadCrumbs + "//a[@class='breadcrumbs-link']";
    static String XPathCapaComposicionYlavado = "//div[@class='product-info-block'][2]";
    static String XPathIconCompYlavado = XPathCapaComposicionYlavado + "//img[@class='product-info-icon']";
    static String XPathBlockKcSafety = XPathWrapper + "//div[@id='KoreaKC']";
    
    public static String getXPathBreadcrumbItem(int position) {
        return "(" + XPathBreadCrumbItemLink + ")[" + position + "]";
    }
    
    public static String getXPathBreadcrumbItemLink(ItemBreadcrumb itemBc) {
        switch (itemBc) {
        case linea:
            return getXPathBreadcrumbItem(1); 
        case grupo:
            return getXPathBreadcrumbItem(2);
        case galeria:
        default:
            return getXPathBreadcrumbItem(3);
        }
    }
    
    public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathWrapper), driver)
    			.wait(maxSeconds).check());
    }
    
    public static boolean isVisibleBreadcrumbs(int maxSeconds, WebDriver driver) {
    	return (state(Visible, By.xpath(XPathBreadCrumbs), driver)
    			.wait(maxSeconds).check());
    }
    
    public static boolean isVisibleItemBreadCrumb(ItemBreadcrumb itemBCrumb, WebDriver driver) {
        String xpathItem = getXPathBreadcrumbItemLink(itemBCrumb);
        return (state(Visible, By.xpath(xpathItem), driver).check());
    }
    
    public static boolean isVisibleBlockKcSafety(WebDriver driver) {
    	return (state(Visible, By.xpath(XPathBlockKcSafety), driver).check());
    }
    
}
