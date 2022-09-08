package com.mng.robotest.domains.bolsa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test.utils.ImporteScreen;


public class LineasArtBolsaNew extends LineasArtBolsa {

	//TODO ha desaparecido el data-testid (15-abril)
	private static final String XPATH_ITEM = "//div[@class[contains(.,'layout-content')] and @class[contains(.,' card')]]";
	
	//TODO pendiente data-testid
	private static final String XPATH_LINK_RELATIVE_ARTICLE = ".//img";

	private static final String XPATH_NOMBRE_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.title']";
	private static final String XPATH_CANTIDAD_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.quantity']";
	private static final String XPATH_TALLA_ALF_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.size']";
	private static final String XPATH_COLOR_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.color']";
	private static final String XPATH_PRECIO_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.finalPrice']";
	
	private static final String TAG_REF = "[TAGREF]";
	private static final String XPATH_LINK_ITEM_REF = XPATH_ITEM + "//img[@src[contains(.,'" + TAG_REF + "')]]";
	private static final String XPATH_ITEM_REF_DESKTOP = XPATH_LINK_ITEM_REF + "/ancestor::*[@class[contains(.,'layout-content')]]";
	private static final String XPATH_ITEM_REF_MOBILE = XPATH_LINK_ITEM_REF + "/ancestor::*[@class[contains(.,'layout-content')]]/..";
	
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
		if (channel==Channel.mobile) {
			return XPATH_ITEM_REF_MOBILE;
		}
		return XPATH_ITEM_REF_DESKTOP;
	}
	
	@Override
	String getXPathLinkRelativeArticle() {
		return XPATH_LINK_RELATIVE_ARTICLE;
	}	
	
	@Override
	public void clearArticuloAndWait(String refArticulo) throws Exception {
		String xpathClearArt = getXPathLinkBorrarArt(refArticulo);
		click(xpathClearArt).exec();
		waitForPageLoaded(driver); 
	}
	
	@Override
	public void clickArticle(int position) {
		WebElement article = getElement(getXPathItem(position));
		click(article).by(By.xpath(XPATH_LINK_RELATIVE_ARTICLE)).exec();
	}
	
	@Override
	public void clickRemoveArticleIfExists() {
		String xpathRemove = getXPathLinkBorrarArt();
		if (state(Present, xpathRemove).check()) {
			click(xpathRemove).exec();
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
