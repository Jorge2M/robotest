package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.DatosStep;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypeStatePanel;

public class SecProductDescrOldStpV {
    
	@Validation
    public static void validateAreInStateInitial(AppEcom appE, WebDriver driver) throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
    	for (TypePanel typePanel : TypePanel.values()) {
    		TypeStatePanel stateExpected = TypeStatePanel.missing;
            if (typePanel.getListApps().contains(appE)) {
                stateExpected = typePanel.getStateInitial();
            }
	      	validations.add(
	    		"El panel <b>" + typePanel + "</b> está en estado <b>" + stateExpected + "</b>",
	    		SecProductDescrOld.getStatePanel(typePanel, driver)==stateExpected, State.Defect);
    	}
    }
    
	final static String tagInitStatePanel = "@TagInitState";
	final static String tagFinalStateExpected = "@TagFinalState";
	@Step (
		description="Seleccionar el panel <b>#{typePanel}</b> (en estado inicial: " + tagInitStatePanel + ")",
        expected="La pestaña queda en estado " + tagFinalStateExpected)
    public static void selectPanel(TypePanel typePanel, WebDriver driver) throws Exception {
        TypeStatePanel statePanelIni = SecProductDescrOld.getStatePanel(typePanel, driver);
        TypeStatePanel stateExpectedAfterClick = SecProductDescrOld.getStatePanelAfterClick(statePanelIni);
        DatosStep datosStep = TestCaseData.getDatosCurrentStep();
        datosStep.replaceInDescription(tagInitStatePanel, statePanelIni.toString());
        datosStep.replaceInExpected(tagFinalStateExpected, stateExpectedAfterClick.toString());
        
        SecProductDescrOld.clickPanel(typePanel, driver);
        int maxSecondsWait = 1;
        checkPanelInState(typePanel, stateExpectedAfterClick, maxSecondsWait, driver);
    }
	
	@Validation (
		description="La sección ha de quedar en estado <b>#{stateExpectedAfterClick}</b> (lo esperamos hasta #{maxSecondsWait} segundos)",
		level=State.Defect)
	private static boolean checkPanelInState(TypePanel typePanel, TypeStatePanel stateExpectedAfterClick, int maxSecondsWait, WebDriver driver) 
	throws Exception {
	    return (SecProductDescrOld.isPanelInStateUntil(typePanel, stateExpectedAfterClick, maxSecondsWait, driver));
	}
}