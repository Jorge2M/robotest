package com.mng.robotest.tests.domains.bolsa.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;
import com.mng.robotest.testslegacy.utils.ImporteScreen;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.Arrays;
import java.util.List;

public class LineasArticuloBolsa extends PageBase {

	public enum DataArtBolsa {
		REFERENCIA, 
		NOMBRE, 
		COLOR, 
		TALLA, 
		CANTIDAD, 
		PRECIO_ENTERO, 
		PRECIO,
		PRECIO_DECIMAL, 
		PRECIO_TOTAL;
		
		public static List<DataArtBolsa> getValues() {
			return Arrays.asList(DataArtBolsa.values());
		}
	}
	
	private static final String XP_ITEM = "//li[@data-testid='bag.item']";
	
	private static final String XP_LINK_RELATIVE_ARTICLE = ".//img";
	private static final String XP_NOMBRE_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.detail.button']";
	private static final String XP_CANTIDAD_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.quantity']";
	
	//TODO pedir @data-testid
	private static final String XP_TALLA_ALF_RELATIVE_ARTICLE = XP_CANTIDAD_RELATIVE_ARTICLE + "/../following-sibling::p";
	
	//TODO pedir @data-testid
	private static final String XP_COLOR_RELATIVE_ARTICLE = XP_NOMBRE_RELATIVE_ARTICLE + "/..//p[3]";
	private static final String XP_PRECIO_RELATIVE_ARTICLE = ".//*[@data-testid[contains(.,'currentPrice')]]";
	private static final String TAG_REF = "[TAGREF]";
	private static final String XP_LINK_ITEM_REF = XP_ITEM + "//img[@src[contains(.,'" + TAG_REF + "')]]";
	private static final String XP_ITEM_REF = XP_LINK_ITEM_REF + "/ancestor::li";
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		String xpathLinkBorrarArtRef = XP_ITEM_REF + "//*[@data-testid[contains(.,'removeItem.button')]]";
		return xpathLinkBorrarArtRef.replace(TAG_REF, refArticulo);
	}
	
	private String getXPathDataRelativeArticle(DataArtBolsa dataArt) {
		switch (dataArt) {
		case NOMBRE:
			return XP_NOMBRE_RELATIVE_ARTICLE;
		case COLOR:
			return XP_COLOR_RELATIVE_ARTICLE;
		case TALLA:
			return XP_TALLA_ALF_RELATIVE_ARTICLE;
		case CANTIDAD:
			return XP_CANTIDAD_RELATIVE_ARTICLE;
		case PRECIO:
			return XP_PRECIO_RELATIVE_ARTICLE;
		default:
			return "";
		}
	}
	
	public void clearArticuloAndWait(String refArticulo) {
		String xpathClearArt = getXPathLinkBorrarArt(refArticulo);
		click(xpathClearArt).waitLoadPage(30).exec();
	}
	
	public void clickArticle(int position) {
		WebElement article = getElement(getXPathItem(position));
		click(article).by(By.xpath(XP_LINK_RELATIVE_ARTICLE)).exec();
	}
	
	public void clickRemoveArticleIfExists() {
		By byRemove = By.xpath(getXPathLinkBorrarArt());
		if (state(PRESENT, byRemove).check()) {
			click(byRemove).exec();
		}
	}
	
	public float getPrecioArticle(WebElement lineaArticleWeb) {
		String importe = getDataArticle(DataArtBolsa.PRECIO, lineaArticleWeb);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importe));		
	}
	
	private String getXPathItem(int position) {
		return "(" + XP_ITEM + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}

	private String getXPathLineaArticleByPosicion(int posicion) {
		return ("(" + XP_ITEM + ")[" + posicion + "]");
	}
	
	private static final String TAG_REFERENCE = "@Reference";
	private String getXPathLineaArticleByReference(String reference) {
		return XP_ITEM_REF.replace(TAG_REFERENCE, reference);
	}
	
	public int getNumLinesArticles() {
		return getElements(XP_ITEM).size();
	}
	
	public WebElement getLineaArticuloByPosicion(int posicion) {
		String xpathArticle = getXPathLineaArticleByPosicion(posicion);
		return getElement(xpathArticle);
	}
	
	public WebElement getLineaArticuloByReferencia(String reference) {
		String xpathArticle = getXPathLineaArticleByReference(reference);
		return getElement(xpathArticle);
	}
	
	private String getDataArticle(DataArtBolsa typeData, WebElement lineaArticle) {
		String xpathData = getXPathDataRelativeArticle(typeData);
		WebElement dataWebElement;
		try {
			dataWebElement = lineaArticle.findElement(By.xpath(xpathData));
		}
		catch (NoSuchElementException e) {
			return "";
		}
		
		String totalData = dataWebElement.getText();
		if ("".compareTo(totalData)==0) {
			totalData = dataWebElement.getAttribute("innerHTML").trim();
		}
		return (removeLitIzquierdaDosPuntosIfExists(totalData));
	}
	
	private static String removeLitIzquierdaDosPuntosIfExists(String data) {
		int finLiteral = data.indexOf(": ");
		if (finLiteral > 0) {
			return (data.substring(finLiteral + 2, data.length()));
		}
		return data;
	}
	
	private String getReferenciaArticle(WebElement lineaArticleWeb) {
		WebElement link = getLineaLinkArticle(lineaArticleWeb);
		if (link==null) {
			return "";
		}
		if (link.getAttribute("href")!=null) {
			return (UtilsTest.getReferenciaFromHref(link.getAttribute("href")));
		} else {
			return (UtilsTest.getReferenciaFromSrcImg(link.getAttribute("src")));
		}
	}
	
	private WebElement getLineaLinkArticle(WebElement lineaArticleWeb) {
		state(VISIBLE, lineaArticleWeb).by(By.xpath(XP_LINK_RELATIVE_ARTICLE)).wait(2).check();
		try {
			return lineaArticleWeb.findElement(By.xpath(XP_LINK_RELATIVE_ARTICLE));
		} 
		catch (Exception e) {
			waitMillis(2000);
			return lineaArticleWeb.findElement(By.xpath(XP_LINK_RELATIVE_ARTICLE));
		}
	}

	public ArticuloDataBolsaScreen getArticuloDataByPosicion(int posicion) {
		WebElement lineaArticleWeb = getLineaArticuloByPosicion(posicion);
		return getArticuloBolsaData(lineaArticleWeb); 
	}
	
	public ArticuloDataBolsaScreen getArticuloDataByReferencia(String reference) {
		WebElement lineaArticleWeb = getLineaArticuloByReferencia(reference);
		return getArticuloBolsaData(lineaArticleWeb);
	}
	
	private ArticuloDataBolsaScreen getArticuloBolsaData(WebElement lineaArticleWeb) {
		if (lineaArticleWeb==null) {
			return null;
		}
		
		var articleData = new ArticuloDataBolsaScreen();
		articleData.setReferencia(getReferenciaArticle(lineaArticleWeb));
		articleData.setNombre(getDataArticle(DataArtBolsa.NOMBRE, lineaArticleWeb));
		articleData.setColor(getDataArticle(DataArtBolsa.COLOR, lineaArticleWeb));
		
		var talla = Talla.fromLabel(
			getDataArticle(DataArtBolsa.TALLA, lineaArticleWeb), 
			PaisShop.from(dataTest.getCodigoPais())); 
		articleData.setTalla(talla);

		articleData.setCantidad(getDataArticle(DataArtBolsa.CANTIDAD, lineaArticleWeb));
		articleData.setPrecio(getPrecioArticle(lineaArticleWeb));
		return articleData;
	}	
	
}
