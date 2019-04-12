package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecBillpay;

public class SecBillpayStpV {
    
	@Validation
    public static ChecksResult validateIsSectionOk(Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparecen 3 desplegables para la selección de la fecha de nacimiento",
			SecBillpay.isPresentSelectBirthBirthYear(driver) &&
            SecBillpay.isPresentSelectBirthMonth(driver) &&
            SecBillpay.isPresentSelectBirthDay(driver), 
            State.Defect); 
	 	validations.add(
			"Aparece el check de \"Acepto\"",
			SecBillpay.isPresentRadioAcepto(channel, driver), State.Defect); 
	 	return validations;
    }
    
    /**
     * @param fechaNac en formato "DD-MM-AAAA"
     */
	@Step (
		description="Informamos la fecha de nacimiento #{fechaNac} y marcamos el check de \"Acepto\"", 
        expected="La fecha se informa correctamente")
    public static void inputDiaNacAndCheckAcepto(String fechaNac, String nombrePago, Channel channel, WebDriver driver) {
        SecBillpay.putBirthday(driver, fechaNac);
        SecBillpay.clickAcepto(driver, channel);
        if (nombrePago.compareTo("Lastschrift")==0) {
        	checkLastschriftAfterAccept(driver);
        }
    }
	
    @Validation
    private static ChecksResult checkLastschriftAfterAccept(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Aparece el campo para la introducción del titular",
			SecBillpay.isPresentInputTitular(driver), State.Defect);
	 	validations.add(
			"Aparece el campo para la introducción del IBAN",
	 		SecBillpay.isPresentInputIBAN(driver), State.Defect);
	 	validations.add(
			"Aparece el campo para la introducción del BIC",
			SecBillpay.isPresentInputBIC(driver), State.Defect);
	 	return validations;
    }
    
    @Step (
    	description="Informamos el titular, IBAN (#{iban}) y BIC (#{bic})", 
        expected="Los datos se informan correctamente")
    public static void inputDataInLastschrift(String iban, String bic, String titular, WebDriver driver) { 
        SecBillpay.sendDataInputIBAN(iban, driver);
        SecBillpay.sendDataInputTitular(titular, driver);
        SecBillpay.sendDataInputBIC(bic, driver);
    }
}
