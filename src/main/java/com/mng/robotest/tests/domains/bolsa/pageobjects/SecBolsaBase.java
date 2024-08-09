package com.mng.robotest.tests.domains.bolsa.pageobjects;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.transversal.cabecera.pageobjects.SecCabecera;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.bolsa.pageobjects.SecBolsaBase.StateBolsa.*;

public abstract class SecBolsaBase extends PageBase {

	public enum StateBolsa { OPEN, CLOSED }
	
	abstract String getXPathPanelBolsa();
	abstract String getXPathBotonComprar();
	abstract String getXPathPrecioSubTotal();
	abstract String getXPathPrecioTransporte();
	public abstract String getPrecioSubTotal();
	public abstract String getPrecioTransporte();
	public abstract void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected);
	public abstract LineasArticuloBolsa getLineasArtBolsa();
	public abstract void addArticleToFavorites();
	
	private static final String XP_ASPA = "//span[@class[contains(.,'outline-close')]]";
	
	public boolean isInStateUntil(StateBolsa stateBolsaExpected, int seconds) {
		String xpath = getXPathPanelBolsa();
		if (stateBolsaExpected == OPEN) {
			return state(VISIBLE, xpath).wait(seconds).check();
		}
		return state(INVISIBLE, xpath).wait(seconds).check();
	}

	public boolean isVisibleBotonComprar() {
		String xpathComprarBt = getXPathBotonComprar();
		return state(VISIBLE, xpathComprarBt).check();
	}

	public boolean isVisibleBotonComprarUntil(int seconds) { 
		String xpathBoton = getXPathBotonComprar();
		return state(VISIBLE, xpathBoton).wait(seconds).check();
	}

	public void clickBotonComprar( int secondsWait) {
		String xpathComprarBt = getXPathBotonComprar();
		state(VISIBLE, xpathComprarBt).wait(secondsWait).check();
		click(xpathComprarBt).type(JAVASCRIPT).exec();
	}
	
	public String getNumberArtIcono() {
		return SecCabecera.make().getNumberArtIcono();
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
		if (!state(VISIBLE, xpathImporte).check()) {
			return "0";
		}
		return getElement(xpathImporte).getText();
	}
	
	public float getPrecioSubTotalFloat() {
		String precioTotal = getPrecioSubTotal();
		return ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
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
	    int intentos = 0;
	    while (intentos < 10 && !numberItemsIsUntil("0", 0)) {
	        clearArticulosIteracion();
	        intentos++;
	    }
	    setBolsaToStateIfNotYet(CLOSED);
	}

	private void clearArticulosIteracion() {
	    int intentos = 0;
	    int maxIntentos = 50;
	    while (intentos < maxIntentos) {
	        try {
	            getLineasArtBolsa().clickRemoveArticleIfExists();
	            state(PRESENT, By.className("bagItem")).wait(3).check();
	        } catch (Exception e) {
	            Log4jTM.getLogger().warn("Problem clearing articles from Bag. {}. {}", e.getClass().getName(), e.getMessage());
	        }

	        int numArticulos = getLineasArtBolsa().getNumLinesArticles();
	        if (numArticulos == 0 || ++intentos == maxIntentos) {
	            break;
	        }
	    }
	}	

	public void click1erArticuloBolsa() {
		getLineasArtBolsa().clickArticle(1);
		waitLoadPage();
	}
	
	public void closeInMobil() {
		if (isOutlet()) {
			click(XP_ASPA).exec();
		} else {
			setBolsaToStateIfNotYet(CLOSED);
		}
	}
	
}
