package com.mng.robotest.tests.domains.compra.payments.paytrail.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.paytrail.pageobjects.PagePaytrailIdConfirm;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaytrailIdConfirmSteps extends StepBase {
	
	private final PagePaytrailIdConfirm pagePaytrailIdConfirm = new PagePaytrailIdConfirm(); 
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página de introducción del ID de confirmación",
			pagePaytrailIdConfirm.isPage());
	   	
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), WARN);
	   	
		return checks;
	}
	
	@Step (
		description="Introducir el ID <b>idConfirm</b> y seleccionar el botón \"Confirmar\"", 
		expected="Aparece la página de introducción del <b>ID de confirmación</b>")
	public void inputIDAndClickConfirmar(String idConfirm, String importeTotal, String codPais) {
		pagePaytrailIdConfirm.inputIdConfirm(idConfirm);
		pagePaytrailIdConfirm.clickConfirmar();
		new PagePaytrailResultadoOkSteps().validateIsPage(importeTotal, codPais);
	}
}
