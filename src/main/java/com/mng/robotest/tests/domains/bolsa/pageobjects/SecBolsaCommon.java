package com.mng.robotest.tests.domains.bolsa.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabeceraMostFrequent;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaCommon.StateBolsa.*;

public abstract class SecBolsaCommon extends PageBase {

	public enum StateBolsa { OPEN, CLOSED }
	
	abstract String getXPathPanelBolsa();
	abstract String getXPathBotonComprar();
	abstract String getXPathPrecioSubTotal();
	abstract String getXPathPrecioTransporte();
	public abstract String getPrecioSubTotal();
	public abstract String getPrecioTransporte();
	public abstract void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected);
	public abstract LineasArticuloBolsa getLineasArtBolsa();
	
	private static final String XPATH_ASPA = "//span[@class[contains(.,'outline-close')]]";
	
	public boolean isInStateUntil(StateBolsa stateBolsaExpected, int seconds) {
		String xpath = getXPathPanelBolsa();
		if (stateBolsaExpected==OPEN) {
			return state(Visible, xpath).wait(seconds).check();
		}
		return state(Invisible, xpath).wait(seconds).check();
	}

	public boolean isVisibleBotonComprar() {
		String xpathComprarBt = getXPathBotonComprar();
		return state(Visible, xpathComprarBt).check();
	}

	public boolean isVisibleBotonComprarUntil(int seconds) { 
		String xpathBoton = getXPathBotonComprar();
		return state(Visible, xpathBoton).wait(seconds).check();
	}

	public void clickBotonComprar( int secondsWait) {
		String xpathComprarBt = getXPathBotonComprar();
		state(Visible, xpathComprarBt).wait(secondsWait).check();
		click(xpathComprarBt).type(TypeClick.javascript).exec();
	}
	
	public String getNumberArtIcono() {
		return (new SecCabeceraMostFrequent().getNumberArtIcono());
	}
	
	
	public boolean numberItemsIs(String items) {
		return numberItemsIsUntil(items, 0);
	}
	public boolean numberItemsIsUntil(String items, int seconds) {
		for (int i=0; i<=seconds; i++) {
			String itemsPantalla = getNumberArtIcono();
			if (items.compareTo(itemsPantalla)==0) {
				return true;
			}
			waitMillis(1000);
		}
		return false;
	}

	public String getPrecioSubtotalTextPant() {
		String xpathImporte = getXPathPrecioSubTotal();
		return getElement(xpathImporte).getText();
	}
	
	public float getPrecioSubTotalFloat() {
		String precioTotal = getPrecioSubTotal();
		return (ImporteScreen.getFloatFromImporteMangoScreen(precioTotal));
	}

	public float getPrecioTransporteFloat() {
		String precioTotal = getPrecioTransporte();
		return (ImporteScreen.getFloatFromImporteMangoScreen(precioTotal));
	}
	
	public boolean isNotThisImporteTotalUntil(String importeSubTotalPrevio, int seconds) {
		String xpathImporte = getXPathPrecioSubTotal();
		try {
			ExpectedCondition<Boolean> expected = ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpathImporte), importeSubTotalPrevio));
			new WebDriverWait(driver, Duration.ofSeconds(seconds)).until(expected);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public void clearArticulos() {
		setBolsaToStateIfNotYet(OPEN);
		int ii = 0;
		do {
			int numArticulos = getLineasArtBolsa().getNumLinesArticles();
			int i = 0;
			while (numArticulos > 0 && i < 50) { 
				try {
					getLineasArtBolsa().clickRemoveArticleIfExists();
				} catch (Exception e) {
					if (i==49) {
						Log4jTM.getLogger().warn(
							"Problem clearing articles from Bag. {}. {}", e.getClass().getName(), e.getMessage());
					}
				}

				try {
					state(Present, By.className("bagItem")).wait(3).check();
					numArticulos = getLineasArtBolsa().getNumLinesArticles();
				} 
				catch (Exception e) {
					Log4jTM.getLogger().debug(
						"Problem getting num articles in Bag. {}. {}" , e.getClass().getName(), e.getMessage());
					numArticulos = 0;
				}
				i += 1;
			}
			ii += 1;
		}
		while (!numberItemsIsUntil("0", 0) && ii<10);

		setBolsaToStateIfNotYet(CLOSED);
	}
	
	public void click1erArticuloBolsa() {
		getLineasArtBolsa().clickArticle(1);
		waitLoadPage();
	}
	
	public void closeInMobil() {
		if (app==AppEcom.outlet) {
			click(XPATH_ASPA).exec();
		} else {
			setBolsaToStateIfNotYet(CLOSED);
		}
	}
	
}
