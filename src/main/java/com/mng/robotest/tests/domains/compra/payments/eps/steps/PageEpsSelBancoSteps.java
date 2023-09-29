package com.mng.robotest.tests.domains.compra.payments.eps.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.eps.pageobjects.PageEpsSelBanco;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageEpsSelBancoSteps extends StepBase {

	private final PageEpsSelBanco pageEpsSelBanco = new PageEpsSelBanco();
	
	@Validation
	public ChecksTM validateIsPage(String importeTotal) {
		var checks = ChecksTM.getNew();
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"Figura el icono correspondiente al pago <b>EPS</b>",
			pageEpsSelBanco.isPresentIconoEps(), Warn);
		
		State stateVal = Warn;
		if (channel.isDevice()) {
			stateVal=Info;
		}
		checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
		
		checks.add(
			"Aparece el logo del banco seleccionado",
			pageEpsSelBanco.isVisibleIconoBanco(), Warn);
		
		return checks;
	}
}
	