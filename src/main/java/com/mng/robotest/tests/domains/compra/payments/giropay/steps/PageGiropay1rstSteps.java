package com.mng.robotest.tests.domains.compra.payments.giropay.steps;

import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.giropay.pageobjects.PageGiropay1rst;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class PageGiropay1rstSteps extends StepBase {

	private final PageGiropay1rst pgGiropay1rst = new PageGiropay1rst();
	
	@Validation
	public ChecksTM validateIsPage(String nombrePago, String importeTotal) {
		var checks = ChecksTM.getNew();
		String codPais = dataTest.getCodigoPais();
		checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago.toLowerCase() + "</b>",
			pgGiropay1rst.isPresentIconoGiropay(), WARN);	

		var stateVal = WARN;
		var store = EVIDENCES;
		if (channel.isDevice()) {
			stateVal = INFO;
			store = NONE;
		}
		checks.add(
			Check.make(
			    "Aparece el importe de la compra: " + importeTotal,
			    ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, pgGiropay1rst.driver), stateVal)
			.store(store).build());
		
		checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pgGiropay1rst.isPresentCabeceraStep(), WARN);	

		if (isDesktop()) {
			int seconds = 2;
			checks.add(
				"Figura un botón de pago " + getLitSecondsWait(seconds),
				pgGiropay1rst.isPresentButtonPagoDesktopUntil(seconds));
		}
		
		return checks;
	}

	@Step (
		description="Pulsamos el botón para continuar con el Pago", 
		expected="Aparece la página de Test de introducción de datos de Giropay")
	public void clickButtonContinuePay() {
		pgGiropay1rst.clickButtonContinuePay();
		new PageGiropayInputBankSteps().checkIsPage();
	}
}
