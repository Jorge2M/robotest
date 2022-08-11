package com.mng.robotest.test.steps.shop.checkout;

import java.util.Arrays;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.conf.State;

import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.checkout.SecSoyNuevo;
import com.mng.robotest.test.pageobject.shop.checkout.SecSoyNuevo.ActionNewsL;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;


public class SecSoyNuevoSteps extends StepBase {

	private final SecSoyNuevo secSoyNuevo = new SecSoyNuevo();
	
	@Step (
		description=
			"1. Desmarcamos el check NewsLetter<br>" + 
			"2. Introducimos el email <b>#{email}</b> (existente: <b>#{emailExistsYet}</b>)<br>" + 
			"3. Seleccionamos \"Continuar\"", 
		expected="Aparece la página de introducción de datos del usuario")
	public void inputEmailAndContinue(
			String email, boolean emailExistsYet, boolean userRegistered, Pais pais) throws Exception {
		
		secSoyNuevo.setCheckPubliNewsletter(ActionNewsL.DEACTIVATE);
		secSoyNuevo.inputEmail(email);
		secSoyNuevo.clickContinue();

		Page2IdentCheckoutSteps page2IdentCheckoutSteps = new Page2IdentCheckoutSteps(channel, pais);
		page2IdentCheckoutSteps.validateIsPage(emailExistsYet, 2);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced)).checks(driver);
	}

	@SuppressWarnings("static-access")
	@Validation
	public ChecksTM validaRGPDText(DataCtxShop dCtxSh) {  
		ChecksTM checks = ChecksTM.getNew();
		int maxSeconds = 5;
		if (dCtxSh.pais.getRgpd().equals("S")) {
		 	checks.add(
				"El link de política de privacidad existe para el pais " + 
				dCtxSh.pais.getCodigo_pais() + " lo esperamos hasta " + maxSeconds + " segundos",
				secSoyNuevo.isLinkPoliticaPrivacidad(maxSeconds), State.Defect);
		} else {
		 	checks.add(
				"El lik de política de privacidad no exite para el pais " + dCtxSh.pais.getCodigo_pais(),
				!secSoyNuevo.isLinkPoliticaPrivacidad(0), State.Defect);			
		}
		
		return checks;
	}
}
