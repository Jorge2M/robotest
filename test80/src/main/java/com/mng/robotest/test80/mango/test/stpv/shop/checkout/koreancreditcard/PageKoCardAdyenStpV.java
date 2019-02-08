package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyen;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@SuppressWarnings("javadoc")
public class PageKoCardAdyenStpV {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void validateIsPage(String importeTotal, Pais pais, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String validacion1 = "";
        if (channel==Channel.desktop) {
            validacion1 = 
            "1) En la página resultante figura el importe total de la compra (" + importeTotal + ")<br>"; 
        }
        String descripValidac = 
            validacion1 + 
            "2) No se trata de la página de precompra (no aparece los logos de formas de pago <br>" + 
    		"3) Aparece la página de Adyen / Korean Kredit Cards";
        datosStep.setStateIniValidations(); 
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (channel==Channel.desktop) {
                if (!ImporteScreen.isPresentImporteInScreen(importeTotal, pais.getCodigo_pais(), dFTest.driver)) {
                    listVals.add(1, State.Warn);
                }
            }
            if (PageCheckoutWrapper.isPresentMetodosPago(pais, channel, dFTest.driver)) {
                listVals.add(2, State.Defect);            
            }
            if (!PageKoCardAdyen.isPage(dFTest.driver)) {
            	listVals.add(3, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    public static DatosStep clickIconForContinue (Channel channel, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep (
        	"Seleccionar el icono de Korean Credit Card para continuar",
            "Aparece la páinga de INIpay");
        datosStep.setStateIniValidations();
        try {
            PageKoCardAdyen.clickForContinue(channel, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validations
        switch (channel) {
        case movil_web:
        	PageKoCardINIpay1MobilStpV2.validateIsPage(datosStep, dFTest);
        	break;
        case desktop:
        	PageKoreanConfDesktopStpV.validateIsPage(datosStep, dFTest);
        	break;
        }
        
        return datosStep;
    }
}
