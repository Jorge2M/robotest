package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageKcpMain extends PageObjTM {

	private static final String XPathAgreeAllRadio = "//input[@id='chk_agree1']";
	private static final String XPathHyundaiBlock = "//span[@class[contains(.,'btnCard')]]//span[text()[contains(.,'현대')]]";
	private static final String XPathSelectInstallment = "//select[@id='select-month']";
	private static final String XPathNextButton = "//button[@id='cardNext']";
	private static final String XPathIframePage = "//iframe[@id='naxIfr']";
	
	public PageKcpMain(WebDriver driver) {
		super(driver);
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
	
	public boolean isVisibleTermAndConditions(int maxSeconds) {
		goToIframe(driver);
		boolean isVisible = PageObjTM.state(State.Present, By.xpath(XPathAgreeAllRadio), driver).wait(maxSeconds).check();
		leaveIframe();
		return isVisible;
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
