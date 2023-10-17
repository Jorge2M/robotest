package com.mng.robotest.tests.domains.galeria.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

public class PageGaleriaDesktopKondo extends PageGaleriaDesktop {

	private static final String XPATH_CAPA_ARTICULO = "//div[@data-testid='plp.product.figure']";
	public static final String XPATH_ARTICULO = XPATH_CAPA_ARTICULO + "/..";
	
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
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@aria-label='" + typeSlider.getKondo() + "']";
	}	
	
	@Override
	public String getRefArticulo(WebElement articulo) {
		return getReference(articulo).substring(0, 8); 
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
		return getReference(articulo).replace(":", "");
	}	
	
	@Override
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		String xpathImg = getXPathImgArticulo();
		if (state(Visible, articulo).by(By.xpath(xpathImg)).wait(1).check()) {
			return getElement(articulo, "." + xpathImg);
		}
		return null;
	}
	
	@Override
	public void clickSlider(WebElement articulo, TypeSlider typeSlider) {
		String xpathSlider = getXPathSliderRelativeToArticle(typeSlider);
		waitMillis(500);
		click(articulo).by(By.xpath("." + xpathSlider)).exec();		
	}	
	
	//TODO Galería Kondo React (13-10)
	private String getXPathImgArticulo() {
		return "//img[@class[contains(.,'xGkIb')]]";
	}	
	
	private String getReference(WebElement articulo) {
		return getElement(articulo, "." + XPATH_CAPA_ARTICULO).getAttribute("reference");
	}
	
}
