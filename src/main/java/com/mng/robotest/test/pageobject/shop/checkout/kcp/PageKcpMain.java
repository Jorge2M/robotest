package com.mng.robotest.test.pageobject.shop.checkout.kcp;

import org.openqa.selenium.By;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;


public class PageKcpMain extends PageBase {

	private static final String XPATH_AGREE_ALL_RADIO = "//input[@id='chk_agree1']";
	private static final String XPATH_HYUNDAI_BLOCK = "//span[@class[contains(.,'btnCard')]]//span[text()[contains(.,'현대')]]";
	private static final String XPATH_SELECT_INSTALLMENT = "//select[@id='select-month']";
	private static final String XPATH_NEXT_BUTTON = "//button[@id='cardNext']";
	private static final String XPATH_IFRAME_PAGE = "//iframe[@id='naxIfr']";
	
	public boolean isPage(int maxSeconds) {
		goToIframe();
		boolean result = state(State.Present, By.xpath(XPATH_AGREE_ALL_RADIO)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void acceptTermsAndConditions() {
		goToIframe();
		click(By.xpath(XPATH_AGREE_ALL_RADIO)).exec();
		leaveIframe();
	}
	
	public boolean isVisibleTermAndConditions(int maxSeconds) {
		goToIframe();
		boolean isVisible = state(State.Present, By.xpath(XPATH_AGREE_ALL_RADIO)).wait(maxSeconds).check();
		leaveIframe();
		return isVisible;
	}
	
	public void selectHyundai() {
		goToIframe();
		click(By.xpath(XPATH_HYUNDAI_BLOCK)).exec();
		leaveIframe();
	}
	
	public boolean isSelectInstallmentVisible(int maxSeconds) {
		goToIframe();
		boolean result = state(State.Visible, By.xpath(XPATH_SELECT_INSTALLMENT)).wait(maxSeconds).check();
		leaveIframe();
		return result;
	}
	
	public void selectInstallement(String value) {
		goToIframe();
		select(By.xpath(XPATH_SELECT_INSTALLMENT), value).type(TypeSelect.VisibleText).exec();
		leaveIframe();
	}
	
	public void clickNext() {
		goToIframe();
		click(By.xpath(XPATH_NEXT_BUTTON)).exec();
		leaveIframe();
	}
	
	public void goToIframe() {
		driver.switchTo().frame(driver.findElement(By.xpath(XPATH_IFRAME_PAGE)));
	}
	
	protected void leaveIframe() {
		driver.switchTo().defaultContent();
	}
}
