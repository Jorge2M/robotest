package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.arq.webdriverwrapper.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


/**
 * Define las interacciones vía XPATH con la página de Reembolsos 
 * @author jorge.munoz
 *
 */

public class PageReembolsos extends WebdrvWrapp {
    
    public enum TypeReembolso {Transferencia, StoreCredit}

    static String XPathRefundsPanel = "//div[@id[contains(.,'refunds-panel')]]";
    static String XPathInputBanco = "//input[@id[contains(.,'bankName')] and @type='text']";
    static String XPathTextBancoAfterSave = "//div[@id='btBankDetails']//strong[1]";
    static String XPathInputTitular = "//input[@id[contains(.,'accountHolder')] and @type='text']";
    static String XPathTitularAfterSave = "//div[@id='btBankDetails']//strong[2]";
    static String XPathInputIBAN = "//input[@id[contains(.,'completeAccount')] and @type='text']";
    static String XPathTextIBANAfterSave = "//div[@id='btBankDetails']//strong[3]";
    static String XPathButtonSaveTransf = "//button[@id[contains(.,'bankTransferSubmit')]]";
    static String XPathModalConfTransf = "//div[@id[contains(.,'Pedidos:confirmation-modal')]]";
    static String XPathRadioStoreCredit = "//div[@class[contains(.,'refund-check')]]//input[@value='store-credit']";
    static String XPathRadioTransferencia = "//div[@class[contains(.,'refund-check')]]//input[@value='bank-transfer']";
    static String XPathSaveButtonStoreCredit = "//button[@data-input-id='customer-balance']";
    
    public static String getXPathBlock(TypeReembolso typeReembolso) {
    	return (getXPathRadio(typeReembolso) + "/..");
    }
    
    public static String getXPathRadio(TypeReembolso typeReembolso) {
        switch (typeReembolso) {
        case StoreCredit:
            return XPathRadioStoreCredit;
        case Transferencia:
            return XPathRadioTransferencia;
        default:
            return "";
        }
    }
    
    /**
     * @return el elemento que contiene el texto con el saldo a nivel del StoreCredit
     */
    public static String getXPATH_textoImporteStoreCredit() {
        //El tag no tiene ningún atributo así que el XPATH resultante no es muy elegante
        return (getXPathRadio(TypeReembolso.StoreCredit) + "/../..//strong");
    }
    
    /**
     * @param checked indica si queremos el XPath de un div con un radio checkeado o no
     */
    public static String getXPath_divRadioCheckedTypeReembolso(TypeReembolso typeReembolso) {
        String xpathRadio = getXPathRadio(typeReembolso);
        return (xpathRadio + "/ancestor::div[@class[contains(.,'custom-radio--checked')]]");
    }
    
    /**
     * @param driver
     * @return si realmente se trata de la página de devoluciones
     */
    public static boolean isPage(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathRefundsPanel)));
    }    
    
    public static boolean existsInputBanco(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputBanco)));
    }
    
    public static boolean isVisibleInputBanco(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputBanco)));
    }
    
    public static void typeInputBanco(WebDriver driver, String banco) {
        driver.findElement(By.xpath(XPathInputBanco)).clear();
        driver.findElement(By.xpath(XPathInputBanco)).sendKeys(banco);
    }
    
    public static boolean isVisibleTextBancoUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathTextBancoAfterSave), maxSecondsToWait));
    }    
    
    public static boolean existsInputTitular(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputTitular)));
    }
    
    public static boolean isVisibleInputTitular(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputTitular)));
    }    
    
    public static void typeInputTitular(WebDriver driver, String titular) {
        driver.findElement(By.xpath(XPathInputTitular)).clear();
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
    }    
    
    public static boolean isVisibleTextTitular(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathTitularAfterSave)));
    }    
    
    public static boolean existsInputIBAN(WebDriver driver) {
        return (isElementPresent(driver, By.xpath(XPathInputIBAN)));
    }
    
    public static boolean isVisibleInputIBAN(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathInputIBAN)));
    }
    
    public static boolean isVisibleTextIBAN(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathTextIBANAfterSave)));
    }
    
    public static void typeInputIBAN(WebDriver driver, String IBAN) {
        driver.findElement(By.xpath(XPathInputIBAN)).clear();
        driver.findElement(By.xpath(XPathInputIBAN)).sendKeys(IBAN);
    }    
    
    /**
     * @return si son visible los 3 inputs para configurar el reembolso por transferencia: banco, titular e iban
     */
    public static boolean isVisibleInputsTransf(WebDriver driver) {
        return (
        	isVisibleInputBanco(driver) && 
        	isVisibleInputTitular(driver) && 
        	isVisibleInputIBAN(driver));
    }
    
    /**
     * Informa los inputs de la opción de transferencias
     */
    public static void typeInputsTransf(WebDriver driver, String banco, String titular, String IBAN) {
        typeInputBanco(driver, banco);
        typeInputTitular(driver, titular);
        typeInputIBAN(driver, IBAN);
    }
    
    public static boolean isVisibleTransferenciaSectionUntil(int maxSecondsWait, WebDriver driver) {
    	String xpathBlock = getXPathBlock(TypeReembolso.Transferencia);
        return (isElementVisibleUntil(driver, By.xpath(xpathBlock), maxSecondsWait));        
    }
    
    public static boolean isVisibleStorecreditSection(WebDriver driver) {
    	String xpathBlock = getXPathBlock(TypeReembolso.StoreCredit);
        return (isElementVisible(driver, By.xpath(xpathBlock)));
    }
    
    /**
     * @return determina si está seleccionado o no el radio de la opción de "Transferencia Bancaria"
     */
    public static boolean isCheckedRadio(TypeReembolso typeReembolso, WebDriver driver) {
        String xpathDiv = getXPath_divRadioCheckedTypeReembolso(typeReembolso);
        return (isElementPresent(driver, By.xpath(xpathDiv)));
    }    
    
    public static void clickRadio(TypeReembolso typeReembolso, WebDriver driver) {
        driver.findElement(By.xpath(getXPathRadio(typeReembolso) + "/..")).click();
    }
    
    /**
     * En ocasiones un sólo click en el botón "Save" no tiene efecto. 
     * Forzamos a que funcione mediante la siguiente estrategia: lo pulsamos, esperamos a que desaparezca y en caso negativo lo volvemos a pulsar
     */
    public static void clickButtonSaveTransfForce(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSaveTransf));
        if (isElementPresent(driver, By.xpath(XPathButtonSaveTransf))) {
            clickAndWaitLoad(driver, By.xpath(XPathButtonSaveTransf));
        }
    }
    
    public static void clickButtonSaveTransf(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathButtonSaveTransf));
    }
    
    /**
     * @param driver
     * @return si es visible el modal con la confirmación del guardado de los datos a nivel de la transferencia
     */
    public static boolean isVisibleModalConfTransf(WebDriver driver, Channel channel, int seconds) throws Exception {
        //En el caso de móvil el div se oculta desplazándolo x píxeles por debajo de la coordenada 0Y
        if (channel==Channel.movil_web) {
            for (int i=0; i<seconds; i++) {
                if (driver.findElement(By.xpath(XPathModalConfTransf)).getLocation().getY()>0) {
                    return (true);
                }
                Thread.sleep(1000);
            }
            
            return false;
        }
        
      //En el caso de Desktop la capa se oculta normalmente
      return (isElementVisibleUntil(driver, By.xpath(XPathModalConfTransf), seconds));
    }
    
    /**
     * @param driver
     * @return si es invisible el modal con la confirmación del guardado de los datos a nivel de la transferencia
     */
    public static boolean isNotVisibleModalConfTransf(WebDriver driver, Channel channel, int seconds) throws Exception {
        //En el caso de móvil el div se oculta desplazándolo x píxeles por debajo de la coordenada 0Y
        if (channel==Channel.movil_web) {
            for (int i=0; i<seconds; i++) {
                if (driver.findElement(By.xpath(XPathModalConfTransf)).getLocation().getY()<20) {
                    return (true);
                }
                Thread.sleep(1000);
            }
            
            return false;
        }

        //En el caso de Desktop la capa se oculta normalmente
        return (isElementInvisibleUntil(driver, By.xpath(XPathModalConfTransf), seconds));
    }
    
    /**
     * @return el importe correspondiente al saldo en cuenta
     */
    public static float getImporteStoreCredit(WebDriver driver) {
        float precioFloat = -1;
        if (isElementVisible(driver, By.xpath(getXPATH_textoImporteStoreCredit()))) {
            String precioTotal = driver.findElement(By.xpath(getXPATH_textoImporteStoreCredit())).getText();
            precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
        }
        
        return precioFloat;
    }
    
    public static boolean isVisibleSaveButtonStoreCredit(WebDriver driver) {
        return (isElementVisible(driver, By.xpath(XPathSaveButtonStoreCredit))); 
    }
    
    public static boolean isVisibleSaveButtonStoreCreditUntil(int maxSecondsToWait, WebDriver driver) {
        return (isElementVisibleUntil(driver, By.xpath(XPathSaveButtonStoreCredit), maxSecondsToWait)); 
    }    
    
    public static void clickSaveButtonStoreCredit(WebDriver driver) throws Exception {
        clickAndWaitLoad(driver, By.xpath(XPathSaveButtonStoreCredit));
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        if (isVisibleSaveButtonStoreCredit(driver)) {
        	clickAndWaitLoad(driver, By.xpath(XPathSaveButtonStoreCredit));       
        }
    }
}
