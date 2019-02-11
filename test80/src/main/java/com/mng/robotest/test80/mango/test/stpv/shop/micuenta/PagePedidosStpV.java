package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageMispedidos;

@SuppressWarnings("javadoc")
public class PagePedidosStpV {

    public static void validaIsPageSinPedidos(String usrRegistrado, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de \"Mis Pedidos\"<br>" +
            "2) La página contiene " + usrRegistrado + "<br>" +
            "3) La lista de pedidos está vacía";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageMispedidos.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageMispedidos.elementContainsText(dFTest.driver, usrRegistrado)) {
                listVals.add(2, State.Warn);
            }
            if (!PageMispedidos.listaPedidosVacia(dFTest.driver)) {
                listVals.add(3, State.Warn);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
