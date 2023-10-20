package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktopNormal;
import com.mng.robotest.testslegacy.data.Talla;

public class SecTallasArticuloNormal extends SecTallasArticulo {

	private static final String XPATH_ARTICULO = PageGaleriaDesktopNormal.XPATH_ARTICULO;
	private static final String XPATH_CAPA_TALLAS_DESKTOP = "//div[@class[contains(.,'sizes-container')]]";
	private static final String XPATH_CAPA_TALLAS_DEVICE = "//ul[@data-testid='plp.sizesSelector.list']";
	private static final String XPATH_TALLA_AVAILABLE = "//button[@data-testid[contains(.,'size.available')]]";
	private static final String XPATH_TALLA_UNAVAILABLE = "//button[@data-testid[contains(.,'size.unavailable')]]";	
	
	public SecTallasArticuloNormal() {
		super(XPATH_ARTICULO);
	}
	
	private String getXPathCapaTallas() {
		if (channel.isDevice()) {
			return XPATH_CAPA_TALLAS_DEVICE;
		}
		return XPATH_CAPA_TALLAS_DESKTOP;
	}
	
	private String getXPathCapaTallas(int position) {
		if (channel.isDevice()) {
			return getXPathCapaTallas();
		}
		return getXPathArticulo(position) + getXPathCapaTallas();
	}

	private String getXPathArticleTallaAvailable(int posArticulo) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo);
		return xpathCapaTallas + XPATH_TALLA_AVAILABLE;
	}
	
	@Override
	public boolean isVisibleArticleCapaTallasUntil(int position, int seconds) {
		String xpathCapa = getXPathCapaTallas(position);
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
		return state(Visible, XPATH_TALLA_UNAVAILABLE).check();
	}
	
	@Override
	public void selectTallaArticleNotAvalaible() {
		click(XPATH_TALLA_UNAVAILABLE).exec();
	}	
	
	@Override
	public void bringSizesBack(WebElement articulo) {
		var sizes = articulo.findElement(By.xpath("." + getXPathCapaTallas()));
		bringElement(sizes, BringTo.BACKGROUND);
	}	
	
}
