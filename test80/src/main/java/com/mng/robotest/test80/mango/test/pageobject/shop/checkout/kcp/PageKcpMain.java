package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageKcpMain extends PageObjTM {

	private final ModalHundayCard1 modalHundayCard1;
	private final ModalHundayCard2 modalHundayCard2;
	private final ModalHundayCard3 modalHundayCard3;
	private final ModalHundayCard4 modalHundayCard4;
	
	private static final String XPathAgreeAllRadio = "//input[@id='chk_all']";
	private static final String XPathHyundaiBlock = "//span[@class[contains(.,'btnCard')]]//span[text()[contains(.,'Hyundai')]]";
	private static final String XPathSelectInstallment = "//select[@id='select-month']";
	private static final String XPathNextButton = "//button[@id='cardNext']";
	private static final String XPathIframePage = "//iframe[@id='naxIfr']";
	
	public PageKcpMain(WebDriver driver) {
		super(driver);
		modalHundayCard1 = new ModalHundayCard1(driver);
		modalHundayCard2 = new ModalHundayCard2(driver);
		modalHundayCard3 = new ModalHundayCard3(driver);
		modalHundayCard4 = new ModalHundayCard4(driver);
	}
	
	public ModalHundayCard1 getModalHundayCard1() {
		return modalHundayCard1;
	}

	public ModalHundayCard2 getModalHundayCard2() {
		return modalHundayCard2;
	}

	public ModalHundayCard3 getModalHundayCard3() {
		return modalHundayCard3;
	}

	public ModalHundayCard4 getModalHundayCard4() {
		return modalHundayCard4;
	}
	
	public boolean isPage(int maxSeconds) {
		goToIframe(driver);
		boolean result = state(State.Present, By.xpath(XPathAgreeAllRadio)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void acceptTermsAndConditions() {
		goToIframe(driver);
		click(By.xpath(XPathAgreeAllRadio)).exec();
		leaveIframe();
	}
	
	public void selectHyundai() {
		goToIframe(driver);
		click(By.xpath(XPathHyundaiBlock)).exec();
		leaveIframe();
	}
	
	public boolean isSelectInstallmentVisible(int maxSeconds) {
		goToIframe(driver);
		boolean result = state(State.Visible, By.xpath(XPathSelectInstallment)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void selectInstallement(String value) {
		goToIframe(driver);
		select(By.xpath(XPathSelectInstallment), value).type(TypeSelect.VisibleText).exec();
		leaveIframe();
	}
	
	public void clickNext() {
		goToIframe(driver);
		click(By.xpath(XPathNextButton)).exec();
		leaveIframe();
	}
	
    public static void goToIframe(WebDriver driver) {
    	driver.switchTo().frame(driver.findElement(By.xpath(XPathIframePage)));
    }
    
    protected void leaveIframe() {
    	driver.switchTo().defaultContent();
    }
}
