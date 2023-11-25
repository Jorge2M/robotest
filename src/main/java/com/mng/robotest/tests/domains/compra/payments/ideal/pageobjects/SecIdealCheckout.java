package com.mng.robotest.tests.domains.compra.payments.ideal.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.tests.domains.base.PageBase;

public class SecIdealCheckout extends PageBase {
	
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

	private static final String XP_CARD_CONDITIONS = "//div[@id='textoCondicionesTarjeta']";
	private static final String XP_SELECTOR_BANK_IDEAL = "//*[@data-testid='nonPciForm.bank.selector']"; //
	private static final String XP_LIST_BANK_IDEAL = "//div[@data-testid='nonPciForm.bank.selector.listbox']/div[@role='option']";

	private String getXPathBankOption(BancoSeleccionado bancoSeleccionado) {
		return XP_LIST_BANK_IDEAL + "//self::*[@name='" + bancoSeleccionado.getNombre() + "']";
	}
	
	public boolean isVisibleUntil(int seconds) {
		return state(Visible, XP_CARD_CONDITIONS).wait(seconds).check();
	}
	
	public boolean isVisibleSelectorOfBank(int seconds) {
		return state(Present, XP_SELECTOR_BANK_IDEAL).wait(seconds).check();
	}
	
	public boolean isBancoDisponible(BancoSeleccionado bancoSeleccionado) {
		String xpath = getXPathBankOption(bancoSeleccionado);
		return state(Present, xpath).check();
	}
	
	public void clickBancoByValue(BancoSeleccionado bancoSeleccionado) {
		click(XP_SELECTOR_BANK_IDEAL).exec();
		click(getXPathBankOption(bancoSeleccionado)).exec();
	}
	
}