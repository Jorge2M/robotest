package com.mng.robotest.test.steps.shop.checkout;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test.pageobject.shop.checkout.SecBillpay;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

public class SecBillpaySteps {
	
	private final SecBillpay secBillpay;
	
	public SecBillpaySteps(Channel channel, WebDriver driver) {
		this.secBillpay = new SecBillpay(channel, driver);
	}
	
	@Validation
	public ChecksTM validateIsSectionOk() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparecen 3 desplegables para la selección de la fecha de nacimiento",
			secBillpay.isPresentSelectBirthBirthYear() &&
			secBillpay.isPresentSelectBirthMonth() &&
			secBillpay.isPresentSelectBirthDay(), 
			State.Defect); 
	 	checks.add(
			"Aparece el check de \"Acepto\"",
			secBillpay.isPresentRadioAcepto(), State.Defect); 
	 	return checks;
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
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el campo para la introducción del titular",
			secBillpay.isPresentInputTitular(), State.Defect);
	 	checks.add(
			"Aparece el campo para la introducción del IBAN",
	 		secBillpay.isPresentInputIBAN(), State.Defect);
	 	checks.add(
			"Aparece el campo para la introducción del BIC",
			secBillpay.isPresentInputBIC(), State.Defect);
	 	return checks;
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
