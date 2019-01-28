package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMispedidos;

@SuppressWarnings("javadoc")
public class PagePedidosStpV {

    public static void validaIsPageSinPedidos(String usrRegistrado, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de \"Mis Pedidos\"<br>" +
            "2) La página contiene " + usrRegistrado + "<br>" +
            "3) La lista de pedidos está vacía";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMispedidos.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageMispedidos.elementContainsText(dFTest.driver, usrRegistrado))
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageMispedidos.listaPedidosVacia(dFTest.driver))
                fmwkTest.addValidation(3, State.Warn, listVals);

            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
