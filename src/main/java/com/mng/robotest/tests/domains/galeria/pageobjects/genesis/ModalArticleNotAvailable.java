package com.mng.robotest.tests.domains.galeria.pageobjects.genesis;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.AVISAME_Y_SUSCRIPCION_LEGAL_TEXTS;

import com.mng.robotest.tests.domains.base.PageBase;

public class ModalArticleNotAvailable extends PageBase {

	public enum StateModal { VISIBLE, NOT_VISIBLE }
	
	private static final String XP_MODAL = "//*[@data-testid='backInStock.modal']";
	private static final String XP_INPUT_MAIL = XP_MODAL + "//input[@data-testid[contains(.,'emailInput')]]";
	private static final String XP_RECIBIR_AVISO_BUTTON = "//button[@data-testid='plp.backInStock.button.submit']";
	private static final String XP_SNACKVAR_AVISAME_OK = "//*[@data-testid[contains(.,'feedback-snackbar')]]";
	private static final String XP_POLITICA_PRIVACIDAD = XP_MODAL + "//*[@data-testid='bacnInStock-policy.privacyPolicy.expand']";
	private static final String XP_ASPA_FOR_CLOSE = XP_MODAL + "//button[@data-testid='modal.close.button']";
	
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
		return state(VISIBLE, XP_MODAL).wait(seconds).check();
	}
	
	public boolean isNotVisibleUntil(int seconds) {
		return (state(INVISIBLE, XP_MODAL).wait(seconds).check());
	}

	public boolean isVisibleRPGD(int seconds) {
		return state(VISIBLE, XP_MODAL).wait(seconds).check();
	}

	public boolean isVisibleInputEmail(int seconds) {
		return state(VISIBLE, XP_INPUT_MAIL + "/..").wait(seconds).check();
	}	
	public void inputMail(String mail) {
		getElement(XP_INPUT_MAIL).sendKeys(mail);
	}
	public void clickRecibirAviso() {
		click(XP_RECIBIR_AVISO_BUTTON).exec();
	}
	public boolean isSnackvarAvisoOkVisible(int seconds) {
		return state(VISIBLE, XP_SNACKVAR_AVISAME_OK).wait(seconds).check();
	}	
	
	public void clickPoliticaPrivacidad() {
		click(XP_POLITICA_PRIVACIDAD).exec();
	}
	
	public void clickAspaForClose() {
		getElement(XP_ASPA_FOR_CLOSE).click();
	}
}
