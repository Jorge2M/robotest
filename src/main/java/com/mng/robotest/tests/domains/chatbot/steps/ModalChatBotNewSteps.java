package com.mng.robotest.tests.domains.chatbot.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBot;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBotNew;

public class ModalChatBotNewSteps extends ModalChatBotSteps {

	private final ModalChatBotNew mdChatBot = new ModalChatBotNew();
	
	@Override
	protected ModalChatBot getModal() {
		return mdChatBot;
	}
	
	@Override
	@Validation (description="Aparece la capa del WebChat " + SECONDS_WAIT)
	protected boolean checkVisible(int seconds) {
		return mdChatBot.checkVisible(seconds);
	}
	
	@Validation (description="Es visible el botón <b>#{button}</b> " + SECONDS_WAIT)
	public boolean isVisibleButton(String button, int seconds) {
		return mdChatBot.isVisibleButton(button, seconds);
	}
	
	@Step (
		description="Seleccionar el botón <b>#{button}</b>", 
		expected="Aparece una respuesta correcta")
	public void selectButton(String button) {
		mdChatBot.selectButton(button);
	}
	
	@Validation (description="Es visible la respuesta \"#{answerExpected}\" " + SECONDS_WAIT)
	public boolean checkResponseVisible(String answerExpected, int seconds) {
		return mdChatBot.checkResponseVisible(answerExpected, seconds);
	}
	
	@Step (
		description="Introducir la cuestión \"#{question}\"", 
		expected="Aparece una respuesta correcta")	
	public void inputQuestion(String question) {
		mdChatBot.inputQuestion(question);
	}
	
	@Validation (description="Es visible el botón <b>Yes</b> " + SECONDS_WAIT)
	public boolean isVisibleYesButton(int seconds) {
		return mdChatBot.isVisibleYesButton(seconds);
	}
	
	@Step (
		description="Seleccionar el botón <b>Yes</b>", 
		expected="Aparece una respuesta correcta")	
	public void clickYesButton() {
		mdChatBot.clickYesButton();
	}
	
	@Step (
		description="Cerrar el modal del Chatbot", 
		expected="El chatbot desaparece")	
	public void close() {
		mdChatBot.close();
	}

	@Validation (description="No está visible la capa del WebChat " + SECONDS_WAIT)
	protected boolean checkInvisible(int seconds) {
		return !mdChatBot.checkInvisible(seconds);
	}

}
