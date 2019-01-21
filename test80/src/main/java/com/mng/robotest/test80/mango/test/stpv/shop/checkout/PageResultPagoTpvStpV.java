package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPagoTpv;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageResultPagoTpvStpV {
    
    public static void validateIsPageOk(DataPedido dataPedido, String codPais, datosStep datosStep, DataFmwkTest dFTest) {
        String importeTotal = dataPedido.getImporteTotal();
        String tagPedido = "[PEDIDO]";
        String codigoPed = "";
        String descripValidac = 
            "1) Aparece un texto de confirmación de la compra<br>" +
            "2) Aparece el importe " + importeTotal + " de la operación<br>" +
            "3) Aparece el código de pedido (" + tagPedido + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);             
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageResultPagoTpv.isPresentCabeceraConfCompra(dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (!PageResultPagoTpv.isVisibleCodPedido(dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
            else
                codigoPed = PageResultPagoTpv.getCodigoPedido(dFTest.driver);

            dataPedido.setCodpedido(codigoPed); 
            dataPedido.setResejecucion(State.Ok);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}
