package com.mng.robotest.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test.utils.ImporteScreen;


public class LineasArtBolsaNewNew extends LineasArtBolsa {

	//
	private static final String XPathItem = "//li/a[@data-testid[contains(.,'image.detail')]]/..";
	//
	private static final String XPathLinkRelativeArticle = ".//img";
	//
	private static final String XPathNombreRelativeArticle = ".//*[@data-testid='bag.item.detail.button']";
	//
	private static final String XPathCantidadRelativeArticle = ".//*[@data-testid='bag.item.quantity']";
	
	//private static final String XPathTallaAlfRelativeArticle = ".//*[@data-testid='bag.item.size']";
	//TODO pedir @data-testid
	private static final String XPathTallaAlfRelativeArticle = ".//span[text()[contains(.,'Talla:')]]/../span[2]";
	
	//TODO pedir @data-testid
	private static final String XPathColorRelativeArticle = XPathNombreRelativeArticle + "//p[3]";
	//
	private static final String XPathPrecioRelativeArticle = ".//*[@data-testid[contains(.,'summary.price')]]";
	//
	private static final String TagRef = "[TAGREF]";
	//
	private static final String XPathLinkItemRef = XPathItem + "//img[@src[contains(.,'" + TagRef + "')]]";
	//
	private static final String XPathItemRefDesktop = XPathLinkItemRef + "/../..";
	//???
	private static final String XPathItemRefMobile = XPathLinkItemRef + "/ancestor::*[@class[contains(.,'layout-content')]]/..";
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		String xpathItemWithTag = getXPathLineaWithTagRef();
		String xpathLinkBorrarArtRef = xpathItemWithTag + "//*[@data-testid[contains(.,'removeItem.button')]]";
		return xpathLinkBorrarArtRef.replace(TagRef, refArticulo);
	}

	@Override
	String getXPathDataRelativeArticle(DataArtBolsa dataArt) {
		switch (dataArt) {
		case NOMBRE:
			return XPathNombreRelativeArticle;
		case COLOR:
			return XPathColorRelativeArticle;
		case TALLA:
			return XPathTallaAlfRelativeArticle;
		case CANTIDAD:
			return XPathCantidadRelativeArticle;
		case PRECIO:
			return XPathPrecioRelativeArticle;
		default:
			return "";
		}
	}
	
	@Override
	String getXPathLinea() {
		return XPathItem;
	}
	
	@Override
	String getXPathLineaWithTagRef() {
		if (channel==Channel.mobile) {
			return XPathItemRefMobile;
		}
		return XPathItemRefDesktop;
	}
	
	@Override
	String getXPathLinkRelativeArticle() {
		return XPathLinkRelativeArticle;
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
		click(article).by(By.xpath(XPathLinkRelativeArticle)).exec();
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
		return "(" + XPathItem + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}

	
}
