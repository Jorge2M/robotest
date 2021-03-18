package com.mng.robotest.test80.mango.test.stpv.shop.loyalty;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.shop.loyalty.PageRegalarMisLikes;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;

public class PageRegalarMisLikesStpV {

	final WebDriver driver;
	final PageRegalarMisLikes pageRegalarMisLikes;
	
	private PageRegalarMisLikesStpV(WebDriver driver) {
		this.driver = driver;
		this.pageRegalarMisLikes = new PageRegalarMisLikes(driver);
	}
	
	public static PageRegalarMisLikesStpV getNew(WebDriver driver) {
		return new PageRegalarMisLikesStpV(driver);
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
	public PageResultadoRegaloLikesStpV inputNumLikesAndClickEnviarRegalo(int numLikesToRegalar) {
		pageRegalarMisLikes.inputLikesToRegalar(numLikesToRegalar);
		pageRegalarMisLikes.clickEnviarRegalo();
		
		PageResultadoRegaloLikesStpV pageResult = new PageResultadoRegaloLikesStpV(driver);
		pageResult.checkIsEnvioLikesOk(3);
		return pageResult;
	}
	
}
