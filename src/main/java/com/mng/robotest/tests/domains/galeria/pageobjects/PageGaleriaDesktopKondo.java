package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class PageGaleriaDesktopKondo extends PageGaleriaDesktop {

	private static final String XPATH_CAPA_ARTICULO = "//div[@data-testid='plp.product.figure']";
	private static final String XPATH_ARTICULO = XPATH_CAPA_ARTICULO + "/..";
	
	//TODO Galería Kondo -> Solicitar React (11-10-2023)
	private static final String XPATH_NOMBRE_RELATIVE_TO_ARTICLE = "//div[@class[contains(.,'md12')]]";
	
	public PageGaleriaDesktopKondo() {
		super();
	}
	
	public PageGaleriaDesktopKondo(From from) {
		super(from);
	}

	@Override
	protected String getXPathArticulo() {
		return XPATH_ARTICULO;
	}
	
	@Override
	protected String getXPathNombreRelativeToArticle() {
		return XPATH_NOMBRE_RELATIVE_TO_ARTICLE;
	}
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		return getReference().substring(0, 8); 
	}	
	
	@Override
	public String getNombreArticulo(WebElement articulo) {
		String xpathNameArticle = "." + getXPathNombreRelativeToArticle();
		if (state(Visible, articulo).by(By.xpath(xpathNameArticle)).check()) {
			return articulo.findElement(By.xpath(xpathNameArticle)).getText();
		} else {
			return "Not Found"; 
		}
	}
	
	@Override
	public String getRefColorArticulo(WebElement articulo) {
		return getReference().replace(":", "");
	}	
	
	private String getReference() {
		return getElement(XPATH_CAPA_ARTICULO).getAttribute("reference");
	}
	
}
