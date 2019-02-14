package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecSelectDPoint.TypeDeliveryPoint;

@SuppressWarnings({"javadoc", "static-access"})
public class SecSelectDPointStpV {
    
    public static DatosStep searchPoblacion(DataSearchDeliveryPoint dataSearchDp, DataFmwkTest dFTest) 
    throws Exception {
        //Step
    	TypeDeliveryPoint typeDp = dataSearchDp.tipoTransporte.getTypeDeliveryPoint();
        DatosStep datosStep = new DatosStep     (
            "Introducimos la provincia <b>" + dataSearchDp.data + "</b> + Return", 
            "Aparecen puntos de recogida de " + dataSearchDp.data);
        try {
            ModalDroppoints.secSelectDPoint.sendProvincia(dataSearchDp.data, dFTest.driver);
            WebdrvWrapp.waitForPageLoaded(dFTest.driver, 5/*maxSecondsToWait*/);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }        

        //Validación-2
        int maxSecondsToWait = 5;
        String descripValidac = 
            "1) La dirección del droppoint seleccionado contiene <b>" + dataSearchDp.data + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDroppoints.secSelectDPoint.deliveryPointSelectedContainsPoblacionUntil(dataSearchDp, maxSecondsToWait, dFTest.driver)) {
            	switch (dataSearchDp.typeData) {
            	case Provincia:
            		listVals.add(1, State.Warn);
            		break;
            	case CodigoPostal:
            		listVals.add(1, State.Info);
            		break;
            	}
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        //Validación-1
        validaDeliveryPointOfType(typeDp, datosStep, dFTest);
        
        return datosStep;
    }
    
    public static void validaDeliveryPointOfType(TypeDeliveryPoint typeDp, DatosStep datosStep, DataFmwkTest dFTest) {
        int maxSecondsToWait = 3;
        String descripValidac = 
            "1) Es visible el 1er delivery point de la lista (lo esperamos hasta " + maxSecondsToWait + " segundos)<br>" +
            "2) El 1er delivery point de la lista es de tipo <b>" + typeDp + "</b>";
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1, maxSecondsToWait, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
            if (ModalDroppoints.secSelectDPoint.getTypeDeliveryPoint(1, dFTest.driver)!=typeDp) {
                listVals.add(2, State.Defect);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
    }
    
    public static DataDeliveryPoint clickDeliveryPointAndGetData(int position, DataFmwkTest dFTest) throws Exception {
        DataDeliveryPoint dataDpToReturn = null;
        DatosStep datosStep = new DatosStep     (
            "Clickamos en el <b>#" + position + "</b> droppoint", 
            "El droppoint queda seleccionado");
        try {
            dataDpToReturn = ModalDroppoints.secSelectDPoint.clickDeliveryPointAndGetData(position, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        String descripValidac = 
            "1) Queda seleccionado el Droppoint #" + position;
        datosStep.setNOKstateByDefault();
        ListResultValidation listVals = ListResultValidation.getNew(datosStep);
        try {
            if (!ModalDroppoints.secSelectDPoint.isDroppointSelected(position, dFTest.driver)) {
                listVals.add(1, State.Defect);
            }
                            
            datosStep.setListResultValidations(listVals);
        }
        finally { listVals.checkAndStoreValidations(descripValidac); }
        
        return dataDpToReturn;
    }
    
    public static void clickSelectButton(Channel channel, DataFmwkTest dFTest) throws Exception {
        DatosStep datosStep = new DatosStep     (
            "Clickamos el botón de \"Select\" de la capa de Droppoints", 
            "Desaparece al capa de droppoint");
        try {
            ModalDroppoints.secSelectDPoint.clickSelectButtonAndWait(5/*maxSecondsToWait*/, dFTest.driver);
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { fmwkTest.grabStep(datosStep, dFTest); }
        
        //Validación
        ModalDroppointsStpV.secConfirmDatos.validateIsVisible(channel, datosStep, dFTest);
    }
}
