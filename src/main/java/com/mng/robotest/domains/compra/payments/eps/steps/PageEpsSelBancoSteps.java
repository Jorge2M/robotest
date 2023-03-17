package com.mng.robotest.domains.compra.payments.eps.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.compra.payments.eps.pageobjects.PageEpsSelBanco;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageEpsSelBancoSteps extends StepBase {

	PageEpsSelBanco pageEpsSelBanco = new PageEpsSelBanco();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"Figura el icono correspondiente al pago <b>EPS</b>",
			pageEpsSelBanco.isPresentIconoEps(), State.Warn);
		
		State stateVal = State.Warn;
		if (channel.isDevice()) {
			stateVal=State.Info;
		}
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
		
		checks.add(
			"Aparece el logo del banco seleccionado",
			pageEpsSelBanco.isVisibleIconoBanco(), State.Warn);
		
		return checks;
	}
}
	