package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.Optional;

import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktopNormal;
import com.mng.robotest.testslegacy.data.Constantes;

public class SecColoresArticuloDesktopNormal extends SecColoresArticuloDesktop {

	private static final String XP_ARTICULO = PageGaleriaDesktopNormal.XP_ARTICULO;
	private static final String XP_ARTICULO_ANCESTOR = XP_ARTICULO.replaceFirst("//", "ancestor::"); 
	private static final String XP_COLORS_ARTICLE = "//div[@class[contains(.,'product-colors')]]";
	private static final String XP_COLOR_ICON = XP_COLORS_ARTICLE + "//img[@loading='lazy']";
	
	private String getXPathArticuloConColores() {
		return (
			XP_COLORS_ARTICLE + "/" + 
			XP_ARTICULO_ANCESTOR);
	}
	
	private String getXPathArticuloConVariedadColores(int numArticulo) {
		return ("(" + getXPathArticuloConColores() + ")" + "[" + numArticulo + "]");
	}
	
	private String getXPathImgColorRelativeArticle(boolean selected) {
		if (!selected) {
			return XP_COLOR_ICON;
		}
		return "//button[@aria-label[contains(.,'colorSelected')]]" + XP_COLOR_ICON;
	}

	private String getXPathImgCodigoColor(String codigoColor) {
		return XP_COLOR_ICON + "//self::*[@src[contains(.,'_" + codigoColor + "_')]]";
	}
	
	@Override
	public String getNameColorFromCodigo(String codigoColor) {
		String xpathImgColor = getXPathImgCodigoColor(codigoColor);
		if (!state(PRESENT, xpathImgColor).check()) {
			return Constantes.COLOR_DESCONOCIDO;
		}
		
		var imgColorWeb = getElement(xpathImgColor);
		return imgColorWeb.getAttribute("alt");
	}
	
	@Override
	public Optional<WebElement> getArticuloConVariedadColores(int numArticulo) {
		String xpathArticulo = getXPathArticuloConVariedadColores(numArticulo);
		for (int i=0; i<8; i++) {
			if (state(PRESENT, xpathArticulo).check()) {
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
