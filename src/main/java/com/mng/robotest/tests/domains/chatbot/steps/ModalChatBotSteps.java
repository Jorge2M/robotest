package com.mng.robotest.tests.domains.chatbot.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.chatbot.pageobjects.ModalChatBot;

public abstract class ModalChatBotSteps extends StepBase {

	abstract ModalChatBot getModal();
	
	public static ModalChatBotSteps make() {
		//TODO activar cuando activen la nueva versión del Chatbot
		//if (PageBase.isEnvPRO()) {
		return new ModalChatBotOldSteps();
		//return new ModalChatBotNewSteps(); 
	}
	
	@Validation (description="Aparece el icono de ChatBot")
	public boolean checkIconVisible() {
		return getModal().checkIconVisible();
	}
	
	@Validation (description="No aparece el icono de ChatBot")
	public boolean checkIconInvisible() {
		return getModal().checkIconInvisible();
	}
	
	@Step (
		description="Clickar el icono de ChatBot", 
		expected="Aparece la capa del webchat")
	public boolean clickIcon() {
		getModal().clickIcon();
		checksDefault();
		return checkVisible(4);
	}
	
	protected abstract boolean checkVisible(int seconds);
	
}
