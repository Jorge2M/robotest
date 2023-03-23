package com.mng.robotest.domains.compra.payments.billpay.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.billpay.pageobjects.SecBillpay;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;

public class SecBillpaySteps extends StepBase {
	
	private final SecBillpay secBillpay = new SecBillpay();
	
	@Validation
	public ChecksTM validateIsSectionOk() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparecen 3 desplegables para la selección de la fecha de nacimiento",
			secBillpay.isPresentSelectBirthBirthYear() &&
			secBillpay.isPresentSelectBirthMonth() &&
			secBillpay.isPresentSelectBirthDay()); 
	 	
	 	checks.add(
			"Aparece el check de \"Acepto\"",
			secBillpay.isPresentRadioAcepto()); 
	 	
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
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece el campo para la introducción del titular",
			secBillpay.isPresentInputTitular());
	 	
	 	checks.add(
			"Aparece el campo para la introducción del IBAN",
	 		secBillpay.isPresentInputIBAN());
	 	
	 	checks.add(
			"Aparece el campo para la introducción del BIC",
			secBillpay.isPresentInputBIC());
	 	
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
