package com.mng.robotest.domains.compra.payments.yandex.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.compra.payments.yandex.pageobjects.PageYandex1rst;
import com.mng.robotest.domains.compra.payments.yandex.pageobjects.PageYandexPayingByCode;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.utils.ImporteScreen;

public class PageYandex1rstSteps extends StepBase {
	
	private final PageYandex1rst pageYandex1rst = new PageYandex1rst();
	
	@Validation
	public ChecksTM validateIsPage(String emailUsr, String importeTotal, String codPais) {
		//Esta validación debería hacerse en un punto posterior, una vez se ha intentado enviar el input que es cuando se genera el botón retry.
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la página inicial de Yandex",
			pageYandex1rst.isPage(), State.Warn);
	 	
	 	checks.add(
			"Figura preinformado el email del usuario: " + emailUsr,
			pageYandex1rst.isValueEmail(emailUsr), State.Warn);
	 	
	 	checks.add(
			"Aparece el importe de la compra por pantalla: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn);
	 	
		return checks;
	}
	
	@Step (
		description="Introducimos el teléfono <b>#{telefonoRuso}</b> y seleccionamos el botón <b>\"Continuar\"</b>", 
		expected="Aparece la página de confirmación del pago")
	public String inputTlfnAndclickContinuar(String telefonoRuso, String importeTotal) {
		pageYandex1rst.inputTelefono(telefonoRuso);
		pageYandex1rst.clickContinue();
		String codPais = dataTest.getCodigoPais();
		if (!pageYandex1rst.retryButtonExists()) {
			new PageYandexPayingByCodeSteps().validateIsPage(importeTotal, codPais);
			return new PageYandexPayingByCode().getPaymentCode();
		} else {
			return retry(importeTotal, codPais);
		}
	}

	public boolean hasFailed() {
		return pageYandex1rst.retryButtonExists();
	}

	public String retry(String importeTotal, String codPais) {
		pageYandex1rst.clickOnRetry();
		pageYandex1rst.clickContinue();
		new PageYandexPayingByCodeSteps().validateIsPage(importeTotal, codPais);
		return new PageYandexPayingByCode().getPaymentCode();
	}
}
