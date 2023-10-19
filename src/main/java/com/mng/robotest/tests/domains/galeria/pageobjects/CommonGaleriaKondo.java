package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.entities.TypeSlider;

public class CommonGaleriaKondo extends PageBase {

	private static final String XPATH_CAPA_ARTICULO = "//div[@data-testid='plp.product.figure']";
	public static final String XPATH_ARTICULO = XPATH_CAPA_ARTICULO + "/..";
	
	//TODO Galería Kondo -> Solicitar React (11-10-2023)
	private static final String XPATH_NOMBRE_RELATIVE_TO_ARTICLE_DESKTOP = "//div[@class[contains(.,'md12')]]";
	private static final String XPATH_NOMBRE_RELATIVE_TO_ARTICLE_DEVICE = "//p[@class[contains(.,'hYlwk')]]/span[2]";

	public String getXPathArticulo() {
		return XPATH_ARTICULO;
	}
	
	public String getXPathNombreRelativeToArticle() {
		if (channel.isDevice()) {
			return XPATH_NOMBRE_RELATIVE_TO_ARTICLE_DEVICE;
		}
		return XPATH_NOMBRE_RELATIVE_TO_ARTICLE_DESKTOP;
	}
	
	private String getXPathSliderRelativeToArticle(TypeSlider typeSlider) {
		return "//button[@aria-label='" + typeSlider.getKondo() + "']";
	}	
	
	public String getRefArticulo(WebElement articulo) {
		return getReference(articulo).substring(0, 8); 
	}	
	
	public String getNombreArticulo(WebElement articulo) {
		String xpathNameArticle = "." + getXPathNombreRelativeToArticle();
		if (state(Visible, articulo).by(By.xpath(xpathNameArticle)).check()) {
			return getElement(articulo, xpathNameArticle).getText();
		} else {
			return "Not Found"; 
		}
	}
	
	public String getRefColorArticulo(WebElement articulo) {
		return getReference(articulo).replace(":", "");
	}	
	
	public WebElement getImagenElementArticulo(WebElement articulo) {
		moveToElement(articulo);
		String xpathImg = getXPathImgArticulo();
		if (state(Visible, articulo).by(By.xpath(xpathImg)).wait(1).check()) {
			return getElement(articulo, "." + xpathImg);
		}
		return null;
	}
	
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
