package com.mng.robotest.domains.compra.pageobjects.secsoynuevo;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Present;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.base.datatest.DataTest;

public abstract class SecSoyNuevo extends PageBase {
	
	public enum RadioState { ACTIVATE, DEACTIVATE }

	abstract String getXPathBotonContinue();
	abstract String getXPathFormIdent();
	abstract String getXPathInputEmail();
	abstract boolean isSection(int seconds);
	abstract boolean isInputWithText(String text);
	public abstract void setCheckPubliNewsletter(RadioState action);
	public abstract void setCheckConsentimiento(RadioState action);
	
	public static SecSoyNuevo make(Channel channel, AppEcom app, DataTest dataTest) {
		if (channel!=Channel.mobile) {
			return new SecSoyNuevoDesktop();
		}
		if (app==AppEcom.shop &&
			!dataTest.getTestCode().contains("COM002")) {
			//TODO el COM002 va sin cookies con lo que vamos por la variante-0 del TestAB
			//quitar cuando se desactive el TestAB) {
			return new SecSoyNuevoMobileNew();
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
