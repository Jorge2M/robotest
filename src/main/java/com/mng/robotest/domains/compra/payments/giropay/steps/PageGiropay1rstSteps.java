package com.mng.robotest.domains.compra.payments.giropay.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.compra.payments.giropay.pageobjects.PageGiropay1rst;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageGiropay1rstSteps extends StepBase {

	private final PageGiropay1rst pageGiropay1rst = new PageGiropay1rst();
	
	@Validation
	public ChecksTM validateIsPage(String nombrePago, String importeTotal) {
		var checks = ChecksTM.getNew();
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago.toLowerCase() + "</b>",
			pageGiropay1rst.isPresentIconoGiropay(), State.Warn);	

		State stateVal = State.Warn;
		StoreType store = StoreType.Evidences;
		if (channel.isDevice()) {
			stateVal = State.Info;
			store = StoreType.None;
		}
		checks.add(
			Check.make(
			    "Aparece el importe de la compra: " + importeTotal,
			    ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pageGiropay1rst.driver), stateVal)
			.store(store).build());
		
		checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pageGiropay1rst.isPresentCabeceraStep(), State.Warn);	

		if (channel==Channel.desktop) {
			int seconds = 2;
			checks.add(
				"Figura un botón de pago (lo esperamos hasta " + seconds + " segundos)",
				pageGiropay1rst.isPresentButtonPagoDesktopUntil(seconds), State.Defect);
		}
		
		return checks;
	}

	@Step (
		description="Pulsamos el botón para continuar con el Pago", 
		expected="Aparece la página de Test de introducción de datos de Giropay")
	public void clickButtonContinuePay() {
		pageGiropay1rst.clickButtonContinuePay();
		new PageGiropayInputBankSteps().checkIsPage();
	}
}
