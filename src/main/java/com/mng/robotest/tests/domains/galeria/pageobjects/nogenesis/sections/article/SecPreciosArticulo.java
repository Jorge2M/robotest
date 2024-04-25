package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.article;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.commons.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.filters.FilterOrdenacion;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecPreciosArticulo extends PageBase {
	
	public enum TipoPrecio {
		PRECIO_INICIAL_TACHADO("//span[@data-testid[contains(.,'crossedOutPrice')]]"),
		PRECIO_2O_TACHADO("//span[@data-testid='crossedOutPrice-2']"),
		PRECIO_REBAJADO_DEFINITIVO("//span[@data-testid='currentPrice']"),
		PRECIO_NO_REBAJADO_DEFINITIVO("//span[@data-testid='currentPrice']");
		
		final String xpath;
		private TipoPrecio(String xpath) {
			this.xpath = xpath;
		}
		public String getXPath() {
			return xpath;
		}
	}

	public String getXPathPrecioArticulo(TypeArticle typeArticle) {
		switch (typeArticle) {
		case REBAJADO:
			return TipoPrecio.PRECIO_REBAJADO_DEFINITIVO.getXPath();
		case NO_REBAJADO:
		default:
			return TipoPrecio.PRECIO_NO_REBAJADO_DEFINITIVO.getXPath();
		}
	}
	
	public String getPrecioDefinitivo(WebElement articulo) {
		return getPrecioDefinitivoElem(articulo).getText();
	}
	public WebElement getPrecioDefinitivoElem(WebElement articulo) {
		if (isArticleRebajado(articulo)) {
			return getPrecioElem(articulo, TipoPrecio.PRECIO_REBAJADO_DEFINITIVO);
		}
		return getPrecioElem(articulo, TipoPrecio.PRECIO_NO_REBAJADO_DEFINITIVO);
	}
	public WebElement getPrecioElem(WebElement articulo, TipoPrecio tipoPrecio) {
		return getElement(articulo, tipoPrecio.getXPath()); 
	}
	
	public boolean isArticleRebajado(WebElement articulo) {
		By byPrecioRebajado = By.xpath("." + TipoPrecio.PRECIO_REBAJADO_DEFINITIVO.getXPath());
		return state(PRESENT, articulo).by(byPrecioRebajado).check();
	}

	public List<WebElement> getListaPreciosPrendas(List<WebElement> listArticles) {
		List<WebElement> listPrecios = new ArrayList<>();
		waitLoadPage();
		for (var articulo : listArticles) {
			//WebElement precio = getElementVisible(articulo, By.xpath("." + XPathPrecioDefinitivoRelativeArticle));
			var precio = getPrecioDefinitivoElem(articulo);
			if (precio!=null) {
				listPrecios.add(precio);
			}
		}
		return listPrecios;
	}

	public String getAnyPrecioNotInOrder(FilterOrdenacion typeOrden, List<WebElement> listArticles) throws Exception {
		List<WebElement> listaPreciosPrendas = getListaPreciosPrendas(listArticles);
		float precioAnt = 0;
		if (typeOrden==FilterOrdenacion.PRECIO_DESC) {
			precioAnt = 9999999;
		}

		for (WebElement prendaPrecio : listaPreciosPrendas) {
			String entero = prendaPrecio.getText();
			float precioActual = Float.parseFloat(entero.replace(",",".").replaceAll("[^\\d.]", ""));
			if (typeOrden==FilterOrdenacion.PRECIO_ASC) {
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
		waitLoadPage();
		List<WebElement> listaPreciosPrendas = getListaPreciosPrendas(listaArticles);
		for (WebElement prendaPrecio : listaPreciosPrendas) {
			String entero = prendaPrecio.getText();
			float precioActual = Float.parseFloat(entero.replace(",",".").replaceAll("[^\\d.]", ""));
			if (precioActual < minimo || precioActual > maximo) {
				return false;
			}
		}
		return true;
	}
}
