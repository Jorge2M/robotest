package com.mng.robotest.domains.loyalty.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.loyalty.pageobjects.PageRegalarMisLikes;

public class PageRegalarMisLikesSteps {

	final WebDriver driver;
	final PageRegalarMisLikes pageRegalarMisLikes;
	
	public PageRegalarMisLikesSteps(WebDriver driver) {
		this.driver = driver;
		this.pageRegalarMisLikes = new PageRegalarMisLikes(driver);
	}

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
		level=State.Warn)
	public boolean checkCuantosLikesVisible() {
		return (pageRegalarMisLikes.checkIsVisibleBlockCuantosLikes());
	}
	
	@Step (
		description="Introducir <b>#{numLikesToRegalar}</b> Likes para regalar y pulsar \"Enviar regalo\"",
		expected="Aparece la página de resultado Ok")
	public PageResultadoRegaloLikesSteps inputNumLikesAndClickEnviarRegalo(int numLikesToRegalar) {
		pageRegalarMisLikes.inputLikesToRegalar(numLikesToRegalar);
		pageRegalarMisLikes.clickEnviarRegalo();
		
		PageResultadoRegaloLikesSteps pageResult = new PageResultadoRegaloLikesSteps(driver);
		pageResult.checkIsEnvioLikesOk(3);
		return pageResult;
	}
	
}
