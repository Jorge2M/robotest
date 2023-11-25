package com.mng.robotest.tests.domains.reembolsos.pageobjects;

import org.openqa.selenium.support.ui.Select;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageReembolsos extends PageBase {
	
	public enum TypeReembolso {TRANSFERENCIA, STORE_CREDIT}

	private static final String XP_REFUNDS_PANEL = "//html[@class[contains(.,'refunds')]]";//
	private static final String XP_INPUT_BANCO = "//*[@data-testid='myRefunds.bankDetails.form.banco']";//
	private static final String XP_TEXT_BANCO_AFTER_SAVE = "//div[@id='btBankDetails']//strong[1]";
	private static final String XP_INPUT_TITULAR = "//*[@data-testid='myRefunds.bankDetails.form.titular']"; //
	private static final String XP_TITULAR_AFTER_SAVE = "//div[@id='btBankDetails']//strong[2]";
	private static final String XP_INPUT_IBAN = "//*[@data-testid='myRefunds.bankDetails.form.cuentaCompleta']"; //
	private static final String XP_INPUT_PASSPORT = "//input[@id[contains(.,'passport')] and @type='text']";
	private static final String XP_BIRTHDAY_DAY_BLOCK = "//div[@class='birthdayDay']";
	private static final String XP_SELECT_DAY_BIRTH = "//select[@id[contains(.,'dateOfBirth-day')]]";
	private static final String XP_SELECT_MONTH_BIRTH = "//select[@id[contains(.,'dateOfBirth-month')]]";
	private static final String XP_SELECT_YEAR_BIRTH = "//select[@id[contains(.,'dateOfBirth-year')]]";
	private static final String XP_TEXT_IBANA_AFTER_SAVE = "//div[@id='btBankDetails']//strong[3]";
	private static final String XP_BUTTON_SAVE_TRANSF = "//button[@id[contains(.,'bankTransferSubmit')]]";
	private static final String XP_MODAL_CONF_TRANS = "//div[@id[contains(.,'Pedidos:confirmation-modal')]]";
	private static final String XP_RADIO_STORE_CREDIT = "//div[@class[contains(.,'refund-check')]]//input[@value='store-credit']";
	private static final String XP_RADIO_TRANSFERENCIA = "//div[@class[contains(.,'refund-check')]]//input[@value='bank-transfer']";
	private static final String XP_SAVE_BUTTON_STORE_CREDIT = "//button[@data-input-id='customer-balance']";
	
	private String getXPathBlock(TypeReembolso typeReembolso) {
		return (getXPathRadio(typeReembolso) + "/..");
	}
	
	private String getXPathRadio(TypeReembolso typeReembolso) {
		switch (typeReembolso) {
		case STORE_CREDIT:
			return XP_RADIO_STORE_CREDIT;
		case TRANSFERENCIA:
			return XP_RADIO_TRANSFERENCIA;
		default:
			return "";
		}
	}
	
	public String getXPathTextoImporteStoreCredit() {
		//El tag no tiene ningún atributo así que el XPATH resultante no es muy elegante
		return (getXPathRadio(TypeReembolso.STORE_CREDIT) + "/../..//strong");
	}
	
	public String getXPath_divRadioCheckedTypeReembolso(TypeReembolso typeReembolso) {
		String xpathRadio = getXPathRadio(typeReembolso);
		return (xpathRadio + "/ancestor::div[@class[contains(.,'custom-radio--checked')]]");
	}

	public boolean isPage() {
		return state(Present, XP_REFUNDS_PANEL).check();
	}

	public boolean existsInputBanco() {
		return state(Present, XP_INPUT_BANCO).check();
	}

	public boolean isVisibleInputBanco() {
		return state(Visible, XP_INPUT_BANCO).check();
	}

	public void typeInputBanco(String banco) {
		getElement(XP_INPUT_BANCO).clear();
		getElement(XP_INPUT_BANCO).sendKeys(banco);
	}

	public boolean isVisibleTextBancoUntil(int seconds) {
		return state(Visible, XP_TEXT_BANCO_AFTER_SAVE).wait(seconds).check();
	}

	public boolean existsInputTitular() {
		return state(Present, XP_INPUT_TITULAR).check();
	}

	public boolean isVisibleInputTitular() {
		return state(Visible, XP_INPUT_TITULAR).check();
	}

	public void typeInputTitular(String titular) {
		getElement(XP_INPUT_TITULAR).clear();
		getElement(XP_INPUT_TITULAR).sendKeys(titular);
	}

	public boolean isVisibleTextTitular() {
		return state(Visible, XP_TITULAR_AFTER_SAVE).check();
	}

	public boolean existsInputIBAN() {
		return state(Present, XP_INPUT_IBAN).check();
	}

	public boolean isVisibleInputIBAN() {
		return state(Visible, XP_INPUT_IBAN).check();
	}

	public boolean isVisibleTextIBAN() {
		return state(Visible, XP_TEXT_IBANA_AFTER_SAVE).check();
	}

	public void typeInputIBAN(String iban) {
		getElement(XP_INPUT_IBAN).clear();
		getElement(XP_INPUT_IBAN).sendKeys(iban);
	}

	public void typeIdPassportIfInputExists(String idPassport) {
		if (state(Visible, XP_INPUT_PASSPORT).check()) {
			getElement(XP_INPUT_PASSPORT).clear();
			getElement(XP_INPUT_PASSPORT).sendKeys(idPassport);
		}
	}
	
	public void typeDateOfBirthIfInputExists(int day, int month, int year) {
		if (state(Visible, XP_BIRTHDAY_DAY_BLOCK).check()) {
			new Select(getElement(XP_SELECT_DAY_BIRTH)).selectByValue(String.valueOf(day));
			new Select(getElement(XP_SELECT_MONTH_BIRTH)).selectByValue(String.valueOf(month));
			new Select(getElement(XP_SELECT_YEAR_BIRTH)).selectByValue(String.valueOf(year));
		}
	}

	public boolean isVisibleInputsTransf() {
		return (
			isVisibleInputBanco() && 
			isVisibleInputTitular() && 
			isVisibleInputIBAN());
	}

	public void typeInputsTransf(String banco, String titular, String iban, String idPassport) {
		typeInputBanco(banco);
		typeInputTitular(titular);
		typeInputIBAN(iban);
		typeIdPassportIfInputExists(idPassport);
		typeDateOfBirthIfInputExists(23, 4, 1974);
	}

	public boolean isVisibleTransferenciaSectionUntil(int seconds) {
		String xpathBlock = getXPathBlock(TypeReembolso.TRANSFERENCIA);
		return state(Visible, xpathBlock).wait(seconds).check();
	}

	public boolean isVisibleStorecreditSection() {
		String xpathBlock = getXPathBlock(TypeReembolso.STORE_CREDIT);
		return state(Visible, xpathBlock).check();
	}

	public boolean isCheckedRadio(TypeReembolso typeReembolso) {
		String xpathDiv = getXPath_divRadioCheckedTypeReembolso(typeReembolso);
		return state(Present, xpathDiv).check();
	}

	public void clickRadio(TypeReembolso typeReembolso) {
		getElement(getXPathRadio(typeReembolso) + "/..").click();
	}

	/**
	 * En ocasiones un sólo click en el botón "Save" no tiene efecto. 
	 * Forzamos a que funcione mediante la siguiente estrategia: lo pulsamos, esperamos a que desaparezca y en caso negativo lo volvemos a pulsar
	 */
	public void clickButtonSaveTransfForce() {
		click(XP_BUTTON_SAVE_TRANSF).exec();
		if (state(Present, XP_BUTTON_SAVE_TRANSF).check()) {
			click(XP_BUTTON_SAVE_TRANSF).exec();
		}
	}

	public void clickButtonSaveTransf() {
		click(XP_BUTTON_SAVE_TRANSF).exec();
	}

	public boolean isVisibleModalConfTransf(int seconds) {
		//En el caso de móvil el div se oculta desplazándolo x píxeles por debajo de la coordenada 0Y
		if (channel.isDevice()) {
			for (int i=0; i<seconds; i++) {
				if (getElement(XP_MODAL_CONF_TRANS).getLocation().getY()>0) {
					return (true);
				}
				waitMillis(1000);
			}
			return false;
		}

		//En el caso de Desktop la capa se oculta normalmente
		return state(Visible, XP_MODAL_CONF_TRANS).wait(seconds).check();
	}

	public boolean isNotVisibleModalConfTransf(int seconds) {
		//En el caso de móvil el div se oculta desplazándolo x píxeles por debajo de la coordenada 0Y
		if (channel.isDevice()) {
			for (int i=0; i<seconds; i++) {
				if (getElement(XP_MODAL_CONF_TRANS).getLocation().getY()<20) {
					return (true);
				}
				waitMillis(1000);
			}
			return false;
		}

		//En el caso de Desktop la capa se oculta normalmente
		return state(Invisible, XP_MODAL_CONF_TRANS).wait(seconds).check();
	}
	
	public float getImporteStoreCredit() {
		float precioFloat = -1;
		if (state(Visible, getXPathTextoImporteStoreCredit()).check()) {
			String precioTotal = getElement(getXPathTextoImporteStoreCredit()).getText();
			precioFloat = ImporteScreen.getFloatFromImporteMangoScreen(precioTotal);
		}
		return precioFloat;
	}

	public boolean isVisibleSaveButtonStoreCredit() {
		return state(Visible, XP_SAVE_BUTTON_STORE_CREDIT).check();
	}

	public boolean isVisibleSaveButtonStoreCreditUntil(int seconds) {
		return state(Visible, XP_SAVE_BUTTON_STORE_CREDIT).wait(seconds).check();
	}

	public void clickSaveButtonStoreCredit() {
		click(XP_SAVE_BUTTON_STORE_CREDIT).exec();

		//Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
		if (isVisibleSaveButtonStoreCredit()) {
			click(XP_SAVE_BUTTON_STORE_CREDIT).exec();
		}
	}
}
