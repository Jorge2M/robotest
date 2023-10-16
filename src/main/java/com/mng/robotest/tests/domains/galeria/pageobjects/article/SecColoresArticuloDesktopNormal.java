package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktopNormal;
import com.mng.robotest.testslegacy.data.Constantes;

public class SecColoresArticuloDesktopNormal extends SecColoresArticuloDesktop {

	private static final String XPATH_ARTICULO = PageGaleriaDesktopNormal.XPATH_ARTICULO;
	private static final String XPATH_ARTICULO_ANCESTOR = XPATH_ARTICULO.replaceFirst("//", "ancestor::"); 
	private static final String XPATH_COLORS_ARTICLE = "//div[@class[contains(.,'product-colors')]]";
	private static final String XPATH_COLOR_ICON = XPATH_COLORS_ARTICLE + "//img[@loading='lazy']";
	
	private String getXPathArticuloConColores() {
		return (
			XPATH_COLORS_ARTICLE + "/" + 
			XPATH_ARTICULO_ANCESTOR);
	}
	
	private String getXPathArticuloConVariedadColores(int numArticulo) {
		return ("(" + getXPathArticuloConColores() + ")" + "[" + numArticulo + "]");
	}
	
	private String getXPathImgColorRelativeArticle(boolean selected) {
		if (!selected) {
			return XPATH_COLOR_ICON;
		}
		return "//button[@aria-label[contains(.,'colorSelected')]]" + XPATH_COLOR_ICON;
	}

	private String getXPathImgCodigoColor(String codigoColor) {
		return XPATH_COLOR_ICON + "//self::*[@src[contains(.,'_" + codigoColor + "_')]]";
	}
	
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		String xpathImgColor = getXPathImgCodigoColor(codigoColor);
		if (!state(Present, xpathImgColor).check()) {
			return Constantes.COLOR_DESCONOCIDO;
		}
		
		var imgColorWeb = getElement(xpathImgColor);
		return imgColorWeb.getAttribute("alt");
	}
	
	@Override
	public Optional<WebElement> getArticuloConVariedadColores(int numArticulo) {
		String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
		for (int i=0; i<8; i++) {
			if (state(Present, xpathArticulo).check()) {
				return Optional.of(getElement(xpathArticulo));
			}
			scrollVertical(1000);
			waitMillis(1000);
		}
		return Optional.empty();
	}
	
	@Override
	public void clickColorArticulo(WebElement articulo, int posColor) {
		String xpathImgColorRelArticle = getXPathImgColorRelativeArticle(false);
		getElements(articulo, xpathImgColorRelArticle).get(posColor-1).click();
	}
	
}
