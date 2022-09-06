package com.mng.robotest.test.steps.shop.checkout.klarna;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.klarna.DataKlarna;
import com.mng.robotest.test.pageobject.shop.checkout.klarna.PageKlarna;

public class PageKlarnaSteps extends StepBase {

	private final PageKlarna pageKlarna = new PageKlarna();
	
	@Validation (
		description="Aparece la página inicial de Klarna (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsPage(int maxSeconds) { 
		return pageKlarna.isPage(maxSeconds);
	}
	
	public PageKlarna getPageObject() {
		return pageKlarna;
	}
	
	@Step (
		description="Seleccionamos el botón <b>Comprar</b>", 
		expected="Aparece el modal de introducción de datos del usuario")
	public void clickComprar() {
		pageKlarna.clickBuyButton();
	}
	
	@Validation (
		description="Aparece el modal de introducción de los datos del usuario (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkIsModalInputUserData(int maxSeconds) { 
		return pageKlarna.isVisibleModalInputUserData(maxSeconds);
	}
	
	@Step (
		description="Introducir y confirmar los datos del usuario:<br>#{dataKlarna.getHtmlFormattedData()}",
		expected="Aparece el modal para la introducción del número personal")
	public void inputUserDataAndConfirm(DataKlarna dataKlarna) {
		pageKlarna.inputUserDataAndConfirm(dataKlarna);
	}
	
	@Step (
		description="Introducir el phone number / otp:<br>#{phoneNumber} #{otp}",
		expected="Aparece el modal para la introducción del número personal")
	public void inputDataPhoneAndConfirm(String phoneNumber, String otp) {
		pageKlarna.inputDataPhoneAndConfirm(phoneNumber, otp);
	}
	
	@Validation (
		description="Es visible el modal para la introducción del número personal (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Info)
	public boolean checkModalInputPersonNumber(int maxSeconds) { 
		return pageKlarna.isVisibleModalPersonNumber(maxSeconds);
	}
	
	@Validation (
		description="Es visible el modal para la introducción del phone number (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Info)
	public boolean checkModalInputPhoneNumber(int maxSeconds) { 
		return pageKlarna.isVisibleModalInputPhone(maxSeconds);
	}
	
	@Step (
		description="Introducir y confirmar el Personal Number: <b>#{personnumber}</b>",
		expected="Aparece la página Mango de resultado Ok de la compra")
	public void inputPersonNumberAndConfirm(String personnumber) {
		pageKlarna.inputPersonNumberAndConfirm(personnumber);
	}
	
}
