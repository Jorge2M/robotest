package com.mng.robotest.test.steps.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test.beans.Pago;
import com.mng.robotest.test.pageobject.shop.checkout.mercadopago.PageMercpagoLogin;

/**
 * Page-1: Identificación en Mercadopago
 * @author jorge.munoz
 *
 */
public class PageMercpagoLoginSteps {

	@Validation
	public static ChecksTM validateIsPage(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la página de identificación de Mercadopago",
			PageMercpagoLogin.isPage(driver), State.Defect);
		validations.add(
			"En la página figuran los campos de identificación (email + password)",
			PageMercpagoLogin.isInputUserVisible(driver) &&
			PageMercpagoLogin.isInputPasswordVisible(driver), State.Defect);
		return validations;
	}
	
	@Step (
		description="Introducir el usuario/password de mercadopago (#{pago.getUseremail()} / #{pago.getPasswordemail()}) + click botón \"Ingresar\"",
		expected=
			"Aparece alguna de las páginas:<br>" +
			" - Elección medio pago<br>" +
			" - Introducción CVC")
	public static void loginMercadopago(Pago pago, Channel channel, WebDriver driver) {
		PageMercpagoLogin.sendInputUser(driver, pago.getUseremail());
		PageMercpagoLogin.sendInputPassword(driver, pago.getPasswordemail());
		PageMercpagoLogin.clickBotonContinuar(driver);
		
		PageMercpagoDatosTrjSteps pageMercpagoDatosTrjSteps = PageMercpagoDatosTrjSteps.newInstance(channel, driver);
		pageMercpagoDatosTrjSteps.validaIsPage(0);
	}
}
