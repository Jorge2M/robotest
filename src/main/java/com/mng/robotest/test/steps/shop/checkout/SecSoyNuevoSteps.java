package com.mng.robotest.test.steps.shop.checkout;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.conf.State;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.SecSoyNuevo;
import com.mng.robotest.test.pageobject.shop.checkout.SecSoyNuevo.ActionNewsL;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class SecSoyNuevoSteps {

	@Step (
		description=
			"1. Desmarcamos el check NewsLetter<br>" + 
			"2. Introducimos el email <b>#{email}</b> (existente: <b>#{emailExistsYet}</b>)<br>" + 
			"3. Seleccionamos \"Continuar\"", 
		expected="Aparece la página de introducción de datos del usuario")
	public static void inputEmailAndContinue(
			String email, boolean emailExistsYet, AppEcom appE, boolean userRegistered, Pais pais, 
			Channel channel, WebDriver driver) throws Exception {
		SecSoyNuevo.setCheckPubliNewsletter(driver, ActionNewsL.deactivate, channel);
		SecSoyNuevo.inputEmail(email, channel, driver);
		SecSoyNuevo.clickContinue(channel, driver);

		Page2IdentCheckoutSteps page2IdentCheckoutSteps = new Page2IdentCheckoutSteps(channel, pais, driver);
		page2IdentCheckoutSteps.validateIsPage(emailExistsYet, 2);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced)).checks(driver);
	}

	@SuppressWarnings("static-access")
	@Validation
	public static ChecksTM validaRGPDText(DataCtxShop dCtxSh, WebDriver driver) {  
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		if (dCtxSh.pais.getRgpd().equals("S")) {
		 	checks.add(
				"El link de política de privacidad existe para el pais " + 
				dCtxSh.pais.getCodigo_pais() + " lo esperamos hasta " + maxSeconds + " segundos",
				Page1IdentCheckout.secSoyNuevo.isLinkPoliticaPrivacidad(maxSeconds, driver), State.Defect);
		} else {
		 	checks.add(
				"El lik de política de privacidad no exite para el pais " + dCtxSh.pais.getCodigo_pais(),
				!Page1IdentCheckout.secSoyNuevo.isLinkPoliticaPrivacidad(0, driver), State.Defect);			
		}
		
		return checks;
	}
}
