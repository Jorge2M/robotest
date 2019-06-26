package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.popupFindAddress;

public class Page2IdentCheckout extends WebdrvWrapp {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    final static String XPathMainForm = "//form[@action[contains(.,'/expressregister')]]";
    final static String XPathInputPassword = "//input[@id[contains(.,'cfPass')]]";
    final static String XPathInputNombreUsr = "//input[@id[contains(.,':cfName')]]";
    final static String XPathInputApellidosUsr = "//input[@id[contains(.,':cfSname')]]";
    final static String XPathInputMiddleNameUsr = "//input[@id[contains(.,':cfMiddleName')]]";
    final static String XPathInputTelefono = "//input[@id[contains(.,':cfTelf')]]";
    final static String XPathInputDireccion1 = "//input[@id[contains(.,':cfDir1')]]";
    final static String XPathInputDireccion2 = "//input[@id[contains(.,':cfDir2')]]";
    final static String XPathCheckPublicidad = "//div[@id[contains(.,':cfPubli')] or @id[contains(.,'_cfPubli')]]";
    final static String XPathInputEmail = "//input[@id[contains(.,':cfEmail')]]";
    final static String XPathInputDNI = "//input[@id[contains(.,':cfDni')]]";
    final static String XPathInputCodPost = "//input[@id[contains(.,':cfCp')]]";
    final static String XPathInputProvEstadoActive = "//input[@id[contains(.,':cfState')] and not(@disabled) and not(@readonly)]";
    final static String XPathInputPoblacionActive = "//input[@id[contains(.,':cfCity')] and not(@disabled) and not(@readonly)]";
    final static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
    final static String XPathSelectProvPais = "//select[@id[contains(.,'provinciaPais')]]";
    final static String XPathSelectEstadosPais = "//select[@id[contains(.,'estadosPais')]]";
    final static String XPathSelectLocalidadesProvCity = "//select[@id[contains(.,'localidadesProvCity')]]";
    final static String XPathCheckHombre = "//div[@id[contains(.,':cfGener_H')]]";
    final static String XPathCheckCondiciones = "//input[@id[contains(.,':cfPriv')]]";
    final static String XPathBotonFindAddress = "//input[@class[contains(.,'load-button')] and @type='button']";
    final static String XPathBotonContinuar = "//div[@class='submitContent']/input[@type='submit']";
    final static String XPathMsgAduanas = "//div[@class='aduanas']";
    final static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    final static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
    
    //Con el substring simulamos un ends-with (que no está disponible en xpath 1.0)
    static String XPathSelectLocalidades = "//select[substring(@id, string-length(@id) - string-length('localidades') +1) = 'localidades']";
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementPresentUntil(driver, By.xpath(XPathMainForm), maxSecondsToWait));
    }
        
    public static boolean isInputPasswordAccordingEmail(boolean emailYetExists, WebDriver driver) {
        boolean isVisiblePassword = isElementVisible(driver, By.xpath(XPathInputPassword));
        if (emailYetExists==isVisiblePassword) {
            return false;
        }
        return true;
    }

    private static boolean setInputIfVisible(String xpathInput, String valueToSet, WebDriver driver) {
        boolean datoSeteado = false;
        try {
            List<WebElement> cfElementList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(xpathInput));
            if (cfElementList.size() > 0) {
                String cfElementReadonly = cfElementList.get(0).getAttribute("readonly");
                if (cfElementReadonly == null || cfElementReadonly.compareTo("true")!=0) {
                    if (cfElementList.get(0).getAttribute("value").compareTo(valueToSet) != 0) {
                        cfElementList.get(0).clear();
                        cfElementList.get(0).sendKeys(valueToSet);
                        cfElementList.get(0).sendKeys(Keys.TAB);
                    }
                    
                    datoSeteado = true;
                }    
            }
        }
        catch (org.openqa.selenium.StaleElementReferenceException e) {
            /*
             * Seguimos pues es posible que el dato esté seteado tal como es preciso 
             */
        }
        
        return datoSeteado;
    }
    
    public static void setNombreUsuarioIfVisible(String nombreUsr, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputNombreUsr, nombreUsr, driver);
        if (datoSeteado) {
            datosRegistro.put("cfName", nombreUsr);
        }
    }
    
    public static void setApellidosUsuarioIfVisible(String apellidosUsr, HashMap<String,String> dataPago, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputApellidosUsr, apellidosUsr, driver);
        if (datoSeteado) {
            dataPago.put("cfSname", apellidosUsr);
        }
    }
    
    public static void setMiddleNameUsuarioIfVisible(String middleNameUsr, HashMap<String,String> dataPago, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputMiddleNameUsr, middleNameUsr, driver);
        if (datoSeteado) {
            dataPago.put("cfMiddleName", middleNameUsr);
        }
    }
    
    public static void setPasswordIfVisible(String password, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputPassword, password, driver);
        if (datoSeteado) {
            datosRegistro.put("cfPass", password);
        }
    }
    
    public static void setTelefonoIfVisible(String movil, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputTelefono, movil, driver);
        if (datoSeteado) {
            datosRegistro.put("cfTelf", movil);
        }
    }    
    
    public static void setInputPoblacionIfVisible(String cfCity, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputPoblacionActive, cfCity, driver);
        if (datoSeteado) {
            datosRegistro.put("cfCity", cfCity);
        }
    }
    
    public static void setInputDireccion1IfVisible(String direccion1, WebDriver driver) {
        setInputIfVisible(XPathInputDireccion1, direccion1, driver);
    }
    
    public static void setInputDireccion1IfVisible(String direccion1, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputDireccion1, direccion1, driver);
        if (datoSeteado)
            datosRegistro.put("cfDir1", direccion1);
    }    
    
    public static void setInputDireccion2IfVisible(String direccion1, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputDireccion2, direccion1, driver);
        if (datoSeteado) {
            datosRegistro.put("cfDir2", direccion1);
        }
    }
    
    public static void setInputProvEstadoIfVisible(String cfState, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputProvEstadoActive, cfState, driver);
        if (datoSeteado) {
            datosRegistro.put("cfState", cfState);
        }
    }    

    public static void setInputDniIfVisible(String dni, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputDNI, dni, driver);        
        if (datoSeteado) {
            datosRegistro.put("cfDni", dni);
        }
    }
    
    /**
     * Se introduce el código postal y si se detectan un 'onkeyup' se espera un máximo de 2 segundos a que esté disponible la lista de poblaciones
     */
    public static boolean setCodPostalIfExistsAndWait(String codPostal, WebDriver driver) {
        boolean datoSeteado = setInputIfVisible(XPathInputCodPost, codPostal, driver);
        if (datoSeteado) {            
            List<WebElement> cfCodpostalList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathInputCodPost));
            if (cfCodpostalList.size() > 0) {
                //Si existe el tag 'onkeyup' (se desencadena petición Ajax) tenemos que esperaremos un máximo de 2 segundos hasta que aparezca el desplegable con las poblaciones
                if (cfCodpostalList.get(0).getAttribute("onkeyup")!=null && 
                    cfCodpostalList.get(0).getAttribute("onkeyup").compareTo("")!=0) {
                    isElementVisibleUntil(driver, By.xpath(XPathSelectLocalidades), 2);
                }
            }
        }        
        
        return datoSeteado;        
    }
    
    public static void setCodPostalIfExistsAndWait(String codigoPostal, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setCodPostalIfExistsAndWait(codigoPostal, driver);
        if (datoSeteado) {
            datosRegistro.put("cfCp", codigoPostal);
        }
    }    
    
    /**
     * Seteamos el email (si el campo de input existe, no está protegido y no está ya informado con ese valor)
     */
    public static String setEmailIfExists(String email, WebDriver driver) {
        String emailRegistro = email;
        
        // Revisamos si está visible el campo de input
        List<WebElement> cfEmailList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathInputEmail));
        if (cfEmailList.size() > 0) {
            //Revisamos si está protegido el campo de input
            String cfMailStatus = cfEmailList.get(0).getAttribute("disabled");
            if (cfMailStatus != null && cfMailStatus.compareTo("true") == 0) {
                emailRegistro = cfEmailList.get(0).getAttribute("value");
            } else {
                emailRegistro = email;
                //Revisamos si el input ya está seteado con ese valor
                if (cfEmailList.get(0).getAttribute("value").compareTo(emailRegistro) != 0) {
                    cfEmailList.get(0).clear();
                    cfEmailList.get(0).sendKeys(emailRegistro);
                    cfEmailList.get(0).sendKeys(Keys.TAB);
                }
            }
        }        
        
        return emailRegistro;
    }
    
    public static void setEmailIfExists(String email, HashMap<String,String> datosRegistro, WebDriver driver) {
        datosRegistro.put("cfEmail", setEmailIfExists(email, driver));
    }
    
    public static boolean setPaisIfVisibleAndNotSelected(String codigoPais, WebDriver driver) {
        boolean datoSeteado = false;
        List<WebElement> paisCf = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectPais));
        if (paisCf.size() > 0) {
            String xpathSelectedPais = XPathSelectPais + "/option[@selected='selected' and @value='" + codigoPais + "']";
            if (isElementPresent(driver, By.xpath(xpathSelectedPais))) {
                new Select(paisCf.get(0)).selectByValue(codigoPais);
                datoSeteado = true;
            }
        }        
        
        return datoSeteado;
    }
    
    public static void setPaisIfVisibleAndNotSelected(String codigoPais, HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setPaisIfVisibleAndNotSelected(codigoPais, driver);
        if (datoSeteado) {
            datosRegistro.put(":pais", codigoPais);
        }
    }    
    
    public static void clickBotonFindAddress(WebDriver driver) throws Exception {
        driver.findElement(By.xpath(XPathBotonFindAddress)).click();
        Thread.sleep(3000);
    }
    
    /**
     * Si existe, utiliza el botón "Find Address" para establecer la dirección (actualmente sólo existe en Corea del Sur)
     */
    public static void setDireccionWithFindAddressIfExists(String codPostalPais, WebDriver driver) throws Exception {
        String codPostalSeteado = getCodigoPostal(driver);
        if (codPostalPais.compareTo(codPostalSeteado)!=0 &&
            isElementVisible(driver, By.xpath(XPathBotonFindAddress))) {
            clickBotonFindAddress(driver);
            String mainWindowHandle = driver.getWindowHandle();
            try {
                String popupBuscador = popupFindAddress.goToPopupAndWait(mainWindowHandle, 5/*maxSecondsToWait*/, driver);
                if ("".compareTo(popupBuscador)!=0 && popupFindAddress.isIFrameUntil(0, driver)) {
                    popupFindAddress.switchToIFrame(driver);
                    if (popupFindAddress.isBuscadorClickableUntil(2/*maxSecondsToWait*/, driver)) {
                        popupFindAddress.setDataBuscador(driver, codPostalPais);
                        popupFindAddress.clickButtonLupa(driver);
                        popupFindAddress.clickFirstDirecc(driver);
                    }
                }
            }
            catch (Exception e) {
                pLogger.warn("Exception clicking Find Address button", e);
            }
            finally { driver.switchTo().window(mainWindowHandle); }
        }
    }
    
    public static String getCodigoPostal(WebDriver driver) {
        if (isElementPresent(driver, By.xpath(XPathInputCodPost))) {
            return (driver.findElement(By.xpath(XPathInputCodPost)).getAttribute("value"));
        }
        return "";
    }
    
    public static void clickPublicidadIfVisible(HashMap<String,String> datosRegistro, WebDriver driver) {
    	By byCheckPublic = By.xpath(XPathCheckPublicidad);
    	if (isElementPresent(driver, byCheckPublic)) {
    		moveToElement(byCheckPublic, driver);
    		if (isElementVisible(driver, byCheckPublic)) {
    			driver.findElement(byCheckPublic).click();
    			datosRegistro.put("cfPubli", "true");
    			return;
    		}
        }

        datosRegistro.put("cfPubli", "false");
    }
    
    /**
     * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
     */
    public static String setSelectLocalidadesIfVisible(int posInSelect, WebDriver driver) throws Exception {
        String datoSeteado = "";
        boolean staleElement = true;
        int i=0;
        Thread.sleep(500);
        
        //Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
        //Probamos hasta 3 veces mientras que obtengamos la Excepción
        while (staleElement && i<3) {
            List<WebElement> localidadesList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectLocalidades));
            if (localidadesList.size() > 0) {
                try {
                    new Select(localidadesList.get(0)).selectByIndex(posInSelect);
                    datoSeteado = localidadesList.get(0).getAttribute("value");
                    staleElement = false;
                }
                catch (StaleElementReferenceException e) {
                    Thread.sleep(500);
                    pLogger.warn("Exception setting localidad from select", e);
                }
            }
            
            i+=1;
        }

        return datoSeteado;
    }
    
    public static void setSelectLocalidadesIfVisible(WebDriver driver, int posInSelect, HashMap<String,String> datosRegistro) throws Exception {
        String datoSeteado = setSelectLocalidadesIfVisible(posInSelect, driver);
        if ("".compareTo(datoSeteado)!=0) {
            datosRegistro.put("cfCity", datoSeteado);
        }
    }
    
    /**
     * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
     */
    final static String firstProvinciaUkranie = "Ananivskyi";
    final static String XPathOptionFirstProvUkranie = "//div[@class[contains(.,'choices')] and text()[contains(.,'" + firstProvinciaUkranie + "')]]";
    public static String setSelectProvPaisIfVisible(int posInSelect, String codCountry, Channel channel, WebDriver driver) {
        String datoSeteado = "";
        WebElement provinciaPais = UtilsMangoTest.findElementPriorizingDisplayed(driver, By.xpath(XPathSelectProvPais));
        if (provinciaPais!=null) {
        	if (codCountry.compareTo(PaisShop.Ukraine.getCodigoPais())==0 &&
        		channel==Channel.desktop) {
            	driver.findElement(By.xpath(XPathSelectProvPais + "/..")).click();
            	driver.findElement(By.xpath(XPathOptionFirstProvUkranie)).click();
            	return firstProvinciaUkranie;
        	} else {
        		new Select(provinciaPais).selectByIndex(posInSelect);
                datoSeteado = provinciaPais.getAttribute("value");
                return datoSeteado;
        	}
        }      
        
        return "";
    }
    
    public static void setSelectProvPaisIfVisible(int posInSelect, HashMap<String,String> datosRegistro, String codPais, Channel channel, WebDriver driver) {
        String datoSeteado = setSelectProvPaisIfVisible(posInSelect, codPais, channel, driver);
        if ("".compareTo(datoSeteado)!=0) {
            datosRegistro.put("provinciaPais", datoSeteado);
        }
    }    
    
    public static String setSelectEstadosPaisIfVisible(int posInSelect, WebDriver driver) throws Exception {
        String datoSeteado = "";
        
        //Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
        //Probamos hasta 3 veces mientras que obtengamos la Excepción
        boolean staleElement = true;
        int i=0;
        while (staleElement && i<3) {
            List<WebElement> estadosPaisList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectEstadosPais));
            if (estadosPaisList.size() > 0) {
                try {
                    Select select = new Select(estadosPaisList.get(0));
                    select.selectByIndex(posInSelect);
                    datoSeteado = select.getFirstSelectedOption().getText();
                    //datoSeteado = estadosPaisList.get(0).getAttribute("value");
                    staleElement = false;
                }
                catch (StaleElementReferenceException e) {
                    Thread.sleep(500);
                    pLogger.warn("Exception selecting Estados from select", e);
                }
            }
            i+=1;
        }
        
        return datoSeteado;
    }
    
    public static void setSelectEstadosPaisIfVisible(int posInSelect, HashMap<String,String> datosRegistro, WebDriver driver) throws Exception {
        String datoSeteado = setSelectEstadosPaisIfVisible(posInSelect, driver);
        if ("".compareTo(datoSeteado)!=0) {
            datosRegistro.put("estadosPais", datoSeteado);
        }
    }    
    
    /**
     * @param posInSelect: elemento del desplegable que queremos desplegar (comenzando desde el 1)
     */
    public static String setSelectLocalidadesProvCity(int posInSelect, WebDriver driver) throws Exception {
        String datoSeteado = "";

        //Tenemos problemas aleatorios de StaleElementReferenceException con este elemento
        //Probamos hasta 3 veces mientras que obtengamos la Excepción
        boolean staleElement = true;
        int i=0;
        while (staleElement && i<3) {
            List<WebElement> localidadesList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathSelectLocalidadesProvCity));
            if (localidadesList.size() > 0) {
                try {
                    new Select(localidadesList.get(0)).selectByIndex(posInSelect);
                    datoSeteado = localidadesList.get(0).getAttribute("value");
                    staleElement = false;
                }
                catch (StaleElementReferenceException e) {
                    Thread.sleep(1000);
                    pLogger.warn("Exception selecting localidad from select. ", e);
                }
                catch (NoSuchElementException e) {
                    Thread.sleep(1000);
                    pLogger.warn("Exception selecting localidad from select. ", e);
                }
            }
            i+=1;
        }
        
        return datoSeteado;
    }
    
    public static void setSelectLocalidadesProvCity(int posInSelect, HashMap<String,String> datosRegistro, WebDriver driver) throws Exception {
        String datoSeteado = setSelectLocalidadesProvCity(posInSelect, driver);
        if ("".compareTo(datoSeteado)!=0) {
            datosRegistro.put("localidadesProvCity", datoSeteado);
        }
    }        
    
    public static boolean setCheckHombreIfVisible(WebDriver driver) {
        boolean datoSeteado = false;
        List<WebElement> cfGener_HList = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathCheckHombre));
        if (cfGener_HList.size() > 0) {
            cfGener_HList.get(0).click();
        }        
        
        return datoSeteado;        
    }
    
    public static void setCheckHombreIfVisible(HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setCheckHombreIfVisible(driver);
        if (datoSeteado) {
            datosRegistro.put("cfGener", "H");
        }
    }    
    
    public static boolean setCheckCondicionesIfVisible(WebDriver driver) {
        boolean datoSeteado = false;
        List<WebElement> cfPriv = UtilsMangoTest.findDisplayedElements(driver, By.xpath(XPathCheckCondiciones + "/../../div[@class='checkbox__image']"));
        if (cfPriv.size() > 0) { //Revisamos si el check NO está marcado 
            driver.findElement(By.xpath(XPathCheckCondiciones)).click();
            datoSeteado = true;
        }        
        
        return datoSeteado;        
    }
    
    public static void setCheckCondicionesIfVisible(HashMap<String,String> datosRegistro, WebDriver driver) {
        boolean datoSeteado = setCheckCondicionesIfVisible(driver);
        if (datoSeteado) {
            datosRegistro.put("cfPriv", "true");
        }
    }
    
    /**
     * Función que introduce los datos de cliente (sirve para la 1a página del registro y el checkout)
     */
    public static HashMap<String,String> inputDataPorDefectoSegunPais(
    		Pais pais, String emailUsr, boolean testCharNoLatinos, boolean clickPubli, Channel channel, WebDriver driver)
    		throws Exception {
        HashMap<String,String> datosSeteados = new HashMap<>();
        String nombreUsr = "Jorge";
        String apellidosUsr = "Muñoz Martínez";
        String middleNameUsr = "Sputnik";
        String codigoPais = pais.getCodigo_pais();
        String direccion1 = "c./ mossen trens n6 5 1a";
        if (testCharNoLatinos) {
            direccion1 = pais.getDireccharnolatinos().getText();
        }
        String direccion2 = "6";
        String cfCity = "VILAFRANCA";
        String codPostalPais = UtilsMangoTest.codigoPostal(pais);
        String cfState = "BARCELONA";        
        String movil = "665015122";
        movil = pais.getTelefono();
        String dni = pais.getDni();
        
        // Lo repetimos 2 veces porque el sendKeys sufre un bug ocasional que envía los datos a inputs incorrectos
        for (int i = 0; i < 2; i++) {
            setNombreUsuarioIfVisible(nombreUsr, datosSeteados, driver);
            setApellidosUsuarioIfVisible(apellidosUsr, datosSeteados, driver);
            setMiddleNameUsuarioIfVisible(middleNameUsr, datosSeteados, driver);
            setTelefonoIfVisible(movil, datosSeteados, driver);
            setPasswordIfVisible(Constantes.pass_standard, datosSeteados, driver);
            setEmailIfExists(emailUsr, datosSeteados, driver);
            setInputDireccion1IfVisible(direccion1, datosSeteados, driver);
            setInputDireccion2IfVisible(direccion2, datosSeteados, driver);
            setDireccionWithFindAddressIfExists(codPostalPais, driver);
            setPaisIfVisibleAndNotSelected(codigoPais, datosSeteados, driver);
            setCodPostalIfExistsAndWait(codPostalPais, datosSeteados, driver);
            setInputPoblacionIfVisible(cfCity, datosSeteados, driver);
            setSelectLocalidadesIfVisible(driver, 1, datosSeteados);
            setSelectProvPaisIfVisible(1, datosSeteados, pais.getCodigo_pais(), channel, driver); // Desplegable provincia país (p.e. Turquía)
            setCheckCondicionesIfVisible(datosSeteados, driver); // Selección aceptación de condiciones (actualmente sólo en Turquía)
            setSelectLocalidadesProvCity(1/*posInSelect*/, datosSeteados, driver); // Desplegable específico de Turquía
            setInputProvEstadoIfVisible(cfState, datosSeteados, driver);
            setInputDniIfVisible(dni, datosSeteados, driver);
            
            //if (pais.getCodigo_pais().compareTo(codPaisEspanya)!=0) 
                setSelectEstadosPaisIfVisible(1/*posInSelect*/, datosSeteados, driver);
            
            if (i==0 && clickPubli) {
                clickPublicidadIfVisible(datosSeteados, driver);
                setCheckHombreIfVisible(datosSeteados, driver);
            }
        }
        
        return datosSeteados;
    }
    
    public static boolean isContinuarClickableUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementClickableUntil(driver, By.xpath(XPathBotonContinuar), maxSecondsToWait));
    }
    
    public static void clickBotonContinuarAndWait(int maxSecondsToWait, WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar), maxSecondsToWait);
 
        //Hay una especie de bug (p.e. en el caso de Turquía) que hace que en ocasiones el click no tenga efecto
        if (isElementPresent(driver, By.xpath(XPathBotonContinuar))) {
            Thread.sleep(1500);
            clickAndWaitLoad(driver, By.xpath(XPathBotonContinuar), TypeOfClick.javascript);
        }
    }
    
    public static boolean isDisplayedAvisoAduanas(WebDriver driver) {
        return (
        	isElementVisibleUntil(driver, By.xpath(XPathMsgAduanas), 1)
        	//isElementPresent(driver, By.xpath(XPathMsgAduanas)) && 
            //driver.findElement(By.xpath(XPathMsgAduanas)).isDisplayed()
        );
    }
    
    public static boolean isTextoRGPDVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathTextRGPD));
	}

	public static boolean isTextoLegalRGPDVisible(WebDriver driver) {
		return isElementVisible(driver, By.xpath(XPathLegalRGPD));
	}
}