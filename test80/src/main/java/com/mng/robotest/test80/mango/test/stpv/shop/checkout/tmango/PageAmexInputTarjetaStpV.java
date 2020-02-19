package com.mng.robotest.test80.mango.test.stpv.shop.checkout.tmango;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.tmango.PageAmexInputTarjeta;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

public class PageAmexInputTarjetaStpV {
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);

    @Validation
    public static ChecksTM validateIsPageOk(String importeTotal, String codPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"Aparece la pasarela de pagos de Banco Sabadell (la esperamos hasta " + maxSecondsWait + " segundos)",
			PageAmexInputTarjeta.isPasarelaBancoSabadellUntil(maxSecondsWait, driver), State.Defect); 
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
