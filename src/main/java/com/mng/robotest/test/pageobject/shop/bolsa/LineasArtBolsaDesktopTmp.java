package com.mng.robotest.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test.utils.ImporteScreen;

public class LineasArtBolsaDesktopTmp extends LineasArtBolsa {

	private static final String XPATH_ITEM = "//*[@data-testid='bag.item']";
	
	//TODO pendiente data-testid
	private static final String XPATH_LINK_RELATIVE_ARTICLE = ".//a[@href[contains(.,'redirect.faces?')]]";
	private static final String XPATH_NOMBRE_RELATIVE_ARTICLE = ".//div[@class[contains(.,'_1MMOd')]/div/div";
	private static final String XPATH_COLOR_RELATIVE_ARTICLE = ".//div[@class[contains(.,'hMucp')]]/div/div/div[3]/div/div";
	private static final String XPATH_TALLA_ALF_RELATIVE_ARTICLE = ".//div[@class[contains(.,'_3hjWl')]]/div[2]/div/div";
	private static final String XPATH_CANTIDAD_RELATIVE_ARTICLE = ".//div[@class[contains(.,'_3hjWl')]]/div/div/div";
	private static final String XPATH_PRECIO_RELATIVE_ARTICLE = ".//div[@data-testid[contains(.,'productPrice.price')]]";
	
	private static final String TAG_REF = "[TAGREF]";
	private static final String XPATH_LINK_ITEM_REF = XPATH_ITEM + "//a[@href[contains(.,'" + TAG_REF + "')]]";
	private static final String XPATH_ITEM_REF = XPATH_LINK_ITEM_REF + "/ancestor::*[@data-testid='bag.item']";
	private static final String XPATH_LINK_BORRAR_ART_REF = XPATH_ITEM_REF + "//*[@data-testid[contains(.,'removeItem.button')]]";

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
		click(By.xpath(xpathClearArt)).exec();
		waitForPageLoaded(driver); 
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
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		return XPATH_LINK_BORRAR_ART_REF.replace(TAG_REF, refArticulo);
	}
	
}