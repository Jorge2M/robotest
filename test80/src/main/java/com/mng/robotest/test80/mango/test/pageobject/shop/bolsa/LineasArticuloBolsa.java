package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.data.Talla;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import com.mng.robotest.test80.mango.test.utils.UtilsTestMango;

public class LineasArticuloBolsa {
	
	public enum DataArtBolsa {
		Referencia(true), 
		Nombre(true), 
		Color(true), 
		Talla(true), 
		Cantidad(true), 
		PrecioEntero(true), 
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

	private final static String TagReference = "@Reference";
	private final static String DivLineaDesktop = "div[@class[contains(.,'bagItem')]]";
	private final static String DivLineaMobil = "div[@id[contains(.,'iteradorBolsa')]]/div[@class='comShipment']";
	private final static String XPathLineaDesktop = "//" + DivLineaDesktop;
	private final static String XPathLineaMobil = "//" + DivLineaMobil;
	private final static String XPathLinkRelativeArticleDesktop = ".//div[@class[contains(.,'itemImage')]]/a";
	private final static String XPathLinkRelativeArticleMobil = ".//div[@class[contains(.,'sbi-information-content')]]/a";
	private final static String XPathLineaWithTagRefDesktop = XPathLinkRelativeArticleDesktop + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DivLineaDesktop;
	private final static String XPathLineaWithTagRefMobil = XPathLinkRelativeArticleMobil + "//self::*[@href[contains(.,'" + TagReference + ".html')]]//ancestor::" + DivLineaMobil;
	private final static String XPathNombreRelativeArticleDesktop = ".//*[@class='bolsa_descripcion']";
	private final static String XPathNombreRelativeArticleMobil = ".//span[@id[contains(.,'articuloDescrBolsa')]]";
	private final static String XPathColorRelativeArticleDesktop = ".//*[@class='itemColor']";
	private final static String XPathColorRelativeArticleMobil = ".//p[@class[contains(.,'sbi-color')]]";
	private final static String XPathTallaAlfRelativeArticleDesktop = ".//p[@class='itemSize']";
//	private final static String XPathTallaNumRelativeArticleDesktop = ".//p[@class='itemSizeID']";
	private final static String XPathTallaAlfRelativeArticleMobil = ".//p[@class[contains(.,'sbi-size')]]";
	private final static String XPathCantidadRelativeArticleDesktop = ".//p[@class='itemsQt']";
	private final static String XPathCantidadRelativeArticleMobil = ".//p[@class[contains(.,'sbi-quantity')]]";
	private final static String XPathPrecioEnteroRelativeArticleDesktop = ".//span[@class='bolsa_price_big' and (not(@style) or @style='')]";
	private final static String XPathPrecioDecimalRelativeArticleDesktop = ".//span[@class='bolsa_price_small' and (not(@style) or @style='')]";
	private final static String XPathPrecioRelativeArticleMobil = ".//div[@class[contains(.,'sbi-price-content')]]//span[@style[not(contains(.,'padding'))]]";
	private final static String XPathPrecioEnteroRelativeArticleMobil = XPathPrecioRelativeArticleMobil + "[1]";
	private final static String XPathPrecioDecimalRelativeArticleMobil = XPathPrecioRelativeArticleMobil + "[2]";
	
	private static String getXPathLinea(Channel channel) {
		switch (channel) {
		case desktop:
		case tablet:
			return XPathLineaDesktop;
		case mobile:
		default:
			return XPathLineaMobil;
		}
	}
	
	private static String getXPathLineaWithTagRef(Channel channel) {
		switch (channel) {
		case desktop:
		case tablet:
			return XPathLineaWithTagRefDesktop;
		case mobile:
		default:
			return XPathLineaWithTagRefMobil;
		}
	}	
	
	private static String getXPathLinkRelativeArticle(Channel channel) {
		switch (channel) {
		case desktop:
		case tablet:
			return XPathLinkRelativeArticleDesktop;
		case mobile:
		default:
			return XPathLinkRelativeArticleMobil;
		}
	}		
	
	private static String getXPathLineaArticleByPosicion(int posicion, Channel channel) {
		String xpathLinea = getXPathLinea(channel);
		return ("(" + xpathLinea + ")[" + posicion + "]");
	}
	
	private static String getXPathLineaArticleByReference(String reference, Channel channel) {
		String xpathLinWithTag = getXPathLineaWithTagRef(channel);
		return xpathLinWithTag.replace(TagReference, reference);
	}
	
	private static String getXPathDataRelativeArticle(DataArtBolsa dataArt, Channel channel) {
		switch (channel) {
		case desktop:
		case tablet:
			return (getXPathDataRelativeArticleDesktop(dataArt));
		case mobile:
		default:
			return (getXPathDataRelativeArticleMobil(dataArt));
		}
	}
	
	private static String getXPathDataRelativeArticleDesktop(DataArtBolsa dataArt) {
		switch (dataArt) {
		case Nombre:
			return XPathNombreRelativeArticleDesktop;
		case Color:
			return XPathColorRelativeArticleDesktop;
		case Talla:
			return XPathTallaAlfRelativeArticleDesktop;
		case Cantidad:
			return XPathCantidadRelativeArticleDesktop;
		case PrecioEntero:
			return XPathPrecioEnteroRelativeArticleDesktop;
		case PrecioDecimal:
			return XPathPrecioDecimalRelativeArticleDesktop;
		default:
			return "";
			
		}
	}
	
	private static String getXPathDataRelativeArticleMobil(DataArtBolsa dataArt) {
		switch (dataArt) {
		case Nombre:
			return XPathNombreRelativeArticleMobil;
		case Color:
			return XPathColorRelativeArticleMobil;
		case Talla:
			return XPathTallaAlfRelativeArticleMobil;
		case Cantidad:
			return XPathCantidadRelativeArticleMobil;
		case PrecioEntero:
			return XPathPrecioEnteroRelativeArticleMobil;
		case PrecioDecimal:
			return XPathPrecioDecimalRelativeArticleMobil;
		default:
			return "";
			
		}
	}
	
    public static int getNumLinesArticles(Channel channel, WebDriver driver) {
		String xpathLinea = getXPathLinea(channel);
    	return (driver.findElements(By.xpath(xpathLinea)).size());
    }
	
    public static WebElement getLineaArticuloByPosicion(int posicion, Channel channel, WebDriver driver) {
    	String xpathArticle = getXPathLineaArticleByPosicion(posicion, channel);
		return (driver.findElement(By.xpath(xpathArticle)));
    }
    
	public static WebElement getLineaArticuloByReferencia(String reference, Channel channel, WebDriver driver) {
		String xpathArticle = getXPathLineaArticleByReference(reference, channel);
		return (driver.findElement(By.xpath(xpathArticle)));
	}
	
	private static String getDataArticle(DataArtBolsa typeData, WebElement lineaArticle, Channel channel) {
		if (!DataArtBolsa.getValuesValidForChannel(channel).contains(typeData)) {
			return "";
		}
		
		String xpathData = getXPathDataRelativeArticle(typeData, channel);
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
	
	private static float getPrecioArticle(WebElement lineaArticleWeb, Channel channel) {
		String parteEntera = getDataArticle(DataArtBolsa.PrecioEntero, lineaArticleWeb, channel);
		String parteDecimal = getDataArticle(DataArtBolsa.PrecioDecimal, lineaArticleWeb, channel);
		return (ImporteScreen.getFloatFromImporteMangoScreen(parteEntera + parteDecimal));        
	}
	
	private static String getReferenciaArticle(WebElement lineaArticleWeb, Channel channel) {
		String xpathLinRelative = getXPathLinkRelativeArticle(channel);
		WebElement link = lineaArticleWeb.findElement(By.xpath(xpathLinRelative));
		if (link==null) {
			return "";
		}
		return (UtilsTestMango.getReferenciaFromHref(link.getAttribute("href")));
	}

	
	public static ArticuloDataBolsaScreen getArticuloDataByPosicion(int posicion, Channel channel, WebDriver driver) {
		WebElement lineaArticleWeb = getLineaArticuloByPosicion(posicion, channel, driver);
		ArticuloDataBolsaScreen articleData = getArticuloBolsaData(lineaArticleWeb, channel); 
		return articleData;
	}
	
	public static ArticuloDataBolsaScreen getArticuloDataByReferencia(String reference, Channel channel, WebDriver driver) {
		WebElement lineaArticleWeb = getLineaArticuloByReferencia(reference, channel, driver);
		ArticuloDataBolsaScreen articleData = getArticuloBolsaData(lineaArticleWeb, channel);
		return articleData;
	}
	
	private static ArticuloDataBolsaScreen getArticuloBolsaData(WebElement lineaArticleWeb, Channel channel) {
		if (lineaArticleWeb==null) {
			return null;
		}
		
		ArticuloDataBolsaScreen articleData = new ArticuloDataBolsaScreen();
		articleData.referencia = getReferenciaArticle(lineaArticleWeb, channel);
		articleData.nombre = getDataArticle(DataArtBolsa.Nombre, lineaArticleWeb, channel);
		articleData.color = getDataArticle(DataArtBolsa.Color, lineaArticleWeb, channel);
		articleData.talla = Talla.from(getDataArticle(DataArtBolsa.Talla, lineaArticleWeb, channel));
		articleData.cantidad = getDataArticle(DataArtBolsa.Cantidad, lineaArticleWeb, channel);
		articleData.precio = getPrecioArticle(lineaArticleWeb, channel);
		return articleData;
	}
}
