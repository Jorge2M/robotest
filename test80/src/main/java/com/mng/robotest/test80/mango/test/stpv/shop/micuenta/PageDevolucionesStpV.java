package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageDevoluciones;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageDevoluciones.Devolucion;;

@SuppressWarnings("javadoc")
public class PageDevolucionesStpV {
    
    public static void validaIsPage(datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de devoluciones<br>" +
            "2) Aparece la opción de " + Devolucion.EnTienda.getLiteral() + "<br>" +
            "3) Aparece la opción de " + Devolucion.EnDomicilio.getLiteral() + "<br>" +
            "4) Aparece la opción de " + Devolucion.PuntoCeleritas.getLiteral();
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageDevoluciones.isPage(dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!Devolucion.EnTienda.isPresentLink(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (!Devolucion.EnDomicilio.isPresentLink(dFTest.driver)) 
                fmwkTest.addValidation(3, State.Defect, listVals);
            //4)
            if (!Devolucion.PuntoCeleritas.isPresentLink(dFTest.driver))
                fmwkTest.addValidation(4, State.Defect, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep solicitarRegogidaGratuitaADomicilio(DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep (
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
