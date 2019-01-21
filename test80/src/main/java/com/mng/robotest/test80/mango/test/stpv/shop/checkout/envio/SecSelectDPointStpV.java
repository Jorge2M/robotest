package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.datosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;

@SuppressWarnings({"javadoc", "static-access"})
public class SecSelectDPointStpV {
    
    public static datosStep searchPoblacion(DataSearchDeliveryPoint dataSearchDp, DataFmwkTest dFTest) 
    throws Exception {
        //Step
    	TypeDeliveryPoint typeDp = dataSearchDp.tipoTransporte.getTypeDeliveryPoint();
        datosStep datosStep = new datosStep     (
            "Introducimos la provincia <b>" + dataSearchDp.data + "</b> + Return", 
            "Aparecen puntos de recogida de " + dataSearchDp.data);
        try {
            ModalDroppoints.secSelectDPoint.sendProvincia(dataSearchDp.data, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver, 5/*maxSecondsToWait*/);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }        

        //Validación-2
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) La dirección del droppoint seleccionado contiene <b>" + dataSearchDp.data + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDroppoints.secSelectDPoint.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, maxSecondsToWait, dFTest.driver)) {
            	switch (dataSearchDp.typeData) {
            	case Provincia:
            		fmwkTest.addValidation(1, State.Warn, listVals);
            		break;
            	case CodigoPostal:
            		fmwkTest.addValidation(1, State.Info, listVals);
            		break;
            	}
            }
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        //Validación-1
        validaDeliveryPointOfType(typeDp, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validaDeliveryPointOfType(TypeDeliveryPoint typeDp, datosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Es visible el 1er delivery point de la lista (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) El 1er delivery point de la lista es de tipo <b>" + typeDp + "</b>";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1/*position*/, 3/*maxSecondsToWait*/, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            //2)
            if (ModalDroppoints.secSelectDPoint.getTypeDeliveryPoint(1/*position*/, dFTest.driver)!=typeDp)
                fmwkTest.addValidation(2, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static DataDeliveryPoint clickDeliveryPointAndGetData(int position, DataFmwkTest dFTest) throws Exception {
        DataDeliveryPoint dataDpToReturn = null;
        datosStep datosStep = new datosStep     (
            "Clickamos en el <b>#" + position + "</b> droppoint", 
            "El droppoint queda seleccionado");
        try {
            dataDpToReturn = ModalDroppoints.secSelectDPoint.clickDeliveryPointAndGetData(position, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        String descripValidac = 
            "1) Queda seleccionado el Droppoint #" + position;
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!ModalDroppoints.secSelectDPoint.isDroppointSelected(position, dFTest.driver)) 
                fmwkTest.addValidation(1, State.Defect, listVals);
                            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
        
        return dataDpToReturn;
    }
    
    public static void clickSelectButton(Channel channel, DataFmwkTest dFTest) throws Exception {
        datosStep datosStep = new datosStep     (
            "Clickamos el botón de \"Select\" de la capa de Droppoints", 
            "Desaparece al capa de droppoint");
        try {
            ModalDroppoints.secSelectDPoint.clickSelectButtonAndWait(5/*maxSecondsToWait*/, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
        
        //Validación
        ModalDroppointsStpV.secConfirmDatos.validateIsVisible(channel, datosStep, dFTest);
    }
}
