package com.mng.robotest.tests.domains.chatbot.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBot;

public class ModalChatBotSteps extends StepBase {

	private final ModalChatBot mdChatBot = new ModalChatBot();
	
	@Validation (description="Aparece el icono de ChatBot")
	public boolean checkIconVisible() {
		return mdChatBot.checkIconVisible();
	}
	
	@Step (
		description="Clickar el icono de ChatBot", 
		expected="Aparece la capa del webchat")
	public boolean clickIcon() {
		mdChatBot.clickIcon();
		checksDefault();
		return checkWebchatVisible(3);
		
	}
	
	@Validation (description="Aparece la capa del WebChat " + SECONDS_WAIT)
	private boolean checkWebchatVisible(int seconds) {
		return mdChatBot.checkWebchatVisible(seconds);
	}
	
	@Validation (description="Es visible la opción #{option} " + SECONDS_WAIT)
	public boolean isVisibleOption(String option, int seconds) {
		return mdChatBot.isOptionVisible(option, seconds);
	}
	
	@Step (
		description="Seleccionar la opción <b>#{option}</b>", 
		expected="La selección es correcta")
	public void selectOption(String option) {
		mdChatBot.clickOption(option);
	}
	
	@Validation (
		description="Aparece una respuesta con el texto #{respuesta} " + SECONDS_WAIT)
	public boolean checkResponseVisible(String respuesta, int seconds) {
		return mdChatBot.isResponseVisible(respuesta, seconds);
	}
	
	@Validation (description="Aparece el botón #{button} " + SECONDS_WAIT)
	public boolean isVisibleButton(String button, int seconds) {
		return mdChatBot.isButtonVisible(button, seconds);
	}
	
}
