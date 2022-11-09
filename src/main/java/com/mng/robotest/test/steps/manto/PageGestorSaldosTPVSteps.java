package com.mng.robotest.test.steps.manto;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.pageobject.manto.PageGestorSaldosTPV;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;


public class PageGestorSaldosTPVSteps extends StepBase {

	private final PageGestorSaldosTPV pageGestorSaldosTPV = new PageGestorSaldosTPV();
	
	@Validation
	public ChecksTM validateIsPage() {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Estamos en la página " + PageGestorSaldosTPV.TITULO,
			pageGestorSaldosTPV.isPage(), State.Defect);
	 	
	 	checks.add(
			"Aparece el input de fecha de TPV",
			pageGestorSaldosTPV.isVisibleTPVInput(), State.Defect);
	 	
	 	return checks;
	}
	
	@Step (
		description="Introducimos una TPV válida y damos click a \"Consultar Saldos\"",
		expected="Muestra la tabla de saldos con el ID de la TPV en ella",
		saveErrorData=SaveWhen.Never)
	public void searchValidTPV(String tpv) {
		pageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv);
		checkAfterConsultSaldosTpv(tpv);
	}
	
	@Validation
	private ChecksTM checkAfterConsultSaldosTpv(String tpv) {
		ChecksTM checks = ChecksTM.getNew();
	 	checks.add(
			"Aparece la tabla de saldos",
			pageGestorSaldosTPV.isTablaSaldosVisible(), State.Defect);
	 	checks.add(
			"Aparece el ID de la TPV <b>" + tpv + "</b> en la tabla",
			pageGestorSaldosTPV.isTPVIDVisible(tpv), State.Defect);
		return checks;
	}
	
	@Step (
		description="Introducimos una TPV no válida y damos click a \"Consultar Saldos\"",
		expected="Muestra el mensaje conforme la TPV no existe",
		saveErrorData=SaveWhen.Never)
	public void searchUnvalidTPV(String tpv) {
		pageGestorSaldosTPV.insertTPVAndClickConsultarSaldos(tpv);
		checkIsVisibleMsgTpvSelectedNotExists();
	}
	
	@Validation (
		description="Aparece el mensaje \"La tpv seleccionada no existe\"",
		level=State.Defect)
	private boolean checkIsVisibleMsgTpvSelectedNotExists() {
		return pageGestorSaldosTPV.isUnvalidTPVMessageVisible();
	}
}
