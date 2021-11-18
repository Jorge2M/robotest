package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class LineasArtBolsaDesktopOld extends LineasArtBolsa {

	private final static String DivLinea = "div[@class[contains(.,'bagItem')]]";
	private final static String XPathLinea = "//" + DivLinea;
	private final static String XPathLinkRelativeArticle = ".//div[@class[contains(.,'itemImage')]]/a";
	private final static String XPathLineaWithTagRef = XPathLinkRelativeArticle + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DivLinea;
	private final static String XPathNombreRelativeArticle = ".//*[@class='bolsa_descripcion']";
	private final static String XPathColorRelativeArticle = ".//*[@class='itemColor']";
	private final static String XPathTallaAlfRelativeArticle = ".//p[@class='itemSize']";
	private final static String XPathCantidadRelativeArticle = ".//p[@class='itemsQt']";
	private final static String XPathPrecioEnteroRelativeArticle = ".//span[@class='bolsa_price_big' and (not(@style) or @style='')]";
	private final static String XPathPrecioDecimalRelativeArticle = ".//span[@class='bolsa_price_small' and (not(@style) or @style='')]";
	
	private static final String TagRefArticle = "[TAGREF]";
	private static final String XPathLinkBorrarArt = "//*[@class='boton_basura' and @onclick[contains(.,'" + TagRefArticle + "')]]/..";
	
	public LineasArtBolsaDesktopOld(Channel channel, WebDriver driver) {
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
