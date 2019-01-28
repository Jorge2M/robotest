package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.controlTest.SimpleValidation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypeStatePanel;

@SuppressWarnings("javadoc")
public class SecProductDescrOldStpV {
    
    public static void validateAreInStateInitial(AppEcom appE, DatosStep datosStep, DataFmwkTest dFTest) throws Exception {
        String descripValidac = "";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);
        try {               
            List<SimpleValidation> listVals = new ArrayList<>();
            //1 to X)
            int validacion = 1;
            for (TypePanel typePanel : TypePanel.values()) {
                TypeStatePanel stateExpected = TypeStatePanel.missing;
                if (typePanel.getListApps().contains(appE))
                    stateExpected = typePanel.getStateInitial();                        
                
                descripValidac+=(validacion + ") El panel <b>" + typePanel + "</b> está en estado <b>" + stateExpected + "</b><br>");
                validacion+=1;
                if (SecProductDescrOld.getStatePanel(typePanel, dFTest.driver)!=stateExpected)
                    fmwkTest.addValidation(validacion, State.Defect, listVals);
            }
                    
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
    
    public static void selectPanel(TypePanel typePanel, DataFmwkTest dFTest) throws Exception {
        //Step.
        TypeStatePanel statePanelIni = SecProductDescrOld.getStatePanel(typePanel, dFTest.driver);
        TypeStatePanel stateExpectedAfterClick = SecProductDescrOld.getStatePanelAfterClick(statePanelIni);
        DatosStep datosStep = new DatosStep (
            "Seleccionar el panel <b>" + typePanel + "</b> (en estado inicial: " + statePanelIni + ")",
            "La pestaña queda en estado " + stateExpectedAfterClick);
        try {
            SecProductDescrOld.clickPanel(typePanel, dFTest.driver);
                        
            datosStep.setExcepExists(false); datosStep.setResultSteps(State.Ok);
        }
        finally { datosStep.setStepNumber(fmwkTest.grabStep(datosStep, dFTest)); }
                
        //Validaciones
        int maxSecondsToWait = 1;
        String descripValidac = 
            "1) La sección ha de quedar en estado <b>" + stateExpectedAfterClick + "</b> (lo esperamos hasta " + maxSecondsToWait + " segundos)";
        datosStep.setExcepExists(true); datosStep.setResultSteps(State.Nok);               
        try {
            List<SimpleValidation> listVals = new ArrayList<>();
            //1)
            if (!SecProductDescrOld.isPanelInStateUntil(typePanel, stateExpectedAfterClick, maxSecondsToWait, dFTest.driver))
                fmwkTest.addValidation(1, State.Defect, listVals);
            
            datosStep.setExcepExists(false); datosStep.setResultSteps(listVals);
        }  
        finally { fmwkTest.grabStepValidation(datosStep, descripValidac, dFTest); }
    }
}