package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.galeria.pageobjects.CommonGaleriaKondo;
import com.mng.robotest.testslegacy.data.Talla;

public class SecTallasArticuloKondo extends SecTallasArticulo {

	private static final String XPATH_ARTICULO_DESKTOP = CommonGaleriaKondo.XPATH_ARTICULO;
	private static final String XPATH_ARTICULO_DEVICE = CommonGaleriaKondo.XPATH_ARTICULO;
	
	private static final String XPATH_CAPA_TALLAS_ARTICULO_DESKTOP = "//*[@data-testid='plp.productSizeSelector.panel']/div";
	private static final String XPATH_TALLA_AVAILABLE_DESKTOP = XPATH_CAPA_TALLAS_ARTICULO_DESKTOP + "//button[@data-testid[contains(.,'size.available')]]";
	private static final String XPATH_TALLA_UNAVAILABLE_DESKTOP = XPATH_CAPA_TALLAS_ARTICULO_DESKTOP + "//button[@data-testid[contains(.,'size.unavailable')]]";
	
	//TODO Galería Kondo (19-10-23) hay un problema en mobile, las tallas no disponibles se muestran con data-testid='plp.sizeSelector.size.available'
	private static final String XPATH_CAPA_TALLAS_ARTICULO_DEVICE = "//*[@data-testid='productCard.sizesSelector.sheet.list']"; 
	private static final String XPATH_TALLA_AVAILABLE_DEVICE = XPATH_CAPA_TALLAS_ARTICULO_DEVICE + "//button/span[not(@class[contains(.,'NG6pA')])]/..";
	private static final String XPATH_TALLA_UNAVAILABLE_DEVICE = XPATH_CAPA_TALLAS_ARTICULO_DEVICE + "//button/span[@class[contains(.,'NG6pA')]]/..";
	
	public SecTallasArticuloKondo(Channel channel) {
		super(getXPathArticulo(channel));
	}
	
	private static String getXPathArticulo(Channel channel) {
		if (channel.isDevice()) {
			return XPATH_ARTICULO_DEVICE;
		}
		return XPATH_ARTICULO_DESKTOP;
	}
	
	private String getXPathCapaTallasArticulo() {
		if (channel.isDevice()) {
			return XPATH_CAPA_TALLAS_ARTICULO_DEVICE;
		} else {
			return XPATH_CAPA_TALLAS_ARTICULO_DESKTOP;
		}
	}
	
	private String getXPathTallaAvailable() {
		if (channel.isDevice()) {
			return XPATH_TALLA_AVAILABLE_DEVICE;
		}
		return XPATH_TALLA_AVAILABLE_DESKTOP;
	}
	private String getXPathTallaUnavailable() {
		if (channel.isDevice()) {
			return XPATH_TALLA_UNAVAILABLE_DEVICE;
		}
		return XPATH_TALLA_UNAVAILABLE_DESKTOP;
	}	
	
	private String getXPathArticleTallaAvailable(int position) {
		return /*getXPathArticulo(position) + */getXPathTallaAvailable();
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
		return state(Visible, getXPathTallaUnavailable()).check();
	}	
	
	@Override
	public void selectTallaArticleNotAvalaible() {
		click(getXPathTallaUnavailable()).exec();
	}	
	
	@Override
	public void bringSizesBack(WebElement articulo) {
		var sizes = articulo.findElement(By.xpath("." + getXPathCapaTallasArticulo()));
		bringElement(sizes, BringTo.BACKGROUND);
	}	

}