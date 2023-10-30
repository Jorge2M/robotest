package com.mng.robotest.tests.domains.galeria.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.ModalArticleNotAvailable;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.ModalArticleNotAvailable.StateModal;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ModalArticleNotAvailableSteps extends StepBase {

	private final ModalArticleNotAvailable modalArticleNotAvailable = new ModalArticleNotAvailable();
	
	@Validation (
		description="El modal de \"Avísame\" por artículo no disponible está en estado #{stateModal} " + SECONDS_WAIT,
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
		int seconds = 2;
		checks.add(
			"Aparece el modal de avisame " + getLitSecondsWait(seconds),
			modalArticleNotAvailable.isVisibleUntil(seconds));

		checks.add(
			"Aparece el input para la introducción del email " + getLitSecondsWait(seconds),
			modalArticleNotAvailable.isVisibleInputEmail(seconds));		
		
		checks.add(
			"Aparece la descripcion de RPGD de usuario",
			modalArticleNotAvailable.isVisibleRPGD(2));
		
		return checks;
	}
	
	@Step (
		description="Seleccionar el link <b>Política de privacidad</b>", 
		expected="Aparece el texto adicional de política de privacidad")
	public void clickPoliticaPrivacidad() {
		modalArticleNotAvailable.clickPoliticaPrivacidad();
	}
	
	@Step (
		description="Pulsamos el botón <b>Recibir Aviso</b>", 
		expected="Aparece el snackbar de Petición confirmada")
	public void clickRecibirAviso() {
		modalArticleNotAvailable.clickRecibirAviso();
		checkSnackvarAvisoOkVisible(3);
	}
	
	@Validation (
		description="Aparece el snackvar de petición confirmada OK " + SECONDS_WAIT)
	public boolean checkSnackvarAvisoOkVisible(int seconds) {
		return modalArticleNotAvailable.isSnackvarAvisoOkVisible(seconds);
	}	
	
}
