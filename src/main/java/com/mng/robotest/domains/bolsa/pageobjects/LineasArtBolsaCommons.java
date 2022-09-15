package com.mng.robotest.domains.bolsa.pageobjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.utils.UtilsTest;

public abstract class LineasArtBolsaCommons extends PageBase {
	
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
				List<DataArtBolsa> listData = new ArrayList<>();
				for (DataArtBolsa dataArt : DataArtBolsa.values()) {
					if (dataArt.validMobilWeb) {
						listData.add(dataArt);
					}
				}
				
				return listData;
			}
				
			return Arrays.asList(values());
		}
	};
	
	static final String TagReference = "@Reference";
	
	abstract String getXPathDataRelativeArticle(DataArtBolsa dataArt);
	abstract String getXPathLinea();
	abstract String getXPathLineaWithTagRef();
	abstract String getXPathLinkRelativeArticle();
	public abstract void clearArticuloAndWait(String refArticulo) throws Exception;
	public abstract void clickArticle(int position);
	public abstract void clickRemoveArticleIfExists();
	public abstract float getPrecioArticle(WebElement lineaArticleWeb);
	
	private String getXPathLineaArticleByPosicion(int posicion) {
		String xpathLinea = getXPathLinea();
		return ("(" + xpathLinea + ")[" + posicion + "]");
	}
	
	private String getXPathLineaArticleByReference(String reference) {
		String xpathLinWithTag = getXPathLineaWithTagRef();
		return xpathLinWithTag.replace(TagReference, reference);
	}
	
	public int getNumLinesArticles() {
		String xpathLinea = getXPathLinea();
		return getElements(xpathLinea).size();
	}
	
	public WebElement getLineaArticuloByPosicion(int posicion) {
		String xpathArticle = getXPathLineaArticleByPosicion(posicion);
		return getElement(xpathArticle);
	}
	
	public WebElement getLineaArticuloByReferencia(String reference) {
		String xpathArticle = getXPathLineaArticleByReference(reference);
		return getElement(xpathArticle);
	}
	
	String getDataArticle(DataArtBolsa typeData, WebElement lineaArticle) {
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
		String xpathLinRelative = getXPathLinkRelativeArticle();
		state(State.Visible, lineaArticleWeb).by(By.xpath(xpathLinRelative)).wait(2).check();
		try {
			return lineaArticleWeb.findElement(By.xpath(xpathLinRelative));
		} 
		catch (Exception e) {
			waitMillis(1000);
			return lineaArticleWeb.findElement(By.xpath(xpathLinRelative));
		}
	}
	

	public ArticuloDataBolsaScreen getArticuloDataByPosicion(int posicion) {
		WebElement lineaArticleWeb = getLineaArticuloByPosicion(posicion);
		ArticuloDataBolsaScreen articleData = getArticuloBolsaData(lineaArticleWeb); 
		return articleData;
	}
	
	public ArticuloDataBolsaScreen getArticuloDataByReferencia(String reference) {
		WebElement lineaArticleWeb = getLineaArticuloByReferencia(reference);
		ArticuloDataBolsaScreen articleData = getArticuloBolsaData(lineaArticleWeb);
		return articleData;
	}
	
	private ArticuloDataBolsaScreen getArticuloBolsaData(WebElement lineaArticleWeb) {
		if (lineaArticleWeb==null) {
			return null;
		}
		
		ArticuloDataBolsaScreen articleData = new ArticuloDataBolsaScreen();
		articleData.referencia = getReferenciaArticle(lineaArticleWeb);
		articleData.nombre = getDataArticle(DataArtBolsa.NOMBRE, lineaArticleWeb);
		articleData.color = getDataArticle(DataArtBolsa.COLOR, lineaArticleWeb);
		articleData.talla = Talla.fromLabel(getDataArticle(DataArtBolsa.TALLA, lineaArticleWeb));
		articleData.cantidad = getDataArticle(DataArtBolsa.CANTIDAD, lineaArticleWeb);
		articleData.precio = getPrecioArticle(lineaArticleWeb);
		return articleData;
	}
}
