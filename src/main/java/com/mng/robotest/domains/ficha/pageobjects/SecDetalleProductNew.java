package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * SectionObject de la ficha nueva correspondiente al bloque al que se scrolla cuando se selecciona el link "Detalle del producto"
 * y que contiene los apartados "Descripción" y "Composición y lavado"
 * @author jorge.munoz
 *
 */

public class SecDetalleProductNew extends PageObjTM {

	public enum ItemBreadcrumb { LINEA, GRUPO, GALERIA }
	
	private static final String XPATH_WRAPPER = "//div[@class='product-info-wrapper']";
	private static final String XPATH_CAPA_DESCRIPTION = "//div[@class='product-info-block'][1]";
	private static final String XPATH_BREAD_CRUMBS = XPATH_CAPA_DESCRIPTION + "//ol[@class='breadcrumbs']";
	private static final String XPATH_BREAD_CRUMB_ITEM_LINK = XPATH_BREAD_CRUMBS + "//a[@class='breadcrumbs-link']";
	private static final String XPATH_BLOCK_KC_SAFETY = XPATH_WRAPPER + "//div[@id='KoreaKC']";
	
	private String getXPathBreadcrumbItem(int position) {
		return "(" + XPATH_BREAD_CRUMB_ITEM_LINK + ")[" + position + "]";
	}
	
	private String getXPathBreadcrumbItemLink(ItemBreadcrumb itemBc) {
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
	
	public boolean isVisibleUntil(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_WRAPPER))
				.wait(maxSeconds).check());
	}
	
	public boolean isVisibleBreadcrumbs(int maxSeconds) {
		return (state(Visible, By.xpath(XPATH_BREAD_CRUMBS))
				.wait(maxSeconds).check());
	}
	
	public boolean isVisibleItemBreadCrumb(ItemBreadcrumb itemBCrumb) {
		String xpathItem = getXPathBreadcrumbItemLink(itemBCrumb);
		return (state(Visible, By.xpath(xpathItem)).check());
	}
	
	public boolean isVisibleBlockKcSafety() {
		return (state(Visible, By.xpath(XPATH_BLOCK_KC_SAFETY)).check());
	}
	
}
