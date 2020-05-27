package com.mng.robotest.test80.mango.test.pageobject.shop;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


/**
 * Define las interacciones vía XPATH con la página de Reembolsos 
 * @author jorge.munoz
 *
 */

public class PageReembolsos {
    
    public enum TypeReembolso {Transferencia, StoreCredit}

    static String XPathRefundsPanel = "//div[@id[contains(.,'refunds-panel')]]";
    static String XPathInputBanco = "//input[@id[contains(.,'bankName')] and @type='text']";
    static String XPathTextBancoAfterSave = "//div[@id='btBankDetails']//strong[1]";
    static String XPathInputTitular = "//input[@id[contains(.,'accountHolder')] and @type='text']";
    static String XPathTitularAfterSave = "//div[@id='btBankDetails']//strong[2]";
    static String XPathInputIBAN = "//input[@id[contains(.,'completeAccount')] and @type='text']";
    static String XPathInputPassport = "//input[@id[contains(.,'passport')] and @type='text']";
    static String XPathBirthdayDayBlock = "//div[@class='birthdayDay']";
    static String XPathSelectDayBirth = "//select[@id[contains(.,'dateOfBirth-day')]]";
    static String XPathSelectMonthBirth = "//select[@id[contains(.,'dateOfBirth-month')]]";
    static String XPathSelectYearBirth = "//select[@id[contains(.,'dateOfBirth-year')]]";
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

	public static boolean isPage(WebDriver driver) {
		return (state(Present, By.xpath(XPathRefundsPanel), driver).check());
	}

	public static boolean existsInputBanco(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputBanco), driver).check());
	}

	public static boolean isVisibleInputBanco(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputBanco), driver).check());
	}

    public static void typeInputBanco(WebDriver driver, String banco) {
        driver.findElement(By.xpath(XPathInputBanco)).clear();
        driver.findElement(By.xpath(XPathInputBanco)).sendKeys(banco);
    }

	public static boolean isVisibleTextBancoUntil(int maxSecondsToWait, WebDriver driver) {
		return (state(Visible, By.xpath(XPathTextBancoAfterSave), driver)
				.wait(maxSecondsToWait).check());
	}

	public static boolean existsInputTitular(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputTitular), driver).check());
	}

	public static boolean isVisibleInputTitular(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputTitular), driver).check());
	}

	public static void typeInputTitular(WebDriver driver, String titular) {
		driver.findElement(By.xpath(XPathInputTitular)).clear();
		driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);
	}

	public static boolean isVisibleTextTitular(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTitularAfterSave), driver).check());
	}

	public static boolean existsInputIBAN(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputIBAN), driver).check());
	}

	public static boolean isVisibleInputIBAN(WebDriver driver) {
		return (state(Visible, By.xpath(XPathInputIBAN), driver).check());
	}

	public static boolean isVisibleTextIBAN(WebDriver driver) {
		return (state(Visible, By.xpath(XPathTextIBANAfterSave), driver).check());
	}

	public static void typeInputIBAN(WebDriver driver, String IBAN) {
		driver.findElement(By.xpath(XPathInputIBAN)).clear();
		driver.findElement(By.xpath(XPathInputIBAN)).sendKeys(IBAN);
	}

	public static void typeIdPassportIfInputExists(WebDriver driver, String idPassport) {
		By byInput = By.xpath(XPathInputPassport);
		if (state(Visible, byInput, driver).check()) {
			driver.findElement(byInput).clear();
			driver.findElement(byInput).sendKeys(idPassport);
		}
	}
	
	public static void typeDateOfBirthIfInputExists(WebDriver driver, int day, int month, int year) {
		if (state(Visible, By.xpath(XPathBirthdayDayBlock), driver).check()) {
			new Select(driver.findElement(By.xpath(XPathSelectDayBirth))).selectByValue(String.valueOf(day));
			new Select(driver.findElement(By.xpath(XPathSelectMonthBirth))).selectByValue(String.valueOf(month));
			new Select(driver.findElement(By.xpath(XPathSelectYearBirth))).selectByValue(String.valueOf(year));
		}
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

	public static void typeInputsTransf(WebDriver driver, String banco, String titular, String IBAN, String idPassport) {
		typeInputBanco(driver, banco);
		typeInputTitular(driver, titular);
		typeInputIBAN(driver, IBAN);
		typeIdPassportIfInputExists(driver, idPassport);
		typeDateOfBirthIfInputExists(driver, 23, 4, 1974);
	}

	public static boolean isVisibleTransferenciaSectionUntil(int maxSeconds, WebDriver driver) {
		String xpathBlock = getXPathBlock(TypeReembolso.Transferencia);
		return (state(Visible, By.xpath(xpathBlock), driver).wait(maxSeconds).check());
	}

	public static boolean isVisibleStorecreditSection(WebDriver driver) {
		String xpathBlock = getXPathBlock(TypeReembolso.StoreCredit);
		return (state(Visible, By.xpath(xpathBlock), driver).check());
	}

	/**
	 * @return determina si está seleccionado o no el radio de la opción de "Transferencia Bancaria"
	 */
	public static boolean isCheckedRadio(TypeReembolso typeReembolso, WebDriver driver) {
		String xpathDiv = getXPath_divRadioCheckedTypeReembolso(typeReembolso);
		return (state(Present, By.xpath(xpathDiv), driver).check());
	}

	public static void clickRadio(TypeReembolso typeReembolso, WebDriver driver) {
		driver.findElement(By.xpath(getXPathRadio(typeReembolso) + "/..")).click();
	}

	/**
	 * En ocasiones un sólo click en el botón "Save" no tiene efecto. 
	 * Forzamos a que funcione mediante la siguiente estrategia: lo pulsamos, esperamos a que desaparezca y en caso negativo lo volvemos a pulsar
	 */
	public static void clickButtonSaveTransfForce(WebDriver driver) {
		click(By.xpath(XPathButtonSaveTransf), driver).exec();
		if (state(Present, By.xpath(XPathButtonSaveTransf), driver).check()) {
			click(By.xpath(XPathButtonSaveTransf), driver).exec();
		}
	}

	public static void clickButtonSaveTransf(WebDriver driver) {
		click(By.xpath(XPathButtonSaveTransf), driver).exec();
	}

	public static boolean isVisibleModalConfTransf(WebDriver driver, Channel channel, int seconds) throws Exception {
		//En el caso de móvil el div se oculta desplazándolo x píxeles por debajo de la coordenada 0Y
		if (channel==Channel.mobile) {
			for (int i=0; i<seconds; i++) {
				if (driver.findElement(By.xpath(XPathModalConfTransf)).getLocation().getY()>0) {
					return (true);
				}
				Thread.sleep(1000);
			}
			return false;
		}

		//En el caso de Desktop la capa se oculta normalmente
		return (state(Visible, By.xpath(XPathModalConfTransf), driver)
				.wait(seconds).check());
	}

	public static boolean isNotVisibleModalConfTransf(WebDriver driver, Channel channel, int seconds) throws Exception {
		//En el caso de móvil el div se oculta desplazándolo x píxeles por debajo de la coordenada 0Y
		if (channel==Channel.mobile) {
			for (int i=0; i<seconds; i++) {
				if (driver.findElement(By.xpath(XPathModalConfTransf)).getLocation().getY()<20) {
					return (true);
				}
				Thread.sleep(1000);
			}
			return false;
		}

		//En el caso de Desktop la capa se oculta normalmente
		return (state(Invisible, By.xpath(XPathModalConfTransf), driver)
				.wait(seconds).check());
	}
	
	public static float getImporteStoreCredit(WebDriver driver) {
		float precioFloat = -1;
		String xpath = getXPATH_textoImporteStoreCredit();
		if (state(Visible, By.xpath(xpath), driver).check()) {
			String precioTotal = driver.findElement(By.xpath(getXPATH_textoImporteStoreCredit())).getText();
			precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
		}
		return precioFloat;
	}

	public static boolean isVisibleSaveButtonStoreCredit(WebDriver driver) {
		return (state(Visible, By.xpath(XPathSaveButtonStoreCredit), driver).check());
	}

	public static boolean isVisibleSaveButtonStoreCreditUntil(int maxSeconds, WebDriver driver) {
		return (state(Visible, By.xpath(XPathSaveButtonStoreCredit), driver)
				.wait(maxSeconds).check());
	}

	public static void clickSaveButtonStoreCredit(WebDriver driver) {
		click(By.xpath(XPathSaveButtonStoreCredit), driver).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleSaveButtonStoreCredit(driver)) {
			click(By.xpath(XPathSaveButtonStoreCredit), driver).exec();
		}
	}
}
