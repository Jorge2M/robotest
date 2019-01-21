package com.mng.robotest.test80.mango.test.pageobject.shop;

import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabeceraMobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalLoyaltyAfterAccess;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.utils.testab.TestAB;

@SuppressWarnings("javadoc")
/**
 * Clase que define la automatización de las diferentes funcionalidades de la página de "GALERÍA DE PRODUCTOS"
 * @author jorge.munoz
 */
public class PagePrehome extends WebdrvWrapp {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    enum ButtonEnter {Enter, Continuar};
	
    static String XPathSelectPaises = "//select[@id='countrySelect']";
    
    //xpath correspondiente al div con el país seleccionado (cuyo click que permite desplegar la lista de países)
    static String XPathDivPaisSeleccionado = "//div[@id='countrySelect_chosen']";
    
    static String XPathIconSalePaisSeleccionado = XPathDivPaisSeleccionado + "//span[@class[contains(.,'salesIcon')]]";
    static String XPathDivProvincias = "//div[@class[contains(.,'Cnt on')]]/div[@class[contains(.,'provinceSelect')]]";
    static String XPathInputPais = "//div[@class[contains(.,'chosen-search')]]/input";
    
    public static String getXPath_optionPaisFromName(String nombrePais) {
        return (XPathSelectPaises + "//option[@data-alt-spellings[contains(.,'" + nombrePais + "')]]");
    }
    
    public static String getXPath_optionPaisFromCodigo(String codigoPais) {
        return (XPathSelectPaises + "//option[@value[contains(.,'" + codigoPais + "')]]");
    }    
    
    //TODO eliminar cuando haya desaparecido definitivamente la lista de provincias de la Shop/Outlet
    public static String getXPathListaProvincias(String codigoPais) {
    	return ("//div[@id='province_" + codigoPais + "_chosen']");
    }
    
    public static String getXPathButtonIdioma(String codigoPais, String nombreIdioma) {
    	return "//div[@id='lang_" + codigoPais + "']//a[text()[contains(.,'" + nombreIdioma + "')]]";
    }
    
    public static String getXPathButtonForEnter(ButtonEnter button, String codigoPais) {
    	switch (button) {
    	case Enter:
    		return ("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'phFormEnter')]]");
    	case Continuar:
    	default:
    		return("//div[@id='lang_" + codigoPais + "']/div[@class[contains(.,'modalFormEnter')]]");
    	}
    }
    
    public static boolean isPage(WebDriver driver) {
        return isElementPresent(driver, By.xpath(XPathDivPaisSeleccionado));
    }
    
    public static boolean isNotPageUntil(int maxSecondsToWait, WebDriver driver) {
    	return isElementInvisibleUntil(driver, By.xpath(XPathDivPaisSeleccionado), maxSecondsToWait);
    }
    
    /**
     * @return el código de país que existe en pantalla en base a su nombre
     */
    public static String getCodigoPais(WebDriver driver, String nombrePais) {
        String xpathOptionPais = getXPath_optionPaisFromName(nombrePais);
        String codigoPais = driver.findElement(By.xpath(xpathOptionPais)).getAttribute("value");
        return codigoPais;
    }
    
    /**
     * @return si el país seleccionado tiene asociada la marca de compra online (indicada con el icono de la bolsa)
     */
    public static boolean isPaisSelectedWithMarcaCompra(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathIconSalePaisSeleccionado)));        
    }    
    
    /**
     * @return indica si está seleccionado el país indicado en 'nombrePais'
     */
    public static boolean isPaisSelectedDesktop(WebDriver driver, String nombrePais) {
        return (driver.findElement(By.xpath(XPathDivPaisSeleccionado)).getText().contains(nombrePais));
    }
    
    /**
     * Averigua si existe el desplegable de provincias
     */
    public static boolean existeDesplProvincias(WebDriver driver) {
        boolean existe = false;
        if (isElementPresent(driver, By.xpath(XPathDivProvincias)) && 
            driver.findElement(By.xpath(XPathDivProvincias)).isDisplayed())
            existe = true;
        
        return existe;
    }    
    
    /**
     * Despliega la lista de países
     */
    public static void desplieguaListaPaises(WebDriver driver) {
    	moveToElement(By.xpath(XPathDivPaisSeleccionado), driver);
        driver.findElement(By.xpath(XPathDivPaisSeleccionado + "/a")).click();
    }
    
    /**
     * Despliega la lista de provincias y selecciona la provincia "Barcelona"
     */
    //TODO eliminar cuando haya desaparecido definitivamente la lista de provincias de la Shop/Outlet
    public static void seleccionaProvincia(WebDriver driver, String nombrePais, Channel channel) {
        if (channel==Channel.desktop) {
            //Desplegamos la lista de provincias
            String codigoPais = getCodigoPais(driver, nombrePais);
            String xpathListaProv = getXPathListaProvincias(codigoPais);
            moveToElement(By.xpath(xpathListaProv), driver);
            driver.findElement(By.xpath(xpathListaProv)).click();
            
            //Introducimos la provincia Barcelona
            sendKeysWithRetry(3, "Barcelona", By.xpath(xpathListaProv + "//div[@class[contains(.,'chosen-search')]]/input"), driver);
            
            //Seleccionamos la provincia encontrada
            driver.findElement(By.xpath(xpathListaProv + "//div[@class='chosen-drop']/ul/li")).click();
        }
        else
            //Seleccionamos la provincia 8 (Barcelona)
            driver.findElement(By.xpath("//select[@id[contains(.,'province')]]/option[@value='" + "8" + "']")).click();
    }    
    
    /**
     * Selecciona un idioma concreto de entre los que aparecen por pantalla
     */
    public static void seleccionaIdioma(WebDriver driver, String nombrePais, String nombreIdioma) throws Exception {
        String codigoPais = getCodigoPais(driver, nombrePais);
        String xpathButtonIdioma = getXPathButtonIdioma(codigoPais, nombreIdioma);
        clickAndWaitLoad(driver, By.xpath(xpathButtonIdioma), TypeOfClick.javascript);
    }
    
    /**
     * Introducimos el nombre del país en el campo de input de "Busca tu país..." y lo seleccionamos
     */
    public static void inputPaisAndSelect(WebDriver driver, String nombrePais, Channel channel) throws Exception {
        String codigoPais = getCodigoPais(driver, nombrePais);
        if (channel!=Channel.movil_web) {
            new WebDriverWait(driver, 5).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//div[@class[contains(.,'chosen-with-drop')]]")));
            driver.findElement(By.xpath(XPathInputPais)).sendKeys(nombrePais);
            
            // Seleccionamos el país encontrado
            driver.findElement(By.xpath("//div[@class='chosen-drop']/ul/li")).click();        
        }
        else {
            //En el caso de mobile no ejecutamos los despliegues porque es muy complejo tratar con los desplegables nativos del dispositivo
            //Seleccionamos el país a partir de su código de país
            driver.findElement(By.xpath(XPathSelectPaises + "/option[@value='" + codigoPais + "']")).click();
        }
    }
    
    /**
     * Selecciona el botón para acceder a la shop (soporta desktop/móvil y prehome/modal)
     */
    public static void selectButtonForEnter(WebDriver driver, String codigoPais) {
        try {
        	boolean buttonEnterSelected = clickButtonForEnterIfExists(ButtonEnter.Enter, codigoPais, driver); 
            if (!buttonEnterSelected)
            	clickButtonForEnterIfExists(ButtonEnter.Continuar, codigoPais, driver);
        } 
        catch (Exception e) {
        	pLogger.warn("Exception clicking button for Enter. But perhaps the click have work fine", e);
        }
    }
    
    public static boolean clickButtonForEnterIfExists(ButtonEnter buttonEnter, String codigoPais, WebDriver driver) throws Exception {
    	String xpathButton = getXPathButtonForEnter(buttonEnter, codigoPais);
        if (isElementPresent(driver, By.xpath(xpathButton)) && driver.findElement(By.xpath(xpathButton)).isDisplayed()) {
        	moveToElement(By.xpath(xpathButton), driver);
        	clickAndWaitLoad(driver, By.xpath(xpathButton + "/a"), TypeOfClick.javascript);
            return true;
        }    	
        
        return false;
    }
    
    /**
     * En caso de existir el modal de NewsLetter lo cerramos
     */
    @SuppressWarnings("unchecked")
    public static void closeModalNewsLetterIfExists(WebDriver driver) {
        //Capturamos la variable JavaScript "sessionObjectJson"
        JavascriptExecutor js = (JavascriptExecutor) driver;
        Object result = js.executeScript("return sessionObjectsJson");
        if (result!=null) {
            Map<String,Object> resultMap = (Map<String,Object>)result;
          
            //Si figura para lanzar la llamada JSON de NewsLetter
            if (resultMap.entrySet().toString().contains("modalRegistroNewsletter")) {
                String xpathDivModal = "//div[@id='modalNewsletter']";
                if (isElementVisibleUntil(driver, By.xpath(xpathDivModal), 5/*seconds*/)) {
                    //Clickamos al aspa para cerrar el modal
                    driver.findElement(By.xpath(xpathDivModal + "//div[@id='modalClose']")).click();
                }
            }
        }
    }
    
    /**
     * Añadimos la cookie de Newsletter con valor 0 para que no aparezca el modal
     * @param driver
     */
    public static void addCookieNewsletter(WebDriver driver) {
        Cookie ck = new Cookie("modalRegistroNewsletter", "0");
        driver.manage().addCookie(ck);
    }


    
    /**
     * Ejecuta una acceso a la shop vía la páinga de prehome
     */
    public static void accesoShopViaPrehome(DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        goToPagePrehome(dCtxSh.urlAcceso, dCtxSh, dFTest);
        
    	//Forzamos galería sin React
    	int versionSinReact = 0;
    	TestAB.activateTestABgaleriaReact(versionSinReact, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
    	
        PagePrehome.selecPaisIdiomaYAccede(dCtxSh, dFTest);
        ModalLoyaltyAfterAccess.closeModalIfVisible(dFTest.driver);
        if (dCtxSh.channel==Channel.movil_web) {
        	SecCabeceraMobil secCabecera = SecCabeceraMobil.getNew(dCtxSh.appE, dFTest.driver);
        	secCabecera.closeSmartBannerIfExists();
        }
    }
    
    /**
     * Ejecuta el flujo correspondiente a la selección de un país + la posterior selección de provincia/idioma/enter (sirve para prehome y modal) 
     */
    public static void selecPaisIdiomaYAccede(DataCtxShop dCtxSh, DataFmwkTest dFTest) //No modificar
    throws Exception {
        selecionPais(dCtxSh, dFTest);
        selecionProvIdiomAndEnter(dCtxSh.pais, dCtxSh.idioma, dCtxSh.channel, dFTest.driver);
    }
    
    /**
     * Nos posiconamos en la página de Prehome desde una URL (sorteando la página de JCAS si aparece)
     */
    public static void goToPagePrehome(String urlPreHome, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
    	//Temporal para test Canary!!!
    	//AccesoNavigations.goToInitURL(urlPreHome + "?canary=true", dCtxSh, dFTest);
    	AccesoNavigations.goToInitURL(urlPreHome, dCtxSh, dFTest);
        waitForPageLoaded(dFTest.driver);
        if (PageJCAS.thisPageIsShown(dFTest.driver))
            PageJCAS.identication(dFTest.driver, "jorge.munoz", "2010martina");
    }    
    
    /**
     * Ejecuta el flujo para selecionar el país especificado
     */
    public static void selecionPais(DataCtxShop dCtxSh, DataFmwkTest dFTest) 
    throws Exception {
        new WebDriverWait(dFTest.driver, 5).until(ExpectedConditions.presenceOfElementLocated(By.xpath(XPathSelectPaises)));
        
        //Damos de alta la cookie de newsLetter porque no podemos gestionar correctamente el cierre 
        //del modal en la página de portada (es aleatorio y aparece en un intervalo de 0 a 5 segundos)
        addCookieNewsletter(dFTest.driver);
        if (dCtxSh.channel==Channel.movil_web || !isPaisSelectedDesktop(dFTest.driver, dCtxSh.pais.getNombre_pais())) {
            if (dCtxSh.channel!=Channel.movil_web)
                //Nos posicionamos y desplegamos la lista de países (en el caso de mobile no desplegamos 
            	//porque entonces es complejo manejar el desplegable que aparece en este tipo de dispositivos)
                desplieguaListaPaises(dFTest.driver);
            
            inputPaisAndSelect(dFTest.driver, dCtxSh.pais.getNombre_pais(), dCtxSh.channel);
        }
    }
    
    /**
     * Ejecuta el flujo (posterior a la selección del país) de selección de país/idioma + entrar
     */
    public static void selecionProvIdiomAndEnter(Pais pais, final IdiomaPais idioma, Channel channel, WebDriver driver) 
    throws Exception { 
        if (existeDesplProvincias(driver))
            //Selecciona la provincia "Barcelona"
            seleccionaProvincia(driver, pais.getNombre_pais(), channel);
    
        if (pais.getListIdiomas().size() > 1)
            //Si el país tiene más de 1 idioma seleccionar el que nos llega como parámetro
            seleccionaIdioma(driver, pais.getNombre_pais(), idioma.getCodigo().getLiteral());
        else {
            String codigoPais = getCodigoPais(driver, pais.getNombre_pais());
            selectButtonForEnter(driver, codigoPais);
        }
    
        //Esperamos a que desaparezca la página de Prehome
        PagePrehome.isNotPageUntil(30, driver);
        waitForPageLoaded(driver);
    }
}
