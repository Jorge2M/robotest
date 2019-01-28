package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoLogin;

/**
 * Page-1: Identificación en Mercadopago
 * @author jorge.munoz
 *
 */
@SuppressWarnings("javadoc")
public class PageMercpagoLoginStpV {

    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de identificación de Mercadopago<br>" + 
            "2) En la página figuran los campos de identificación (email + password)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if ((!PageMercpagoLogin.isPage(dFTest.driver)))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageMercpagoLogin.isInputUserVisible(dFTest.driver) ||
                !PageMercpagoLogin.isInputPasswordVisible(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            /*
             * 
             */
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DatosStep loginMercadopago(Pago pago, String importeTotal, String codigoPais, Channel channel, DataFmwkTest dFTest) 
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones
        //if (PageMercpagoDatosTrjDesktop.isPage(dFTest.driver))
            PageMercpagoDatosTrjStpV.validaIsPage(channel, datosStep, dFTest);
//        else
//            PageMercpagoSelPagoStpV.validateIsPage(importeTotal, codigoPais, datosStep, dFTest);
        
        return datosStep;
    }
}
