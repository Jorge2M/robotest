package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.klarna;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageKlarna extends PageObjTM {

	private final ModalUserDataKlarna modalUserData;
	private final ModalConfUserDataKlarna modalConfUserData;
	private final ModalInputPersonnumberKlarna modalInputPersonnumber;
	
	private final static String XPathBuyButton = "//button[@id='buy-button']";
	private final static String XPathIframe = "//iframe[@id[contains(.,'klarna-hpp-instance-fullscreen')]]";
	
	public PageKlarna(WebDriver driver) {
		super(driver);
		modalUserData = new ModalUserDataKlarna(driver);
		modalConfUserData = new ModalConfUserDataKlarna(driver);
		modalInputPersonnumber = new ModalInputPersonnumberKlarna(driver);
	}
	
	public boolean isPage(int maxSeconds) {
		return state(State.Visible, By.xpath(XPathBuyButton)).wait(maxSeconds).check();
	}
	
	public void clickBuyButton() {
		click(By.xpath(XPathBuyButton)).exec();
		if (!state(State.Invisible, By.xpath(XPathBuyButton)).wait(2).check()) {
			click(By.xpath(XPathBuyButton)).exec();
		}
	}
	
	public boolean isVisibleModalInputUserData(int maxSeconds) {
		goToIframe();
		boolean result = modalUserData.isModal(maxSeconds);
		leaveIframe();
		return result;
	}
	
	public void inputUserDataAndConfirm(DataKlarna dataKlarna) {
		goToIframe();
		modalUserData.inputData(dataKlarna);
		modalUserData.clickButtonContinue();
		if (modalConfUserData.isModal(2)) {
			modalConfUserData.clickButtonConfirmation();
		}
		leaveIframe();
	}
	
	public boolean isVisibleModalPersonNumber(int maxSeconds) {
		goToIframe();
		boolean result = modalInputPersonnumber.isModal(maxSeconds);
		leaveIframe();
		return result;
	}
	
	public void inputPersonNumberAndConfirm(String personnumber) {
		goToIframe();
		modalInputPersonnumber.inputPersonNumber(personnumber);
		modalInputPersonnumber.clickButtonConf();
		leaveIframe();
	}
	
    private void goToIframe() {
    	driver.switchTo().frame(driver.findElement(By.xpath(XPathIframe)));
    }
    
    private void leaveIframe() {
    	driver.switchTo().defaultContent();
    }
}
