package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktopKondo;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDevice;
import com.mng.robotest.testslegacy.data.Talla;

public class SecTallasArticuloKondo extends SecTallasArticulo {

	private static final String XPATH_ARTICULO_DESKTOP = PageGaleriaDesktopKondo.XPATH_ARTICULO;
	private static final String XPATH_ARTICULO_DEVICE = PageGaleriaDevice.XPATH_ARTICULO;
	
	private static final String XPATH_CAPA_TALLAS_ARTICULO_SHOP = "//*[@data-testid='plp.productSizeSelector.panel']/div";
	private static final String XPATH_TALLA_AVAILABLE = "//button[@data-testid[contains(.,'size.available')]]";
	private static final String XPATH_TALLA_UNAVAILABLE = "//button[@data-testid[contains(.,'size.unavailable')]]";	
	
	public SecTallasArticuloKondo(Channel channel) {
		super(getXPathArticulo(channel));
	}
	
	private static String getXPathArticulo(Channel channel) {
		if (channel.isDevice()) {
			return XPATH_ARTICULO_DEVICE;
		}
		return XPATH_ARTICULO_DESKTOP;
	}	

	private String getXPathArticleTallaAvailable(int position) {
		String xpathArticulo = getXPathArticulo(position) + XPATH_CAPA_TALLAS_ARTICULO_SHOP;
		return xpathArticulo + XPATH_TALLA_AVAILABLE;
	}
	
	@Override
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		String xpathCapa = getXPathArticleTallaAvailable(posArticulo);
		return state(Visible, xpathCapa).wait(seconds).check();
	}
	
	@Override
	public Talla selectTallaAvailableArticle(int posArticulo) throws Exception {
		String xpathTalla = getXPathArticleTallaAvailable(posArticulo);
		if (state(Visible, xpathTalla).check()) {
			var tallaToSelect = getElement(xpathTalla);
			tallaToSelect.click();
			return Talla.fromLabel(tallaToSelect.getText());
		}
		return null;
	}
	
	@Override
	public boolean isVisibleTallaNotAvailable() {
		String xpathTallaNoDipo = XPATH_TALLA_UNAVAILABLE;
		return state(Visible, xpathTallaNoDipo).check();
	}	
	
	@Override
	public void selectTallaArticleNotAvalaible() {
		click(XPATH_TALLA_UNAVAILABLE).exec();
	}	
	
	@Override
	public void bringSizesBack(WebElement articulo) {
		var sizes = articulo.findElement(By.xpath("." + XPATH_CAPA_TALLAS_ARTICULO_SHOP));
		bringElement(sizes, BringTo.BACKGROUND);
	}	

}