package com.mng.robotest.tests.domains.chatbot.steps;

import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.chatbot.beans.AnswersExpected;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBot;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBotNew;
import com.mng.robotest.tests.domains.votfconsole.utils.ChecksResultWithStringData;

public class ModalChatBotNewSteps extends ModalChatBotSteps {

	private final ModalChatBotNew mdChatBot = new ModalChatBotNew();
	
	@Override
	protected ModalChatBot getModal() {
		return mdChatBot;
	}
	
	@Override
	public boolean isNewChatBot() {
		return true;
	}
	
	@Override
	@Validation (description="Aparece la capa del WebChat " + SECONDS_WAIT)
	protected boolean checkVisible(int seconds) {
		return mdChatBot.checkVisible(seconds);
	}
	
	public Optional<String> isVisibleAnyButton(List<String> buttons, int seconds) {
		String button = isVisibleAnyButtonInternal(buttons, seconds).getData();
		if (button==null || "".compareTo(button)==0) {
			return Optional.empty();
		}
		return Optional.of(button);
	}
	
	@Validation 
	private ChecksResultWithStringData isVisibleAnyButtonInternal(List<String> buttons, int seconds) {
		var checks = ChecksResultWithStringData.getNew();
		var buttonOpt = mdChatBot.isVisibleAnyButton(buttons, seconds);
		checks.add(
			"Es visible alguno de los botones <b>" + buttons + "</b> " + getLitSecondsWait(seconds),
			buttonOpt.isPresent());
		
		if (buttonOpt.isPresent()) {
			checks.setData(buttonOpt.get());
		} else {
			checks.setData("");
		}
		
		return checks;
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
	
	@Validation (description="Es visible alguna de las respuestas:<br>#{answersExpected.toHtml()} " + SECONDS_WAIT)
	public boolean checkAnyResponseVisible(AnswersExpected answersExpected, int seconds) {
		return mdChatBot.checkAnyResponseVisible(answersExpected, seconds);
	}
	
	@Validation (description="Es visible la respuesta \"#{answerExpected}\" " + SECONDS_WAIT)
	public boolean checkResponseVisible(String answerExpected, int seconds) {
		return mdChatBot.checkResponseVisible(answerExpected, seconds);
	}
	
	@Validation (description="Es visible la respuesta \"#{answerExpected1}\" o \"#{answerExpected2}\" " + SECONDS_WAIT)
	public boolean checkResponseVisible(String answerExpected1, String answerExpected2, int seconds) {
		return mdChatBot.checkResponseVisible(answerExpected1, answerExpected2, seconds);
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
