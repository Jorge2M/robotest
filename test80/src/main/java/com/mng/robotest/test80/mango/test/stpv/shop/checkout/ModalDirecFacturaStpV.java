package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.DataDireccion;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ModalDirecFactura;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;

public class ModalDirecFacturaStpV {

	@Validation
    public static ChecksResult validateIsOk(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
        int maxSecondsWait = 5;
	 	validations.add(
			"Es visible el formulario para la introducción de la \"Dirección de facturación\" (lo esperamos hasta " + maxSecondsWait + " seconds)<br>",
			ModalDirecFactura.isVisibleFormUntil(maxSecondsWait, driver), State.Defect);    
	 	validations.add(
			"Es visible el botón \"Actualizar\"",
	 		ModalDirecFactura.isVisibleButtonActualizar(driver), State.Defect);   
    	return validations;
    }
    
	@Step (
		description="Introducir los datos y pulsar \"Actualizar\"<br>#{dataDirFactura.getFormattedHTMLData()}", 
        expected="Los datos se actualizan correctamente")
    public static void inputDataAndActualizar(DataDireccion dataDirFactura, WebDriver driver) throws Exception {
        ModalDirecFactura.sendDataToInputs(dataDirFactura, driver);
        ModalDirecFactura.clickActualizar(driver);
        checkAfterChangeDireccion(driver);
    }
	
	@Validation
	private static ChecksResult checkAfterChangeDireccion(WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Desaparece el modal de introducción de los datos de la dirección<br>",
	 		!ModalDirecFactura.isVisibleFormUntil(0, driver), State.Defect);    
	 	validations.add(
			"Queda marcado el radiobutton \"Quiero recibir una factura\"",
			Page1DktopCheckout.isMarkedQuieroFactura(driver), State.Defect); 
	 	return validations;
	}
}
