package com.mng.robotest.test.steps.shop.checkout.postfinance;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfCodSeg;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePostfCodSegSteps extends StepBase {
	
	private final PagePostfCodSeg pagePostfCodSeg = new PagePostfCodSeg();
	
	public PagePostfCodSeg getPageObj() {
		return pagePostfCodSeg;
	}
	
	@Validation
	public ChecksTM validateIsPageTest(String nombrePago, String importeTotal) {
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
			"Aparece la pasarela de pagos de PostFinance E-Payment de Test",
			pagePostfCodSeg.isPasarelaPostfinanceTest(nombrePago), State.Defect);
		
		checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			pagePostfCodSeg.isPresentButtonAceptar(), State.Defect);
		
		boolean existsCode = pagePostfCodSeg.isPresentInputCodSeg();
		if (isPostfinanceEcard(nombrePago)) {
			checks.add(
				"SÍ existe el campo de introducción del código de seguridad",
				existsCode, State.Defect);
		} else {
			checks.add(
				"NO existe el campo de introducción del código de seguridad",
				!existsCode, State.Defect);
		}

		return checks;
	}
	
	@Validation
	public ChecksTM validateIsPagePro(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
		int seconds = 5;
		checks.add(
			"Aparece la pasarela de pagos de PostFinance E-Payment (la esperamos hasta " + seconds + " segundos)",
			pagePostfCodSeg.isPasarelaPostfinanceProUntil(seconds), State.Defect);
		
		checks.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
		
		checks.add(
			"Aparece el botón Weiter (Aceptar)",
			pagePostfCodSeg.isPresentButtonWeiter(), State.Defect);
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón Aceptar", 
		expected="Aparece una página de redirección")
	public PagePostfRedirectSteps inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo) {
		try {
			if (isPostfinanceEcard(nombreMetodo)) {
				//El código de seguridad sólo lo pide en el caso de "PostFinance Card" no en el de "PostFinance e-finance"
				StepTM step = TestMaker.getCurrentStepInExecution();
				step.setDescripcion("Introducir el código de seguridad " + codSeguridad + ". " + step.getDescripcion());
				pagePostfCodSeg.inputCodigoSeguridad(codSeguridad);
			}
				  
			pagePostfCodSeg.clickAceptarButton();
		}
		finally {
			driver.switchTo().defaultContent(); 
		}
		
		PagePostfRedirectSteps pagePostfRedirectSteps = new PagePostfRedirectSteps();
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
