package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class LineasArtBolsaNew extends LineasArtBolsa {

	//TODO ha desaparecido el data-testid (15-abril)
	//private static final String XPathItem = "//*[@data-testid='bag.item']";
	private static final String XPathItem = "//div[@class[contains(.,'_3oq0q')]]";
	
	//TODO pendiente data-testid
	//private final static String XPathLinkRelativeArticle = ".//a[@href[contains(.,'redirect.faces?')]]";
	private final static String XPathLinkRelativeArticle = ".//img";

	private final static String XPathNombreRelativeArticle = ".//*[@data-testid='bag.item.title']";
	private final static String XPathCantidadRelativeArticle = ".//*[@data-testid='bag.item.quantity']";
	private final static String XPathTallaAlfRelativeArticle = ".//*[@data-testid='bag.item.size']";
	private final static String XPathColorRelativeArticle = ".//*[@data-testid='bag.item.color']";
	private final static String XPathPrecioRelativeArticle = ".//*[@data-testid='bag.item.finalPrice']";
	
	private static final String TagRef = "[TAGREF]";
	private static final String XPathLinkItemRef = XPathItem + "//img[@src[contains(.,'" + TagRef + "')]]";
	private static final String XPathItemRef = XPathLinkItemRef + "/ancestor::*[@class[contains(.,'_3oq0q')]]";	
	private static final String XPathLinkBorrarArtRef = XPathItemRef + "//*[@data-testid[contains(.,'removeItem.button')]]";
	
	public LineasArtBolsaNew(Channel channel, WebDriver driver) {
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
