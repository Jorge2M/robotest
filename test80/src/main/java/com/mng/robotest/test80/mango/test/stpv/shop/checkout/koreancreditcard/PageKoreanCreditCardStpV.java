package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoreanCreditCard;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoreanCreditCardMobile;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoreanCreditCardMobile.KsMobile;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("javadoc")
public class PageKoreanCreditCardStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void validateIsPage(String importeTotal, Pais pais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String validacion1 = "";
        if (channel==Channel.desktop)
            validacion1 = 
            "1) En la página resultante figura el importe total de la compra (" + importeTotal + ")"; 
            
        String descripValidac = 
            validacion1 + "<br>" + 
            "2) No se trata de la página de precompra (no aparece los logos de formas de pago <br>" + 
    		"3) Aparece el botón para continuar con la compra";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);           
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (channel==Channel.desktop) {
                if (!ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), dFTest.driver)) 
                    fmwkTest.addValidation(1, State.Warn, listVals);
            }
            //2)
            if (PageCheckoutWrapper.isPresentMetodosPago(pais, channel, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);            
            //3)
            if (!PageKoreanCreditCard.isButtonSubmitVisible(dFTest.driver))
            	fmwkTest.addValidation(3, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem validating \"Otras\" pasarela for country {}", pais.getNombre_pais(), e);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void paymentMobileCorea (DataFmwkTest dFTest) throws Exception {
        payMobile(dFTest);
        preparePaymentMobile(dFTest);
        continuarConPagoCoreaMobile(dFTest);
        confirmMainPaymentCorea(dFTest);
        endPayment(dFTest);
    }

    public static void payMobile (DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
                "Aparece el botón para proceder con el pago",
                "Carga la pagina de la pasarela de Corea");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            PageKoreanCreditCard.clickButtonCreditCard(dFTest.driver);
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validacion
        String descripValidac =
                "1) Existe el <b>SamsungPay</b><br>" +
                "2) Existe el checkbox para los <b>terminos</b> del pago<br>" +
                "3) Existen diversas opciones para definir el pago<br>" +
                "4) Existe el titulo de los terminos<br>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.samsungpay, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.terms, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (!PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.type, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
            //4)
            if (!PageKoreanCreditCard.isElementInStateUntil(KsMobile.termsTitle, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(4, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void preparePaymentMobile(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
                "Marcamos el checkbox de los terminos",
                "Desaparecen el titulo de los temrinos");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            PageKoreanCreditCardMobile.clickAndWait(KsMobile.terms, 2, dFTest.driver);
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validation
        String descripValidac =
                "1) El <b>titulo</b> de los terminos ha desaparecido";

        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.termsTitle, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void continuarConPagoCoreaMobile(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
                "Seleccionamos la opcion deseada (ultima fila, segunda columna)",
                "Aparece información varia y el boton de continuar");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            PageKoreanCreditCardMobile.clickAndWait(KsMobile.type, 2, dFTest.driver);
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validation
        String descripValidac =
                "1) El boton <b>continuar</b> aparece correctamente<br>" +
                "2) Tenemos una lista enumerable<br>" +
                "3) Podemos retroceder al paso anterior con el botón <b>volver</b>";

        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.nextButton, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.iniMon, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.previousButton, StateElem.Present, 2, dFTest.driver))
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void confirmMainPaymentCorea(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
                "Procedemos a continuar con el proceso",
                "Aparece la proxima pagina del procedimiento de pago");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            PageKoreanCreditCardMobile.clickAndWait(KsMobile.nextButton, 2, dFTest.driver);
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validation
        String descripValidac =
                "1) El boton <b>continuar</b> aparece correctamente<br>" +
                        "2) Aparece el boton de <b>volver</b> correctamente" +
                        "3) Aparece el <b>main button</b> correctamente";

        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.nextButton, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.previousButton, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.mainButton, StateElem.Present, 2, dFTest.driver))
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void endPayment(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
                "Procedemos a finalizar el proceso",
                "Aparece la proxima pagina del procedimiento de pago");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            PageKoreanCreditCardMobile.clickAndWait(KsMobile.nextButton, 2, dFTest.driver);
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validation
        String descripValidac =
                "1) El boton <b>continuar</b> aparece correctamente para finalizar la pasarela<br>" +
                        "2) Aparece el boton de <b>volver</b> correctamente" +
                        "3) Aparece la <b>información final</b> correctamente";
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.nextButton, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.previousButton, StateElem.Present, 2, dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (PageKoreanCreditCardMobile.isElementInStateUntil(KsMobile.infoPayment, StateElem.Present, 2, dFTest.driver))
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        } finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static datosStep clickConfirmarButton(DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Seleccionar el botón para Confirmar", 
            "Aparece la página de confirmación de KrediKarti");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);    
        try {       
        	PageKoreanCreditCard.clickButtonSubmit(dFTest.driver);

            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validation
        PageKoreanConfirmatioStpV.validateIsPage(datosStep, dFTest);

        return datosStep;
    }
}
