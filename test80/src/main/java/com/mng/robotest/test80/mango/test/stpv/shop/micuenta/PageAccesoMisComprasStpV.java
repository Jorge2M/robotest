package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras.TypeBlock;
import com.mng.robotest.test80.mango.test.stpv.shop.pedidos.PageDetallePedidoStpV;

@SuppressWarnings("javadoc")
public class PageAccesoMisComprasStpV {
    
    public static void validateIsPage(DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones.
        String descripValidac = 
            "1) Aparece la página de \"Acceso a Mis Compras\"<br>" +
            "2) Aparecen el bloque \"Ya estoy registrado\"<br>" +
            "3) Aparece el bloque de \"No estoy registrado\"";
        datosStep.setStateIniValidations();      
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageAccesoMisCompras.isPage(dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!PageAccesoMisCompras.isPresentBlock(TypeBlock.SiRegistrado, dFTest.driver)) {
                listVals.add(2, State.Warn);
            }
            if (!PageAccesoMisCompras.isPresentBlock(TypeBlock.NoRegistrado, dFTest.driver)) {
                listVals.add(3, State.Warn);
            }
        
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void clickBlock(TypeBlock typeBlock, DataFmwkTest dFTest) {
        //Step.
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el bloque \"" + typeBlock + "\"", 
            "Se hace visible el bloque de " + typeBlock);
        try {
            PageAccesoMisCompras.clickBlock(typeBlock, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validation
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Se hace visible el bloque de \"" + typeBlock + "\" (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setStateIniValidations();      
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!PageAccesoMisCompras.isVisibleBlockUntil(typeBlock, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static DatosStep enterForSiRegistrado(String usuario, String password, DataFmwkTest dFTest) throws Exception {
        //Step.
        DatosStep datosStep = new DatosStep     (
            "En el bloque de \"Si Registrado\", introducir el usuario/password (" + usuario + "/" + password + ") y pulsar \"Entrar\"", 
            "Aparece la página de \"Mis compras\"");
        try {
            PageAccesoMisCompras.inputUserPasswordBlockSi(usuario, password, dFTest.driver); 
            PageAccesoMisCompras.clickEntrarBlockSi(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validation
        PageMisComprasStpV.validateIsPage(datosStep, dFTest);
        
        return datosStep;
    }
    
    public static DatosStep buscarPedidoForNoRegistrado(DataPedido dataPedido, AppEcom app, DataFmwkTest dFTest) throws Exception {
        //Step.
        String usuario = dataPedido.getEmailCheckout();
        DatosStep datosStep = new DatosStep     (
            "En el bloque de \"No Registrado\", introducir el usuario/núm pedido (" + usuario + "/" + dataPedido.getCodpedido() + ") y pulsar \"Buscar pedido\"", 
            "Aparece la página de detalle del pedido");
        try {
            PageAccesoMisCompras.inputUserAndNumPedidoBlockNo(usuario, dataPedido.getCodpedido(), dFTest.driver); 
            PageAccesoMisCompras.clickBuscarPedidoBlockNo(dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validation
        PageDetallePedidoStpV pageDetPedidoStpV = new PageDetallePedidoStpV(dFTest.driver);
        pageDetPedidoStpV.validateIsPageOk(dataPedido, app, datosStep, dFTest);
        
        return datosStep;        
    }
}
