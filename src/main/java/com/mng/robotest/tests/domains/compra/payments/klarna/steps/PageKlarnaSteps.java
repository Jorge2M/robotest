package com.mng.robotest.tests.domains.compra.payments.klarna.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.klarna.pageobjects.DataKlarna;
import com.mng.robotest.tests.domains.compra.payments.klarna.pageobjects.PageKlarna;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageKlarnaSteps extends StepBase {

	private final PageKlarna pageKlarna = new PageKlarna();
	
	@Validation (
		description="Aparece la página inicial de Klarna " + SECONDS_WAIT)
	public boolean checkIsPage(int seconds) { 
		return pageKlarna.isPage(seconds);
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
		description="Aparece el modal de introducción de los datos del usuario " + SECONDS_WAIT)
	public boolean checkIsModalInputUserData(int seconds) { 
		return pageKlarna.isVisibleModalInputUserData(seconds);
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
		description="Es visible el modal para la introducción del número personal " + SECONDS_WAIT,
		level=INFO)
	public boolean checkModalInputPersonNumber(int seconds) { 
		return pageKlarna.isVisibleModalPersonNumber(seconds);
	}
	
	@Validation (
		description="Es visible el modal para la introducción del phone number " + SECONDS_WAIT,
		level=INFO)
	public boolean checkModalInputPhoneNumber(int seconds) { 
		return pageKlarna.isVisibleModalInputPhone(seconds);
	}
	
	@Step (
		description="Introducir y confirmar el Personal Number: <b>#{personnumber}</b>",
		expected="Aparece la página Mango de resultado Ok de la compra")
	public void inputPersonNumberAndConfirm(String personnumber) {
		pageKlarna.inputPersonNumberAndConfirm(personnumber);
	}
	
}
