package com.mng.robotest.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test.utils.ImporteScreen;


public class LineasArtBolsaMobile extends LineasArtBolsa {

	private static final String DivLinea = "div[@id[contains(.,'iteradorBolsa')]]/div[@class='comShipment']";
	private static final String XPathLinea = "//" + DivLinea;
	private static final String XPathLinkRelativeArticle = ".//div[@class[contains(.,'sbi-information-content')]]/a";
	private static final String XPathLineaWithTagRef = XPathLinkRelativeArticle + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DivLinea;
	private static final String XPathNombreRelativeArticle = ".//span[@id[contains(.,'articuloDescrBolsa')]]";
	private static final String XPathColorRelativeArticle = ".//p[@class[contains(.,'sbi-color')]]";
	private static final String XPathTallaAlfRelativeArticle = ".//p[@class[contains(.,'sbi-size')]]";
	private static final String XPathCantidadRelativeArticle = ".//p[@class[contains(.,'sbi-quantity')]]";
	private static final String XPathPrecioRelativeArticle = ".//div[@class[contains(.,'sbi-price-content')]]//span[@style[not(contains(.,'padding'))]]";
	private static final String XPathPrecioEnteroRelativeArticle = XPathPrecioRelativeArticle + "[1]";
	private static final String XPathPrecioDecimalRelativeArticle = XPathPrecioRelativeArticle + "[2]";
	private static final String TagRefArticle = "[TAGREF]";
	private static final String XPathLinkBorrarArt = "//*[@id[contains(.,'trashMobile')] and @onclick[contains(.,'" + TagRefArticle + "')]]";
	
	public LineasArtBolsaMobile(WebDriver driver) {
		super(Channel.mobile, driver);
	}
	
	@Override
	String getXPathDataRelativeArticle(DataArtBolsa dataArt) {
		switch (dataArt) {
		case Nombre:
			return XPathNombreRelativeArticle;
		case Color:
			return XPathColorRelativeArticle;
		case Talla:
			return XPathTallaAlfRelativeArticle;
		case Cantidad:
			return XPathCantidadRelativeArticle;
		case PrecioEntero:
			return XPathPrecioEnteroRelativeArticle;
		case PrecioDecimal:
			return XPathPrecioDecimalRelativeArticle;
		default:
			return "";
			
		}
	}
	
	@Override
	String getXPathLinea() {
		return XPathLinea;
	}
	
	@Override
	String getXPathLineaWithTagRef() {
		return XPathLineaWithTagRef;
	}
	
	@Override
	String getXPathLinkRelativeArticle() {
		return XPathLinkRelativeArticle;
	}	
	
	@Override
	public void clickArticle(int position) {
		By byArticle = By.xpath(getXPathItem(position));
		WebElement article = driver.findElement(byArticle);
		click(article).by(By.xpath(XPathLinkRelativeArticle)).exec();
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
		String parteEntera = getDataArticle(DataArtBolsa.PrecioEntero, lineaArticleWeb);
		String parteDecimal = getDataArticle(DataArtBolsa.PrecioDecimal, lineaArticleWeb);
		return (ImporteScreen.getFloatFromImporteMangoScreen(parteEntera + parteDecimal));		
	}
	
	private String getXPathItem(int position) {
		return "(" + XPathLinea + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		return XPathLinkBorrarArt.replace(TagRefArticle, refArticulo);
	}
}
