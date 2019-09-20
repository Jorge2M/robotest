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
import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.arq.webdriverwrapper.TypeOfClick;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page2IdentCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.registro.ListDataRegistro.DataRegType;


public class PageRegistroIni extends WebdrvWrapp {
	
	private final WebDriver driver;
	
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
    
    private static String XPathCheckBoxPubli = "//input[@id[contains(.,'STEP1_cfPubli')]]";
    private static String XPathTextRGPD = "//p[@class='gdpr-text gdpr-profiling']";
    private static String XPathLegalRGPD = "//p[@class='gdpr-text gdpr-data-protection']";
    private static String XPathTextRGPDloyalty = "//p[@class='gdpr-text-loyalty gdpr-profiling']";
    private static String XPathLegalRGPDloyalty = "//p[@class='gdpr-text-loyalty gdpr-data-protection']";
    
    public static String XPathInputObligatorioNoInformado = "//input[@placeholder[contains(.,'*')] and @value='']";
    public static String XPathselectPais = "//select[@id[contains(.,'pais')]]";
    
    private static String msgNameInvalid = "nombre. Este campo solo acepta letras";
    private static String msgApellidosInvalid = "apellidos. Este campo solo acepta letras";
    private static String msgEmailInvalid = "Introduce un e-mail correcto";
    private static String msgPasswordInvalid = "Revisa la contraseña";
    private static String msgTelefonoInvalid = "Revisa tu móvil";
    private static String msgCodPostalInvalid = "Revisa este campo";
    
    private static String msgCampoObligatorio = "Este campo es obligatorio";
    private static String msgUsrDuplicadoPostClick = "Email ya registrado";
    
    private PageRegistroIni(WebDriver driver) {
    	this.driver = driver;
    }
    
    public static PageRegistroIni getNew(WebDriver driver) {
    	return new PageRegistroIni(driver);
    }
    
    private String getXPath_mensajeErrorFormulario(String mensajeError) {
        return ("//div[@class='formErrors']//li[text()[contains(.,'" + mensajeError + "')]]");
    }
    
    private InputDataXPath getXPathDataInput(DataRegType inputType) {
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
    
    public void clickRegisterTab(WebDriver driver) {
    	driver.findElement(By.xpath(XPathPestanyaRegistro)).click();
    }
    
    public boolean isPageUntil(int maxSecondsToWait) {
        return (isElementVisibleUntil(driver, By.xpath(XPathPestanyaRegistro), maxSecondsToWait));
    }
    
    public boolean isCapaLoadingInvisibleUntil(int maxSecondsToWait) {
    	return (isElementInvisibleUntil(driver, By.xpath(XPathCapaLoading), maxSecondsToWait));
    }
    
    public String getNewsLetterTitleText() {
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
    
    public boolean newsLetterTitleContains(String literal) {
        return (getNewsLetterTitleText().contains(literal));
    }
            
    public int getNumInputsObligatoriosNoInformados() {
        String xpathInput = XPathInputObligatorioNoInformado;
        List<WebElement> inputsObligatorios = driver.findElements(By.xpath(xpathInput));
        return (inputsObligatorios.size());
    }
    
    private void sendKeysToInput(DataRegType inputType, String dataToSend) {
        String xpathInput = getXPathDataInput(inputType).getXPah();
        moveToElement(By.xpath(xpathInput), driver);
        //Hay un problema en Chrome que provoca que aleatoriamente se corten los strings enviados mediante SendKeys. Así que debemos reintentarlo si no ha funcionado correctamente
        //https://github.com/angular/protractor/issues/2019
        //moveToElement(By.xpath(xpathInput), driver);
        sendKeysWithRetry(3, dataToSend, By.xpath(xpathInput), driver);
    }
    
    public HashMap<String,String> sendDataAccordingCountryToInputs(
    		Pais pais, String emailNonExistent, boolean clickPubli, Channel channel) throws Exception {
        return (Page2IdentCheckout.inputDataPorDefectoSegunPais(pais, emailNonExistent, false, clickPubli, channel, driver));
    }
    
    public void sendDataToInputs(ListDataRegistro dataToSend) {
    	clickRegisterTab(driver);
        for (DataRegistro dataInput : dataToSend.getDataPageInicial()) {
            if (dataInput.dataRegType!=DataRegType.codpais) {
                sendKeysToInput(dataInput.dataRegType, dataInput.data);
            } else {
                selectPais(dataInput.data);
            }
        }
        
        driver.findElement(By.xpath("//body")).sendKeys(Keys.TAB);
    }
    
    public void selectPais(String codPais) {
        new Select(driver.findElement(By.xpath(XPathSelectPais))).selectByValue(codPais);
    }
    
    public boolean isVisibleMsgInputInvalid(DataRegType inputType) {
        return (isElementPresent(driver, By.xpath(getXPathDataInput(inputType).getXPathDivError())));
    }
    
    public int getNumberMsgInputInvalid(DataRegType inputType) {
        String xpathError = getXPathDataInput(inputType).getXPathDivError() + "//span";
        if (isElementPresent(driver, By.xpath(xpathError))) {
            return (getNumElementsVisible(driver, By.xpath(xpathError)));
        }
        return 0;
    }
    
    public boolean isVisibleAnyInputErrorMessage() {
        return (isElementVisible(driver, By.xpath(XPathDivErrorName)));
    }

    public boolean isButtonRegistrateVisible() {
    	return (isElementVisible(driver, By.xpath(XPathButtonRegistrate)));
    }
    
    public void clickButtonRegistrate() throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonRegistrate));
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isButtonRegistrateVisible()) {
        	try {
        		clickAndWaitLoad(driver, By.xpath(XPathButtonRegistrate), TypeOfClick.javascript);
        	}
        	catch (Exception e) {
        		pLogger.info("Problem in second click to Registrate Button", e);
        	}
        }
    }
    
    public boolean isVisibleErrorUsrDuplicadoUntil(int maxSecondsToWait) {
        String xpathError = getXPath_mensajeErrorFormulario(msgUsrDuplicadoPostClick);
        return (isElementPresentUntil(driver, By.xpath(xpathError), maxSecondsToWait));
    }    
    
    public int getNumberMsgCampoObligatorio() {
        String xpathError = "//div[@class='errorValidation']/span[text()[contains(.,'" + msgCampoObligatorio + "')]]";
        return (getNumElementsVisible(driver, By.xpath(xpathError)));
    }    
    
    public int getNumberInputsTypePassword() {
        return (getNumElementsVisible(driver, By.xpath("//input[@type='password']")));
    }
    
    public boolean isVisibleSelectPais() {
        return (isElementVisible(driver, By.xpath(XPathSelectPais)));
    }
    
    public boolean isSelectedOptionPais(String codigoPais) {
        String xpathOption = XPathSelectPais + "/option[@selected='selected' and @value='" + codigoPais + "']"; 
        return (isElementPresent(driver, By.xpath(xpathOption)));
    }

	public boolean isTextoRGPDVisible() {
		//TODO dejar sólo la versión de Loyalty cuando esta operativa suba a producción
		WebElement textoElem = getElementWithSizeNot0(driver, By.xpath(XPathTextRGPD));
		if (textoElem==null) {
			textoElem = getElementWithSizeNot0(driver, By.xpath(XPathTextRGPDloyalty));
		}
		
		return (textoElem!=null);
	}

	public boolean isTextoLegalRGPDVisible() {
		//TODO dejar sólo la versión de Loyalty cuando esta operativa suba a producción
		WebElement textoElem = getElementWithSizeNot0(driver, By.xpath(XPathLegalRGPD));
		if (textoElem==null) {
			textoElem = getElementWithSizeNot0(driver, By.xpath(XPathLegalRGPDloyalty));
		}
		
		return (textoElem!=null);
	}

	public boolean isCheckboxRecibirInfoPresentUntil(int maxSecondsToWait) {
		return isElementPresentUntil(driver, By.xpath(XPathCheckBoxPubli), maxSecondsToWait);
	}
}
