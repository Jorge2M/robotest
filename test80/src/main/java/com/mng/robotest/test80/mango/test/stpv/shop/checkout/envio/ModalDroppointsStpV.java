package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
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
        datosStep.setStateIniValidations();     
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDroppoints.isInvisibleCargandoMsgUntil(maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            if (!ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1, maxSecondsToWait, dFTest.driver)) {
                listVals.add(2, State.Info);
            }
            if (!ModalDroppoints.isVisible(channel, dFTest.driver)) {
                listVals.add(3, State.Defect);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static void validaIsNotVisible(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        String descripValidac = 
            "1) No aparece el modal con el mapa de Droppoints";
        datosStep.setStateIniValidations();   
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (ModalDroppoints.isVisible(channel, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }        
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
