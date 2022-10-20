package com.mng.robotest.domains.compra.pageobject;

import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.TypeClick;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecSoyNuevo extends PageBase {
	
	public enum ActionNewsL { ACTIVATE, DEACTIVATE }

	private static final String XPATH_FORM_IDENT = "//div[@class='register' or @id='registerCheckOut']//form"; //desktop y mobil
	private static final String XPATH_INPUT_EMAIL = XPATH_FORM_IDENT + "//input[@id[contains(.,'expMail')]]";
	private static final String XPATH_INPUT_CONTENT = XPATH_FORM_IDENT + "//span[@class='eac-cval']";
	private static final String XPATH_BOTON_CONTINUE_MOBIL = "//div[@id='registerCheckOut']//div[@class='submit']/input";
	private static final String XPATH_BOTON_CONTINUE_DESKTOP = "//div[@class='register']//div[@class='submit']/input";
	private static final String XPATH_LINK_POLITICA_PRIVACIDAD = "//span[@data-testid='mng-link']";
	
	private static final String XPATH_INPUT_PUBLICIDAD_DESKTOP = "//input[@id[contains(.,':publicidad')]]";
	private static final String XPATH_INPUT_PUBLICIDAD_MOBIL = "//input[@id[contains(.,'publicidadActiva')]]";
	private static final String XPATH_RADIO_PUBLICIDAD_DESKTOP = XPATH_INPUT_PUBLICIDAD_DESKTOP;
	private static final String XPATH_RADIO_PUBLICIDAD_MOBIL = "//div[@class[contains(.,'subscribe__checkbox')]]";
	
	private String getXPathInputPublicidad() {
		if (channel==Channel.mobile) {
			return XPATH_INPUT_PUBLICIDAD_MOBIL;
		}
		return XPATH_INPUT_PUBLICIDAD_DESKTOP;
	}			
			
	private String getXPathRadioPublicidad() {
		if (channel==Channel.mobile) {
			return XPATH_RADIO_PUBLICIDAD_MOBIL;
		}
		return XPATH_RADIO_PUBLICIDAD_DESKTOP;
	}
	
	private String getXPathBotonContinue() {
		if (channel==Channel.mobile) {
			return XPATH_BOTON_CONTINUE_MOBIL;
		}
		return XPATH_BOTON_CONTINUE_DESKTOP;
	}

	public boolean isFormIdentUntil(int seconds) { 
		return state(Present, XPATH_FORM_IDENT).wait(seconds).check();
	}

	public boolean isCheckedPubliNewsletter() {
		if (state(State.Invisible, getXPathInputPublicidad()).check()) {
			return false;
		}
		String checked = getElement(getXPathInputPublicidad()).getAttribute("checked");
		return checked!=null;
	}

	public void setCheckPubliNewsletter(ActionNewsL action) {
		boolean isActivated = isCheckedPubliNewsletter();
		String xpathRadio = getXPathRadioPublicidad();
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

	public void inputEmail(String email) {
		inputEmailOneTime(email);
		if (!isInputWithText(email)) {
			inputEmailOneTime(email);
		}
	}
	
	private boolean isInputWithText(String text) {
		if (channel==Channel.desktop) {
			return getElement(XPATH_INPUT_CONTENT).getAttribute("innerHTML").compareTo(text)==0;
		} else {
			return getElement(XPATH_INPUT_EMAIL).getAttribute("value").compareTo(text)==0;
		}
	}

	private void inputEmailOneTime(String email) {
		WebElement input = getElement(XPATH_INPUT_EMAIL);
		input.clear();
		input.sendKeys(email);
		waitMillis(500);
	}

	public void clickContinue() {
		click(getXPathBotonContinue()).type(TypeClick.javascript).exec();
	}

	public boolean isLinkPoliticaPrivacidad(int seconds) {
		return state(Visible, XPATH_LINK_POLITICA_PRIVACIDAD).wait(seconds).check();
	}

}
