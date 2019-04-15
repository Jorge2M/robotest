package com.mng.robotest.test80.mango.test.pageobject.shop.registro;

import java.util.HashMap;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2IdentCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.DataRegType;


public class PageRegistroIni extends WebdrvWrapp {
	static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
	
	private static String XPathPestanyaRegistro = "//div[@class[contains(.,'registerTab')]]";
	private static String XPathHeaderNews = "//div[@class[contains(.,'registerModal')]]//div[@class='info']";
	
    private static String XPathNewsletterTitle = XPathHeaderNews + "//p[@class[contains(.,'newsletter-register-title')]]";
    
    private static String XPathInputName = "//input[@id[contains(.,'cfName')]]";
    private static String XPathInputApellidos = "//input[@id[contains(.,'cfSname')]]";
    private static String XPathInputEmail = "//input[@id[contains(.,'cfEmail')]]";
    private static String XPathInputPassword = "//input[@id[contains(.,'cfPassw')]]";
    private static String XPathInputTelefono = "//input[@id[contains(.,'cfTelf')]]";
    private static String XPathInputCodPostal = "//input[@id[contains(.,'cfCp')]]";
    private static String XPathSelectPais = "//select[@id[contains(.,':pais')]]";
    private static String XPathButtonRegistrate = "//div[@class[contains(.,'registerModal')]]//div[@class='submitContent']//input[@type='submit']";

    private static String XPathDivErrorName = "//div[@id[contains(.,'cfName')] and @class='errorValidation']";
    private static String XPathDivErrorApellidos = "//div[@id[contains(.,'cfSname')] and @class='errorValidation']";
    private static String XPathDivErrorEmail = "//div[@id[contains(.,'cfEmail')] and @class='errorValidation']";
    private static String XPathDivErrorPassword = "//div[@id[contains(.,'cfPassw')] and @class='errorValidation']";
    private static String XPathDivErrorTelefono = "//div[@id[contains(.,'cfTelf')] and @class='errorValidation']";
    private static String XPathDivErrorCodPostal = "//div[@id[contains(.,'cfCp')] and @class='errorValidation']";
    
    private static String XPathCapaLoading = "//div[@class[contains(.,'container-full-centered-loading')]]";
    
    private static String XPathCheckBoxPubli = "//div[@id='STEP1_cfPubli']";
    private static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
    private static String XPathTextRGPDloyalty = "//p[@class='gdpr-text-loyalty gdpr-profiling']";
    private static String XPathLegalRGPDloyalty = "//p[@class='gdpr-text-loyalty gdpr-data-protection']";
    
    private static String msgNameInvalid = "nombre. Este campo solo acepta letras";
    private static String msgApellidosInvalid = "apellidos. Este campo solo acepta letras";
    private static String msgEmailInvalid = "Introduce un e-mail correcto";
    private static String msgPasswordInvalid = "Revisa la contraseña";
    private static String msgTelefonoInvalid = "Revisa tu móvil";
    private static String msgCodPostalInvalid = "Revisa este campo";
    
    private static String msgCampoObligatorio = "Este campo es obligatorio";
    private static String msgUsrDuplicadoPostClick = "Email ya registrado";
    
    public static void clickRegisterTab(WebDriver driver) {
    	driver.findElement(By.xpath(XPathPestanyaRegistro)).click();
    }
    
    public static boolean isPageUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathPestanyaRegistro), maxSecondsToWait));
    }
    
    public static boolean isCapaLoadingInvisibleUntil(int maxSecondsToWait, WebDriver driver) {
    	return (isElementInvisibleUntil(driver, By.xpath(XPathCapaLoading), maxSecondsToWait));
    }
    
    public static String getNewsLetterTitleText(WebDriver driver) {
        try {
            WebElement titleNws = driver.findElement(By.xpath(XPathNewsletterTitle));
            if (titleNws!=null) {
                return driver.findElement(By.xpath(XPathNewsletterTitle)).getText();
            }
        }
        catch (Exception e) {
            //Retornamos ""
        }
        
        return "";
    }
    
    public static boolean newsLetterTitleContains(String literal, WebDriver driver) {
        return (getNewsLetterTitleText(driver).contains(literal));
    }
            
    public static InputDataXPath getXPathDataInput(DataRegType inputType) {
        switch (inputType) {
        case name:
            return (new InputDataXPath(XPathInputName, XPathDivErrorName, msgNameInvalid));
        case apellidos:
            return (new InputDataXPath(XPathInputApellidos, XPathDivErrorApellidos, msgApellidosInvalid));
        case email:
            return (new InputDataXPath(XPathInputEmail, XPathDivErrorEmail, msgEmailInvalid));
        case password:
            return (new InputDataXPath(XPathInputPassword, XPathDivErrorPassword, msgPasswordInvalid));
        case telefono:
            return (new InputDataXPath(XPathInputTelefono, XPathDivErrorTelefono, msgTelefonoInvalid));
        case codpostal:
            return (new InputDataXPath(XPathInputCodPostal, XPathDivErrorCodPostal, msgCodPostalInvalid));
        case codpais:
            return (new InputDataXPath("", "", ""));
        default:
            return null;
        }
    }
    
    public static String getXPath_mensajeErrorFormulario(String mensajeError) {
        return ("//div[@class='formErrors']//li[text()[contains(.,'" + mensajeError + "')]]");
    }
    
    public static String getXPath_inputObligatorioNoInformado() {
        return ("//input[@placeholder[contains(.,'*')] and @value='']");
    }
    
    public static String getXPath_selectPais() {
        return ("//select[@id[contains(.,'pais')]]");
    }
    
    public static int getNumInputsObligatoriosNoInformados(WebDriver driver) {
        String xpathInput = getXPath_inputObligatorioNoInformado();
        List<WebElement> inputsObligatorios = driver.findElements(By.xpath(xpathInput));
        return (inputsObligatorios.size());
    }
    
    public static void sendKeysToInput(DataRegType inputType, String dataToSend, WebDriver driver) {
        String xpathInput = getXPathDataInput(inputType).getXPah();
        moveToElement(By.xpath(xpathInput), driver);
        //Hay un problema en Chrome que provoca que aleatoriamente se corten los strings enviados mediante SendKeys. Así que debemos reintentarlo si no ha funcionado correctamente
        //https://github.com/angular/protractor/issues/2019
        //moveToElement(By.xpath(xpathInput), driver);
        sendKeysWithRetry(3, dataToSend, By.xpath(xpathInput), driver);
    }
    
    public static HashMap<String,String> sendDataAccordingCountryToInputs(Pais pais, String emailNonExistent, boolean clickPubli, WebDriver driver) 
    throws Exception {
        return (Page2IdentCheckout.inputDataPorDefectoSegunPais(pais, emailNonExistent, false, clickPubli, driver));
    }
    
    public static void sendDataToInputs(ListDataRegistro dataToSend, WebDriver driver) {
    	clickRegisterTab(driver);
        for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
            if (dataInput.dataRegType!=DataRegType.codpais) {
                sendKeysToInput(dataInput.dataRegType, dataInput.data, driver);
            } else {
                selectPais(dataInput.data, driver);
            }
        }
        
        driver.findElement(By.xpath("//body")).sendKeys(Keys.TAB);
    }
    
    public static void selectPais(String codPais, WebDriver driver) {
        new Select(driver.findElement(By.xpath(XPathSelectPais))).selectByValue(codPais);
    }
    
    /**
     * @return si es visible o no el mensaje concreto de error asociado al campo de input
     */
    public static boolean isVisibleMsgInputInvalid(DataRegType inputType, WebDriver driver) {
        return (isElementPresent(driver, By.xpath(getXPathDataInput(inputType).getXPathDivError())));
    }
    
    /**
     * @return número de mensajes de error asociados al campo de input
     */
    public static int getNumberMsgInputInvalid(DataRegType inputType, WebDriver driver) {
        String xpathError = getXPathDataInput(inputType).getXPathDivError() + "//span";
        if (isElementPresent(driver, By.xpath(xpathError))) {
            return (getNumElementsVisible(driver, By.xpath(xpathError)));
        }
        return 0;
    }
    
    public static boolean isVisibleAnyInputErrorMessage(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathDivErrorName)));
    }

    public static boolean isButtonRegistrateVisible(WebDriver driver) {
    	return (isElementVisible(driver, By.xpath(XPathButtonRegistrate)));
    }
    
    public static void clickButtonRegistrate(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonRegistrate));
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isButtonRegistrateVisible(driver)) {
        	try {
        		clickAndWaitLoad(driver, By.xpath(XPathButtonRegistrate));
        	}
        	catch (Exception e) {
        		pLogger.info("Problem in second click to Registrate Button", e);
        	}
        }
    }
    
    public static boolean isVisibleErrorUsrDuplicadoUntil(WebDriver driver, int maxSecondsToWait) {
        String xpathError = getXPath_mensajeErrorFormulario(msgUsrDuplicadoPostClick);
        return (isElementPresentUntil(driver, By.xpath(xpathError), maxSecondsToWait));
    }    
    
    public static int getNumberMsgCampoObligatorio(WebDriver driver) {
        String xpathError = "//div[@class='errorValidation']/span[text()[contains(.,'" + msgCampoObligatorio + "')]]";
        return (getNumElementsVisible(driver, By.xpath(xpathError)));
    }    
    
    public static int getNumberInputsTypePassword(WebDriver driver) {
        return (getNumElementsVisible(driver, By.xpath("//input[@type='password']")));
    }
    
    public static boolean isVisibleSelectPais(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(getXPath_selectPais())));
    }
    
    public static boolean isSelectedOptionPais(WebDriver driver, String codigoPais) {
        String xpathOption = getXPath_selectPais() + "/option[@selected='selected' and @value='" + codigoPais + "']"; 
        return (isElementPresent(driver, By.xpath(xpathOption)));
    }

	public static boolean isTextoRGPDVisible(WebDriver driver) {
		//TODO dejar sólo la versión de Loyalty cuando esta operativa suba a producción
		WebElement textoElem = getElementWithSizeNot0(driver, By.xpath(XPathTextRGPD));
		if (textoElem==null) {
			textoElem = getElementWithSizeNot0(driver, By.xpath(XPathTextRGPDloyalty));
		}
		
		return (textoElem!=null);
	}

	public static boolean isTextoLegalRGPDVisible(WebDriver driver) {
		//TODO dejar sólo la versión de Loyalty cuando esta operativa suba a producción
		WebElement textoElem = getElementWithSizeNot0(driver, By.xpath(XPathLegalRGPD));
		if (textoElem==null) {
			textoElem = getElementWithSizeNot0(driver, By.xpath(XPathLegalRGPDloyalty));
		}
		
		return (textoElem!=null);
	}

	public static boolean isCheckboxRecibirInfoPresentUntil(int maxSecondsToWait, WebDriver driver) {
		return isElementPresentUntil(driver, By.xpath(XPathCheckBoxPubli), maxSecondsToWait);
	}
}
