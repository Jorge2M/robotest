package com.mng.robotest.domains.compra.payments.ideal.pageobjects;

import com.mng.robotest.domains.transversal.PageBase;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecIdeal extends PageBase {
	
	//HAY QUE AÑADIR LAS OPCIONES DE PRE Y QUITAR LAS DE PRO
	public enum BancoSeleccionado {
		KiesEenBank("Kies een bank", "selected"),
		TestIssuer("Test Issuer", "1121"),
		TestIssuer2("Test Issuer 2", "1151"),
		TestIssuer3("Test Issuer 3", "1152"),
		TestIssuer4("Test Issuer 4", "1153"),
		TestIssuer5("Test Issuer 5", "1154"),
		TestIssuer6("Test Issuer 6", "1155"),
		TestIssuer7("Test Issuer 7", "1156"),
		TestIssuer8("Test Issuer 8", "1157"),
		TestIssuer9("Test Issuer 9", "1158"),
		TestIssuer10("Test Issuer 10", "1159"),
		TestIssuerRefused("Test Issuer Refused", "1160"),
		TestIssuerPending("Test Issuer Pending", "1161"),
		TestIssuerCancelled("Test Issuer Cancelled", "1162");
		
		public String nombre; 
		public String valueOption;
		
		private BancoSeleccionado(String nombre, String valueOption) {
			this.nombre = nombre;
			this.valueOption = valueOption;
		}
	};

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