package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoLogin;

/**
 * Page-1: Identificación en Mercadopago
 * @author jorge.munoz
 *
 */

public class PageMercpagoLoginStpV {

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de identificación de Mercadopago<br>" + 
            "2) En la página figuran los campos de identificación (email + password)";
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if ((!PageMercpagoLogin.isPage(dFTest.driver))) {
                listVals.add(1, State.Defect);
            }
            if (!PageMercpagoLogin.isInputUserVisible(dFTest.driver) ||
                !PageMercpagoLogin.isInputPasswordVisible(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep loginMercadopago(Pago pago, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        DatosStep datosStep = new DatosStep       (
            "Introducir el usuario/password de mercadopago (" + pago.getUseremail() + " / " + pago.getPasswordemail() + ") + click botón \"Ingresar\"",
            "Aparece alguna de las páginas:<br>" +
            " - Elección medio pago<br>" +
            " - Introducción CVC");
        try {
            PageMercpagoLogin.sendInputUser(dFTest.driver, pago.getUseremail());
            PageMercpagoLogin.sendInputPassword(dFTest.driver, pago.getPasswordemail());
            PageMercpagoLogin.clickBotonContinuar(dFTest.driver);
                                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validaciones
        //if (PageMercpagoDatosTrjDesktop.isPage(dFTest.driver))
            PageMercpagoDatosTrjStpV.validaIsPage(channel, datosStep, dFTest);
//        else
//            PageMercpagoSelPagoStpV.validateIsPage(importeTotal, codigoPais, datosStep, dFTest);
        
        return datosStep;
    }
}
