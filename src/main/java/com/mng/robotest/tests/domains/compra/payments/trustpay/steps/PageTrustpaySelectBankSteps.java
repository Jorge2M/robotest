package com.mng.robotest.tests.domains.compra.payments.trustpay.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.trustpay.pageobjects.PageTrustpaySelectBank;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageTrustpaySelectBankSteps extends StepBase {

	private final PageTrustpaySelectBank pageTrustpaySelectBank = new PageTrustpaySelectBank();
	
	@Validation
	public ChecksTM checkIsPage(String nombrePago, String importeTotal) {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			pageTrustpaySelectBank.isPresentEntradaPago(nombrePago), WARN);
	 	
	 	State level = WARN;
		if (channel.isDevice()) {
			level = INFO;
		}
		String codPais = dataTest.getCodigoPais();
	 	checks.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), level);
	 	
	 	checks.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			pageTrustpaySelectBank.isPresentCabeceraStep(nombrePago), WARN);
	 	
		if (isDesktop()) {
		 	checks.add(
				"Figura el desplegable de bancos",
				pageTrustpaySelectBank.isPresentSelectBancos(), WARN);
		 	
		 	checks.add(
				"Figura un botón de pago",
				pageTrustpaySelectBank.isPresentButtonPago()); 
		}
		
		return checks;
	}
	
	private static final String TAG_POSSIBLE_BANKS = "@TagPosibleBanks";
	@Step (
		description="Seleccionamos un banco de test (contiene alguno de los textos " + TAG_POSSIBLE_BANKS + ") y pulsamos <b>Pay</b>", 
		expected="Aparece la página de test para la confirmación")
	public void selectTestBankAndPay(String importeTotal) {
		List<String> listOfPosibleValues = new ArrayList<>();
		listOfPosibleValues.addAll(Arrays.asList("TestPay", "Fio banka"));
		replaceStepDescription(TAG_POSSIBLE_BANKS, String.join(",", listOfPosibleValues));

		pageTrustpaySelectBank.selectBankThatContains(listOfPosibleValues);
		pageTrustpaySelectBank.clickButtonToContinuePay();
		
		//Validation
		//PageTrustpayTestConfirmSteps.validateIsPage(StepTestMaker, dFTest);
		new PageTrustPayResultSteps().checkIsPage(importeTotal);
	}
}
