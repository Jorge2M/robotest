package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexInputTarjeta;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageAmexInputTarjetaStpV {

    @Validation
    public static ChecksTM validateIsPageOk(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
	 	validations.add(
			"Aparece la pasarela de pagos de Banco Sabadell (la esperamos hasta " + maxSeconds + " segundos)",
			PageAmexInputTarjeta.isPasarelaBancoSabadellUntil(maxSeconds, driver), State.Defect); 
	 	validations.add(
			"En la página resultante figura el importe total de la compra (" + importeTotal + ")",
			ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, driver), State.Warn); 
	 	validations.add(
			"Aparecen los campos de introducción de tarjeta, fecha caducidad y código de seguridad",
			PageAmexInputTarjeta.isPresentNumTarj(driver) &&
            PageAmexInputTarjeta.isPresentInputMesCad(driver) &&
            PageAmexInputTarjeta.isPresentInputAnyCad(driver) &&
            PageAmexInputTarjeta.isPresentInputCvc(driver), State.Warn); 
	 	validations.add(
			"Figura un botón de Aceptar",
			PageAmexInputTarjeta.isPresentPagarButton(driver), State.Defect); 
	 	return validations;
    }
    
    @Step (
		description="Introducimos los datos de la tarjeta: #{numTarj} / #{mesCad}-#{anyCad} / #{Cvc} y pulsamos el botón \"Pagar\"", 
        expected="Aparece la página de introducción del CIP")
    public static void inputTarjetaAndPayButton(String numTarj, String mesCad, String anyCad, String Cvc, 
    												 String importeTotal, String codigoPais, WebDriver driver) throws Exception {
        PageAmexInputTarjeta.inputDataTarjeta(numTarj, mesCad, anyCad, Cvc, driver);
        PageAmexInputTarjeta.clickPagarButton(driver);
                    
        //Validaciones
        PageAmexInputCipStpV.validateIsPageOk(importeTotal, codigoPais, driver);
    }
}
