package com.mng.robotest.test80.mango.test.stpv.shop.checkout.yandex;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.yandex.PageYandex1rst;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;


public class PageYandex1rstStpv {
    
    public static void validateIsPage(String emailUsr, String importeTotal, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validation
        //Esta validación debería hacerse en un punto posterior, una vez se ha intentado enviar el input que es cuando se genera el botón retry.
        String descripValidac =
                    "1) Aparece la página inicial de Yandex<br>" +
                            "2) Figura preinformado el email del usuario: " + emailUsr + "<br>" +
                            "3) Aparece el importe de la compra por pantalla: " + importeTotal;

        datosStep.setNOKstateByDefault();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageYandex1rst.isPage(dFTest.driver)) {
               listVals.add(1, State.Warn);
            }
            if (!PageYandex1rst.isValueEmail(emailUsr, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            //3)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static String inputTlfnAndclickContinuar(String telefonoRuso, String emailUsr, String importeTotal, String codPais, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Introducimos el teléfono <b>" + telefonoRuso + "</b> y seleccionamos el botón <b>\"Continuar\"</b>", 
            "Aparece la página de confirmación del pago");
        try {
            PageYandex1rst.inputTelefono(telefonoRuso, dFTest.driver);
            PageYandex1rst.clickContinue(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        if (!PageYandex1rst.retryButtonExists(dFTest.driver)) {
            //Validation
            String paymentCode = PageYandexPayingByCodeStpV.validateIsPage(emailUsr, importeTotal, codPais, channel, datosStep, dFTest);
            return paymentCode;
        } else {
            String paymentCode = retry(emailUsr, importeTotal, codPais, datosStep, channel, dFTest);
            return paymentCode;
        }
    }

    public static boolean hasFailed(WebDriver driver) {
        return PageYandex1rst.retryButtonExists(driver);
    }

    public static String retry(String emailUsr, String importeTotal, String codPais, DatosStep datosStep, Channel channel, DataFmwkTest dFTest) throws Exception {
        PageYandex1rst.clickOnRetry(dFTest.driver);
        PageYandex1rst.clickContinue(dFTest.driver);
        String paymentCode = PageYandexPayingByCodeStpV.validateIsPage(emailUsr, importeTotal, codPais, channel, datosStep, dFTest);
        return paymentCode;
    }
}
