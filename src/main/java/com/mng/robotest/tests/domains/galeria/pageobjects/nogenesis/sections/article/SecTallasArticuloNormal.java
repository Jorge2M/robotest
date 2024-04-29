package com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.sections.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.galeria.pageobjects.nogenesis.CommonGaleriaNoGenesis;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.data.Talla;

public class SecTallasArticuloNormal extends SecTallasArticulo {

	private static final String XP_ARTICULO_DESKTOP = CommonGaleriaNoGenesis.XP_ARTICULO;
	private static final String XP_ARTICULO_DEVICE = CommonGaleriaNoGenesis.XP_ARTICULO;
	
	private static final String XP_CAPA_TALLAS_ARTICULO_DESKTOP = "//*[@data-testid='plp.productSizeSelector.panel']/div";
	private static final String XP_TALLA_AVAILABLE_DESKTOP = XP_CAPA_TALLAS_ARTICULO_DESKTOP + "//button[@data-testid[contains(.,'size.available')]]";
	private static final String XP_TALLA_UNAVAILABLE_DESKTOP = XP_CAPA_TALLAS_ARTICULO_DESKTOP + "//button[@data-testid[contains(.,'size.unavailable')]]";
	
	//TODO Galería Kondo (19-10-23) hay un problema en mobile, las tallas no disponibles se muestran con data-testid='plp.sizeSelector.size.available'
	private static final String XP_CAPA_TALLAS_ARTICULO_DEVICE = "//*[@data-testid='productCard.sizesSelector.sheet.list']"; 
	private static final String XP_TALLA_AVAILABLE_DEVICE = XP_CAPA_TALLAS_ARTICULO_DEVICE + "//button/span[not(@class[contains(.,'NG6pA')])]/..";
	private static final String XP_TALLA_UNAVAILABLE_DEVICE = XP_CAPA_TALLAS_ARTICULO_DEVICE + "//button/span[@class[contains(.,'NG6pA')]]/..";
	
	public SecTallasArticuloNormal(Channel channel) {
		super(getXPathArticulo(channel));
	}
	
	private static String getXPathArticulo(Channel channel) {
		if (channel.isDevice()) {
			return XP_ARTICULO_DEVICE;
		}
		return XP_ARTICULO_DESKTOP;
	}
	
	private String getXPathCapaTallasArticulo() {
		if (channel.isDevice()) {
			return XP_CAPA_TALLAS_ARTICULO_DEVICE;
		} else {
			return XP_CAPA_TALLAS_ARTICULO_DESKTOP;
		}
	}
	private String getXPathCapaTallasArticulo(int position) {
		return "(" + getXPathCapaTallasArticulo() + ")[" + position + "]";
	}
	
	private String getXPathTallaAvailable() {
		if (channel.isDevice()) {
			return XP_TALLA_AVAILABLE_DEVICE;
		}
		return XP_TALLA_AVAILABLE_DESKTOP;
	}
	private String getXPathTallaUnavailable() {
		if (channel.isDevice()) {
			return XP_TALLA_UNAVAILABLE_DEVICE;
		}
		return XP_TALLA_UNAVAILABLE_DESKTOP;
	}	
	
	private String getXPathArticleTallaAvailable(int position) {
		if (channel.isDevice()) {
			return getXPathTallaAvailable();
		}
		return getXPathArticulo(position) + getXPathTallaAvailable();
	}
	
	@Override
	public boolean isVisibleArticleCapaTallasUntil(int posArticulo, int seconds) {
		String xpathCapa = getXPathArticleTallaAvailable(posArticulo);
		return state(VISIBLE, xpathCapa).wait(seconds).check();
	}
	
	@Override
	public Talla selectTallaAvailableArticle(int posArticulo) throws Exception {
		String xpathTalla = getXPathArticleTallaAvailable(posArticulo);
		if (state(VISIBLE, xpathTalla).check()) {
			var tallaToSelect = getElement(xpathTalla);
			tallaToSelect.click();
			return Talla.fromLabel(tallaToSelect.getText(), PaisShop.from(dataTest.getCodigoPais()));
		}
		return null;
	}
	
	@Override
	public boolean isVisibleTallaNotAvailable() {
		return state(VISIBLE, getXPathTallaUnavailable()).check();
	}	
	
	@Override
	public void selectTallaArticleNotAvalaible() {
		for (int i=1; i<10; i++) {
			if (isUnavailableSizeInArticle(i)) {
				click(getXPathTallaUnavailable()).exec();
				return;
			}
		}
	}	
	
	private boolean isUnavailableSizeInArticle(int position) {
		moveToElement(getXPathArticulo(position));
		moveToElement(getXPathCapaTallasArticulo(position));
		keyDown(2); //Without this fails (real problem in production) (20-10-23)
		return state(VISIBLE, getXPathTallaUnavailable()).wait(1).check();
	}
	
	@Override
	public void bringSizesBack(WebElement articulo) {
		var sizes = articulo.findElement(By.xpath("." + getXPathCapaTallasArticulo()));
		bringElement(sizes, BringTo.BACKGROUND);
	}	

}