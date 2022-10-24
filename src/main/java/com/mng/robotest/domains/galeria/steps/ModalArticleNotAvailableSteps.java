package com.mng.robotest.domains.galeria.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.galeria.pageobjects.ModalArticleNotAvailable;
import com.mng.robotest.domains.galeria.pageobjects.ModalArticleNotAvailable.StateModal;
import com.mng.robotest.domains.transversal.StepBase;

public class ModalArticleNotAvailableSteps extends StepBase {

	private final ModalArticleNotAvailable modalArticleNotAvailable = new ModalArticleNotAvailable();
	
	@Validation (
		description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} (lo esperamos hasta #{seconds} segundos)",
		level=State.Info)
	public boolean validateState(int seconds, StateModal stateModal) throws Exception {
		return modalArticleNotAvailable.inStateUntil(stateModal, seconds);
	}
	
	@Step (
		description="Seleccionamos el aspa del modal para cerrarlo", 
		expected="El modal queda en estado No-visible")
	public void clickAspaForClose() throws Exception {
		modalArticleNotAvailable.clickAspaForClose();
		validateState(1, StateModal.notvisible);
	}

	@Validation
	public ChecksTM checkVisibleAvisame() throws Exception {
		ChecksTM checks = ChecksTM.getNew();
		boolean isVisibleModal = modalArticleNotAvailable.isVisibleUntil(2);
		boolean isVisibleRPGD = modalArticleNotAvailable.isVisibleRPGD(2);
		checks.add(
			"Si aparece el modal de avisame",
			isVisibleModal, State.Defect);
		
		checks.add(
			"Si aparece la descripcion de RPGD de usuario",
			isVisibleRPGD, State.Defect);
		
		return checks;
	}
}
