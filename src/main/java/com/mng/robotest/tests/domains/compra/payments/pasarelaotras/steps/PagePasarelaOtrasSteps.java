package com.mng.robotest.tests.domains.compra.payments.pasarelaotras.steps;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.pageobjects.PageCheckoutWrapper;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PagePasarelaOtrasSteps extends StepBase {
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
		if (channel==Channel.desktop) {
		   	checks.add(
				"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
				ImporteScreen.isPresentImporteInScreen(importeTotal, dataTest.getCodigoPais(), driver), WARN);
		}
	   	checks.add(
			"No se trata de la página de precompra (no aparece los logos de formas de pago)",
			new PageCheckoutWrapper().isPresentMetodosPago());
	   	
	   	return checks;
	}
}
