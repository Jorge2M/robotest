package com.mng.robotest.domains.ficha.pageobjects;



import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;


/**
 * SectionObject de la ficha nueva correspondiente al bloque al que se scrolla cuando se selecciona el link "Detalle del producto"
 * y que contiene los apartados "Descripción" y "Composición y lavado"
 * @author jorge.munoz
 *
 */

public class SecDetalleProduct extends PageBase {

	public enum ItemBreadcrumb { LINEA, GALERIA, SUBGALERIA }
	
	private static final String XPATH_WRAPPER = "//div[@class='product-info-wrapper']";
	private static final String XPATH_BREAD_CRUMBS = "//ol[@class='breadcrumbs']";
	private static final String XPATH_BREAD_CRUMB_ITEM_LINK = XPATH_BREAD_CRUMBS + "//a[@class='breadcrumbs-link']";
	private static final String XPATH_BLOCK_KC_SAFETY = XPATH_WRAPPER + "//div[@id='KoreaKC']";
	
	private String getXPathBreadcrumbItem(int position) {
		return "(" + XPATH_BREAD_CRUMB_ITEM_LINK + ")[" + position + "]";
	}
	
	private String getXPathBreadcrumbItemLink(ItemBreadcrumb itemBc) {
		switch (itemBc) {
		case LINEA:
			return getXPathBreadcrumbItem(1); 
		case GALERIA:
			return getXPathBreadcrumbItem(2);
		case SUBGALERIA:
		default:
			return getXPathBreadcrumbItem(3);
		}
	}
	
	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XPATH_WRAPPER).wait(seconds).check();
	}
	
	public boolean isVisibleBreadcrumbs(int seconds) {
		return state(Visible, XPATH_BREAD_CRUMBS).wait(seconds).check();
	}
	
	public boolean isVisibleItemBreadCrumb(ItemBreadcrumb itemBCrumb) {
		String xpathItem = getXPathBreadcrumbItemLink(itemBCrumb);
		return state(Visible, xpathItem).check();
	}
	
	public String getUrlItemBreadCrumb(ItemBreadcrumb itemBCrumb) {
		String xpathItem = getXPathBreadcrumbItemLink(itemBCrumb);
		if (state(Visible, xpathItem).check()) {
			return getElement(xpathItem).getAttribute("href");
		}
		return "";
	}	
	
	public boolean isVisibleBlockKcSafety() {
		return state(Visible, XPATH_BLOCK_KC_SAFETY).check();
	}
	
}
