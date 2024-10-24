package com.mng.robotest.tests.domains.compra.payments.postfinance.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.postfinance.pageobjects.PagePostfCodSeg;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePostfCodSegSteps extends StepBase {
	
	private final PagePostfCodSeg pagePostfCodSeg = new PagePostfCodSeg();
	
	public PagePostfCodSeg getPageObj() {
		return pagePostfCodSeg;
	}
	
	@Validation
	public ChecksTM validateIsPageTest(String nombrePago, String importeTotal) {
		var checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pasarela de pagos de PostFinance E-Payment de Test",
			pagePostfCodSeg.isPasarelaPostfinanceTest(nombrePago));
		
		checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			pagePostfCodSeg.isPresentButtonAceptar());
		
		boolean existsCode = pagePostfCodSeg.isPresentInputCodSeg();
		if (isPostfinanceEcard(nombrePago)) {
			checks.add(
				"SÍ existe el campo de introducción del código de seguridad",
				existsCode);
		} else {
			checks.add(
				"NO existe el campo de introducción del código de seguridad",
				!existsCode);
		}

		return checks;
	}
	
	@Validation
	public ChecksTM validateIsPagePro(String importeTotal) {
		var checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la pasarela de pagos de PostFinance E-Payment " + getLitSecondsWait(seconds),
			pagePostfCodSeg.isPasarelaPostfinanceProUntil(seconds));
		
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
		
		checks.add(
			"Aparece el botón Weiter (Aceptar)",
			pagePostfCodSeg.isPresentButtonWeiter());
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón Aceptar", 
		expected="Aparece una página de redirección")
	public PagePostfRedirectSteps inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo) {
		try {
			if (isPostfinanceEcard(nombreMetodo)) {
				//El código de seguridad sólo lo pide en el caso de "PostFinance Card" no en el de "PostFinance e-finance"
				setStepDescription("Introducir el código de seguridad " + codSeguridad + ". Seleccionar el botón Aceptar");
				pagePostfCodSeg.inputCodigoSeguridad(codSeguridad);
			}
				  
			pagePostfCodSeg.clickAceptarButton();
		}
		finally {
			leaveIframe();
		}
		
		var pagePostfRedirectSteps = new PagePostfRedirectSteps();
		pagePostfRedirectSteps.isPageAndFinallyDisappears();
		return pagePostfRedirectSteps;
	}
	
	/**
	 * @return si se trata de "PostFinance Card" y no de "PostFinance e-finance"
	 */
	private static boolean isPostfinanceEcard(String nombreMetodo) {
		return (nombreMetodo.toUpperCase().contains("CARD"));
	}
}
