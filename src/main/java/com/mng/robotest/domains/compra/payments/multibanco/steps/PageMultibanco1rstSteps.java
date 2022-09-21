package com.mng.robotest.domains.compra.payments.multibanco.steps;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.compra.payments.multibanco.pageobjects.PageMultibanco1rst;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.utils.ImporteScreen;


public class PageMultibanco1rstSteps extends StepBase {
	
	PageMultibanco1rst pageMultibanco1rst = new PageMultibanco1rst();
	
	@Validation
	public ChecksTM validateIsPage(String nombrePago, String importeTotal, String emailUsr) {
		ChecksTM checks = ChecksTM.getNew();
		String codPais = dataTest.getCodigoPais();
	   	checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			pageMultibanco1rst.isPresentEntradaPago(nombrePago), State.Warn);
	   	
	   	State stateVal = State.Warn;
		if (channel.isDevice()) {
			stateVal = State.Info;
		}
	   	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), stateVal);
	   	
	   	checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pageMultibanco1rst.isPresentCabeceraStep(), State.Warn);
	   	
		if (channel==Channel.desktop) {
		   	checks.add(
				"Aparece un campo de introducción de email (informado con <b>" + emailUsr + "</b>)",
				pageMultibanco1rst.isPresentEmailUsr(emailUsr), State.Warn);
		   	
		   	checks.add(
				"Figura un botón de pago",
				pageMultibanco1rst.isPresentButtonPagoDesktop(), State.Defect);		   	
		}
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el botón \"Pagar\"", 
		expected="Aparece la página de \"En progreso\"")
	public void continueToNextPage() throws Exception {
		pageMultibanco1rst.continueToNextPage();
		new PageMultibancoEnProgresoSteps().validateIsPage();
	}
}
