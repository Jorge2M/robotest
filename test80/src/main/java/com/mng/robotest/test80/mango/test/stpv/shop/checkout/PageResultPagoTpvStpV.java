package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageResultPagoTpv;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

@SuppressWarnings("javadoc")
public class PageResultPagoTpvStpV {
    
    public static void validateIsPageOk(DataPedido dataPedido, String codPais, DatosStep datosStep, DataFmwkTest dFTest) {
        String importeTotal = dataPedido.getImporteTotal();
        String tagPedido = "[PEDIDO]";
        String codigoPed = "";
        String descripValidac = 
            "1) Aparece un texto de confirmación de la compra<br>" +
            "2) Aparece el importe " + importeTotal + " de la operación<br>" +
            "3) Aparece el código de pedido (" + tagPedido + ")";
        datosStep.setNOKstateByDefault();    
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageResultPagoTpv.isPresentCabeceraConfCompra(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ImporteScreen.isPresentImporteInScreen(importeTotal, codPais, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageResultPagoTpv.isVisibleCodPedido(dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
            else {
                codigoPed = PageResultPagoTpv.getCodigoPedido(dFTest.driver);
            }

            dataPedido.setCodpedido(codigoPed); 
            dataPedido.setResejecucion(State.Ok);
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
}
