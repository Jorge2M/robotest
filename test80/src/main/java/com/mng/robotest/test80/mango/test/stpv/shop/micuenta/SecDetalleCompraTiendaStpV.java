package com.mng.robotest.test80.mango.test.stpv.shop.micuenta;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.CompraTienda;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageMisCompras;

@SuppressWarnings("javadoc")
public class SecDetalleCompraTiendaStpV {
    @SuppressWarnings("static-access")
    public static void validateIsOk(CompraTienda compraTienda, Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validaciones
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) Es visible la capa correspondiente al detalle del tícket de compra (la esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Figura un número de tícket " + compraTienda.idCompra + "<br>" +
            "3) Figura el importe " + compraTienda.importe + "<br>" +
            "4) Figura la dirección " + compraTienda.direccion + "<br>" +
            "5) Figura la fecha " + compraTienda.fecha + "<br>" +
            "6) Existen " + compraTienda.numPrendas + " prendas";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!PageMisCompras.SecDetalleCompraTienda.isVisibleSectionUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (PageMisCompras.SecDetalleCompraTienda.getNumTicket(dFTest.driver).compareTo(compraTienda.idCompra)!=0)
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (PageMisCompras.SecDetalleCompraTienda.getImporte(dFTest.driver).compareTo(compraTienda.importe)!=0)
                fmwkTest.addValidation(3, State.Warn, listVals);
            //4)
            if (PageMisCompras.SecDetalleCompraTienda.getDireccion(dFTest.driver).compareTo(compraTienda.direccion)!=0)
                fmwkTest.addValidation(4, State.Warn, listVals);
            //5)
            if (PageMisCompras.SecDetalleCompraTienda.getFecha(channel, dFTest.driver).compareTo(compraTienda.fecha)!=0)
                fmwkTest.addValidation(5, State.Warn, listVals);
            //6)
            if (PageMisCompras.SecDetalleCompraTienda.getNumPrendas(dFTest.driver)!=compraTienda.numPrendas)
                fmwkTest.addValidation(6, State.Warn, listVals);
        
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        if (channel==Channel.movil_web) {
            descripValidac = 
                "1) Aparece la imagen correspondiente al código de barras de la compra";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
            try {
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!PageMisCompras.SecDetalleCompraTienda.isVisibleCodigoBarrasImg(dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        }
    }
    
    @SuppressWarnings("static-access")
    public static DatosStep selectArticulo(int posArticulo, DataFmwkTest dFTest) {
        //Step.
        ArticuloScreen articulo = null;
        DatosStep datosStep = new DatosStep     (
            "Seleccionar el " + posArticulo + "o artículo de la Compra", 
            "Aparece la sección correspondiente al \"QuickView\" del artículo");
        try {
            articulo = PageMisCompras.SecDetalleCompraTienda.getDataArticulo(posArticulo, dFTest.driver);
            PageMisCompras.SecDetalleCompraTienda.selectArticulo(posArticulo, dFTest.driver);
                
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validaciones
        PageMisComprasStpV.SecQuickViewArticulo.validateIsOk(articulo, datosStep, dFTest);
        
        return datosStep;
    }
}
