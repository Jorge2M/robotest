package com.mng.robotest.test80.mango.test.pageobject.shop.checkout.pci;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;


public interface SecTarjetaPci {
    public boolean isVisiblePanelPagoUntil(String nombrePago, int maxSeconds, WebDriver driver);
    public boolean isPresentInputNumberUntil(int maxSecondsToWait, WebDriver driver);
    public boolean isPresentInputTitular(WebDriver driver);
    public boolean isPresentSelectMes(WebDriver driver);
    public boolean isPresentSelectAny(WebDriver driver);
    public boolean isPresentInputCvc(WebDriver driver);
    public boolean isPresentInputDni(WebDriver driver); //Specific for Codensa (Colombia)
    public void inputNumber(String number, WebDriver driver);
    public void inputTitular(String titular, WebDriver driver);
    public void inputCvc(String cvc, WebDriver driver);
    public void inputDni(String dni, WebDriver driver);  //Specific for Codensa (Colombia)
    public void selectMesByVisibleText(String mes, WebDriver driver);
    public void selectAnyByVisibleText(String any, WebDriver driver);
    
    public static SecTarjetaPci makeSecTarjetaPci(Channel channel, WebDriver driver) {
		if (channel==Channel.desktop) {
		    return (SecTarjetaPciDktop.make());
		}
		
		//TODO cuando suba a PRE pci en iframe se podrá eliminar SecTarjetaPciNotInIframeMobil.
		SecTarjetaPci secTarjetaPci = SecTarjetaPciNotInIframeMobil.make();
		if (secTarjetaPci.isPresentInputNumberUntil(1, driver)) {
			return secTarjetaPci;
		}
		return (SecTarjetaPciInIframeMobil.make());    
    }
}
