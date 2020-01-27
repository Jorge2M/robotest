package com.mng.robotest.test80.mango.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class SecPreciosArticuloDesktop extends WebdrvWrapp {
	
	public enum TipoPrecio {
		precio_inicial_tachado (
			"//span[@class='tAcLx']",
			"//span[@class[contains(.,'product-list-price')] and @class[contains(.,'line-through')]]"),
		precio_2o_tachado (
			"//span[@class='?']",
			"//span[@class[contains(.,'product-list-price')] and @class[contains(.,'line-through')]][2]"),
		precio_rebajado_definitivo (
			"//div[@class[contains(.,'_2-Zal')]]/span[@class='B16Le']",
			"//span[@class='product-list-sale-price']"),
		precio_no_rebajado_definitivo (
			"//div[@class='_3wfbJ']/span[@class='B16Le']",
			"//span[@class[contains(.,'product-list-price') and not(@class[contains(.,'line-through')])]]");
		
		String xpathShop;
		String xpathOutlet;
		private TipoPrecio(String xpathShop, String xpathOutlet) {
			this.xpathShop = xpathShop;
			this.xpathOutlet = xpathOutlet;
		}
		public String getXPath(AppEcom app) {
			if (app==AppEcom.outlet) {
				return xpathOutlet;
			}
			return xpathShop;
		}
	}

	private final AppEcom app;

	public SecPreciosArticuloDesktop(AppEcom app) {
		this.app = app;
	}
	
	public String getXPathPrecioArticulo(TypeArticle typeArticle) {
		switch (typeArticle) {
		case rebajado:
			return TipoPrecio.precio_rebajado_definitivo.getXPath(app);
		case norebajado:
		default:
			return TipoPrecio.precio_no_rebajado_definitivo.getXPath(app);
		}
	}
	
	public String getPrecioDefinitivo(WebElement articulo) {
		if (isArticleRebajado(articulo)) {
			return getPrecio(articulo, TipoPrecio.precio_rebajado_definitivo);
		}
		return getPrecio(articulo, TipoPrecio.precio_no_rebajado_definitivo);
	}
	
	public String getPrecio(WebElement articulo, TipoPrecio tipoPrecio) {
		By byPrecio = By.xpath("." + tipoPrecio.getXPath(app));
		return (articulo.findElement(byPrecio).getText());
	}
	
	public boolean isArticleRebajado(WebElement articulo) {
		By byPrecioRebajado = By.xpath("." + TipoPrecio.precio_rebajado_definitivo.getXPath(app));
		return (isElementPresent(articulo, byPrecioRebajado));
	}
}
