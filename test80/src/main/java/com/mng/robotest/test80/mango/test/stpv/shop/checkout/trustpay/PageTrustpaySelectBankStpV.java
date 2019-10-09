package com.mng.robotest.test80.mango.test.stpv.shop.checkout.trustpay;

import java.util.ArrayList;
import java.util.Arrays;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.TestCaseData;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.trustpay.PageTrustpaySelectBank;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageTrustpaySelectBankStpV {

	@Validation
    public static ChecksResult validateIsPage(String nombrePago, String importeTotal, String codPais, Channel channel, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Figura el bloque correspondiente al pago <b>" + nombrePago + "</b>",
			PageTrustpaySelectBank.isPresentEntradaPago(nombrePago, channel, driver), State.Warn);
	 	
	 	State level = State.Warn;
        if (channel==Channel.movil_web) {
            level = State.Info;
        }
	 	validations.add(
			"Aparece el importe de la compra: " + importeTotal,
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), level); 
	 	validations.add(
			"Aparece la cabecera indicando la 'etapa' del pago",
			PageTrustpaySelectBank.isPresentCabeceraStep(nombrePago, channel, driver), State.Warn); 
        if (channel==Channel.desktop) {
		 	validations.add(
				"Figura el desplegable de bancos",
				PageTrustpaySelectBank.isPresentSelectBancos(driver), State.Warn); 
		 	validations.add(
				"Figura un botón de pago",
				PageTrustpaySelectBank.isPresentButtonPago(driver), State.Defect); 
        }
        
        return validations;
    }
    
	final static String tagPosibleBanks = "@TagPosibleBanks";
	@Step (
		description="Seleccionamos un banco de test (contiene alguno de los textos " + tagPosibleBanks + ") y pulsamos <b>Pay</b>", 
        expected="Aparece la página de test para la confirmación")
    public static void selectTestBankAndPay(String importeTotal, String codPais, Channel channel, WebDriver driver) throws Exception {
        ArrayList<String> listOfPosibleValues = new ArrayList<>();
        listOfPosibleValues.addAll(Arrays.asList("TestPay", "Fio banka"));
        TestCaseData.getDatosCurrentStep().replaceInDescription(tagPosibleBanks, String.join(",", listOfPosibleValues));

        PageTrustpaySelectBank.selectBankThatContains(listOfPosibleValues, channel, driver);
        PageTrustpaySelectBank.clickButtonToContinuePay(channel, driver);
        
        //Validation
        //PageTrustpayTestConfirmStpV.validateIsPage(StepTestMaker, dFTest);
        PageTrustPayResultStpV.validateIsPage(importeTotal, codPais, driver);
    }
}
