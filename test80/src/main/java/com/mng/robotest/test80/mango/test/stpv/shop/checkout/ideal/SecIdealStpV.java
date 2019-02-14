package com.mng.robotest.test80.mango.test.stpv.shop.checkout.ideal;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.ideal.SecIdeal.BancoSeleccionado;

public class SecIdealStpV {

    public static void validateIsSectionOk(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece el bloque de selección del banco";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!SecIdeal.isVisibleSelectorOfBank(channel, 1/*maxSecondsToWait*/, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        catch (Exception e) {
            //
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    /**
     * @param el valor de las opciones del banco a seleccionar contiene el "value" del listBox...
     */
    public static DatosStep clickBanco(BancoSeleccionado bancoSeleccionado, Channel channel, DataFmwkTest dFTest) {
        //Step
        DatosStep datosStep = new DatosStep (
            "Seleccionar el banco \"" + bancoSeleccionado + "\"", 
            "El resultado es correcto");
        try {
            SecIdeal.clickBancoByValue(dFTest.driver, channel, bancoSeleccionado);
                                   
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        return datosStep;
    }

}