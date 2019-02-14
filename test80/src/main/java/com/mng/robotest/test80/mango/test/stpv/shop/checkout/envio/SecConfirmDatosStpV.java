package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;

@SuppressWarnings({"javadoc", "static-access"})
public class SecConfirmDatosStpV {
    
    public static void validateIsVisible(Channel channel, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Es visible la capa de confirmación de los datos (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDroppoints.secConfirmDatos.isVisibleUntil(maxSecondsToWait, channel, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                          
          datosStep.setListResultValidations(listVals);
      }
      finally { listVals.checkAndStoreValidations(descripValidac); }        
    }
    
    public static DatosStep clickConfirmarDatosButton(Channel channel, DataPedido dataPedido, DataFmwkTest dFTest) throws Exception {
        //Step
        DatosStep datosStep = new DatosStep     (
            "Clickamos el botón \"Confirmar Datos\"", 
            "La dirección de envío se establece a la de la tienda");
        try {
            ModalDroppoints.secConfirmDatos.clickConfirmarDatosButtonAndWait(5/*maxSecondsToWait*/, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver, 2/*waitSeconds*/);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }        
        
        //Validaciones
        DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
        String descripValidac = 
            "1) Desaparece la capa de Droppoints<br>" +
            "2) Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")<br>" +
            "3) Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (ModalDroppoints.isVisible(channel, dFTest.driver)) {
                listVals.add(1, State.Warn);
            }
            
            String textDireccionEnvioCompleta = PageCheckoutWrapper.getTextDireccionEnvioCompleta(channel, dFTest.driver);
            dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
            if (!textDireccionEnvioCompleta.contains(dataDp.getDireccion())) {
                listVals.add(2, State.Defect);
            }
            if (!textDireccionEnvioCompleta.contains(dataDp.getCPandPoblacion())) {
                listVals.add(3, State.Defect);
            }
            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return datosStep;
    }
}
