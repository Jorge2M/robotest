package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrDevice;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrDevice.TypePanel;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrDevice.TypeStatePanel;

public class SecProductDescrDeviceSteps {
	
	private final SecProductDescrDevice secProductDescr = new SecProductDescrDevice();
	
	@Validation
	public ChecksTM validateAreInStateInitial(AppEcom appE) {
		var checks = ChecksTM.getNew();
		for (TypePanel typePanel : TypePanel.values()) {
			TypeStatePanel stateExpected = TypeStatePanel.MISSING;
			if (typePanel.getListApps().contains(appE)) {
				stateExpected = typePanel.getStateInitial();
			}
		  	checks.add(
				"El panel <b>" + typePanel + "</b> está en estado <b>" + stateExpected + "</b>",
				secProductDescr.getStatePanel(typePanel)==stateExpected);
		}
		return checks;
	}
	
	private static final String TAG_INIT_STATE_PANEL = "@TagInitState";
	private static final String TAG_FINAL_STATE_EXPECTED = "@TagFinalState";
	@Step (
		description="Seleccionar el panel <b>#{typePanel}</b> (en estado inicial: " + TAG_INIT_STATE_PANEL + ")",
		expected="La pestaña queda en estado " + TAG_FINAL_STATE_EXPECTED)
	public void selectPanel(TypePanel typePanel) {
		TypeStatePanel statePanelIni = secProductDescr.getStatePanel(typePanel);
		TypeStatePanel stateExpectedAfterClick = secProductDescr.getStatePanelAfterClick(statePanelIni);
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(TAG_INIT_STATE_PANEL, statePanelIni.toString());
		step.replaceInExpected(TAG_FINAL_STATE_EXPECTED, stateExpectedAfterClick.toString());
		
		secProductDescr.clickPanel(typePanel);
		int seconds = 1;
		checkPanelInState(typePanel, stateExpectedAfterClick, seconds);
	}
	
	@Validation (
		description="La sección ha de quedar en estado <b>#{stateExpectedAfterClick}</b> (lo esperamos hasta #{seconds} segundos)")
	private boolean checkPanelInState(TypePanel typePanel, TypeStatePanel stateExpectedAfterClick, int seconds) {
		return (secProductDescr.isPanelInStateUntil(typePanel, stateExpectedAfterClick, seconds));
	}
}