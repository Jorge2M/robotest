package com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.tests.domains.base.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick.*;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.compra.pageobjects.secsoynuevo.SecSoyNuevo.RadioState.*;

public abstract class SecSoyNuevo extends PageBase {
	
	public enum RadioState { ACTIVATE, DEACTIVATE }

	abstract String getXPathBotonContinue();
	abstract String getXPathFormIdent();
	abstract String getXPathInputEmail();
	abstract boolean isSection(int seconds);
	abstract boolean isInputWithText(String text);
	public abstract void setCheckPubliNewsletter(RadioState action);
	public abstract void setCheckConsentimiento(RadioState action);
	
	public static SecSoyNuevo make(Channel channel) {
		//TODO [flux-bolsa] reactivar cuando se reactive el nuevo flujo
//		if (channel!=Channel.mobile) {
			return new SecSoyNuevoDesktop();
//		}
//		return new SecSoyNuevoMobile();
	}
	
	public boolean isFormIdentUntil(int seconds) { 
		return state(PRESENT, getXPathFormIdent()).wait(seconds).check();
	}
	
	public boolean isInputEmailUntil(int seconds) { 
		return state(PRESENT, getXPathInputEmail()).wait(seconds).check();
	}
	
	public void inputEmail(String email) {
		inputEmailOneTime(email);
		if (!isInputWithText(email)) {
			inputEmailOneTime(email);
		}
	}
	
	public void clickContinue() {
		//TODO [flux-bolsa] quitar el caso de device cuando se reactive el nuevo flujo
		if (isDevice()) {
			click("//input[@id[contains(.,'RegLogChkNew')] and @type='submit']").exec();
		} else {
			click(getXPathBotonContinue()).type(JAVASCRIPT).exec();
		}
	}

	void clickRadio(RadioState radioAction, boolean isActivated, String xpathRadio) {
	    var radioElement = getElement(xpathRadio);
	    if ((radioAction == ACTIVATE && !isActivated) || 
	        (radioAction == DEACTIVATE && isActivated)) {
	        radioElement.click();
	    }
	}	
	
	private void inputEmailOneTime(String email) {
		var input = getElement(getXPathInputEmail());
		input.clear();
		input.sendKeys(email);
		waitMillis(500);
	}
	
}
