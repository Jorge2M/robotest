package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypeStatePanel;

public class SecProductDescrOldStpV {
    
	@Validation
    public static ChecksTM validateAreInStateInitial(AppEcom appE, WebDriver driver) throws Exception {
    	ChecksTM validations = ChecksTM.getNew();
    	for (TypePanel typePanel : TypePanel.values()) {
    		TypeStatePanel stateExpected = TypeStatePanel.missing;
            if (typePanel.getListApps().contains(appE)) {
                stateExpected = typePanel.getStateInitial();
            }
	      	validations.add(
	    		"El panel <b>" + typePanel + "</b> está en estado <b>" + stateExpected + "</b>",
	    		SecProductDescrOld.getStatePanel(typePanel, driver)==stateExpected, State.Defect);
    	}
    	return validations;
    }
    
	final static String tagInitStatePanel = "@TagInitState";
	final static String tagFinalStateExpected = "@TagFinalState";
	@Step (
		description="Seleccionar el panel <b>#{typePanel}</b> (en estado inicial: " + tagInitStatePanel + ")",
        expected="La pestaña queda en estado " + tagFinalStateExpected)
    public static void selectPanel(TypePanel typePanel, WebDriver driver) {
        TypeStatePanel statePanelIni = SecProductDescrOld.getStatePanel(typePanel, driver);
        TypeStatePanel stateExpectedAfterClick = SecProductDescrOld.getStatePanelAfterClick(statePanelIni);
        StepTM step = TestMaker.getCurrentStepInExecution();
        step.replaceInDescription(tagInitStatePanel, statePanelIni.toString());
        step.replaceInExpected(tagFinalStateExpected, stateExpectedAfterClick.toString());
        
        SecProductDescrOld.clickPanel(typePanel, driver);
        int maxSeconds = 1;
        checkPanelInState(typePanel, stateExpectedAfterClick, maxSeconds, driver);
    }
	
	@Validation (
		description="La sección ha de quedar en estado <b>#{stateExpectedAfterClick}</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private static boolean checkPanelInState(TypePanel typePanel, TypeStatePanel stateExpectedAfterClick, int maxSeconds, WebDriver driver) {
	    return (SecProductDescrOld.isPanelInStateUntil(typePanel, stateExpectedAfterClick, maxSeconds, driver));
	}
}