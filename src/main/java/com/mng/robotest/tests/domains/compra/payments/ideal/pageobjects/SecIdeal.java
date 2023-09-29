package com.mng.robotest.tests.domains.compra.payments.ideal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecIdeal extends PageBase {
	
	//HAY QUE AÑADIR LAS OPCIONES DE PRE Y QUITAR LAS DE PRO
	public enum BancoSeleccionado {
		KIES_EEN_BANK("Kies een bank", "selected"),
		TEST_ISSUER("Test Issuer", "1121"),
		TEST_ISSUER_2("Test Issuer 2", "1151"),
		TEST_ISSUER_3("Test Issuer 3", "1152"),
		TEST_ISSUER_4("Test Issuer 4", "1153"),
		TEST_ISSUER_5("Test Issuer 5", "1154"),
		TEST_ISSUER_6("Test Issuer 6", "1155"),
		TEST_ISSUER_7("Test Issuer 7", "1156"),
		TEST_ISSUER_8("Test Issuer 8", "1157"),
		TEST_ISSUER_9("Test Issuer 9", "1158"),
		TEST_ISSUER_10("Test Issuer 10", "1159"),
		TEST_ISSUER_REFUSED("Test Issuer Refused", "1160"),
		TEST_ISSUER_PENDING("Test Issuer Pending", "1161"),
		TEST_ISSUER_CANCELLED("Test Issuer Cancelled", "1162");
		
		private String nombre; 
		private String valueOption;
		private BancoSeleccionado(String nombre, String valueOption) {
			this.nombre = nombre;
			this.valueOption = valueOption;
		}
		
		public String getNombre() {
			return nombre;
		}
		public String getValueOption() {
			return valueOption;
		}
	}

	private static final String XPATH_CARD_CONDITIONS = "//div[@id='textoCondicionesTarjeta']";
	private static final String XPATH_SELECTOR_BANK_IDEAL = XPATH_CARD_CONDITIONS + "//div[@id='ideal-bank-selector']";
	private static final String XPATH_LIST_BANK_IDEAL = XPATH_SELECTOR_BANK_IDEAL + "//select[@name[contains(.,'panelTarjetasForm')] and @class='bank-select']";
	
	private static final String XPATH_CONDITIONS_MOBILE = "//div[@id='avisoConfirmar']";
	private static final String XPATH_SELECTOR_BANK_IDEAL_MOBILE = XPATH_CONDITIONS_MOBILE + "//div[@id='ideal-bank-selector']";
	private static final String XPATH_LIST_BANK_IDEAL_MOBILE = "//select[@class[contains(.,'bank-select')]]";
	
	public String getXPathSection() {
		if (channel.isDevice()) {
			return XPATH_CONDITIONS_MOBILE;
		}
		return XPATH_CARD_CONDITIONS;
	}

	public boolean isVisibleUntil(int seconds) {
		return state(Visible, getXPathSection()).wait(seconds).check();
	}
	
	public boolean isVisibleSelectorOfBank(int seconds) {
		if (channel.isDevice()) {
			return state(Visible, XPATH_SELECTOR_BANK_IDEAL_MOBILE).wait(seconds).check();
		}
		return state(Present, XPATH_SELECTOR_BANK_IDEAL).wait(seconds).check();
	}
	
	public boolean isBancoDisponible(BancoSeleccionado bancoSeleccionado) {
		String xpath = getXPathBankOptionByValue(bancoSeleccionado);
		return state(Present, xpath).check();
	}
	
	public String getXPathBankOptionByValue(BancoSeleccionado bancoSeleccionado) {
		if (channel.isDevice()) { 		
			return XPATH_LIST_BANK_IDEAL_MOBILE + "//option[@value='" + bancoSeleccionado.valueOption + "']";
		}
		return XPATH_LIST_BANK_IDEAL + "//option[@value='" + bancoSeleccionado.valueOption + "']";
	}
	
	public String getXPathBankOptionByName(BancoSeleccionado bancoSeleccionado) {
		if (channel.isDevice()) {
			return XPATH_LIST_BANK_IDEAL_MOBILE + "//option[@text='" + bancoSeleccionado.nombre + "']";
		}
		return XPATH_LIST_BANK_IDEAL + "//option[@text='" + bancoSeleccionado.nombre + "']";
	}
	
	public void clickBancoByValue(BancoSeleccionado bancoSeleccionado) {
		getElement(getXPathBankOptionByValue(bancoSeleccionado)).click();
	}
	
	public void clickBancoByName(BancoSeleccionado bancoSeleccionado) {
		getElement(getXPathBankOptionByName(bancoSeleccionado)).click();
	}
}