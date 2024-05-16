package com.mng.robotest.tests.domains.compra.payments.kcp.pageobjects;

import org.openqa.selenium.By;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.SelectElement.TypeSelect;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageKcpMain extends PageBase {

	private static final String XP_AGREE_ALL_RADIO = "//input[@id='chk_agree1']";
	private static final String XP_HYUNDAI_BLOCK = "//span[@class[contains(.,'btnCard')]]//span[text()[contains(.,'현대')]]";
	private static final String XP_SELECT_INSTALLMENT = "//select[@id='select-month']";
	private static final String XP_NEXT_BUTTON = "//button[@id='cardNext']";
	private static final String XP_IFRAME_PAGE = "//iframe[@id='naxIfr']";
	
	public boolean isPage(int seconds) {
		goToIframe();
		boolean result = state(PRESENT, XP_AGREE_ALL_RADIO).wait(seconds).check();
		leaveIframe();
		return result;
	}
	
	public void acceptTermsAndConditions() {
		goToIframe();
		click(XP_AGREE_ALL_RADIO).exec();
		leaveIframe();
	}
	
	public boolean isVisibleTermAndConditions(int seconds) {
		goToIframe();
		boolean isVisible = state(PRESENT, XP_AGREE_ALL_RADIO).wait(seconds).check();
		leaveIframe();
		return isVisible;
	}
	
	public void selectHyundai() {
		goToIframe();
		click(XP_HYUNDAI_BLOCK).exec();
		leaveIframe();
	}
	
	public boolean isSelectInstallmentVisible(int seconds) {
		goToIframe();
		boolean result = state(VISIBLE, XP_SELECT_INSTALLMENT).wait(seconds).check();
		leaveIframe();
		return result;
	}
	
	public void selectInstallement(String value) {
		goToIframe();
		select(By.xpath(XP_SELECT_INSTALLMENT), value).type(TypeSelect.VisibleText).exec();
		leaveIframe();
	}
	
	public void clickNext() {
		goToIframe();
		click(XP_NEXT_BUTTON).exec();
		leaveIframe();
	}
	
	public void goToIframe() {
		driver.switchTo().frame(getElement(XP_IFRAME_PAGE));
	}

}
