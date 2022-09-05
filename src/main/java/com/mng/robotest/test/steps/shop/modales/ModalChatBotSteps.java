package com.mng.robotest.test.steps.shop.modales;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test.pageobject.shop.modales.ModalChatBot;

public class ModalChatBotSteps {

	private final ModalChatBot modalChatBot = new ModalChatBot();
	
	@Validation (
		description="Aparece el icono de ChatBot",
		level=State.Defect)
	public boolean checkIconVisible() {
		return modalChatBot.checkIconVisible();
	}
	
	@Step (
		description="Clickar el icono de ChatBot", 
		expected="Aparece la capa del webchat")
	public boolean clickIcon() {
		modalChatBot.clickIcon();
		return checkWebchatVisible(3);
	}
	
	@Validation (
		description="Aparece la capa del WebChat (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	private boolean checkWebchatVisible(int maxSeconds) {
		return modalChatBot.checkWebchatVisible(maxSeconds);
	}
	
	@Validation (
		description="Es visible la opción #{option} (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean isVisibleOption(String option, int maxSeconds) {
		return modalChatBot.isOptionVisible(option, maxSeconds);
	}
	
	@Step (
		description="Seleccionar la opción <b>#{option}</b>", 
		expected="La selección es correcta")
	public void selectOption(String option) {
		modalChatBot.clickOption(option);
	}
	
	@Validation (
		description="Aparece una respuesta con el texto #{respuesta} (la esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean checkResponseVisible(String respuesta, int maxSeconds) {
		return modalChatBot.isResponseVisible(respuesta, maxSeconds);
	}
	
	@Validation (
		description="Aparece el botón #{respuesta} (lo esperamos hasta #{maxSeconds} segundos)",
		level=State.Defect)
	public boolean isVisibleButton(String button, int maxSeconds) {
		return modalChatBot.isButtonVisible(button, maxSeconds);
	}
	
}
