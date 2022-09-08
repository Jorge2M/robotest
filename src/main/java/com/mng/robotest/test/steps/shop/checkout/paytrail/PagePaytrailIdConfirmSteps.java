package com.mng.robotest.test.steps.shop.checkout.paytrail;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.paytrail.PagePaytrailIdConfirm;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePaytrailIdConfirmSteps extends StepBase {
	
	private final PagePaytrailIdConfirm pagePaytrailIdConfirm = new PagePaytrailIdConfirm(); 
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal, String codPais) {
		ChecksTM checks = ChecksTM.getNew();
	   	checks.add(
			"Aparece la página de introducción del ID de confirmación",
			pagePaytrailIdConfirm.isPage(), State.Defect);
	   	
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	   	
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