package com.mng.robotest.test80.mango.test.pageobject.shop.ficha;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;

/**
 * SectionObject de la ficha nueva correspondiente al bloque al que se scrolla cuando se selecciona el link "Detalle del producto"
 * y que contiene los apartados "Descripción" y "Composición y lavado"
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class SecDetalleProductNew extends WebdrvWrapp {

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
    
    public static boolean isVisibleUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathWrapper), maxSecondsToWait));
    }
    
    public static boolean isVisibleBreadcrumbs(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathBreadCrumbs), maxSecondsToWait));
    }
    
    public static boolean isVisibleItemBreadCrumb(ItemBreadcrumb itemBCrumb, WebDriver driver) {
        String xpathItem = getXPathBreadcrumbItemLink(itemBCrumb);
        return (isElementVisible(driver, By.xpath(xpathItem)));
    }
    
    public static boolean isVisibleBlockKcSafety(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathBlockKcSafety)));
    }
    
}
