package com.mng.robotest.test.pageobject.shop.checkout.klarna;

import com.mng.robotest.domains.transversal.PageBase;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;

public class PageKlarna extends PageBase {

	private final ModalUserDataKlarna modalUserData = new ModalUserDataKlarna();
	private final ModalConfUserDataKlarna modalConfUserData = new ModalConfUserDataKlarna();
	private final ModalInputPersonnumberKlarna modalInputPersonnumber = new ModalInputPersonnumberKlarna();
	private final ModalInputPhoneKlarna modalInputPhone = new ModalInputPhoneKlarna();
	
	private static final String XPATH_BUY_BUTTON = "//button[@id[contains(.,'buy-button')]]";
	private static final String XPATH_IFRMAE = "//iframe[@id[contains(.,'klarna-hpp-instance-fullscreen')]]";
	
	public boolean isPage(int maxSeconds) {
		return state(State.Visible, XPATH_BUY_BUTTON).wait(maxSeconds).check();
	}
	
	public void clickBuyButton() {
		click(XPATH_BUY_BUTTON).exec();
		if (!state(State.Invisible, XPATH_BUY_BUTTON).wait(2).check()) {
			click(XPATH_BUY_BUTTON).exec();
		}
	}
	
	public boolean isVisibleModalInputUserData(int maxSeconds) {
		goToIframe();
		boolean result = modalUserData.isModal(maxSeconds);
		leaveIframe();
		return result;
	}
	
	public boolean isVisibleModalInputPhone(int maxSeconds) {
		goToIframe();
		boolean result = modalInputPhone.isModal(maxSeconds);
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
	
	public void inputDataPhoneAndConfirm(String phoneNumber, String otp) {
		goToIframe();
		modalInputPhone.inputPhoneNumber(phoneNumber);
		modalInputPhone.clickButtonContinue();
		modalInputPhone.inputOTP(otp);
		modalInputPhone.clickButtonConfirm();
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
		driver.switchTo().frame(getElement(XPATH_IFRMAE));
	}
	
	private void leaveIframe() {
		driver.switchTo().defaultContent();
	}
}
