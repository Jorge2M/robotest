package com.mng.robotest.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test.utils.ImporteScreen;

//TODO remove
public class LineasArtBolsaDesktopOld /*extends LineasArtBolsa*/ {

//	private static final String DivLinea = "div[@class[contains(.,'bagItem')]]";
//	private static final String XPathLinea = "//" + DivLinea;
//	private static final String XPathLinkRelativeArticle = ".//div[@class[contains(.,'itemImage')]]/a";
//	private static final String XPathLineaWithTagRef = XPathLinkRelativeArticle + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DivLinea;
//	private static final String XPathNombreRelativeArticle = ".//*[@class='bolsa_descripcion']";
//	private static final String XPathColorRelativeArticle = ".//*[@class='itemColor']";
//	private static final String XPathTallaAlfRelativeArticle = ".//p[@class='itemSize']";
//	private static final String XPathCantidadRelativeArticle = ".//p[@class='itemsQt']";
//	private static final String XPathPrecioEnteroRelativeArticle = ".//span[@class='bolsa_price_big' and (not(@style) or @style='')]";
//	private static final String XPathPrecioDecimalRelativeArticle = ".//span[@class='bolsa_price_small' and (not(@style) or @style='')]";
//	
//	private static final String TagRefArticle = "[TAGREF]";
//	private static final String XPathLinkBorrarArt = "//*[@class='boton_basura' and @onclick[contains(.,'" + TagRefArticle + "')]]/..";
//	
//	public LineasArtBolsaDesktopOld(Channel channel, WebDriver driver) {
//		super(channel, driver);
//	}
//	
//	@Override
//	String getXPathDataRelativeArticle(DataArtBolsa dataArt) {
//		switch (dataArt) {
//		case Nombre:
//			return XPathNombreRelativeArticle;
//		case Color:
//			return XPathColorRelativeArticle;
//		case Talla:
//			return XPathTallaAlfRelativeArticle;
//		case Cantidad:
//			return XPathCantidadRelativeArticle;
//		case PrecioEntero:
//			return XPathPrecioEnteroRelativeArticle;
//		case PrecioDecimal:
//			return XPathPrecioDecimalRelativeArticle;
//		default:
//			return "";
//			
//		}
//	}
//	
//	@Override
//	String getXPathLinea() {
//		return XPathLinea;
//	}
//	
//	@Override
//	String getXPathLineaWithTagRef() {
//		return XPathLineaWithTagRef;
//	}
//	
//	@Override
//	String getXPathLinkRelativeArticle() {
//		return XPathLinkRelativeArticle;
//	}	
//	
//	@Override
//	public void clearArticuloAndWait(String refArticulo) throws Exception {
//		String xpathClearArt = getXPathLinkBorrarArt(refArticulo);
//		click(By.xpath(xpathClearArt)).exec();
//		waitForPageLoaded(driver); 
//	}
//	
//	@Override
//	public void clickArticle(int position) {
//		By byArticle = By.xpath(getXPathItem(position));
//		WebElement article = driver.findElement(byArticle);
//		click(article).by(By.xpath(XPathLinkRelativeArticle)).exec();
//	}
//	
//	@Override
//	public void clickRemoveArticleIfExists() {
//		By byRemove = By.xpath(getXPathLinkBorrarArt());
//		if (state(Present, byRemove).check()) {
//			click(byRemove).exec();
//		}
//	}
//	
//	@Override
//	public float getPrecioArticle(WebElement lineaArticleWeb) {
//		String parteEntera = getDataArticle(DataArtBolsa.PrecioEntero, lineaArticleWeb);
//		String parteDecimal = getDataArticle(DataArtBolsa.PrecioDecimal, lineaArticleWeb);
//		return (ImporteScreen.getFloatFromImporteMangoScreen(parteEntera + parteDecimal));		
//	}
//	
//	private String getXPathItem(int position) {
//		return "(" + XPathLinea + ")[" + position + "]";
//	}
//	
//	private String getXPathLinkBorrarArt() {
//		return getXPathLinkBorrarArt("");
//	}
//	
//	private String getXPathLinkBorrarArt(String refArticulo) {
//		return XPathLinkBorrarArt.replace(TagRefArticle, refArticulo);
//	}
}
