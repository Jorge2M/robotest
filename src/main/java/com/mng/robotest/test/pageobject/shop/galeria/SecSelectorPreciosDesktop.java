package com.mng.robotest.test.pageobject.shop.galeria;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.shop.filtros.SecFiltrosDesktop;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.PageBase;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


/**
 * Page Object correspondiente al selector de precios de Desktop
 * @author jorge.munoz
 *
 */
public class SecSelectorPreciosDesktop extends PageBase {
	
	public enum TypeClick {left, right}
	
	private final AppEcom app;
	
	private static String XPathLineaFiltroShop = "//div[@class[contains(.,'input-range__track--background')]]"; //
	private static String XPathImporteMinimoShop = "(" + XPathLineaFiltroShop + "//span[@class[contains(.,'label-container')]])[1]"; //
	private static String XPathImporteMaximoShop = "(" + XPathLineaFiltroShop + "//span[@class[contains(.,'label-container')]])[2]"; //
	private static String XPathFiltroWrapperShop = "//div[@class='input-range']"; //
	private static String XPathLeftCornerShop = XPathImporteMinimoShop + "/../..";
	private static String XPathRightCornerShop = XPathImporteMaximoShop + "/../..";
	
	public SecSelectorPreciosDesktop(AppEcom app, WebDriver driver) {
		super(driver);
		this.app = app;
	}

	public boolean isVisible() {
		By byLineaFiltro = By.xpath(XPathLineaFiltroShop);
		PageGaleria pageGaleria = PageGaleria.getNew(Channel.desktop, app);
		SecFiltrosDesktop secFiltros = SecFiltrosDesktop.getInstance(pageGaleria, driver);
		secFiltros.showFilters();
		boolean visible = state(Visible, byLineaFiltro).check();
		secFiltros.hideFilters();
		return visible;
	}

	public int getImporteMinimo() {
		By byImporteMinimo = By.xpath(XPathImporteMinimoShop);
		Integer valueOf = Integer.valueOf(driver.findElement(byImporteMinimo).getText());
		return valueOf.intValue();
	}

	public int getImporteMaximo() {
		By byImporteMaximo = By.xpath(XPathImporteMaximoShop);
		Integer valueOf = Integer.valueOf(driver.findElement(byImporteMaximo).getText());
		return valueOf.intValue();
	}

	/**
	 * Seleccionamos un mínimo (click por la izquierda del buscador) y un máximo (click por la derecha del buscador)
	 * @param margenPixelsIzquierda indica los píxels desde la izquierda del selector donde ejecutaremos el click para definir un mínimo
	 * @param margenPixelsDerecha indica los píxels desde la derecha del selector donde ejecutaremos el click para definir un máximo
	 */
	public void clickMinAndMax(int margenPixelsIzquierda, int margenPixelsDerecha) throws Exception {
		click(TypeClick.right, -30);
		click(TypeClick.left, 30);
	}

	private void click(TypeClick typeClick, int pixelsFromCorner) throws Exception {
		Actions builder = new Actions(driver);
		moveToCornerSelector(TypeClick.right);
		Thread.sleep(2000);
		moveToCornerSelector(typeClick);
		waitForPageLoaded(driver);
		builder.moveByOffset(pixelsFromCorner, 0).click().build().perform();
		waitForPageLoaded(driver);
	}

	private void moveToCornerSelector(TypeClick typeCorner) throws Exception {
		waitForPageLoaded(driver);
		moveToElement(By.xpath(XPathFiltroWrapperShop), driver);
		switch (typeCorner) {
		case left: 
			moveToElement(By.xpath(XPathLeftCornerShop), driver);
			break;
		case right:
			moveToElement(By.xpath(XPathRightCornerShop), driver);
		}
	}
}
