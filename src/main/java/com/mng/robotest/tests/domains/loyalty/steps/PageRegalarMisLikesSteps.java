package com.mng.robotest.tests.domains.loyalty.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageRegalarMisLikes;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageRegalarMisLikesSteps extends StepBase {

	private final PageRegalarMisLikes pageRegalarMisLikes = new PageRegalarMisLikes();

	@Validation (
		description="Estamos en la página de \"Regalar Likes\"")
	public boolean checkIsPage() {
		return (pageRegalarMisLikes.checkIsPage());
	}
	
	@Step (
		description="Introducir mensaje y usuario receptor #{emailReceptor}",
		expected="Aparece el bloque para introducir los likes a enviar")
	public void inputReceptorAndClickContinuar(String mensaje, String emailReceptor) {
		pageRegalarMisLikes.inputMensaje(mensaje);
		pageRegalarMisLikes.inputEmailReceptor(emailReceptor);
		pageRegalarMisLikes.clickContinuar();
		checkCuantosLikesVisible();
	}
	
	@Validation (
		description="Es visible el bloque para la introducción de los Likes a regalar",
		level=Warn)
	public boolean checkCuantosLikesVisible() {
		return (pageRegalarMisLikes.checkIsVisibleBlockCuantosLikes());
	}
	
	@Step (
		description="Introducir <b>#{numLikesToRegalar}</b> Likes para regalar y pulsar \"Enviar regalo\"",
		expected="Aparece la página de resultado Ok")
	public PageResultadoRegaloLikesSteps inputNumLikesAndClickEnviarRegalo(int numLikesToRegalar) {
		pageRegalarMisLikes.inputLikesToRegalar(numLikesToRegalar);
		pageRegalarMisLikes.clickEnviarRegalo();
		
		var pageResult = new PageResultadoRegaloLikesSteps();
		pageResult.checkIsEnvioLikesOk(3);
		checksDefault();
		return pageResult;
	}
	
}
