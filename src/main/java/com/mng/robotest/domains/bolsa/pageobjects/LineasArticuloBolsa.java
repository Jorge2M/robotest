package com.mng.robotest.domains.bolsa.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.utils.ImporteScreen;
import com.mng.robotest.test.utils.UtilsTest;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class LineasArticuloBolsa extends PageBase {

	public enum DataArtBolsa {
		REFERENCIA(true), 
		NOMBRE(true), 
		COLOR(true), 
		TALLA(true), 
		CANTIDAD(true), 
		PRECIO_ENTERO(true), 
		PRECIO(false),
		PRECIO_DECIMAL(true), 
		PRECIO_TOTAL(true);
		
		boolean validMobilWeb = true;
		DataArtBolsa(boolean validMobilWeb) {
			this.validMobilWeb = validMobilWeb;
		}
		
		public static List<DataArtBolsa> getValuesValidForChannel(Channel channel) {
			if (channel.isDevice()) {
				var listData = new ArrayList<DataArtBolsa>();
				for (var dataArt : DataArtBolsa.values()) {
					if (dataArt.validMobilWeb) {
						listData.add(dataArt);
					}
				}
				
				return listData;
			}
				
			return Arrays.asList(values());
		}
	}
	
	//TODO cuando se active la nueva bolsa en pro se puede eliminar el segundo xpath
	private static final String XPATH_ITEM = "//li[@data-testid='bag.item']";
	private static final String XPATH_ITEM_OLD = "//div[@class[contains(.,'layout-row')]]";
	
	private static final String XPATH_LINK_RELATIVE_ARTICLE = ".//img";
	private static final String XPATH_NOMBRE_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.detail.button']";
	private static final String XPATH_CANTIDAD_RELATIVE_ARTICLE = ".//*[@data-testid='bag.item.quantity']";
	
	//TODO pedir @data-testid
	private static final String XPATH_TALLA_ALF_RELATIVE_ARTICLE = ".//span[text()[contains(.,'Talla:')]]/../span[2]";
	
	//TODO pedir @data-testid
	private static final String XPATH_COLOR_RELATIVE_ARTICLE = XPATH_NOMBRE_RELATIVE_ARTICLE + "/..//p[3]";

	private static final String XPATH_PRECIO_RELATIVE_ARTICLE = ".//*[@data-testid[contains(.,'currentPrice')]]";
	
	//TODO cuando se active la nueva bolsa en pro se puede eliminar el segundo xpath
	private static final String TAG_REF = "[TAGREF]";
	private static final String XPATH_LINK_ITEM_REF = XPATH_ITEM + "//img[@src[contains(.,'" + TAG_REF + "')]]";
	private static final String XPATH_LINK_ITEM_REF_OLD = XPATH_ITEM_OLD + "//img[@src[contains(.,'" + TAG_REF + "')]]";
	
	//TODO cuando se active la nueva bolsa en pro se puede eliminar el segundo xpath	
	private static final String XPATH_ITEM_REF = XPATH_LINK_ITEM_REF + "/ancestor::li";
	private static final String XPATH_ITEM_REF_OLD = XPATH_LINK_ITEM_REF_OLD + "/ancestor::div[@class[contains(.,'layout-row')]]";
	
	private String getXPathLinkBorrarArt(String refArticulo) {
		return "(" + getXPathLinkBorrarArtNew(refArticulo) + " | " +
			   getXPathLinkBorrarArtOld(refArticulo) + ")";
	}
	
	private String getXPathLinkBorrarArtNew(String refArticulo) {
		String xpathLinkBorrarArtRef = XPATH_ITEM_REF + "//*[@data-testid[contains(.,'removeItem.button')]]";
		return xpathLinkBorrarArtRef.replace(TAG_REF, refArticulo);
	}
	
	private String getXPathLinkBorrarArtOld(String refArticulo) {
		String xpathLinkBorrarArtRef = XPATH_ITEM_REF_OLD + "//*[@data-testid[contains(.,'removeItem.button')]]";
		return xpathLinkBorrarArtRef.replace(TAG_REF, refArticulo);
	}

	private String getXPathDataRelativeArticle(DataArtBolsa dataArt) {
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
	
	public void clearArticuloAndWait(String refArticulo) {
		String xpathClearArt = getXPathLinkBorrarArt(refArticulo);
		click(xpathClearArt).waitLoadPage(30).exec();
	}
	
	public void clickArticle(int position) {
		WebElement article = getElement(getXPathItem(position));
		click(article).by(By.xpath(XPATH_LINK_RELATIVE_ARTICLE)).exec();
	}
	
	public void clickRemoveArticleIfExists() {
		By byRemove = By.xpath(getXPathLinkBorrarArt());
		if (state(Present, byRemove).check()) {
			click(byRemove).exec();
		}
	}
	
	public float getPrecioArticle(WebElement lineaArticleWeb) {
		String importe = getDataArticle(DataArtBolsa.PRECIO, lineaArticleWeb);
		return (ImporteScreen.getFloatFromImporteMangoScreen(importe));		
	}
	
	//TODO cuando suba la nueva bolsa quitar el XPATH_ITEM_OLD
	private String getXPathItem(int position) {
		return "(" + XPATH_ITEM + " | " + XPATH_ITEM_OLD + ")[" + position + "]";
	}
	
	private String getXPathLinkBorrarArt() {
		return getXPathLinkBorrarArt("");
	}

	private String getXPathLineaArticleByPosicion(int posicion) {
		return ("(" + XPATH_ITEM + ")[" + posicion + "]");
	}
	
	private static final String TAG_REFERENCE = "@Reference";
	private String getXPathLineaArticleByReference(String reference) {
		return XPATH_ITEM_REF.replace(TAG_REFERENCE, reference);
	}
	
	public int getNumLinesArticles() {
		return getElements(XPATH_ITEM).size();
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
		if (!DataArtBolsa.getValuesValidForChannel(channel).contains(typeData)) {
			return "";
		}
		
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
		state(Visible, lineaArticleWeb).by(By.xpath(XPATH_LINK_RELATIVE_ARTICLE)).wait(2).check();
		try {
			return lineaArticleWeb.findElement(By.xpath(XPATH_LINK_RELATIVE_ARTICLE));
		} 
		catch (Exception e) {
			waitMillis(2000);
			return lineaArticleWeb.findElement(By.xpath(XPATH_LINK_RELATIVE_ARTICLE));
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
		articleData.setTalla(Talla.fromLabel(getDataArticle(DataArtBolsa.TALLA, lineaArticleWeb)));
		articleData.setCantidad(getDataArticle(DataArtBolsa.CANTIDAD, lineaArticleWeb));
		articleData.setPrecio(getPrecioArticle(lineaArticleWeb));
		return articleData;
	}	
	
}
