package com.mng.robotest.tests.domains.compranew.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compranew.pageobjects.PageCheckoutGuestData;
import com.mng.robotest.tests.domains.compranew.pageobjects.PageCheckoutIdentDesktop;
import com.mng.robotest.tests.domains.compranew.pageobjects.PageCheckoutPayment;
import com.mng.robotest.tests.domains.compranew.pageobjects.beans.DeliveryData;
import com.mng.robotest.testslegacy.beans.Pago;

public class CheckoutNewSteps extends StepBase {

	PageCheckoutIdentDesktop pIdentCheckoutDesktop = new PageCheckoutIdentDesktop();
	PageCheckoutGuestData pGuestCheckout = new PageCheckoutGuestData(); 
	PageCheckoutPayment pCheckout = new PageCheckoutPayment();
	
	@Validation (description="Aparece página de identificación-checkout " + SECONDS_WAIT)
	public boolean isPageIdentificationDesktop(int seconds) {
		return pIdentCheckoutDesktop.isPage(seconds);
	}	
	
	@Step (
		description="Loginarse utilizando <b>#{mail} / #{password}</b>",
		expected="Aparece la página de Checkout")
	public void loginDesktop(String mail, String password) {
		pIdentCheckoutDesktop.login(mail, password);
		isPageCheckout(10); 
	}	
	
	@Step (
		description="Seleccionar el botón <b>Continue as guest</b>",
		saveHtmlPage=SaveWhen.IF_PROBLEM)
	public void continueAsGuestDesktop() {
		pIdentCheckoutDesktop.continueAsGuest();
	}
	
	@Validation (
		description="Aparece página de introducción de los datos de delivery asociados al Usuario Guest " + SECONDS_WAIT)
	public boolean isPageGuestUserData(int seconds) {
		return pGuestCheckout.isPage(seconds);
	}
	
	@Step (description="Introducimos los datos del cliente invitado:")	
	public void inputDeliveryGuestDefaultData() {
		var pais = dataTest.getPais();
		var delivery = new DeliveryData();
		delivery.setName("Jorge");
		delivery.setSurname("Muñoz Martínez");
		delivery.setCountry("VILAFRANCA DEL PENEDES");
		delivery.setCity("BARCELONA");
		delivery.setProvince("BARCELONA");
		delivery.setAddress(pais.getAddress());
		delivery.setPostcode(pais.getCodpos());
		delivery.setEmail(getUserEmail());
		delivery.setMobile(pais.getTelefono());
		delivery.setDni(pais.getDni());
		addDeliveryDataToStepDescription(delivery);
		
		pGuestCheckout.inputData(delivery);
	}
	
	private void addDeliveryDataToStepDescription(DeliveryData delivery) {
		setStepDescription(getStepDescription() + delivery.getHtmlData());
	}
	
	@Step (description="Seleccionamos el botón <b>Continue to payment</b> de la página de input data guest user")
	public void clickContinueToPaymentButton() {
		pGuestCheckout.clickContinueToPayment();
		isPageCheckout(10);
	}
	
	@Validation (description="Aparece página de checkout " + SECONDS_WAIT)
	public boolean isPageCheckout(int seconds) {
		return pCheckout.isPage(seconds);
	}	
	
	@Step(description="Seleccionamos el checkbox para grabar la tarjeta", expected="")
	public void selectSaveCard() {
		pCheckout.selectSaveCard();
	}
	
	@Step(
		description="Seleccionamos la tarjeta guardada y pulsamos el botón <b>Pay now</b>", 
		expected="El pago se realiza correctamente")
	public void selectTrjGuardadaAndPayNow() {
		pCheckout.selectSavedCard();
		pCheckout.clickPayNow();
	}
	
	@Step (
		description=
			"Introducimos los datos de la tarjeta <b>#{pago.getTipotarj()} #{pago.getNumtarj()}</b> " + 
			"y pulsamos el botón <b>Pay Now</b>",
		expected=
			"Aparece la página de resultado OK")
	public void inputTrjAndPayNow(Pago pago) {
		pCheckout.unfoldCardFormulary();
		pCheckout.inputCard(pago);
		pCheckout.clickPayNow();
	}
	
}
