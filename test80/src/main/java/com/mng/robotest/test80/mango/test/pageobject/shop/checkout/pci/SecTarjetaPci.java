package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;


public interface SecTarjetaPci {
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds);
    public boolean isPresentInputNumberUntil(int maxSecondsToWait);
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
    
    public static SecTarjetaPci makeSecTarjetaPci(Channel channel, WebDriver driver) {
		//TODO cuando suba a PRO pci en iframe se podrá eliminar SecTarjetaPciNotInIframeMobil y SecTarjetaPciNotInIframeDesktop
		if (channel==Channel.desktop) {
			SecTarjetaPci secTarjetaPci = SecTarjetaPciNotInIframeDesktop.getNew(driver);
			if (secTarjetaPci.isPresentInputNumberUntil(1)) {
				return secTarjetaPci;
			}
			return (SecTarjetaPciInIframe.getNew(driver));    
		}
		
		SecTarjetaPci secTarjetaPci = SecTarjetaPciNotInIframeMobil.getNew(driver);
		if (secTarjetaPci.isPresentInputNumberUntil(1)) {
			return secTarjetaPci;
		}
		return (SecTarjetaPciInIframe.getNew(driver));    
    }
}
