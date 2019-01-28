package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;

@SuppressWarnings("javadoc")
public class ModalDroppointsStpV {
    
    public static SecSelectDPointStpV secSelectDPoint;
    public static SecConfirmDatosStpV secConfirmDatos;
    
    @SuppressWarnings("static-access")
    public static void validaIsVisible(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        //Validación
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Desaparece el mensaje de \"Cargando...\" (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) Aparece un 1er Droppoint visible (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "3) Sí aparece el modal con el mapa de Droppoints";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDroppoints.isInvisibleCargandoMsgUntil(maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Warn, listVals);
            //2)
            if (!ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(2, State.Info, listVals);
            //3)
            if (!ModalDroppoints.isVisible(channel, dFTest.driver))
                fmwkTest.addValidation(3, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void validaIsNotVisible(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) No aparece el modal con el mapa de Droppoints";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (ModalDroppoints.isVisible(channel, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    @SuppressWarnings("static-access")
    public static void fluxSelectDroppoint(DataCtxPago dCtxPago, DataCtxShop dCtxSh, DataFmwkTest dFTest) throws Exception {
        Pago pago = dCtxPago.getDataPedido().getPago();
    	DataSearchDeliveryPoint dataSearchDp = DataSearchDeliveryPoint.getInstance(pago, dCtxSh.appE, dCtxSh.pais);
        secSelectDPoint.searchPoblacion(dataSearchDp, dFTest);
        DataDeliveryPoint dataDp = ModalDroppointsStpV.secSelectDPoint.clickDeliveryPointAndGetData(2/*position*/, dFTest);
        dCtxPago.getDataPedido().setTypeEnvio(pago.getTipoEnvioType(dCtxSh.appE));
        dCtxPago.getDataPedido().setDataDeliveryPoint(dataDp);
        secSelectDPoint.clickSelectButton(dCtxSh.channel, dFTest);
        secConfirmDatos.clickConfirmarDatosButton(dCtxSh.channel, dCtxPago.getDataPedido(), dFTest);                
    }
}
