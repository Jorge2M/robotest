package com.mng.robotest.tests.domains.galeria.pageobjects.article;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.mng.robotest.testslegacy.data.Talla;

public class SecTallasArticuloNormal extends SecTallasArticulo {

	private static final String XPATH_CAPA_TALLAS_ARTICULO_SHOP = "//div[@class[contains(.,'sizes-container')]]";
	private static final String CLASS_CAPA_ACTIVE_SHOP = "@class[contains(.,'active')]";
	private static final String XPATH_TALLA_AVAILABLE = "//button[@data-testid[contains(.,'size.available')]]";
	private static final String XPATH_TALLA_UNAVAILABLE = "//button[@data-testid[contains(.,'size.unavailable')]]";	
	
	public SecTallasArticuloNormal(String xpathArticulo) {
		super(xpathArticulo);
	}
	
	private String getXPathCapaTallas(int posArticulo, boolean capaVisible) {
		String xpathCapaAdd = getXPathArticleCapaInferiorDesktop(posArticulo);
		String classCapaActive = CLASS_CAPA_ACTIVE_SHOP;
		if (capaVisible) {
			return xpathCapaAdd + "//self::div[" + classCapaActive + "]";
		}
		return xpathCapaAdd + "//self::div[not(" + classCapaActive + ")]"; 
	}

	private String getXPathArticleTallaAvailable(int posArticulo) {
		String xpathCapaTallas = getXPathCapaTallas(posArticulo, true);
		return xpathCapaTallas + XPATH_TALLA_AVAILABLE;
	}
	
	private String getXPathArticleCapaInferiorDesktop(int position) {
		return getXPathArticulo(position) + XPATH_CAPA_TALLAS_ARTICULO_SHOP;
	}

	@Override
	public boolean isVisibleArticleCapaTallasUntil(int position, int seconds) {
		String xpathCapa = getXPathCapaTallas(position, true);
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
		String xpathTallaNoDipo = XPATH_TALLA_UNAVAILABLE;
		click(xpathTallaNoDipo).exec();
	}	
	
	@Override
	public void bringSizesBack(WebElement articulo) {
		var sizes = articulo.findElement(By.xpath("." + XPATH_CAPA_TALLAS_ARTICULO_SHOP));
		bringElement(sizes, BringTo.BACKGROUND);
	}	
	
}
