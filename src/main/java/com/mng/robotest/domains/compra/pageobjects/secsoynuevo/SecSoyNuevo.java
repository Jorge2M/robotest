package com.mng.robotest.domains.compra.pageobjects.secsoynuevo;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.domains.base.PageBase;

public abstract class SecSoyNuevo extends PageBase {
	
	public enum RadioState { ACTIVATE, DEACTIVATE }

	abstract String getXPathBotonContinue();
	abstract String getXPathFormIdent();
	abstract String getXPathInputEmail();
	abstract boolean isSection(int seconds);
	abstract boolean isInputWithText(String text);
	public abstract void setCheckPubliNewsletter(RadioState action);
	public abstract void setCheckConsentimiento(RadioState action);
	
	//TODO eliminar SecSoyNuevoDeviceOld cuando suba el nuevo flujo de login->checkout a PRO (11-abril-2023)
	public static SecSoyNuevo make(Channel channel) {
		if (channel!=Channel.mobile) {
			return new SecSoyNuevoDesktop();
		}
	
		var secSoyNuevoDeviceNew = new SecSoyNuevoMobileNew();
		if (secSoyNuevoDeviceNew.isSection(3)) {
			return secSoyNuevoDeviceNew;
		}
		
		return new SecSoyNuevoMobileOld();
	}
	
	public boolean isFormIdentUntil(int seconds) { 
		return state(Present, getXPathFormIdent()).wait(seconds).check();
	}

	public void inputEmail(String email) {
		inputEmailOneTime(email);
		if (!isInputWithText(email)) {
			inputEmailOneTime(email);
		}
	}
	
	public void clickContinue() {
		click(getXPathBotonContinue()).type(TypeClick.javascript).exec();
	}

	void clickRadio(RadioState action, boolean isActivated, String xpathRadio) {
		switch (action) {
		case ACTIVATE:
			if (!isActivated) {
				getElement(xpathRadio).click();
			}
			break;
		case DEACTIVATE:
			if (isActivated) {
				getElement(xpathRadio).click();
			}
			break;
		default:
			break;
		}
	}	
	
	private void inputEmailOneTime(String email) {
		var input = getElement(getXPathInputEmail());
		input.clear();
		input.sendKeys(email);
		waitMillis(500);
	}
	
}
