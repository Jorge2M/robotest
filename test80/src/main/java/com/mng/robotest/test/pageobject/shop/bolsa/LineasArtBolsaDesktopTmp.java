package com.mng.robotest.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test.utils.ImporteScreen;


public class LineasArtBolsaDesktopTmp extends LineasArtBolsa {


//	private final static String XPathLinkRelativeArticle = ".//div[@class[contains(.,'itemImage')]]/a";
//	private final static String XPathLineaWithTagRef = XPathLinkRelativeArticle + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DivLinea;
//	private final static String XPathNombreRelativeArticle = ".//*[@class='bolsa_descripcion']";
//	private final static String XPathColorRelativeArticle = ".//*[@class='itemColor']";
//	private final static String XPathTallaAlfRelativeArticle = ".//p[@class='itemSize']";
//	private final static String XPathCantidadRelativeArticle = ".//p[@class='itemsQt']";
//	private final static String XPathPrecioEnteroRelativeArticle = ".//span[@class='bolsa_price_big' and (not(@style) or @style='')]";
//	private final static String XPathPrecioDecimalRelativeArticle = ".//span[@class='bolsa_price_small' and (not(@style) or @style='')]";
	
	private static final String XPathItem = "//*[@data-testid='bag.item']";
	
	//TODO pendiente data-testid
	private final static String XPathLinkRelativeArticle = ".//a[@href[contains(.,'redirect.faces?')]]";
	private final static String XPathNombreRelativeArticle = ".//div[@class[contains(.,'_1MMOd')]/div/div";
	private final static String XPathColorRelativeArticle = ".//div[@class[contains(.,'hMucp')]]/div/div/div[3]/div/div";
	private final static String XPathTallaAlfRelativeArticle = ".//div[@class[contains(.,'_3hjWl')]]/div[2]/div/div";
	private final static String XPathCantidadRelativeArticle = ".//div[@class[contains(.,'_3hjWl')]]/div/div/div";
	private final static String XPathPrecioRelativeArticle = ".//div[@data-testid[contains(.,'productPrice.price')]]";
	
	private static final String TagRef = "[TAGREF]";
	private static final String XPathLinkItemRef = XPathItem + "//a[@href[contains(.,'" + TagRef + "')]]";
	private static final String XPathItemRef = XPathLinkItemRef + "/ancestor::*[@data-testid='bag.item']";
	private static final String XPathLinkBorrarArtRef = XPathItemRef + "//*[@data-testid[contains(.,'removeItem.button')]]";
	
	public LineasArtBolsaDesktopTmp(Channel channel, WebDriver driver) {
		super(channel, driver);
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
		case Precio:
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
		return XPathItemRef;
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
		String importe = getDataArticle(DataArtBolsa.Precio, lineaArticleWeb);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importe));		
	}
	
	private String getXPathItem(int position) {
		return "(" + XPathItem + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		return XPathLinkBorrarArtRef.replace(TagRef, refArticulo);
	}
	
}