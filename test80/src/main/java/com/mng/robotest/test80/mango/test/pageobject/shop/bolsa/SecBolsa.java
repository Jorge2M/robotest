package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Invisible;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public abstract class SecBolsa extends PageObjTM {

	public enum StateBolsa {Open, Closed};
	
    final Channel channel;
    final AppEcom app;
	
	abstract String getXPathPanelBolsa();
	abstract String getXPathBotonComprar();
	abstract String getXPathPrecioSubTotal();
	abstract String getXPathPrecioTransporte();
	abstract public String getPrecioSubTotal();
	abstract public String getPrecioTransporte();
	abstract public void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected);
	abstract public LineasArtBolsa getLineasArtBolsa();
	
    private static final String XPathAspa = "//span[@class[contains(.,'outline-close')]]";

    public static SecBolsa make(DataCtxShop dCtxShop, WebDriver driver) {
    	return make(dCtxShop.channel, dCtxShop.appE, dCtxShop.pais, driver);
    }
    
	public static SecBolsa make(Channel channel, AppEcom app, Pais pais, WebDriver driver) {
		if (app==AppEcom.outlet) {
			if (channel==Channel.mobile) {
				return new SecBolsaMobileOld(app, pais, driver);
			}
			return new SecBolsaDesktopOld(channel, app, driver);
		}
		return new SecBolsaNew(channel, app, pais, driver);
		
//		if (channel==Channel.mobile) {
//			return new SecBolsaMobile(app, pais, driver);
//		}
//		if (app==AppEcom.outlet) {
//		//if (app==AppEcom.outlet || channel==Channel.tablet) {
//			return new SecBolsaDesktopOld(channel, app, driver);
//		}
//		return new SecBolsaDesktopNew(channel, app, pais, driver);
	}
	
	protected SecBolsa(Channel channel, AppEcom app, WebDriver driver) {
		super(driver);
		this.channel = channel;
		this.app = app;
	}
	
	public boolean isInStateUntil(StateBolsa stateBolsaExpected, int maxSeconds) {
		String xpath = getXPathPanelBolsa();
		switch (stateBolsaExpected) {
		case Open:
			if (state(Visible, By.xpath(xpath), driver).wait(maxSeconds).check()) {
				return true;
			}
			break;
		case Closed:
			if (state(Invisible, By.xpath(xpath), driver).wait(maxSeconds).check()) {
				return true;
			}
			break;
		}
		
		return false;
	}

	public boolean isVisibleBotonComprar() {
		String xpathComprarBt = getXPathBotonComprar();
		return (state(Visible, By.xpath(xpathComprarBt), driver).check());
	}

	public boolean isVisibleBotonComprarUntil(int maxSeconds) {
		String xpathBoton = getXPathBotonComprar();
		return (state(Visible, By.xpath(xpathBoton), driver).wait(maxSeconds).check());
	}

	public void clickBotonComprar( int secondsWait) {
		String xpathComprarBt = getXPathBotonComprar();
		state(State.Visible, By.xpath(xpathComprarBt)).wait(secondsWait).check();
		click(By.xpath(xpathComprarBt), driver).type(TypeClick.javascript).exec();
	}
    
    public String getNumberArtIcono(Channel channel, AppEcom app) throws Exception {
    	return (SecCabecera.getNew(channel, app, driver).getNumberArtIcono());
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
        return (driver.findElement(By.xpath(xpathImporte)).getText());
    }
    
    public float getPrecioSubTotalFloat() {
        String precioTotal = getPrecioSubTotal();
        return (ImporteScreen.getFloatFromImporteMangoScreen(precioTotal));
    }

    public float getPrecioTransporteFloat() {
        String precioTotal = getPrecioTransporte();
        return (ImporteScreen.getFloatFromImporteMangoScreen(precioTotal));
    }
    
    /**
     * @return si el importe total de la bolsa NO coincide con el pasado por parámetro (importe previamente capturado)
     */
    public boolean isNotThisImporteTotalUntil(String importeSubTotalPrevio, int maxSecondsToWait) 
    throws Exception {
        String xpathImporte = getXPathPrecioSubTotal();
        try {
            ExpectedCondition<Boolean> expected = ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpathImporte), importeSubTotalPrevio));
            new WebDriverWait(driver, maxSecondsToWait).until(expected);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

	@SuppressWarnings("static-access")
	public void clearArticulos() throws Exception {
		setBolsaToStateIfNotYet(StateBolsa.Open);
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
							"Problem clearing articles from Bag. " + e.getClass().getName() + ". " + e.getMessage());
					}
				}

				try {
					new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.className("bagItem")));
					numArticulos = getLineasArtBolsa().getNumLinesArticles();
				} 
				catch (Exception e) {
					Log4jTM.getLogger().debug(
						"Problem getting num articles in Bag. " + e.getClass().getName() + ". " + e.getMessage());
					numArticulos = 0;
				}
				i += 1;
			}
			ii += 1;
		}
		while (!numberItemsIsUntil("0", channel, app, 0) && ii<10);

		setBolsaToStateIfNotYet(StateBolsa.Closed);
	}
    
    public void click1erArticuloBolsa() throws Exception {
    	getLineasArtBolsa().clickArticle(1);
        waitForPageLoaded(driver);
    }
    
	public void clickAspaMobil() {
		click(By.xpath(XPathAspa), driver).exec();
	}
	
}
