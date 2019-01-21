package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataPedido;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;

@SuppressWarnings({"javadoc", "static-access"})
public class SecConfirmDatosStpV {
    
    public static void validateIsVisible(Channel channel, datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Es visible la capa de confirmación de los datos (la esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDroppoints.secConfirmDatos.isVisibleUntil(maxSecondsToWait, channel, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
                          
          datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
      }
      finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }        
    }
    
    public static datosStep clickConfirmarDatosButton(Channel channel, DataPedido dataPedido, DataFmwkTest dFTest) throws Exception {
        //Step
        datosStep datosStep = new datosStep     (
            "Clickamos el botón \"Confirmar Datos\"", 
            "La dirección de envío se establece a la de la tienda");
        try {
            ModalDroppoints.secConfirmDatos.clickConfirmarDatosButtonAndWait(5/*maxSecondsToWait*/, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver, 2/*waitSeconds*/);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        
        
        //Validaciones
        DataDeliveryPoint dataDp = dataPedido.getDataDeliveryPoint();
        String descripValidac = 
            "1) Desaparece la capa de Droppoints<br>" +
            "2) Se modifica la dirección de envío por la del Delivery Point (" + dataDp.getDireccion() + ")<br>" +
            "3) Se modifica el código postal de envío por el del Delivery Point (" + dataDp.getCPandPoblacion() + ")";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (ModalDroppoints.isVisible(channel, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Warn, listVals);
            
            String textDireccionEnvioCompleta = PageCheckoutWrapper.getTextDireccionEnvioCompleta(channel, dFTest.driver);
            dataPedido.setDireccionEnvio(textDireccionEnvioCompleta);
            //2)
            if (!textDireccionEnvioCompleta.contains(dataDp.getDireccion()))
                fmwkTest.addValidation(2, State.Defect, listVals);
            //3)
            if (!textDireccionEnvioCompleta.contains(dataDp.getCPandPoblacion()))
                fmwkTest.addValidation(3, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return datosStep;
    }
}
