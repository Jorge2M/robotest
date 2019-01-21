package com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;

public class SecIdealStpV {

    public static void validateIsSectionOk(Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el bloque de selección del banco";
        
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecIdeal.isVisibleSelectorOfBank(channel, 1/*maxSecondsToWait*/, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    /**
     * @param el valor de las opciones del banco a seleccionar contiene el "value" del listBox...
     */
    public static datosStep clickBanco(BancoSeleccionado bancoSeleccionado, Channel channel, DataFmwkTest dFTest) {
        //Step
        datosStep datosStep = new datosStep (
            "Seleccionar el banco \"" + bancoSeleccionado + "\"", 
            "El resultado es correcto");
        try {
            SecIdeal.clickBancoByValue(dFTest.driver, channel, bancoSeleccionado);
                                   
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }

}