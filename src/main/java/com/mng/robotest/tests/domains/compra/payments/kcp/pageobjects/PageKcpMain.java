package com.mng.robotest.tests.domains.compra.payments.kcp.pageobjects;

import org.openqa.selenium.By;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKcpMain extends PageBase {

	private static final String XPATH_AGREE_ALL_RADIO = "//input[@id='chk_agree1']";
	private static final String XPATH_HYUNDAI_BLOCK = "//span[@class[contains(.,'btnCard')]]//span[text()[contains(.,'현대')]]";
	private static final String XPATH_SELECT_INSTALLMENT = "//select[@id='select-month']";
	private static final String XPATH_NEXT_BUTTON = "//button[@id='cardNext']";
	private static final String XPATH_IFRAME_PAGE = "//iframe[@id='naxIfr']";
	
	public boolean isPage(int seconds) {
		goToIframe();
		boolean result = state(Present, XPATH_AGREE_ALL_RADIO).wait(seconds).check();
		leaveIframe();
		return result;
	}
	
	public void acceptTermsAndConditions() {
		goToIframe();
		click(XPATH_AGREE_ALL_RADIO).exec();
		leaveIframe();
	}
	
	public boolean isVisibleTermAndConditions(int seconds) {
		goToIframe();
		boolean isVisible = state(Present, XPATH_AGREE_ALL_RADIO).wait(seconds).check();
		leaveIframe();
		return isVisible;
	}
	
	public void selectHyundai() {
		goToIframe();
		click(XPATH_HYUNDAI_BLOCK).exec();
		leaveIframe();
	}
	
	public boolean isSelectInstallmentVisible(int seconds) {
		goToIframe();
		boolean result = state(Visible, XPATH_SELECT_INSTALLMENT).wait(seconds).check();
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
		click(XPATH_NEXT_BUTTON).exec();
		leaveIframe();
	}
	
	public void goToIframe() {
		driver.switchTo().frame(getElement(XPATH_IFRAME_PAGE));
	}
	
	protected void leaveIframe() {
		driver.switchTo().defaultContent();
	}
}
