package com.mng.robotest.tests.domains.compra.pageobjects.pci;

import com.github.jorge2m.testmaker.conf.Channel;

public interface SecTarjetaPci {
	public boolean isVisiblePanelPagoUntil(String nombrePago, int seconds);
	public boolean isPresentInputNumberUntil(int seconds);
	public boolean isPresentInputTitular();
	public boolean isPresentSelectMes();
	public boolean isPresentSelectAny();
	public boolean isPresentInputCvc();
	public boolean isPresentInputDni(); //Specific for Codensa (Colombia)
	public void selectSaveCard();
	public void inputNumber(String number);
	public void inputTitular(String titular);
	public void inputCvc(String cvc);
	public void inputDni(String dni);  //Specific for Codensa (Colombia)
	public void selectMesByVisibleText(String mes);
	public void selectAnyByVisibleText(String any);
	
	public static SecTarjetaPci makeSecTarjetaPci(Channel channel) {
		return new SecTarjetaPciInIframe();	
	}
}
