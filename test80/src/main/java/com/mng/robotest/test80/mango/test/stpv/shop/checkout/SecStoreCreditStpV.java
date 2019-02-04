package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen; 

@SuppressWarnings({"javadoc", "static-access"})
public class SecStoreCreditStpV { 
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    public static DatosStep validateInitialStateOk(Channel channel, DataCtxPago dCtxPago, DataFmwkTest dFTest) 
    throws Exception {
        String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(channel);
        DatosStep datosStep = new DatosStep (
            "Revisamos el bloque de \"Saldo en cuenta\"", 
            "Sólo aparece el método de pago " + nombrePago);
        try {
            //No hacemos nada...
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
            dCtxPago.getDataPedido().setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, dFTest.driver));
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                    
        //Validaciones (bloque "Saldo en cuenta" marcado)
        validaBloqueSaldoEnCuenta(datosStep, true/*marcado*/, channel, dCtxPago, dFTest);
        
        return datosStep;
    }

    public static DatosStep selectSaldoEnCuentaBlock(Pais pais, DataCtxPago dCtxPago, AppEcom app, Channel channel, DataFmwkTest dFTest) 
    throws Exception {
        boolean marcadoInicialmente = Page1DktopCheckout.secStoreCredit.isChecked(dFTest.driver);
        DatosStep datosStep = new DatosStep (
            "Seleccionamos el bloque de \"Saldo en cuenta\"", 
            "Aparecen el resto de métodos de pago");
        try {
            Page1DktopCheckout.secStoreCredit.selectSaldoEnCuenta(dFTest.driver);
                                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                            
        PageCheckoutWrapperStpV.validateLoadingDisappears(datosStep, dFTest);
        validaBloqueSaldoEnCuenta(datosStep, !marcadoInicialmente, channel, dCtxPago, dFTest);
    
        if (marcadoInicialmente) {
            //Validaciones saldo cuenta Desmarcado -> validamos los métodos de pago disponibles
            boolean isEmpl = dCtxPago.getFTCkout().isEmpl;
            PageCheckoutWrapperStpV.validaMetodosPagoDisponibles(datosStep, pais, isEmpl, app, channel, dFTest);
            
            //Almacenamos el importe total
            if (channel==Channel.desktop)
                dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, dFTest.driver));
        }
        else {
            //Validaciones saldo cuenta Marcado -> No han de aparecer los métodos de pago
            int numPagosExpected = 0;
            String descripValidac = 
                "1) Aparecen " + numPagosExpected + " métodos de pago";
            datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);                 
            try { 
                List<SimpleValidation> listVals = new ArrayList<>();
                //1)
                if (!PageCheckoutWrapper.isNumpagos(numPagosExpected, channel, pais, dFTest.driver))
                    fmwkTest.addValidation(1, State.Warn, listVals);
                                                
                datosStep.setExcepExists(false); datosStep.setResultSteps(listVals); 
            }
            finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest);  }
 
            //Almacenamos el importe total
            if (channel==Channel.movil_web)
                dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, dFTest.driver));
 
        }
        
        return datosStep;
    }
    

    /**
     * Valida que aparezca un bloque válido correspondiente al pago con "Saldo en Cuenta"
     */
    public static void validaBloqueSaldoEnCuenta(DatosStep datosStep, boolean marcado, Channel channel, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        float saldoCta = dCtxPago.getSaldoCta();
        //DataBag dataBag = dCtxPago.getDataPedido().getDataBag(); 
        //float importeSubTotal = dataBag.getImporteTotalFloat();
        //float importeTransp = dataBag.getImporteTranspFloat();
        //float importeTotal = importeSubTotal + importeTransp;
        
        //Validaciones
        String validacion2 = "";
        String validacion3 = "";
        String validacion4 = "";
        if (marcado) {
            validacion2 = "2) SÍ está marcado el radio del bloque de \"Saldo en cuenta\"<br>";
            validacion3 = "3) NO aparece el link para desplegar los métodos de pago<br>";
            if (channel==Channel.desktop) {
                validacion4 = "4) Figura un importe total de 0<br>";
            }
        }
        else {
            validacion2 = "2) NO está marcado el radio del bloque de \"Saldo en cuenta\"<br>";
            validacion3 = "3) SÍ aparece el link para desplegar los métodos de pago<br>";
            validacion4 = "4) No figura un importe total de 0 <br>";
        }
        
        String descripValidac = 
            "1) Es visible el bloque correspondiente al pago mediante \"Saldo en cuenta\"<br>" +
            validacion2 +
            validacion3 +
            validacion4 +
            "5) Figura un saldo en cuenta de: " + saldoCta;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!Page1DktopCheckout.secStoreCredit.isVisible(dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (marcado && !Page1DktopCheckout.secStoreCredit.isChecked(dFTest.driver) ||
                !marcado && Page1DktopCheckout.secStoreCredit.isChecked(dFTest.driver)) 
                fmwkTest.addValidation(2, State.Warn, listVals);
            //3)
            if (marcado && !Page1DktopCheckout.secStoreCredit.isChecked(dFTest.driver) ||
                !marcado && Page1DktopCheckout.secStoreCredit.isChecked(dFTest.driver)) 
                fmwkTest.addValidation(3,State.Warn, listVals);
            //4)
            if (!marcado || channel==Channel.desktop) {
                String impTotResumen = PageCheckoutWrapper.getPrecioTotalFromResumen(channel, dFTest.driver);
                float impFloat = ImporteScreen.getFloatFromImporteMangoScreen(impTotResumen);
                if ((marcado && impFloat!=0.0) ||
                    (!marcado && impFloat==0.0))
                    fmwkTest.addValidation(4, State.Warn, listVals);
            }
            //5)
            if (Page1DktopCheckout.secStoreCredit.getImporte(dFTest.driver) != saldoCta)
                fmwkTest.addValidation(5,State.Warn, listVals);
    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        catch (Exception e) {
            pLogger.warn("Problem validating Block Checkout StoreCredit", e);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}