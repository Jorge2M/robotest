package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecBillpay {
	
    static String XPathBlockBillpayDesktop = "//div[@class[contains(.,'billpayFormulario')]]";
    static String XPathBlockRechnungMobil = "//div[@class[contains(.,'billpayinvoice')] and @class[contains(.,'show')]]";
    static String XPathBlockLastschriftMobil = "//div[@class[contains(.,'billpaydirectdebit')] and @class[contains(.,'show')]]";
    static String XPathSelectBirthDay = "//select[@id[contains(.,'birthDay')]]";
    static String XPathSelectBirthMonth = "//select[@id[contains(.,'birthMonth')]]";
    static String XPathSelectBirthYear = "//select[@id[contains(.,'birthYear')]]";
    static String XPathInputTitular = "//input[@id[contains(.,'accountHolderName')]]";
    static String XPathInputIBAN = "//input[@id[contains(.,':iban')] or @id[contains(.,':billpay_iban')]]";
    static String XPathInputBIC = "//input[@id[contains(.,':bic')] or @id[contains(.,':billpay_bic')]]";
    static String XPathRadioAceptoMobil = "//div[@class[contains(.,'contenidoTarjetaBillpay')]]//div[@class[contains(.,'custom-check')]]"; 
    static String XPathRadioAceptoDesktop = "//div[@class='legalText']/input[@type='checkbox']";            
   
    /**
     * @return el XPATH correspondiente al radio 'acepto' de billpay
     */
    public static String getXPath_radioAcepto(Channel channel) {
        if (channel==Channel.mobile) {
            return XPathRadioAceptoMobil;
        }
        return XPathRadioAceptoDesktop;
    }

	public static boolean isVisibleUntil(Channel channel, int maxSeconds, WebDriver driver) {
		if (channel==Channel.mobile) {
			String xpath = XPathBlockRechnungMobil + " | " + XPathBlockLastschriftMobil;
			return (state(Visible, By.xpath(xpath), driver).wait(maxSeconds).check());
		}
		return (state(Visible, By.xpath(XPathBlockBillpayDesktop), driver).wait(maxSeconds).check());
	}

    /**
     * Informa la fecha de nacimiento en los 3 desplegables de Billpay
     * @param datNac fecha en formato DD-MM-YYYY
     */
    public static void putBirthday(WebDriver driver, String datNac) {
        String[] valuesDate = datNac.split("-");
        int dia = Integer.valueOf(valuesDate[0]).intValue();
        int mes = Integer.valueOf(valuesDate[1]).intValue();
        int any = Integer.valueOf(valuesDate[2]).intValue();
        new Select(driver.findElement(By.xpath(XPathSelectBirthDay))).selectByValue(String.valueOf(dia));
        new Select(driver.findElement(By.xpath(XPathSelectBirthMonth))).selectByValue(String.valueOf(mes));
        new Select(driver.findElement(By.xpath(XPathSelectBirthYear))).selectByValue(String.valueOf(any));
    }
    
    /**
     * Informa la fecha de nacimiento en los 3 desplegables de Billpay
     * @param datNac fecha en formato DD-MM-YYYY
     */
    public static void clickAcepto(WebDriver driver, Channel channel) {
        driver.findElement(By.xpath(getXPath_radioAcepto(channel))).click();
    }
    
    public static boolean isPresentSelectBirthDay(WebDriver driver) {
    	return (state(Present, By.xpath(XPathSelectBirthDay), driver).check());
    }
    
    public static boolean isPresentSelectBirthMonth(WebDriver driver) {
    	return (state(Present, By.xpath(XPathSelectBirthMonth), driver).check());
    }
    
    public static boolean isPresentSelectBirthBirthYear(WebDriver driver) {
    	return (state(Present, By.xpath(XPathSelectBirthYear), driver).check());
    }

	public static boolean isPresentRadioAcepto(Channel channel, WebDriver driver) {
		String xpath = getXPath_radioAcepto(channel);
		return (state(Present, By.xpath(xpath), driver).check());
	}

	public static boolean isPresentInputTitular(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputTitular), driver).check());
	}

	public static boolean isPresentInputIBAN(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputIBAN), driver).check());
	}

	public static boolean isPresentInputBIC(WebDriver driver) {
		return (state(Present, By.xpath(XPathInputBIC), driver).check());
	}

    public static void sendDataInputTitular(String titular, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);    
    }
    
    public static void sendDataInputIBAN(String iban, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputIBAN)).sendKeys(iban);
    }
    
    public static void sendDataInputBIC(String bic, WebDriver driver) {
        driver.findElement(By.xpath(XPathInputBIC)).sendKeys(bic);
    }
}
