package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageInputPedido;

@SuppressWarnings("javadoc")
public class PageInputPedidoStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) La página contiene un campo para la introducción del Nº de pedido";
        datosStep.setNOKstateByDefault();       
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try { 
            if (!PageInputPedido.isVisibleInputPedido(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
                            
            datosStep.setListResultValidations(listVals); 
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DatosStep inputPedidoAndSubmit(DataPedido dataPedido, AppEcom app, DataFmwkTest dFTest) 
    throws Exception {
        //Step
        String usuarioAcceso = dataPedido.getEmailCheckout();
        String codPedido = dataPedido.getCodpedido();
        DatosStep datosStep = new DatosStep (
            "Buscar el pedido <b>" + codPedido + "</b> introduciendo email + nº pedido</b>", 
            "Apareca la página con los datos correctos del pedido");
        try {
            PageInputPedido.inputEmailUsr(usuarioAcceso, dFTest.driver);
            PageInputPedido.inputPedido(codPedido, dFTest.driver);
            PageInputPedido.clickRecuperarDatos(dFTest.driver);
                                                                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validaciones
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(dFTest.driver);
        pageDetPedidoStpV.validateIsPageOk(dataPedido, app, datosStep, dFTest);
        
        return datosStep;
    }
}
