package com.mng.robotest.test.stpv.shop.registro;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

import java.util.Arrays;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.registro.ListDataNinos;
import com.mng.robotest.test.pageobject.shop.registro.PageRegistroNinos;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;


public class PageRegistroNinosStpV {
	
	@Validation
	public static ChecksTM validaIsPageWithNinos(int numNinos, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSecondsToWait = 5;
		validations.add(
			"Aparece la página de introducción de datos del niño (la esperamos un máximo de " + maxSecondsToWait + " segundos)",
			PageRegistroNinos.isPageUntil(driver, maxSecondsToWait), State.Defect);
		validations.add(
			"Aparecen inputs para introducir <b>" + numNinos + "</b>",
			PageRegistroNinos.getNumInputsNameNino(driver)==numNinos, State.Defect);
		return validations;		
	}
	
	@Step (
		description="Introducir datos de los niños: <br>#{listaNinos.getFormattedHTMLData()}<br> y finalmente pulsar el botón \"Continuar\"", 
		expected="Aparece la página de introducción de datos de la dirección")
	public static void sendNinoDataAndContinue(ListDataNinos listaNinos, Pais pais, WebDriver driver) {
		PageRegistroNinos.setDataNinoIfNotExists(listaNinos, 2, driver);
		PageRegistroNinos.clickContinuar(driver);
		PageRegistroDirecStpV.isPageFromPais(pais, driver);
		GenericChecks.from(Arrays.asList(
				GenericCheck.SEO, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks(driver);
	}
}
