package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.pageobject.shop.PageRecADomic;


public class PageRecogidaDomicStpV {

    public static void vaidaIsPageSinDevoluciones(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) Aparece la página de Recogida a Domicilio<br>" +
            "2) Aparece la tabla de devoluciones<br>" +
            "3) No aparece ningún pedido";
        datosStep.setNOKstateByDefault();  
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageRecADomic.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageRecADomic.isTableDevoluciones(dFTest.driver)) {
                listVals.add(2, State.Defect);
            }
            if (PageRecADomic.hayPedidos(dFTest.driver)) {
                listVals.add(3, State.Info);
            }

            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
