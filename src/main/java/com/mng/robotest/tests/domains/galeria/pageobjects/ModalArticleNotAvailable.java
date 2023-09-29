package com.mng.robotest.tests.domains.galeria.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.AVISAME_Y_SUSCRIPCION_LEGAL_TEXTS;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalArticleNotAvailable extends PageBase {

	public enum StateModal { VISIBLE, NOT_VISIBLE }
	
	private static final String XPATH_MODAL = "//div[@data-testid[contains(.,'backInStock.dialog')]]/../..";
	private static final String XPATH_INPUT_MAIL = XPATH_MODAL + "//input[@data-testid[contains(.,'emailInput')]]";
	private static final String XPATH_RECIBIR_AVISO_BUTTON = "//button[@data-testid='listado.backInStock.button.submit']";
	private static final String XPATH_BUTTON_ENTENDIDO = "//button[@data-testid='listado.backInStock.feedbackButton.closeDialog']";
	private static final String XPATH_POLITICA_PRIVACIDAD = XPATH_MODAL + "//span[@data-testid='mng-link']";
	private static final String XPATH_ASPA_FOR_CLOSE = XPATH_MODAL + "//button[@data-testid='modal.close.button']";
	
	public ModalArticleNotAvailable() {
		super(AVISAME_Y_SUSCRIPCION_LEGAL_TEXTS);
	}
	
	public boolean inStateUntil(StateModal stateModal, int seconds) {
		switch (stateModal) {
		case VISIBLE:
			return isVisibleUntil(seconds);
		default:
		case NOT_VISIBLE:
			return isNotVisibleUntil(seconds);
		}
	}
	
	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XPATH_MODAL).wait(seconds).check();
	}
	
	public boolean isNotVisibleUntil(int seconds) {
		return (state(Invisible, XPATH_MODAL).wait(seconds).check());
	}

	public boolean isVisibleRPGD(int seconds) {
		return state(Visible, XPATH_MODAL).wait(seconds).check();
	}

	public boolean isVisibleInputEmail(int seconds) {
		return state(Visible, XPATH_INPUT_MAIL + "/..").wait(seconds).check();
	}	
	public void inputMail(String mail) {
		getElement(XPATH_INPUT_MAIL).sendKeys(mail);
	}
	public void clickRecibirAviso() {
		click(XPATH_RECIBIR_AVISO_BUTTON).exec();
	}
	public boolean isModalAvisoOkVisible(int seconds) {
		return state(Visible, XPATH_BUTTON_ENTENDIDO).wait(seconds).check();
	}
	public boolean isModalAvisoOkInvisible(int seconds) {
		return state(Invisible, XPATH_BUTTON_ENTENDIDO).wait(seconds).check();
	}	
	public void clickButtonEntendido() {
		click(XPATH_BUTTON_ENTENDIDO).exec();
	}
	
	public void clickPoliticaPrivacidad() {
		click(XPATH_POLITICA_PRIVACIDAD).exec();
	}
	
	public void clickAspaForClose() {
		getElement(XPATH_ASPA_FOR_CLOSE).click();
	}
}
