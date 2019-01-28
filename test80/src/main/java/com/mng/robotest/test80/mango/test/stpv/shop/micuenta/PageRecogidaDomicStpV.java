package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRecADomic;

@SuppressWarnings("javadoc")
public class PageRecogidaDomicStpV {

    public static void vaidaIsPageSinDevoluciones(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de Recogida a Domicilio<br>" +
            "2) Aparece la tabla de devoluciones<br>" +
            "3) No aparece ningún pedido";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageRecADomic.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageRecADomic.isTableDevoluciones(dFTest.driver))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (PageRecADomic.hayPedidos(dFTest.driver))
                fmwkTest.addValidation(3, State.Info, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
