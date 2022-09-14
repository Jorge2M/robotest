package com.mng.robotest.domains.bolsa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test.utils.ImporteScreen;

public class LineasArtBolsaNewNew extends LineasArtBolsa {

	private static final String XPATH_ITEM = "//li//a[@data-testid[contains(.,'image.detail')]]/ancestor::li";
	private static final String XPATH_LINK_RELATIVE_ARTICLE = ".//img";
	private static final String XPATH_NOMBRE_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.detail.button']";
	private static final String XPATH_CANTIDAD_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.quantity']";
	
	//TODO pedir @data-testid
	private static final String XPATH_TALLA_ALF_RELATIVE_ARTICLE = ".//span[text()[contains(.,'Talla:')]]/../span[2]";
	
	//TODO pedir @data-testid
	private static final String XPATH_COLOR_RELATIVE_ARTICLE = XPATH_NOMBRE_RELATIVE_ARTICLE + "//p[3]";

	private static final String XPATH_PRECIO_RELATIVE_ARTICLE = ".//*[@data-testid[contains(.,'summary.price')]]";
	private static final String TAG_REF = "[TAGREF]";
	private static final String XPATH_LINK_ITEM_REF = XPATH_ITEM + "//img[@src[contains(.,'" + TAG_REF + "')]]";
	private static final String XPATH_ITEM_REF = XPATH_LINK_ITEM_REF + "/ancestor::li";
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		String xpathItemWithTag = getXPathLineaWithTagRef();
		String xpathLinkBorrarArtRef = xpathItemWithTag + "//*[@data-testid[contains(.,'removeItem.button')]]";
		return xpathLinkBorrarArtRef.replace(TAG_REF, refArticulo);
	}

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
		case PRECIO:
			return XPATH_PRECIO_RELATIVE_ARTICLE;
		default:
			return "";
		}
	}
	
	@Override
	String getXPathLinea() {
		return XPATH_ITEM;
	}
	
	@Override
	String getXPathLineaWithTagRef() {
		return XPATH_ITEM_REF;
	}
	
	@Override
	String getXPathLinkRelativeArticle() {
		return XPATH_LINK_RELATIVE_ARTICLE;
	}	
	
	@Override
	public void clearArticuloAndWait(String refArticulo) throws Exception {
		String xpathClearArt = getXPathLinkBorrarArt(refArticulo);
		click(xpathClearArt).waitLoadPage(30).exec();
	}
	
	@Override
	public void clickArticle(int position) {
		By byArticle = By.xpath(getXPathItem(position));
		WebElement article = driver.findElement(byArticle);
		click(article).by(By.xpath(XPATH_LINK_RELATIVE_ARTICLE)).exec();
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
		String importe = getDataArticle(DataArtBolsa.PRECIO, lineaArticleWeb);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importe));		
	}
	
	private String getXPathItem(int position) {
		return "(" + XPATH_ITEM + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}

	
}
