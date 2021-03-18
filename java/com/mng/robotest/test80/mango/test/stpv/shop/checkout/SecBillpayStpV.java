package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.SecBillpay;

public class SecBillpayStpV {
    
	private final SecBillpay secBillpay;
	
	public SecBillpayStpV(Channel channel, WebDriver driver) {
		this.secBillpay = new SecBillpay(channel, driver);
	}
	
	@Validation
    public ChecksTM validateIsSectionOk() {
		ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparecen 3 desplegables para la selección de la fecha de nacimiento",
			secBillpay.isPresentSelectBirthBirthYear() &&
            secBillpay.isPresentSelectBirthMonth() &&
            secBillpay.isPresentSelectBirthDay(), 
            State.Defect); 
	 	validations.add(
			"Aparece el check de \"Acepto\"",
			secBillpay.isPresentRadioAcepto(), State.Defect); 
	 	return validations;
    }
    
    /**
     * @param fechaNac en formato "DD-MM-AAAA"
     */
	@Step (
		description="Informamos la fecha de nacimiento #{fechaNac} y marcamos el check de \"Acepto\"", 
        expected="La fecha se informa correctamente")
    public void inputDiaNacAndCheckAcepto(String fechaNac, String nombrePago) {
        secBillpay.putBirthday(fechaNac);
        secBillpay.clickAcepto();
        if (nombrePago.compareTo("Lastschrift")==0) {
        	checkLastschriftAfterAccept();
        }
    }
	
    @Validation
    private ChecksTM checkLastschriftAfterAccept() {
    	ChecksTM validations = ChecksTM.getNew();
	 	validations.add(
			"Aparece el campo para la introducción del titular",
			secBillpay.isPresentInputTitular(), State.Defect);
	 	validations.add(
			"Aparece el campo para la introducción del IBAN",
	 		secBillpay.isPresentInputIBAN(), State.Defect);
	 	validations.add(
			"Aparece el campo para la introducción del BIC",
			secBillpay.isPresentInputBIC(), State.Defect);
	 	return validations;
    }
    
    @Step (
    	description="Informamos el titular, IBAN (#{iban}) y BIC (#{bic})", 
        expected="Los datos se informan correctamente")
    public void inputDataInLastschrift(String iban, String bic, String titular) { 
        secBillpay.sendDataInputIBAN(iban);
        secBillpay.sendDataInputTitular(titular);
        secBillpay.sendDataInputBIC(bic);
    }
}
