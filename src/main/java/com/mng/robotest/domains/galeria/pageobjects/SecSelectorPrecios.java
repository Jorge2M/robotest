package com.mng.robotest.domains.galeria.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.github.jorge2m.testmaker.conf.Channel;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSelectorPrecios extends PageBase {
	
	public enum TypeClick { LEFT, RIGHT }
	
	private static final String XPATH_LINEA_FILTRO_SHOP = "//div[@class[contains(.,'input-range__track--background')]]"; //
	private static final String XPATH_IMPORTE_MINIMO_SHOP = "(" + XPATH_LINEA_FILTRO_SHOP + "//span[@class[contains(.,'label-container')]])[1]"; //
	private static final String XPATH_IMPORTE_MAXIMO_SHOP = "(" + XPATH_LINEA_FILTRO_SHOP + "//span[@class[contains(.,'label-container')]])[2]"; //
	private static final String XPATH_FILTRO_WRAPPER_SHOP = "//div[@class='input-range']"; //
	private static final String XPATH_LEFT_CORNER_SHOP = XPATH_IMPORTE_MINIMO_SHOP + "/../..";
	private static final String XPATH_RIGHT_CORNER_SHOP = XPATH_IMPORTE_MAXIMO_SHOP + "/../..";
	
	public boolean isVisible() {
		By byLineaFiltro = By.xpath(XPATH_LINEA_FILTRO_SHOP);
		if (channel==Channel.desktop) {
			PageGaleria pageGaleria = PageGaleria.getNew(channel);
			SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria);
			secFiltros.showFilters();
			boolean visible = state(Visible, byLineaFiltro).check();
			secFiltros.hideFilters();
			return visible;
		}
		return state(Visible, byLineaFiltro).check();
	}

	public int getImporteMinimo() {
		By byImporteMinimo = By.xpath(XPATH_IMPORTE_MINIMO_SHOP);
		Integer valueOf = Integer.valueOf(driver.findElement(byImporteMinimo).getText());
		return valueOf.intValue();
	}

	public int getImporteMaximo() {
		By byImporteMaximo = By.xpath(XPATH_IMPORTE_MAXIMO_SHOP);
		Integer valueOf = Integer.valueOf(driver.findElement(byImporteMaximo).getText());
		return valueOf.intValue();
	}

	/**
	 * Seleccionamos un mínimo (click por la izquierda del buscador) y un máximo (click por la derecha del buscador)
	 * @param margenPixelsIzquierda indica los píxels desde la izquierda del selector donde ejecutaremos el click para definir un mínimo
	 * @param margenPixelsDerecha indica los píxels desde la derecha del selector donde ejecutaremos el click para definir un máximo
	 */
	public void clickMinAndMax(int margenPixelsIzquierda, int margenPixelsDerecha) throws Exception {
		click(TypeClick.RIGHT, -30);
		click(TypeClick.LEFT, 30);
	}

	private void click(TypeClick typeClick, int pixelsFromCorner) {
		Actions builder = new Actions(driver);
		moveToCornerSelector(TypeClick.RIGHT);
		waitMillis(2000);
		moveToCornerSelector(typeClick);
		waitLoadPage();
		builder.moveByOffset(pixelsFromCorner, 0).click().build().perform();
		waitLoadPage();
	}

	private void moveToCornerSelector(TypeClick typeCorner) {
		waitForPageLoaded(driver);
		moveToElement(XPATH_FILTRO_WRAPPER_SHOP);
		switch (typeCorner) {
		case LEFT: 
			moveToElement(XPATH_LEFT_CORNER_SHOP);
			break;
		case RIGHT:
			moveToElement(XPATH_RIGHT_CORNER_SHOP);
		}
	}
}
