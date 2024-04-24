package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice.TypePanel;
import com.mng.robotest.tests.domains.ficha.pageobjects.nogenesis.SecProductDescrDevice.TypeStatePanel;

public class SecProductDescrDeviceSteps extends StepBase {
	
	private final SecProductDescrDevice secProductDescr = new SecProductDescrDevice();
	
	@Validation
	public ChecksTM checkAreInStateInitial() {
		var checks = ChecksTM.getNew();
		for (var typePanel : TypePanel.values()) {
			var stateExpected = TypeStatePanel.MISSING;
			if (typePanel.getListApps().contains(app)) {
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
		var statePanelIni = secProductDescr.getStatePanel(typePanel);
		var stateExpectedAfterClick = secProductDescr.getStatePanelAfterClick(statePanelIni);
		replaceStepDescription(TAG_INIT_STATE_PANEL, statePanelIni.toString());
		replaceStepExpected(TAG_FINAL_STATE_EXPECTED, stateExpectedAfterClick.toString());
		
		secProductDescr.clickPanel(typePanel);
		checkPanelInState(typePanel, stateExpectedAfterClick, 1);
	}
	
	@Validation (
		description="La sección ha de quedar en estado <b>#{stateExpectedAfterClick}</b> " + SECONDS_WAIT)
	private boolean checkPanelInState(TypePanel typePanel, TypeStatePanel stateExpectedAfterClick, int seconds) {
		return secProductDescr.isPanelInStateUntil(typePanel, stateExpectedAfterClick, seconds);
	}
	
}