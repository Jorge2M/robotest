package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypeStatePanel;


public class SecProductDescrOldSteps {
	
	private final SecProductDescrOld secProductDescrOld;
	
	public SecProductDescrOldSteps(Channel channel, AppEcom app) {
		secProductDescrOld = new SecProductDescrOld(channel, app);
	}
	
	@Validation
	public ChecksTM validateAreInStateInitial(AppEcom appE) throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		for (TypePanel typePanel : TypePanel.values()) {
			TypeStatePanel stateExpected = TypeStatePanel.MISSING;
			if (typePanel.getListApps().contains(appE)) {
				stateExpected = typePanel.getStateInitial();
			}
		  	checks.add(
				"El panel <b>" + typePanel + "</b> está en estado <b>" + stateExpected + "</b>",
				secProductDescrOld.getStatePanel(typePanel)==stateExpected, State.Defect);
		}
		return checks;
	}
	
	static final String tagInitStatePanel = "@TagInitState";
	static final String tagFinalStateExpected = "@TagFinalState";
	@Step (
		description="Seleccionar el panel <b>#{typePanel}</b> (en estado inicial: " + tagInitStatePanel + ")",
		expected="La pestaña queda en estado " + tagFinalStateExpected)
	public void selectPanel(TypePanel typePanel) {
		TypeStatePanel statePanelIni = secProductDescrOld.getStatePanel(typePanel);
		TypeStatePanel stateExpectedAfterClick = secProductDescrOld.getStatePanelAfterClick(statePanelIni);
		StepTM step = TestMaker.getCurrentStepInExecution();
		step.replaceInDescription(tagInitStatePanel, statePanelIni.toString());
		step.replaceInExpected(tagFinalStateExpected, stateExpectedAfterClick.toString());
		
		secProductDescrOld.clickPanel(typePanel);
		int maxSeconds = 1;
		checkPanelInState(typePanel, stateExpectedAfterClick, maxSeconds);
	}
	
	@Validation (
		description="La sección ha de quedar en estado <b>#{stateExpectedAfterClick}</b> (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkPanelInState(TypePanel typePanel, TypeStatePanel stateExpectedAfterClick, int maxSeconds) {
		return (secProductDescrOld.isPanelInStateUntil(typePanel, stateExpectedAfterClick, maxSeconds));
	}
}