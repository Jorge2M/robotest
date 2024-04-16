package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.NumColumnas;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;

public class CommonGaleriaGenesis extends PageBase {

	private static final String XP_LISTA_ARTICULOS = "//*[@data-testid[contains(.,'plp.products.list')]]//ul";
	public static final String XP_ARTICULO = XP_LISTA_ARTICULOS + "//li[@data-slot]";
	private static final String XP_ANCESTOR_ARTICLE = "//ancestor::li[@data-slot]/div";
	private static final String XP_IMAGE_ARTICLE = "//img[@data-testid[contains(.,'plp.product-slot')]]";
	private static final String XP_LINK_2_COLUMNAS = "//*[@data-testid='column-selector-2']";
	private static final String XP_LINK_4_COLUMNAS = "//*[@data-testid='column-selector-4']";
	private static final String XP_ICONO_UP_GALERY = "//button[@aria-label='plp.catalog.scroll-to-top']";

	public String getXPathArticulo() {
		return XP_ARTICULO;
	}
	
	public String getXPathArticuloAncestor() {
		return XP_ANCESTOR_ARTICLE;
	}	
	
	public String getXPathIconUpGalery() {
		return XP_ICONO_UP_GALERY;
	}
	
	public String getXPathNombreRelativeToArticle() {
		return new CommonGaleriaNormal().getXPathNombreRelativeToArticle();
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@data-testid[contains(.,'slideshow" + typeSlider.getGenesis() + "')]";
	}	

	private String getXPathLinkNumColumnas(NumColumnas numColumnas) {
		if (numColumnas==NumColumnas.DOS) {
			return XP_LINK_2_COLUMNAS;
		}
		return XP_LINK_4_COLUMNAS;
	}
	
	public String getRefArticulo(WebElement articulo) {
		return getReference(articulo).substring(0, 8); 
	}	
	
	public String getNombreArticulo(WebElement articulo) {
		return new CommonGaleriaNormal().getNombreArticulo(articulo);
	}
	
	public String getRefColorArticulo(WebElement articulo) {
		return getReference(articulo).replace(":", "");
	}	
	
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		if (state(PRESENT, articulo).by(By.xpath("." + XP_IMAGE_ARTICLE)).wait(1).check()) {
			return getElement(articulo, "." + XP_IMAGE_ARTICLE);
		}
		return null;
	}
	
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath("." + xpathSlider)).exec();		
	}	
	
	public void clickLinkColumnas(NumColumnas numColumnas) {
		String xpathLink = getXPathLinkNumColumnas(numColumnas);
		click(xpathLink).exec();
	}
	
	public int getLayoutNumColumnas() {
		String xpathLink2 = getXPathLinkNumColumnas(NumColumnas.DOS);
		var elemLink2 = getElement(xpathLink2);
		if (elemLink2!=null && elemLink2.getAttribute("class").contains("current")) {
			return 2;
		}
		return 4;
	}
	
	private String getReference(WebElement articulo) {
		return articulo.getAttribute("data-slot");
	}

}
