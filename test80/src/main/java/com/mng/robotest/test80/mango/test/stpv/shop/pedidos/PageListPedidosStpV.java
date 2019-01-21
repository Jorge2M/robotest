package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageListPedidos;

@SuppressWarnings("javadoc")
public class PageListPedidosStpV {

    public static void validateIsPage(String codigoPedido, datosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) La página contiene el bloque correspondiente a la lista de pedidos<br>" +
            "2) Figura la línea correspondiente al pedido " + codigoPedido;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageListPedidos.isPage(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (!PageListPedidos.isVisibleCodPedido(codigoPedido, dFTest.driver))
                fmwkTest.addValidation(2, State.Info, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static datosStep selectPedido(String codPedido, DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep       (
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
