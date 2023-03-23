package com.mng.robotest.domains.galeria.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.galeria.pageobjects.ModalArticleNotAvailable;
import com.mng.robotest.domains.galeria.pageobjects.ModalArticleNotAvailable.StateModal;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalArticleNotAvailableSteps extends StepBase {

	private final ModalArticleNotAvailable modalArticleNotAvailable = new ModalArticleNotAvailable();
	
	@Validation (
		description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} (lo esperamos #{seconds} segundos)",
		level=Info)
	public boolean validateState(int seconds, StateModal stateModal) {
		return modalArticleNotAvailable.inStateUntil(stateModal, seconds);
	}
	
	@Step (
		description="Seleccionamos el aspa del modal para cerrarlo", 
		expected="El modal queda en estado No-visible")
	public void clickAspaForClose() {
		modalArticleNotAvailable.clickAspaForClose();
		validateState(1, StateModal.NOT_VISIBLE);
	}

	@Validation
	public ChecksTM checkVisibleAvisame() {
		var checks = ChecksTM.getNew();
		boolean isVisibleModal = modalArticleNotAvailable.isVisibleUntil(2);
		boolean isVisibleRPGD = modalArticleNotAvailable.isVisibleRPGD(2);
		checks.add(
			"Si aparece el modal de avisame",
			isVisibleModal, Defect);
		
		checks.add(
			"Si aparece la descripcion de RPGD de usuario",
			isVisibleRPGD, Defect);
		
		return checks;
	}
	
	@Step (
		description="Introducimos el mail #{mail} y pulsamos el botón <b>Recibir Aviso</b>", 
		expected="Aparece el modal de Petición confirmada")
	public void inputMailAndClickRecibirAviso(String mail) {
		modalArticleNotAvailable.inputMail(mail);
		modalArticleNotAvailable.clickRecibirAviso();
		checkModalAvisoOkVisible(2);
	}
	
	@Validation (
		description="Aparece el modal de petición confirmada OK (lo esperamos #{seconds} segundos)",
		level=Defect)
	public boolean checkModalAvisoOkVisible(int seconds) {
		return modalArticleNotAvailable.isModalAvisoOkVisible(seconds);
	}
	
	@Step (
		description="Seleccionamos el botón <b>Entendido</b>", 
		expected="Desaparece el modal de petición confirmada OK")
	public void clickButtonEntendido() {
		modalArticleNotAvailable.clickButtonEntendido();
		checkModalAvisoOkInvisible(1);
	}
	
	@Validation (
		description="Desaparece el modal de petición confirmada OK (lo esperamos #{seconds} segundos)",
		level=Defect)	
	public boolean checkModalAvisoOkInvisible(int seconds) {
		return modalArticleNotAvailable.isModalAvisoOkInvisible(seconds);
	}	
	
}
