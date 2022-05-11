package com.mng.robotest.test.stpv.shop.checkout.postfinance;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.pageobject.shop.checkout.postfinance.PagePostfCodSeg;
import com.mng.robotest.test.utils.ImporteScreen;


public class PagePostfCodSegStpV {
	
	private final PagePostfCodSeg pagePostfCodSeg;
	private final WebDriver driver;
	
	public PagePostfCodSegStpV(WebDriver driver) {
		this.pagePostfCodSeg = new PagePostfCodSeg(driver);
		this.driver = driver;
	}
	
	public PagePostfCodSeg getPageObj() {
		return pagePostfCodSeg;
	}
	
	@Validation
	public ChecksTM validateIsPageTest(String nombrePago, String importeTotal) {
		ChecksTM validations = ChecksTM.getNew();
		validations.add(
			"Aparece la pasarela de pagos de PostFinance E-Payment de Test",
			pagePostfCodSeg.isPasarelaPostfinanceTest(nombrePago), State.Defect);
		validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			pagePostfCodSeg.isPresentButtonAceptar(), State.Defect);
		
		boolean existsCode = pagePostfCodSeg.isPresentInputCodSeg();
		if (isPostfinanceEcard(nombrePago)) {
			validations.add(
				"SÍ existe el campo de introducción del código de seguridad",
				existsCode, State.Defect);
		} else {
			validations.add(
				"NO existe el campo de introducción del código de seguridad",
				!existsCode, State.Defect);
		}

		return validations;
	}
	
	@Validation
	public ChecksTM validateIsPagePro(String importeTotal, String codPais) {
		ChecksTM validations = ChecksTM.getNew();
		int maxSeconds = 5;
		validations.add(
			"Aparece la pasarela de pagos de PostFinance E-Payment (la esperamos hasta " + maxSeconds + " segundos)",
			pagePostfCodSeg.isPasarelaPostfinanceProUntil(maxSeconds), State.Defect);		
		validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);			  
		validations.add(
			"Aparece el botón Weiter (Aceptar)",
			pagePostfCodSeg.isPresentButtonWeiter(), State.Defect);
		return validations;
	}
	
	@Step (
		description="Seleccionar el botón Aceptar", 
		expected="Aparece una página de redirección")
	public PagePostfRedirectStpV inputCodigoSeguridadAndAccept(String codSeguridad, String nombreMetodo) {
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
		
		PagePostfRedirectStpV pagePostfRedirectStpV = new PagePostfRedirectStpV(driver);
		pagePostfRedirectStpV.isPageAndFinallyDisappears();
		return pagePostfRedirectStpV;
	}
	
	/**
	 * @return si se trata de "PostFinance Card" y no de "PostFinance e-finance"
	 */
	private static boolean isPostfinanceEcard(String nombreMetodo) {
		return (nombreMetodo.toUpperCase().contains("CARD"));
	}
}
