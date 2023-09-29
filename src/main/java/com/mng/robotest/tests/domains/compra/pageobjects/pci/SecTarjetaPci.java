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
	public void inputNumber(String number);
	public void inputTitular(String titular);
	public void inputCvc(String cvc);
	public void inputDni(String dni);  //Specific for Codensa (Colombia)
	public void selectMesByVisibleText(String mes);
	public void selectAnyByVisibleText(String any);
	
	public static SecTarjetaPci makeSecTarjetaPci(Channel channel) {
		//TODO cuando suba a PRO pci en iframe se podrá eliminar SecTarjetaPciNotInIframeMobil y SecTarjetaPciNotInIframeDesktop
		if (channel==Channel.desktop) {
			var secTarjetaPci = new SecTarjetaPciNotInIframeDesktop();
			if (secTarjetaPci.isPresentInputNumberUntil(1)) {
				return secTarjetaPci;
			}
		}
		return new SecTarjetaPciInIframe();	
	}
}
