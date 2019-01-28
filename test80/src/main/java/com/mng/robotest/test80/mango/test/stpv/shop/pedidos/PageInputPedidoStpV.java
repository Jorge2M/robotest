package com.mng.robotest.test80.mango.test.stpv.shop.pedidos;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
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
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                
        try { 
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageInputPedido.isVisibleInputPedido(dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
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
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(dFTest.driver);
        pageDetPedidoStpV.validateIsPageOk(dataPedido, app, datosStep, dFTest);
        
        return datosStep;
    }
}
