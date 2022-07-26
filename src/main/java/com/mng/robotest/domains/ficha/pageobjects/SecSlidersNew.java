package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecSlidersNew {

	private static final String XPATH_COMPLETA_TU_LOOK = "//div[@id='lookTotal']";
	private static final String XPATH_ELEGIDO_PARA_TI = "//div[@id='recommendations']";
	private static final String XPATH_LO_ULTIMO_VISTO = "//div[@id='garments']";
	private static final String RELATIVE_XPATH_ARTICLE = "//div[@class[contains(.,'slider-module-product')] and @data-id]";

	public static String getXPath(Slider sliderType) {
		switch (sliderType) {
		case COMPLETA_TU_LOOK:
			return XPATH_COMPLETA_TU_LOOK;
		case ELEGIDO_PARA_TI:
			return XPATH_ELEGIDO_PARA_TI;
		case LO_ULTIMO_VISTO:
		default:
			return XPATH_LO_ULTIMO_VISTO;
		}
	}
	
	public static String getXPathCabecera(Slider sliderType) {
		String xpathSlider = getXPath(sliderType);
		return (xpathSlider + "/span");
	}
	
	public static String getXPathArticle(Slider sliderType) {
		String xpathSlider = getXPath(sliderType);
		return xpathSlider + RELATIVE_XPATH_ARTICLE;
	}
	
	public static boolean isVisible(Slider sliderType, WebDriver driver) {
		String xpathSlider = getXPath(sliderType);
		return (state(Visible, By.xpath(xpathSlider), driver).check());
	}
	
	public static int getNumVisibleArticles(Slider sliderType, WebDriver driver) {
		String xpathArticle = getXPathArticle(sliderType);
		return (getNumElementsVisible(driver, By.xpath(xpathArticle)));
	}
}
