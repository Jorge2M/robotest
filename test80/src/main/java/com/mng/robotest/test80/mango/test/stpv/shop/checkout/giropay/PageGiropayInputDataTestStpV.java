package com.mng.robotest.test80.mango.test.stpv.shop.checkout.giropay;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.giropay.PageGiropayInputDataTest;

public class PageGiropayInputDataTestStpV {
    
	@Validation (
		description="Aparece la página de Test para la introducción de los datos de Giropay (la esperamos hasta #{maxSecondsWait})",
		level=State.Warn)
    public static boolean validateIsPage(int maxSecondsWait, WebDriver driver) {
		return (PageGiropayInputDataTest.isPageUntil(maxSecondsWait, driver));
    }
    
	@Step (
		description=
			"Introducimos los datos del pago:<br>" +
			"  - sc: <b>#{pago.getScgiropay()}</b><br>" +
	        "  - extensionSc: <b>#{pago.getExtscgiropay()}</b><br>" +
	        "  - customerName: <b>#{pago.getTitular()}</b><br>" +
	        "  - customerIBAN: <b>#{pago.getIban()}</b><br>" +
	        "Y pulsamos <b>Abseden</b>",
	     expected=
	     	"Aparece la página de Test de introducción de datos de Giropay")
    public static void inputDataAndClick(Pago pago, WebDriver driver) throws Exception {
        PageGiropayInputDataTest.inputSc(pago.getScgiropay(), driver);
        PageGiropayInputDataTest.inputExtensionSc(pago.getExtscgiropay(), driver);
        PageGiropayInputDataTest.inputCustomerName(pago.getTitular(), driver);
        PageGiropayInputDataTest.inputIBAN(pago.getIban(), driver);
        PageGiropayInputDataTest.clickButtonAbseden(driver);
    }
}
