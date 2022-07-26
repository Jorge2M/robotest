package com.mng.robotest.domains.ficha.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecSlidersOld {
	
	private static final String XPATH_ELEGIDO_PARA_TI = "//div[@class='recommendations']";
	private static final String XPATH_ELEGIDO_PARA_TI_CABECERA = XPATH_ELEGIDO_PARA_TI + "/p";
	private static final String XPATH_ART_ELEGIDO_PARA_TI = XPATH_ELEGIDO_PARA_TI + "//div[@class[contains(.,'recommendations-product')]]";
	private static final String XPATH_COMPLETA_TU_LOOK = "//div[@class='total-look']";
	private static final String XPATH_COMPLETA_TU_LOOK_CABECERAR = XPATH_COMPLETA_TU_LOOK + "/span";
	private static final String XPATH_ART_COMPLETA_TU_LOOK = XPATH_COMPLETA_TU_LOOK + "//div[@class[contains(.,'total-look-product')]]";
	private static final String XPATH_LO_ULTIMO_VISTO = "//div[@class='last-viewed']";
	private static final String XPATH_LO_ULTIMO_VISTO_CABECERA = XPATH_LO_ULTIMO_VISTO + "/span";
	private static final String XPART_ART_LO_ULTIMO_VISTO = XPATH_LO_ULTIMO_VISTO + "//div[@class[contains(.,'last-viewed-product')]]";

	private SecSlidersOld() {}
	
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
		switch (sliderType) {
		case COMPLETA_TU_LOOK:
			return XPATH_COMPLETA_TU_LOOK_CABECERAR;
		case ELEGIDO_PARA_TI:
			return XPATH_ELEGIDO_PARA_TI_CABECERA;
		case LO_ULTIMO_VISTO:
		default:
			return XPATH_LO_ULTIMO_VISTO_CABECERA;
		}
	}
	
	public static String getXPathArticle(Slider sliderType) {
		switch (sliderType) {
		case COMPLETA_TU_LOOK:
			return XPATH_ART_COMPLETA_TU_LOOK;
		case ELEGIDO_PARA_TI:
			return XPATH_ART_ELEGIDO_PARA_TI;
		case LO_ULTIMO_VISTO:
		default:
			return XPART_ART_LO_ULTIMO_VISTO;
		}
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
