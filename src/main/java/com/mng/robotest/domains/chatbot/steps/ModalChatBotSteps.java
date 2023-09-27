package com.mng.robotest.domains.chatbot.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.chatbot.pageobjects.ModalChatBot;

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
	
	@Validation (description="Aparece la capa del WebChat " + SECONDS_WAIT)
	private boolean checkWebchatVisible(int seconds) {
		return modalChatBot.checkWebchatVisible(seconds);
	}
	
	@Validation (description="Es visible la opción #{option} " + SECONDS_WAIT)
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
		description="Aparece una respuesta con el texto #{respuesta} " + SECONDS_WAIT)
	public boolean checkResponseVisible(String respuesta, int seconds) {
		return modalChatBot.isResponseVisible(respuesta, seconds);
	}
	
	@Validation (description="Aparece el botón #{button} " + SECONDS_WAIT)
	public boolean isVisibleButton(String button, int seconds) {
		return modalChatBot.isButtonVisible(button, seconds);
	}
	
}
