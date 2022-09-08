package com.mng.robotest.domains.bolsa.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.utils.ImporteScreen;

public abstract class SecBolsa extends PageBase {

	public enum StateBolsa { OPEN, CLOSED };
	
	abstract String getXPathPanelBolsa();
	abstract String getXPathBotonComprar();
	abstract String getXPathPrecioSubTotal();
	abstract String getXPathPrecioTransporte();
	public abstract String getPrecioSubTotal();
	public abstract String getPrecioTransporte();
	public abstract void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected);
	public abstract LineasArtBolsa getLineasArtBolsa();
	
	private static final String XPATH_ASPA = "//span[@class[contains(.,'outline-close')]]";
	
	public static SecBolsa make(Channel channel, AppEcom app) {
		if (app==AppEcom.outlet) {
			if (channel==Channel.mobile) {
				return new SecBolsaMobileOutlet();
			}
			return new SecBolsaDesktopOutlet();
		}
		return new SecBolsaShop();
	}
	
	public boolean isInStateUntil(StateBolsa stateBolsaExpected, int maxSeconds) {
		String xpath = getXPathPanelBolsa();
		if (stateBolsaExpected==StateBolsa.OPEN) {
			return state(Visible, xpath).wait(maxSeconds).check();
//			if (channel==Channel.mobile && app==AppEcom.shop) {
//				return (capaVisible && )
//			}
		}
		return state(Invisible, xpath).wait(maxSeconds).check();
	}

	public boolean isVisibleBotonComprar() {
		String xpathComprarBt = getXPathBotonComprar();
		return state(Visible, xpathComprarBt).check();
	}

	public boolean isVisibleBotonComprarUntil(int maxSeconds) { 
		String xpathBoton = getXPathBotonComprar();
		return state(Visible, xpathBoton).wait(maxSeconds).check();
	}

	public void clickBotonComprar( int secondsWait) {
		String xpathComprarBt = getXPathBotonComprar();
		state(State.Visible, By.xpath(xpathComprarBt)).wait(secondsWait).check();
		click(xpathComprarBt).type(TypeClick.javascript).exec();
	}
	
	public String getNumberArtIcono(Channel channel, AppEcom app) throws Exception {
		return (SecCabecera.getNew(channel, app).getNumberArtIcono());
	}
	
	public boolean numberItemsIsUntil(String itemsMightHave, Channel channel, AppEcom app, int maxSecodsToWait) 
	throws Exception {
		for (int i=0; i<=maxSecodsToWait; i++) {
			String itemsPantalla = getNumberArtIcono(channel, app);
			if (itemsMightHave.compareTo(itemsPantalla)==0) {
				return true;
			}
			Thread.sleep(1000);
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
	
	public boolean isNotThisImporteTotalUntil(String importeSubTotalPrevio, int maxSeconds) {
		String xpathImporte = getXPathPrecioSubTotal();
		try {
			ExpectedCondition<Boolean> expected = ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpathImporte), importeSubTotalPrevio));
			new WebDriverWait(driver, maxSeconds).until(expected);
			return true;
		}
		catch (Exception e) {
			return false;
		}
	}

	public void clearArticulos() throws Exception {
		setBolsaToStateIfNotYet(StateBolsa.OPEN);
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
					new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.className("bagItem")));
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
		while (!numberItemsIsUntil("0", channel, app, 0) && ii<10);

		setBolsaToStateIfNotYet(StateBolsa.CLOSED);
	}
	
	public void click1erArticuloBolsa() {
		getLineasArtBolsa().clickArticle(1);
		waitForPageLoaded(driver);
	}
	
	public void closeInMobil() {
		if (app==AppEcom.outlet) {
			click(XPATH_ASPA).exec();
		} else {
			setBolsaToStateIfNotYet(StateBolsa.CLOSED);
		}
	}
	
}