package com.mng.robotest.test80.mango.test.stpv.shop.checkout.koreancreditcard;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.StepAspect;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardAdyen;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay1Mobil;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.koreancreditcard.PageKoCardINIpay1Mobil.BodyPageKoCardINIpay1;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PageKoCardINIpay1MobilStpV2 {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validacion
        String descripValidac =
        	"1) Aparece una página de INIpay<br>" +
            "2) Existe el botón <b>SamsungPay</b><br>" +
            "3) Existe el checkbox para los <b>terminos</b> del pago<br>" +
            "4) Existe el titulo de los terminos<br>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageKoCardINIpay1Mobil.isPage(dFTest.driver)) {
            	listVals.add(1, State.Warn);
            }
            if (!PageKoCardINIpay1Mobil.isElementInStateUntil(BodyPageKoCardINIpay1.samsungpay, StateElem.Present, 2, dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (!PageKoCardINIpay1Mobil.isElementInStateUntil(BodyPageKoCardINIpay1.terms, StateElem.Present, 2, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
            if (!PageKoCardAdyen.isElementInStateUntil(BodyPageKoCardINIpay1.termsTitle, StateElem.Present, 2, dFTest.driver)) {
                listVals.add(4, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    public static void checkTerminosBox(DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Marcamos el checkbox de los terminos",
            "Desaparecen el titulo de los terminos");
        datosStep.setNOKstateByDefault();
        try {
            PageKoCardINIpay1Mobil.clickAndWait(BodyPageKoCardINIpay1.terms, 2, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }

        //Validation
        String descripValidac =
            "1) Desaparece el apartado de los términos";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (PageKoCardINIpay1Mobil.isElementInStateUntil(BodyPageKoCardINIpay1.termsTitle, StateElem.Visible, 1, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        } 
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }

    public static void continuarConPagoCoreaMobile(DataFmwkTest dFTest) throws Exception {
        //Step
    	String litButtonTypeCard = "케이뱅크";
        DatosStep datosStep = new DatosStep     (
            "Seleccionamso el botón correspondiente al tipo de tarjeta <b>" + litButtonTypeCard + "</b>",
            "Aparece información varia y el boton de continuar");
        datosStep.setNOKstateByDefault();
        try {
        	BodyPageKoCardINIpay1.clickTypeCardButton(litButtonTypeCard, dFTest.driver);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        } 
        finally { StepAspect.storeDataAfterStep(datosStep); }
        
        //Validations
        PageKoCardINIpay2MobilStpV.validateIsPage(datosStep, dFTest);
    }
}
