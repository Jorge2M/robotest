package com.mng.robotest.test.pageobject.shop.bolsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.test.data.Talla;
import com.mng.robotest.test.utils.UtilsTest;

public abstract class LineasArtBolsa extends PageObjTM {
	
	
	public enum DataArtBolsa {
		Referencia(true), 
		Nombre(true), 
		Color(true), 
		Talla(true), 
		Cantidad(true), 
		PrecioEntero(true), 
		Precio(false),
		PrecioDecimal(true), 
		PrecioTotal(true);
		
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
	
	protected final Channel channel;
	
	static final String TagReference = "@Reference";
	
	abstract String getXPathDataRelativeArticle(DataArtBolsa dataArt);
	abstract String getXPathLinea();
	abstract String getXPathLineaWithTagRef();
	abstract String getXPathLinkRelativeArticle();
	public abstract void clearArticuloAndWait(String refArticulo) throws Exception;
	public abstract void clickArticle(int position);
	public abstract void clickRemoveArticleIfExists();
	public abstract float getPrecioArticle(WebElement lineaArticleWeb);
	
	
	LineasArtBolsa(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}
	
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
		return (driver.findElements(By.xpath(xpathLinea)).size());
	}
	
	public WebElement getLineaArticuloByPosicion(int posicion) {
		String xpathArticle = getXPathLineaArticleByPosicion(posicion);
		return (driver.findElement(By.xpath(xpathArticle)));
	}
	
	public WebElement getLineaArticuloByReferencia(String reference) {
		String xpathArticle = getXPathLineaArticleByReference(reference);
		return (driver.findElement(By.xpath(xpathArticle)));
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
		String xpathLinRelative = getXPathLinkRelativeArticle();
		WebElement link = lineaArticleWeb.findElement(By.xpath(xpathLinRelative));
		if (link==null) {
			return "";
		}
		if (link.getAttribute("href")!=null) {
			return (UtilsTest.getReferenciaFromHref(link.getAttribute("href")));
		} else {
			return (UtilsTest.getReferenciaFromSrcImg(link.getAttribute("src")));
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
		articleData.nombre = getDataArticle(DataArtBolsa.Nombre, lineaArticleWeb);
		articleData.color = getDataArticle(DataArtBolsa.Color, lineaArticleWeb);
		articleData.talla = Talla.fromLabel(getDataArticle(DataArtBolsa.Talla, lineaArticleWeb));
		articleData.cantidad = getDataArticle(DataArtBolsa.Cantidad, lineaArticleWeb);
		articleData.precio = getPrecioArticle(lineaArticleWeb);
		return articleData;
	}
}
