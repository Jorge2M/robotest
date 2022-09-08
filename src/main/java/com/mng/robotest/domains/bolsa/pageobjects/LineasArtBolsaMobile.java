package com.mng.robotest.domains.bolsa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test.utils.ImporteScreen;

public class LineasArtBolsaMobile extends LineasArtBolsa {

	private static final String DIV_LINEA = "div[@id[contains(.,'iteradorBolsa')]]/div[@class='comShipment']";
	private static final String XPATH_LINEA = "//" + DIV_LINEA;
	private static final String XPATH_LINK_RELATIVE_ARTICLE = ".//div[@class[contains(.,'sbi-information-content')]]/a";
	private static final String XPATH_LINEA_WITH_TAG_REF = XPATH_LINK_RELATIVE_ARTICLE + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DIV_LINEA;
	private static final String XPATH_NOMBRE_RELATIVE_ARTICLE = ".//span[@id[contains(.,'articuloDescrBolsa')]]";
	private static final String XPATH_COLOR_RELATIVE_ARTICLE = ".//p[@class[contains(.,'sbi-color')]]";
	private static final String XPATH_TALLA_ALF_RELATIVE_ARTICLE = ".//p[@class[contains(.,'sbi-size')]]";
	private static final String XPATH_CANTIDAD_RELATIVE_ARTICLE = ".//p[@class[contains(.,'sbi-quantity')]]";
	private static final String XPATH_PRECIO_RELATIVE_ARTICLE = ".//div[@class[contains(.,'sbi-price-content')]]//span[@style[not(contains(.,'padding'))]]";
	private static final String XPATH_PRECIO_ENTERO_RELATIVE_ARTICLE = XPATH_PRECIO_RELATIVE_ARTICLE + "[1]";
	private static final String XPATH_PRECIO_DECIMAL_RELATIVE_ARTICLE = XPATH_PRECIO_RELATIVE_ARTICLE + "[2]";
	private static final String TAG_REF_ARTICLE = "[TAGREF]";
	private static final String XPATH_LINK_BORRAR_ART = "//*[@id[contains(.,'trashMobile')] and @onclick[contains(.,'" + TAG_REF_ARTICLE + "')]]";
	
	@Override
	String getXPathDataRelativeArticle(DataArtBolsa dataArt) {
		switch (dataArt) {
		case NOMBRE:
			return XPATH_NOMBRE_RELATIVE_ARTICLE;
		case COLOR:
			return XPATH_COLOR_RELATIVE_ARTICLE;
		case TALLA:
			return XPATH_TALLA_ALF_RELATIVE_ARTICLE;
		case CANTIDAD:
			return XPATH_CANTIDAD_RELATIVE_ARTICLE;
		case PRECIO_ENTERO:
			return XPATH_PRECIO_ENTERO_RELATIVE_ARTICLE;
		case PRECIO_DECIMAL:
			return XPATH_PRECIO_DECIMAL_RELATIVE_ARTICLE;
		default:
			return "";
			
		}
	}
	
	@Override
	String getXPathLinea() {
		return XPATH_LINEA;
	}
	
	@Override
	String getXPathLineaWithTagRef() {
		return XPATH_LINEA_WITH_TAG_REF;
	}
	
	@Override
	String getXPathLinkRelativeArticle() {
		return XPATH_LINK_RELATIVE_ARTICLE;
	}	
	
	@Override
	public void clickArticle(int position) {
		By byArticle = By.xpath(getXPathItem(position));
		WebElement article = driver.findElement(byArticle);
		click(article).by(By.xpath(XPATH_LINK_RELATIVE_ARTICLE)).exec();
	}
	
	@Override
	public void clearArticuloAndWait(String refArticulo) throws Exception {
		String xpathClearArt = getXPathLinkBorrarArt(refArticulo);
		click(By.xpath(xpathClearArt)).exec();
		waitForPageLoaded(driver); 
	}
	
	@Override
	public void clickRemoveArticleIfExists() {
		By byRemove = By.xpath(getXPathLinkBorrarArt());
		if (state(Present, byRemove).check()) {
			click(byRemove).exec();
		}
	}
	
	@Override
	public float getPrecioArticle(WebElement lineaArticleWeb) {
		String parteEntera = getDataArticle(DataArtBolsa.PRECIO_ENTERO, lineaArticleWeb);
		String parteDecimal = getDataArticle(DataArtBolsa.PRECIO_DECIMAL, lineaArticleWeb);
		return (ImporteScreen.getFloatFromImporteMangoScreen(parteEntera + parteDecimal));		
	}
	
	private String getXPathItem(int position) {
		return "(" + XPATH_LINEA + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		return XPATH_LINK_BORRAR_ART.replace(TAG_REF_ARTICLE, refArticulo);
	}
}
