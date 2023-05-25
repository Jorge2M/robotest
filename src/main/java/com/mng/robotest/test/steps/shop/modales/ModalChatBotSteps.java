package com.mng.robotest.test.steps.shop.modales;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.test.pageobject.shop.modales.ModalChatBot;

public class ModalChatBotSteps extends StepBase {

	private final ModalChatBot modalChatBot = new ModalChatBot();
	
	@Validation (description="Aparece el icono de ChatBot")
	public boolean checkIconVisible() {
		return modalChatBot.checkIconVisible();
	}
	
	@Step (
		description="Clickar el icono de ChatBot", 
		expected="Aparece la capa del webchat")
	public boolean clickIcon() {
		modalChatBot.clickIcon();
		checksDefault();
		return checkWebchatVisible(3);
		
	}
	
	@Validation (description="Aparece la capa del WebChat (la esperamos hasta #{seconds} segundos)")
	private boolean checkWebchatVisible(int seconds) {
		return modalChatBot.checkWebchatVisible(seconds);
	}
	
	@Validation (description="Es visible la opción #{option} (la esperamos hasta #{seconds} segundos)")
	public boolean isVisibleOption(String option, int seconds) {
		return modalChatBot.isOptionVisible(option, seconds);
	}
	
	@Step (
		description="Seleccionar la opción <b>#{option}</b>", 
		expected="La selección es correcta")
	public void selectOption(String option) {
		modalChatBot.clickOption(option);
	}
	
	@Validation (
		description="Aparece una respuesta con el texto #{respuesta} (la esperamos hasta #{seconds} segundos)")
	public boolean checkResponseVisible(String respuesta, int seconds) {
		return modalChatBot.isResponseVisible(respuesta, seconds);
	}
	
	@Validation (description="Aparece el botón #{respuesta} (lo esperamos hasta #{seconds} segundos)")
	public boolean isVisibleButton(String button, int seconds) {
		return modalChatBot.isButtonVisible(button, seconds);
	}
	
}
