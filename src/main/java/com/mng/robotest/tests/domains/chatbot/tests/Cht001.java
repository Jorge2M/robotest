package com.mng.robotest.tests.domains.chatbot.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.chatbot.steps.ModalChatBotNewSteps;
import com.mng.robotest.tests.domains.chatbot.steps.ModalChatBotOldSteps;
import com.mng.robotest.tests.domains.chatbot.steps.ModalChatBotSteps;
import com.mng.robotest.tests.domains.transversal.modales.pageobject.ModalsSubscriptionsSteps;

public class Cht001 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		new ModalsSubscriptionsSteps().closeIfVisible();
		
		var chatBotSteps = ModalChatBotSteps.make();
		if (isOutlet()) {
			chatBotSteps.checkIconInvisible();
			return;
		}
		
		if (!chatBotSteps.checkIconVisible()) {
			return;
		}
		if (!chatBotSteps.clickIcon()) {
			return;
		}
		
		if (isPRO()) {
			executeOldChatBot();
		} else {
			executeNewChatBot();
		}
	}
	
	private void executeOldChatBot() {
		var chatBotSteps = new ModalChatBotOldSteps();
		
		String option1 = "Tiendas";
		chatBotSteps.isVisibleOption(option1, 5);
		chatBotSteps.selectOption(option1);
		
		String option2 = "Información sobre tiendas";
		chatBotSteps.isVisibleOption(option2, 5);
		chatBotSteps.selectOption(option2);
		
		chatBotSteps.checkResponseVisible("Si quieres saber el horario, el teléfono o las colecciones disponibles de alguna tienda", 3);
		chatBotSteps.isVisibleButton("¡Sí, gracias!", 5);		
	}
	
	private void executeNewChatBot() {
		var chatBotSteps = new ModalChatBotNewSteps();
		
		String button = "¿Dónde está mi pedido?";
		chatBotSteps.isVisibleButton(button, 5);
		chatBotSteps.selectButton(button);
		String answerExpected = "Para poder encontrar tu pedido, escribe el e-mail con el que hiciste la compra (ej. nombre@ejemplo.com).";
		chatBotSteps.checkResponseVisible(answerExpected, 3);
		chatBotSteps.close();

		chatBotSteps.clickIcon();
		String question = "Cómo encontrar un producto por su referencia";
		chatBotSteps.inputQuestion(question);
		answerExpected = "Puedes encontrar un producto por su referencia utilizando";
		chatBotSteps.checkResponseVisible(answerExpected, 5);
		
		chatBotSteps.isVisibleYesButton(5);
		chatBotSteps.clickYesButton();
		chatBotSteps.checkResponseVisible("Muchas gracias", "¿Puedo hacer algo más por ti?", 5);
		chatBotSteps.close();
	}
	
}
