package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.filtros.FilterOrdenacion;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecPreciosArticulo extends PageObjTM {
	
	public enum TipoPrecio {
		precio_inicial_tachado (
			"//span[@class[contains(.,'price-crossed-1')]]",
			"//span[@class[contains(.,'price-crossed-1')] or @class='tAcLx']",
			"//span[@class[contains(.,'product-price-crossed')]]",
			"//span[@class[contains(.,'price-text--through')]]"),
		precio_2o_tachado (
			"//span[@class[contains(.,'price-crossed-2')]]",
			"//span[@class[contains(.,'price-crossed-2')]]", //?
			"//span[@class[contains(.,'product-price-crossed')]][2]",
			"//span[@class[contains(.,'price-text--through')]][2]"),
		precio_rebajado_definitivo (
			"//div[@class[contains(.,'prices-container')]]//span[@class[contains(.,'price-sale')]]",
			
			//"//div[@class[contains(.,'prices-container')]]//span[@class[contains(.,'price-sale')]]",
			"//span[@class='B16Le']", //TODO a la espera de los cambios de Sergio Campillo
			
			"//div[@class[contains(.,'prices--cross')]]/span[@class='product-price']",
			"//span[@class[contains(.,'info-price-sale')] or @class='product-price']"),
		precio_no_rebajado_definitivo (
			"//div[@class='_3wfbJ' or not(@class)]/span[@class[contains(.,'price-sale')]]", //El not(@class) es debido al nuevo desarrollo en Cloud (04-febrero-2020)
			"//div[@class='_3wfbJ' or not(@class)]/span[@class[contains(.,'price-sale')]]", //El not(@class) es debido al nuevo desarrollo en Cloud (04-febrero-2020)
			"//div[@class='product-prices']/span[@class='product-price']",
			"//span[@class[contains(.,'info-price-sale')] or @class='product-price']"); //?
		
		String xpathShopDesktop;
		String xpathOutletDesktop;
		String xpathShopMovil;
		String xpathOutletMovil;
		private TipoPrecio(String xpathShopDesktop, String xpathOutletDesktop, String xpathShopMovil, String xpathOutletMovil) {
			this.xpathShopDesktop = xpathShopDesktop;
			this.xpathOutletDesktop = xpathOutletDesktop;
			this.xpathShopMovil = xpathShopMovil;
			this.xpathOutletMovil = xpathOutletMovil;
		}
		public String getXPath(Channel channel, AppEcom app) {
			if (app==AppEcom.outlet) {
				if (channel==Channel.mobile) {
					return xpathOutletMovil;
				}
				return xpathOutletDesktop;
			}
			if (channel==Channel.mobile) {
				return xpathShopMovil;
			}
			return xpathShopDesktop;
		}
	}

	private final Channel channel;
	private final AppEcom app;

	public SecPreciosArticulo(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
	}
	
	public String getXPathPrecioArticulo(TypeArticle typeArticle) {
		switch (typeArticle) {
		case rebajado:
			return TipoPrecio.precio_rebajado_definitivo.getXPath(channel, app);
		case norebajado:
		default:
			return TipoPrecio.precio_no_rebajado_definitivo.getXPath(channel, app);
		}
	}
	
	public String getPrecioDefinitivo(WebElement articulo) {
		return getPrecioDefinitivoElem(articulo).getText();
	}
	public WebElement getPrecioDefinitivoElem(WebElement articulo) {
		if (isArticleRebajado(articulo)) {
			return getPrecioElem(articulo, TipoPrecio.precio_rebajado_definitivo);
		}
		return getPrecioElem(articulo, TipoPrecio.precio_no_rebajado_definitivo);
	}
	public WebElement getPrecioElem(WebElement articulo, TipoPrecio tipoPrecio) {
		By byPrecio = By.xpath("." + tipoPrecio.getXPath(channel, app));
		return (articulo.findElement(byPrecio));
	}
	
	public boolean isArticleRebajado(WebElement articulo) {
		By byPrecioRebajado = By.xpath("." + TipoPrecio.precio_rebajado_definitivo.getXPath(channel, app));
		return (state(Present, byPrecioRebajado).check());
	}

    public List<WebElement> getListaPreciosPrendas(List<WebElement> listArticles) throws Exception {
		List<WebElement> listPrecios = new ArrayList<WebElement>();
		waitForPageLoaded(driver);
		for (WebElement articulo : listArticles) {
			//WebElement precio = getElementVisible(articulo, By.xpath("." + XPathPrecioDefinitivoRelativeArticle));
			WebElement precio = getPrecioDefinitivoElem(articulo);
			if (precio!=null) {
				listPrecios.add(precio);
			}
		}
		return listPrecios;
	}

	public String getAnyPrecioNotInOrder(FilterOrdenacion typeOrden, List<WebElement> listArticles) throws Exception {
		List<WebElement> listaPreciosPrendas = getListaPreciosPrendas(listArticles);
		float precioAnt = 0;
		if (typeOrden==FilterOrdenacion.PrecioDesc) {
			precioAnt = 9999999;
		}

		for (WebElement prendaPrecio : listaPreciosPrendas) {
			String entero = prendaPrecio.getText();
			float precioActual = Float.valueOf(entero.replace(",",".").replaceAll("[^\\d.]", "")).floatValue();
			if (typeOrden==FilterOrdenacion.PrecioAsc) {
				if (precioActual < precioAnt) {
					return (precioAnt + "->" + precioActual);
				}
			} else {
				if (precioActual > precioAnt) {
					return (precioAnt + "->" + precioActual);
				}
			}

			precioAnt = precioActual;
		}

		return "";
	}

	public boolean preciosInIntervalo(int minimo, int maximo, List<WebElement> listaArticles) throws Exception {
		waitForPageLoaded(driver);
		List<WebElement> listaPreciosPrendas = getListaPreciosPrendas(listaArticles);
		for (WebElement prendaPrecio : listaPreciosPrendas) {
			String entero = prendaPrecio.getText();
			float precioActual = Float.valueOf(entero.replace(",",".").replaceAll("[^\\d.]", "")).floatValue();
			if (precioActual < minimo || precioActual > maximo) {
				return false;
			}
		}

		return true;
	}
}
