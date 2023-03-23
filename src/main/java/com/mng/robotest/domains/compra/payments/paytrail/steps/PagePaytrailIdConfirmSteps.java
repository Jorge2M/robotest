package com.mng.robotest.domains.compra.payments.paytrail.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.paytrail.pageobjects.PagePaytrailIdConfirm;
import com.mng.robotest.test.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePaytrailIdConfirmSteps extends StepBase {
	
	private final PagePaytrailIdConfirm pagePaytrailIdConfirm = new PagePaytrailIdConfirm(); 
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		var checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página de introducción del ID de confirmación",
			pagePaytrailIdConfirm.isPage(), Defect);
	   	
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), Warn);
	   	
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
