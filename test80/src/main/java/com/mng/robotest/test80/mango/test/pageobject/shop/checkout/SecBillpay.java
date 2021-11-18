package com.mng.robotest.test80.mango.test.pageobject.shop.checkout;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;


public class SecBillpay extends PageObjTM {
	
	private final Channel channel;
	
	private final static String XPathBlockBillpayDesktop = "//div[@class[contains(.,'billpayFormulario')]]";
	private final static String XPathBlockRechnungMobil = "//div[@class[contains(.,'billpayinvoice')] and @class[contains(.,'show')]]";
	private final static String XPathBlockLastschriftMobil = "//div[@class[contains(.,'billpaydirectdebit')] and @class[contains(.,'show')]]";
	private final static String XPathSelectBirthDay = "//select[@id[contains(.,'birthDay')]]";
	private final static String XPathSelectBirthMonth = "//select[@id[contains(.,'birthMonth')]]";
	private final static String XPathSelectBirthYear = "//select[@id[contains(.,'birthYear')]]";
	private final static String XPathInputTitular = "//input[@id[contains(.,'accountHolderName')]]";
	private final static String XPathInputIBAN = "//input[@id[contains(.,':iban')] or @id[contains(.,':billpay_iban')]]";
	private final static String XPathInputBIC = "//input[@id[contains(.,':bic')] or @id[contains(.,':billpay_bic')]]";
	private final static String XPathRadioAceptoMobil = "//div[@class[contains(.,'contenidoTarjetaBillpay')]]//div[@class[contains(.,'custom-check')]]"; 
	private final static String XPathRadioAceptoDesktop = "//div[@class='legalText']/input[@type='checkbox']";			
   
	public SecBillpay(Channel channel, WebDriver driver) {
		super(driver);
		this.channel = channel;
	}
	
	public String getXPath_radioAcepto() {
		if (channel.isDevice()) {
			return XPathRadioAceptoMobil;
		}
		return XPathRadioAceptoDesktop;
	}

	public boolean isVisibleUntil(int maxSeconds) {
		if (channel.isDevice()) {
			String xpath = XPathBlockRechnungMobil + " | " + XPathBlockLastschriftMobil;
			return (state(Visible, By.xpath(xpath)).wait(maxSeconds).check());
		}
		return (state(Visible, By.xpath(XPathBlockBillpayDesktop), driver).wait(maxSeconds).check());
	}

	/**
	 * Informa la fecha de nacimiento en los 3 desplegables de Billpay
	 * @param datNac fecha en formato DD-MM-YYYY
	 */
	public void putBirthday(String datNac) {
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
	public void clickAcepto() {
		driver.findElement(By.xpath(getXPath_radioAcepto())).click();
	}
	
	public boolean isPresentSelectBirthDay() {
		return (state(Present, By.xpath(XPathSelectBirthDay)).check());
	}
	
	public boolean isPresentSelectBirthMonth() {
		return (state(Present, By.xpath(XPathSelectBirthMonth), driver).check());
	}
	
	public boolean isPresentSelectBirthBirthYear() {
		return (state(Present, By.xpath(XPathSelectBirthYear)).check());
	}

	public boolean isPresentRadioAcepto() {
		String xpath = getXPath_radioAcepto();
		return (state(Present, By.xpath(xpath)).check());
	}

	public boolean isPresentInputTitular() {
		return (state(Present, By.xpath(XPathInputTitular)).check());
	}

	public boolean isPresentInputIBAN() {
		return (state(Present, By.xpath(XPathInputIBAN)).check());
	}

	public boolean isPresentInputBIC() {
		return (state(Present, By.xpath(XPathInputBIC)).check());
	}

	public void sendDataInputTitular(String titular) {
		driver.findElement(By.xpath(XPathInputTitular)).sendKeys(titular);	
	}
	
	public void sendDataInputIBAN(String iban) {
		driver.findElement(By.xpath(XPathInputIBAN)).sendKeys(iban);
	}
	
	public void sendDataInputBIC(String bic) {
		driver.findElement(By.xpath(XPathInputBIC)).sendKeys(bic);
	}
}
