package com.mng.robotest.test.stpv.shop.checkout;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.checkout.SecSoyNuevo;
import com.mng.robotest.test.pageobject.shop.checkout.SecSoyNuevo.ActionNewsL;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class SecSoyNuevoStpV {

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

		Page2IdentCheckoutStpV page2IdentCheckoutStpV = new Page2IdentCheckoutStpV(channel, driver);
		page2IdentCheckoutStpV.validateIsPage(emailExistsYet, 2);
		GenericChecks.from(Arrays.asList(
				GenericCheck.Analitica,
				GenericCheck.TextsTraduced)).checks(driver);
	}

	@SuppressWarnings("static-access")
	@Validation
	public static ChecksTM validaRGPDText(DataCtxShop dCtxSh, WebDriver driver) {  
		ChecksTM validations = ChecksTM.getNew();
		if (dCtxSh.pais.getRgpd().equals("S")) {
		 	validations.add(
				"El texto de info de RGPD <b>SI</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				Page1IdentCheckout.secSoyNuevo.isTextoRGPDVisible(driver), State.Defect);
		 	validations.add(
				"El texto legal de RGPD <b>SI</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				Page1IdentCheckout.secSoyNuevo.isTextoLegalRGPDVisible(driver), State.Defect);
		} else {
		 	validations.add(
				"El texto de info de RGPD <b>NO</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				!Page1IdentCheckout.secSoyNuevo.isTextoRGPDVisible(driver), State.Defect);			
		 	validations.add(
				"El texto legal de RGPD <b>NO</b> existe en el apartado de <b>Soy nuevo</b> para el pais " + dCtxSh.pais.getCodigo_pais(),
				!Page1IdentCheckout.secSoyNuevo.isTextoLegalRGPDVisible(driver), State.Defect);		
		}
		
		return validations;
	}
}
