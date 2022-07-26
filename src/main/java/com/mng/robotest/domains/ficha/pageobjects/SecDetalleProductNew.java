package com.mng.robotest.domains.ficha.pageobjects;

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

	public enum ItemBreadcrumb { LINEA, GRUPO, GALERIA }
	
	private static final String XPATH_WRAPPER = "//div[@class='product-info-wrapper']";
	private static final String XPATH_CAPA_DESCRIPTION = "//div[@class='product-info-block'][1]";
	private static final String XPATH_BREAD_CRUMBS = XPATH_CAPA_DESCRIPTION + "//ol[@class='breadcrumbs']";
	private static final String XPATH_BREAD_CRUMB_ITEM_LINK = XPATH_BREAD_CRUMBS + "//a[@class='breadcrumbs-link']";
	private static final String XPATH_CAPA_COMPOSICION_Y_LAVADO = "//div[@class='product-info-block'][2]";
	private static final String XPATH_ICON_COMP_Y_LAVADO = XPATH_CAPA_COMPOSICION_Y_LAVADO + "//img[@class='product-info-icon']";
	private static final String XPATH_BLOCK_KC_SAFETY = XPATH_WRAPPER + "//div[@id='KoreaKC']";
	
	public static String getXPathBreadcrumbItem(int position) {
		return "(" + XPATH_BREAD_CRUMB_ITEM_LINK + ")[" + position + "]";
	}
	
	public static String getXPathBreadcrumbItemLink(ItemBreadcrumb itemBc) {
		switch (itemBc) {
		case LINEA:
			return getXPathBreadcrumbItem(1); 
		case GRUPO:
			return getXPathBreadcrumbItem(2);
		case GALERIA:
		default:
			return getXPathBreadcrumbItem(3);
		}
	}
	
	public static boolean isVisibleUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_WRAPPER), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isVisibleBreadcrumbs(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_BREAD_CRUMBS), driver)
				.wait(maxSeconds).check());
	}
	
	public static boolean isVisibleItemBreadCrumb(ItemBreadcrumb itemBCrumb, WebDriver driver) {
		String xpathItem = getXPathBreadcrumbItemLink(itemBCrumb);
		return (state(Visible, By.xpath(xpathItem), driver).check());
	}
	
	public static boolean isVisibleBlockKcSafety(WebDriver driver) {
		return (state(Visible, By.xpath(XPATH_BLOCK_KC_SAFETY), driver).check());
	}
	
}
