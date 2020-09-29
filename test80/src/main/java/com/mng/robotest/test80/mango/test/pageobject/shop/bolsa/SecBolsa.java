package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import java.util.ListIterator;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Bolsa"
 * @author jorge.munoz
 *
 */
public class SecBolsa {
	
	public enum StateBolsa {Open, Closed}

    public static LineasArticuloBolsa lineasArticuloBolsa;
    
    //TODO cuando suba a PRO la operativa eliminar el 1er XPath
    private static final String XPathAspaMobil = "(//a[@class[contains(.,'iconCross')]] | //span[@class[contains(.,'outline-close')]])";
    
    private static final String XPathPanelBolsaMobil = "//div[@class[contains(.,'m_bolsa')]]";
    private static final String XPathPanelBolsaDesktop = "//div[@id='mainDivBolsa']";
    private static final String XPathBotonComprarMobil = "//div[@class='comButton']/span";
    private static final String XPathBotonComprarDesktop = "//*[@id='bolsaComprar']";
    private static final String tagRefArticle = "[TAGREF]";
    private static final String XPathLinkBorrarArtMobilNew = "//*[@id[contains(.,'trashMobile')] and @onclick[contains(.,'" + tagRefArticle + "')]]"; 
    private static final String XPathLinkBorrarArtDesktop = "//*[@class='boton_basura' and @onclick[contains(.,'" + tagRefArticle + "')]]/..";
    private static final String XPathPrecioSubTotalMobil = "//div[@class[contains(.,'totalPriceContainer')]]";
    private static final String XPathPrecioSubTotalDesktop = "//*[@class='box_total_price']";
    private static final String XPathLinkArticulo = "//div[@class[contains(.,'itemDesc')]]/a";
    
    private static String getXPathPanelBolsa(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathPanelBolsaMobil;
        }
        return XPathPanelBolsaDesktop;
    }
    
    private static String getXPATH_BotonComprar(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathBotonComprarMobil;
        }
        return XPathBotonComprarDesktop;
    }
    
    /**
     * @return el XPATH correspondiente al link de borrar/eliminar artículo de la bolsa (asociado a cada artículo)
     */
    private static String getXPATH_LinkBorrarArt(Channel channel) {
        return getXPATH_LinkBorrarArt(channel, "");
    }
    
    private static String getXPATH_LinkBorrarArt(Channel channel, String refArticle) {
        if (channel==Channel.mobile) {
            return (XPathLinkBorrarArtMobilNew.replace(tagRefArticle, refArticle));
        }

        return (XPathLinkBorrarArtDesktop.replace(tagRefArticle, refArticle)); 
    }

    /**
     * @return el xpath correspondiente al elemento que contiene el precio subtotal (sin el importe)
     */
    private static String getXPATH_precioSubTotal(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathPrecioSubTotalMobil;
        }
        return XPathPrecioSubTotalDesktop; 
    }    
    
    /**
     * @return el xpath correspondiente al elemento que contiene el precio del transporte
     */
    private static String getXPATH_precioTransporte(Channel channel) {
        String xpathCapaBolsa = SecBolsa.getXPathPanelBolsa(channel);
        if (channel==Channel.mobile) {
            return "(" + xpathCapaBolsa + "//div[@class[contains(.,'totalPriceContainer')]])[2]";
        }
        return xpathCapaBolsa + "//*[@class='contenedor_precio_transporte']"; 
    }

	public static boolean isInStateUntil(StateBolsa stateBolsaExpected, Channel channel, int maxSeconds, WebDriver driver) {
		switch (stateBolsaExpected) {
		case Open:
			String xpath = SecBolsa.getXPathPanelBolsa(channel);
			if (state(Visible, By.xpath(xpath), driver).wait(maxSeconds).check()) {
				return true;
			}
			break;
		case Closed:
			String xpath2 = SecBolsa.getXPathPanelBolsa(channel);
			if (state(Invisible, By.xpath(xpath2), driver).wait(maxSeconds).check()) {
				return true;
			}
			break;
		}
		
		return false;
	}

	public static void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected, Channel channel, AppEcom app, WebDriver driver) {
		if (!isInStateUntil(stateBolsaExpected, channel, 1, driver)) {
			setBolsaToState(stateBolsaExpected, channel, app, driver);
		}
	}

	static void setBolsaToState(StateBolsa stateBolsaExpected, Channel channel, AppEcom app, WebDriver driver) {
		SecCabecera secCabecera = SecCabecera.getNew(channel, app, driver);
		if (stateBolsaExpected==StateBolsa.Open || channel==Channel.desktop) {
			secCabecera.clickIconoBolsaWhenDisp(2);
		} else {
			clickIconoCloseMobil(driver, channel);
		}
		isInStateUntil(stateBolsaExpected, channel, 2, driver);
	}

	public static boolean isVisibleBotonComprar(Channel channel, WebDriver driver) {
		String xpathComprarBt = SecBolsa.getXPATH_BotonComprar(channel);
		return (state(Visible, By.xpath(xpathComprarBt), driver).check());
	}

	public static boolean isVisibleBotonComprarUntil(WebDriver driver, Channel channel, int maxSeconds) {
		String xpathBoton = getXPATH_BotonComprar(channel);
		return (state(Visible, By.xpath(xpathBoton), driver).wait(maxSeconds).check());
	}

	public static void clickBotonComprar(WebDriver driver, Channel channel, int secondsWait) {
		String xpathComprarBt = SecBolsa.getXPATH_BotonComprar(channel);
		new WebDriverWait(driver, secondsWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathComprarBt)));
		click(By.xpath(xpathComprarBt), driver).type(TypeClick.javascript).exec();
	}
    
    /**
     * @return el número que aparece en el icono de la bolsa y que se corresponde con el número de artículos que contiene
     */
    public static String getNumberArtIcono(Channel channel, AppEcom app, WebDriver driver) throws Exception {
    	return (SecCabecera.getNew(channel, app, driver).getNumberArtIcono());
    }
    
    public static boolean numberItemsIsUntil(String itemsMightHave, Channel channel, AppEcom app, 
    										 int maxSecodsToWait, WebDriver driver) throws Exception {
        for (int i=0; i<=maxSecodsToWait; i++) {
            String itemsPantalla = SecBolsa.getNumberArtIcono(channel, app, driver);
            if (itemsMightHave.compareTo(itemsPantalla)==0) {
                return true;
            }
            Thread.sleep(1000);
        }
        
        return false;
    }

    /**
     * Obtenemosel precio total (sin incluir el transporte) tal como está pintado en pantalla
     */
    public static String getPrecioSubtotalTextPant(Channel channel, WebDriver driver) {
        String xpathImporte = getXPATH_precioSubTotal(channel);
        return (driver.findElement(By.xpath(xpathImporte)).getText());
    }
    
    /**
     * Obtenemos el precio total (sin incluir el transporte) de la bolsa
     */
    public static String getPrecioSubTotal(Channel channel, WebDriver driver) {
        String precioTotal = "";
        ListIterator<WebElement> itTotalEntero = null;
        ListIterator<WebElement> itTotalDecimal = null;
        String xpathCapaBolsa = SecBolsa.getXPathPanelBolsa(channel);
        String xpathSubtotal = getXPATH_precioSubTotal(channel);
        if (channel==Channel.mobile) {
            itTotalEntero = driver.findElements(By.xpath("(" + xpathCapaBolsa + xpathSubtotal + ")[1]" + "//span[@style[not(contains(.,'padding'))]][1]")).listIterator();
            itTotalDecimal = driver.findElements(By.xpath("(" + xpathCapaBolsa + xpathSubtotal + ")[1]" + "//span[@style[not(contains(.,'padding'))]][2]")).listIterator();
        } else {
            itTotalEntero = driver.findElements(By.xpath(xpathCapaBolsa + xpathSubtotal + "//*[@class='bolsa_price_big']")).listIterator();
            itTotalDecimal = driver.findElements(By.xpath(xpathCapaBolsa + xpathSubtotal + "//*[@class='bolsa_price_small']")).listIterator();
        }
        
        while (itTotalEntero != null && itTotalEntero.hasNext())
            precioTotal += itTotalEntero.next().getText();

        while (itTotalDecimal != null && itTotalDecimal.hasNext())
            precioTotal += itTotalDecimal.next().getText();

        return (ImporteScreen.normalizeImportFromScreen(precioTotal));
    }    

    /**
     * @return el precio total de la bolsa en formato float
     */
    public static float getPrecioSubTotalFloat(WebDriver driver, Channel channel) {
        String precioTotal = SecBolsa.getPrecioSubTotal(channel, driver);
        return (ImporteScreen.getFloatFromImporteMangoScreen(precioTotal));
    }

	public static String getPrecioTransporte(WebDriver driver, Channel channel) {
		String precioTotal = "0";
		ListIterator<WebElement> itTotalEntero = null;
		ListIterator<WebElement> itTotalDecimal = null;
		String xpathImpTransp = getXPATH_precioTransporte(channel);
		if (state(Present, By.xpath(xpathImpTransp), driver).check()) {
			if (channel==Channel.mobile) {
				itTotalEntero = driver.findElements(By.xpath("(" + xpathImpTransp + ")[1]" + "//span[1]")).listIterator();
				itTotalDecimal = driver.findElements(By.xpath("(" + xpathImpTransp + ")[1]" + "//span[2]")).listIterator();
			} else {
				itTotalEntero = driver.findElements(By.xpath(xpathImpTransp + "//*[@class='bolsa_price_big']")).listIterator();
				itTotalDecimal = driver.findElements(By.xpath(xpathImpTransp + "//*[@class='bolsa_price_small']")).listIterator();
			}

			while (itTotalEntero != null && itTotalEntero.hasNext()) {
				precioTotal += itTotalEntero.next().getText();
			}
			while (itTotalDecimal != null && itTotalDecimal.hasNext()) {
				precioTotal += itTotalDecimal.next().getText();
			}
			precioTotal = ImporteScreen.normalizeImportFromScreen(precioTotal);
		}

		return precioTotal;
	}

    /**
     * @return el precio de transporte de la bolsa en formato float
     */
    public static float getPrecioTransporteFloat(WebDriver driver, Channel channel) {
        String precioTotal = SecBolsa.getPrecioTransporte(driver, channel);
        return (ImporteScreen.getFloatFromImporteMangoScreen(precioTotal));
    }
    
    /**
     * @return si el importe total de la bolsa NO coincide con el pasado por parámetro (importe previamente capturado)
     */
    public static boolean isNotThisImporteTotalUntil(String importeSubTotalPrevio, Channel channel, int maxSecondsToWait, WebDriver driver) throws Exception {
        String xpathImporte = getXPATH_precioSubTotal(channel);
        try {
            ExpectedCondition<Boolean> expected = ExpectedConditions.not(ExpectedConditions.textToBePresentInElementLocated(By.xpath(xpathImporte), importeSubTotalPrevio));
            new WebDriverWait(driver, maxSecondsToWait).until(expected);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

	public static void clickAspaMobil(WebDriver driver) {
		click(By.xpath(XPathAspaMobil), driver).exec();
	}

    public static void clearArticuloAndWait(Channel channel, String refArticulo, WebDriver driver) throws Exception {
        //Seleccionar el link de la papelera para eliminar de la bolsa
        String xpathClearArt = getXPATH_LinkBorrarArt(channel, refArticulo);
        driver.findElement(By.xpath(xpathClearArt)).click();
        waitForPageLoaded(driver); 
    }

	@SuppressWarnings("static-access")
	public static void clearArticulos(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
		setBolsaToStateIfNotYet(StateBolsa.Open, dCtxSh.channel, dCtxSh.appE, driver);
		String xpathDeleteArt = SecBolsa.getXPATH_LinkBorrarArt(dCtxSh.channel);
		int ii = 0;
		do {
			// Se comprueba si la bolsa tiene artículos
			int numArticulos = lineasArticuloBolsa.getNumLinesArticles(dCtxSh.channel, driver);
			int i = 0;
			while (numArticulos > 0 && i < 50) { // 50 para evitar bucles infinitos
				// Seleccionamos el link de borrar asociado a cada uno de los artículos
				try {
					if (state(Present, By.xpath(xpathDeleteArt), driver).check()) {
						driver.findElement(By.xpath(xpathDeleteArt)).click();
					}
				} catch (Exception e) {
					if (i==49) {
						Log4jTM.getLogger().warn(
							"Problem clearing articles from Bag. " + e.getClass().getName() + ". " + e.getMessage());
					}
				}

				try {
					new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.className("bagItem")));
					numArticulos = lineasArticuloBolsa.getNumLinesArticles(dCtxSh.channel, driver);
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
		while (!numberItemsIsUntil("0"/*itemsMightHave*/, dCtxSh.channel, dCtxSh.appE, 0/*maxSecodsToWait*/, driver) && 
				ii<10/* evitar bucles infinitos */);

		setBolsaToStateIfNotYet(StateBolsa.Closed, dCtxSh.channel, dCtxSh.appE, driver);
	}

	private static void clickIconoCloseMobil(WebDriver driver, Channel channel) {
		String xpathAspa =  "//div[@id='close_mobile']";
		if (state(Visible, By.xpath(xpathAspa), driver).check()) {
			click(By.xpath(xpathAspa), driver).exec();
		}
	}

    static boolean isUnitalla(String talla) {
        if (talla.toLowerCase().compareTo("u")==0 ||
            talla.toLowerCase().compareTo("unitalla")==0 ||
            talla.toLowerCase().compareTo("0")==0) {
            return true;
        }
        return false;
    }
    
    public static void click1erArticuloBolsa(WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathLinkArticulo)).click();
        waitForPageLoaded(driver);
    }
}
