package com.mng.robotest.test80.mango.test.pageobject.shop.bolsa;

import java.util.ListIterator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.TypeOfClick;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


/**
 * Clase que define la automatización de las diferentes funcionalidades de la sección de "Bolsa"
 * @author jorge.munoz
 *
 */
public class SecBolsa extends WebdrvWrapp {
	public enum StateBolsa {Open, Closed}
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    public static LineasArticuloBolsa lineasArticuloBolsa;
    
    //TODO cuando suba a PRO la operativa eliminar el 1er XPath
    private static final String XPathAspaMobil = "(//a[@class[contains(.,'iconCross')]] | //span[@class[contains(.,'outline-close')]])";
    
    private static final String XPathPanelBolsaMobil = "//div[@class[contains(.,'m_bolsa')]]";
    private static final String XPathPanelBolsaDesktop = "//div[@id='mainDivBolsa']";
    private static final String XPathBotonComprarMobil = "//div[@class='comButton']/span";
    private static final String XPathBotonComprarDesktop = "//*[@id='bolsaComprar']";
    private static final String tagRefArticle = "[TAGREF]";
    private static final String XPathLinkBorrarArtMobilNew = "//*[@id[contains(.,'trashMobile')] and @onclick[contains(.,'" + tagRefArticle + "')]]"; 
    private static final String XPathLinkBorrarArtDesktop = "//*[@class='boton_basura' and @onclick[contains(.,'" + tagRefArticle + "')]]";
    private static final String XPathPrecioSubTotalMobil = "//div[@class[contains(.,'totalPriceContainer')]]";
    private static final String XPathPrecioSubTotalDesktop = "//*[@class='box_total_price']";
    private static final String XPathLinkArticulo = "//div[@class[contains(.,'itemDesc')]]/a";
    
    private static String getXPathPanelBolsa(Channel channel) {
        if (channel==Channel.movil_web)
            return XPathPanelBolsaMobil;
 
        return XPathPanelBolsaDesktop;
    }
    
    private static String getXPATH_BotonComprar(Channel channel) {
        if (channel==Channel.movil_web)
            return XPathBotonComprarMobil;

        return XPathBotonComprarDesktop;
    }
    
    /**
     * @return el XPATH correspondiente al link de borrar/eliminar artículo de la bolsa (asociado a cada artículo)
     */
    private static String getXPATH_LinkBorrarArt(Channel channel) {
        return getXPATH_LinkBorrarArt(channel, "");
    }
    
    private static String getXPATH_LinkBorrarArt(Channel channel, String refArticle) {
        if (channel==Channel.movil_web) {
            return (XPathLinkBorrarArtMobilNew.replace(tagRefArticle, refArticle));
        }

        return (XPathLinkBorrarArtDesktop.replace(tagRefArticle, refArticle)); 
    }

    /**
     * @return el xpath correspondiente al elemento que contiene el precio subtotal (sin el importe)
     */
    private static String getXPATH_precioSubTotal(Channel channel) {
        if (channel==Channel.movil_web)
            return XPathPrecioSubTotalMobil;
        
        return XPathPrecioSubTotalDesktop; 
    }    
    
    /**
     * @return el xpath correspondiente al elemento que contiene el precio del transporte
     */
    private static String getXPATH_precioTransporte(Channel channel) {
        String xpathCapaBolsa = SecBolsa.getXPathPanelBolsa(channel);
        if (channel==Channel.movil_web)
            return "(" + xpathCapaBolsa + "//div[@class[contains(.,'totalPriceContainer')]])[2]";

        return xpathCapaBolsa + "//*[@class='contenedor_precio_transporte']"; 
    }

    public static boolean isInStateUntil(StateBolsa stateBolsaExpected, Channel channel, int maxSecondsWait, WebDriver driver) {
    	switch (stateBolsaExpected) {
    	case Open:
    		if (isElementVisibleUntil(driver, By.xpath(SecBolsa.getXPathPanelBolsa(channel)), maxSecondsWait))
    			return true;
    		break;
    	case Closed:
    		if (isElementInvisibleUntil(driver, By.xpath(SecBolsa.getXPathPanelBolsa(channel)), maxSecondsWait))
    			return true;
    		break;
    	}
    	
    	return false;
    }  
    
    
    public static void setBolsaToStateIfNotYet(StateBolsa stateBolsaExpected, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
        if (!isInStateUntil(stateBolsaExpected, channel, 1, driver)) {
        	setBolsaToState(stateBolsaExpected, channel, app, driver);
        }
    }
    
    static void setBolsaToState(StateBolsa stateBolsaExpected, Channel channel, AppEcom app, WebDriver driver) 
    throws Exception {
    	int secondsWaitToIconoBolsaDisp = 2;
    	//TODO ahora resulta que el test A/B que ha ganado era el original
//    	if (channel==Channel.movil_web || app==AppEcom.outlet)
    		SecCabecera secCabecera = SecCabecera.getNew(channel, app, driver);
    		secCabecera.clickIconoBolsaWhenDisp(secondsWaitToIconoBolsaDisp);
//    	else {
//    		switch (stateBolsaExpected) {
//    		case Open:
//    			SecCabeceraWrapper.hoverIconoBolsa(channel, app, driver);
//    			break;
//    		case Closed:
//    			SecCabeceraDesktop.focusAwayBolsa(driver);
//    		}
//    	}
        
        int maxSecondsToWait = 2;
        isInStateUntil(stateBolsaExpected, channel, maxSecondsToWait, driver);
    }
    
    public static boolean isVisibleBotonComprar(Channel channel, WebDriver driver) {
        String xpathComprarBt = SecBolsa.getXPATH_BotonComprar(channel);
        return (isElementVisible(driver, By.xpath(xpathComprarBt)));
    }

    public static boolean isVisibleBotonComprarUntil(WebDriver driver, Channel channel, int maxSecondsToWait) {
        String xpathBoton = getXPATH_BotonComprar(channel);
        return (isElementVisibleUntil(driver, By.xpath(xpathBoton), maxSecondsToWait));
    }
    
    /**
     * Esperamos a que esté disponible y seleccionamos el botón "COMPRAR" de la bolsa
     */
    public static void clickBotonComprar(WebDriver driver, Channel channel, int secondsWait) throws Exception {
        String xpathComprarBt = SecBolsa.getXPATH_BotonComprar(channel);
        new WebDriverWait(driver, secondsWait).until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathComprarBt)));
        clickAndWaitLoad(driver, By.xpath(xpathComprarBt), TypeOfClick.javascript);
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
            if (itemsMightHave.compareTo(itemsPantalla)==0)
                return true;
            
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
        if (channel==Channel.movil_web) {
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
    
    /**
     * Obtenemos el precio del transporte
     */
    public static String getPrecioTransporte(WebDriver driver, Channel channel) {
        String precioTotal = "0";
        ListIterator<WebElement> itTotalEntero = null;
        ListIterator<WebElement> itTotalDecimal = null;
        String xpathImpTransp = getXPATH_precioTransporte(channel);
        if (isElementPresent(driver, By.xpath(xpathImpTransp))) {
            if (channel==Channel.movil_web) {
                itTotalEntero = driver.findElements(By.xpath("(" + xpathImpTransp + ")[1]" + "//span[1]")).listIterator();
                itTotalDecimal = driver.findElements(By.xpath("(" + xpathImpTransp + ")[1]" + "//span[2]")).listIterator();
            } else {
                itTotalEntero = driver.findElements(By.xpath(xpathImpTransp + "//*[@class='bolsa_price_big']")).listIterator();
                itTotalDecimal = driver.findElements(By.xpath(xpathImpTransp + "//*[@class='bolsa_price_small']")).listIterator();
            }
            
            while (itTotalEntero != null && itTotalEntero.hasNext())
                precioTotal += itTotalEntero.next().getText();
    
            while (itTotalDecimal != null && itTotalDecimal.hasNext())
                precioTotal += itTotalDecimal.next().getText();
    
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
    
    public static void clickAspaMobil(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathAspaMobil));
    }
    
    public static void clearArticuloAndWait(Channel channel, String refArticulo, WebDriver driver) throws Exception {
        //Seleccionar el link de la papelera para eliminar de la bolsa
        String xpathClearArt = getXPATH_LinkBorrarArt(channel, refArticulo);
        driver.findElement(By.xpath(xpathClearArt)).click();
        waitForPageLoaded(driver); 
    }
    
    /**
     * Borrar todos los artículos existentes en la Bolsa. Selecciona los iconos de papelera que encuentra hasta que no encuentra más
     */
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
                    if (isElementPresent(driver, By.xpath(xpathDeleteArt)))
                        driver.findElement(By.xpath(xpathDeleteArt)).click();
                } catch (Exception e) {
                    if (i==49) 
                        pLogger.warn("Problem clearing articles from Bag. " + e.getClass().getName() + ". " + e.getMessage());
                }

                try {
                    new WebDriverWait(driver, 3).until(ExpectedConditions.presenceOfElementLocated(By.className("bagItem")));
                    numArticulos = lineasArticuloBolsa.getNumLinesArticles(dCtxSh.channel, driver);
                } 
                catch (Exception e) {
                    pLogger.debug("Problem getting num articles in Bag. " + e.getClass().getName() + ". " + e.getMessage());
                    numArticulos = 0;
                }

                i += 1;
            }

            //Cerramos la bolsa medialnte selección de la aspa de close
            SecBolsa.clickIconoClose(driver, dCtxSh.channel);
                
            ii += 1;
        }
        while (!numberItemsIsUntil("0"/*itemsMightHave*/, dCtxSh.channel, dCtxSh.appE, 0/*maxSecodsToWait*/, driver) && 
               ii<10/* evitar bucles infinitos */);
        
        setBolsaToStateIfNotYet(StateBolsa.Closed, dCtxSh.channel, dCtxSh.appE, driver);
    }
    
    //Función que clicka en el icono de aspa que cierra la bolsa
    public static void clickIconoClose(WebDriver driver, Channel channel) throws Exception {
        if (channel==Channel.movil_web) {
            if (isElementVisible(driver, By.xpath("//a[@class[contains(.,'icon-cross')]]")))
                clickAndWaitLoad(driver, By.xpath("//a[@class[contains(.,'icon-cross')]]"));
        } else {
            if (!UtilsMangoTest.findDisplayedElements(driver, By.cssSelector("p.bolsa_close.icono")).isEmpty()) {
                clickAndWaitLoad(driver, By.cssSelector("p.bolsa_close.icono"));
            } else { 
                if (!UtilsMangoTest.findDisplayedElements(driver, By.cssSelector("bolsa_close")).isEmpty()) {
                    clickAndWaitLoad(driver, By.className("bolsa_close"));
                }
            }
        }
    }    

    static boolean isUnitalla(String talla) {
        if (talla.toLowerCase().compareTo("u")==0 ||
            talla.toLowerCase().compareTo("unitalla")==0 ||
            talla.toLowerCase().compareTo("99")==0)
            return true;
         
        return false;
    }
    
    public static void click1erArticuloBolsa(WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathLinkArticulo)).click();
        waitForPageLoaded(driver);
    }
}
