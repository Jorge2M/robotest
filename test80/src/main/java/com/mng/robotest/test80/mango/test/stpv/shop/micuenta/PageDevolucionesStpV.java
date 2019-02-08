package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageDevoluciones;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageDevoluciones.Devolucion;;

@SuppressWarnings("javadoc")
public class PageDevolucionesStpV {
    
    public static void validaIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de devoluciones<br>" +
            "2) Aparece la opción de " + Devolucion.EnTienda.getLiteral() + "<br>" +
            "3) Aparece la opción de " + Devolucion.EnDomicilio.getLiteral() + "<br>" +
            "4) Aparece la opción de " + Devolucion.PuntoCeleritas.getLiteral();
        datosStep.setStateIniValidations();      
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageDevoluciones.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!Devolucion.EnTienda.isPresentLink(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (!Devolucion.EnDomicilio.isPresentLink(dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
            if (!Devolucion.PuntoCeleritas.isPresentLink(dFTest.driver)) {
                listVals.add(4, State.Defect);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep solicitarRegogidaGratuitaADomicilio(DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep (
            "Pulsar \"Recogida gratuíta a domicilio\" + \"Solicitar Recogida\"", 
            "Aparece la tabla de devoluciones sin ningún pedido");
        try {
        	boolean desplegada = true;
            Devolucion.EnDomicilio.click(dFTest.driver);
            Devolucion.EnDomicilio.waitForInState(desplegada, 2, dFTest.driver);
            PageDevoluciones.clickSolicitarRecogida(dFTest.driver);
                                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }

        //Validaciones.
        PageRecogidaDomicStpV.vaidaIsPageSinDevoluciones(datosStep, dFTest);
        
        return datosStep;
    }
}
