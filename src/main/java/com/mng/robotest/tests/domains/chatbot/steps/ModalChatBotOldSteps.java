package com.mng.robotest.tests.domains.chatbot.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBot;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBotOld;

public class ModalChatBotOldSteps extends ModalChatBotSteps {

	private final ModalChatBotOld mdChatBot = new ModalChatBotOld();
	
	@Override
	protected ModalChatBot getModal() {
		return mdChatBot;
	}
	
	@Override
	public boolean isNewChatBot() {
		return false;
	}
	
	@Override
	@Validation (description="Aparece la capa del WebChat " + SECONDS_WAIT)
	protected boolean checkVisible(int seconds) {
		return mdChatBot.checkVisible(seconds);
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
