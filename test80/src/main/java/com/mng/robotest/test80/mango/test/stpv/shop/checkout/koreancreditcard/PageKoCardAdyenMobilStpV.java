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
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyenMobil;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("javadoc")
public class PageKoCardAdyenMobilStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void validateIsPage(String importeTotal, Pais pais, Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String validacion1 = "";
        if (channel==Channel.desktop) {
            validacion1 = 
            "1) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>"; 
        }
        String descripValidac = 
            validacion1 + 
            "2) No se trata de la página de precompra (no aparece los logos de formas de pago <br>" + 
    		"3) Aparece la página de Adyen / Korean Kredit Cards";
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
            if (!PageKoCardAdyenMobil.isPage(dFTest.driver))
            	fmwkTest.addValidation(3, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }

    public static void clickIconForContinue (DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep (
        	"Seleccionar el icono de Korean Credit Card para continuar",
            "Aparece la páinga de INIpay");
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            PageKoCardAdyenMobil.clickIconForContinue(dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validations
        PageKoCardINIpay1MobilStpV2.validateIsPage(datosStep, dFTest);
    }

}
