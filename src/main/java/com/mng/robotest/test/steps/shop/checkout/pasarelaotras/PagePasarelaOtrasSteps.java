package com.mng.robotest.test.steps.shop.checkout.pasarelaotras;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test.utils.ImporteScreen;

public class PagePasarelaOtrasSteps extends StepBase {
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal) {
		ChecksTM checks = ChecksTM.getNew();
		if (channel==Channel.desktop) {
		   	checks.add(
				"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
				ImporteScreen.isPresentImporteInScreen(importeTotal, dataTest.getCodigoPais(), driver), State.Warn);
		}
	   	checks.add(
			"No se trata de la página de precompra (no aparece los logos de formas de pago)",
			new PageCheckoutWrapper().isPresentMetodosPago(), State.Defect);
	   	
	   	return checks;
	}
}
