package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageListPedidos;

@SuppressWarnings("javadoc")
public class PageListPedidosStpV {

    public static void validateIsPage(String codigoPedido, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) La página contiene el bloque correspondiente a la lista de pedidos<br>" +
            "2) Figura la línea correspondiente al pedido " + codigoPedido;
        datosStep.setStateIniValidations();      
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageListPedidos.isPage(dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (!PageListPedidos.isVisibleCodPedido(codigoPedido, dFTest.driver)) {
                listVals.add(2, State.Info);
            }
                            
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep selectPedido(String codPedido, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep       (
            "Seleccionar el pedido de la lista <b>" + codPedido + "</b>", 
            "Apareca la página con los datos correctos del pedido");
        try {
            PageListPedidos.selectLineaPedido(codPedido, dFTest.driver);
                                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        return datosStep;
    }
}
