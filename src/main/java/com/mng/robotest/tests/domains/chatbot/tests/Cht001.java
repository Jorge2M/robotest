package com.mng.robotest.tests.domains.chatbot.tests;

import java.util.Arrays;

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
		if (isOutlet() && isPRO()) { //En breve subirá a PRO (7-6-24)
			chatBotSteps.checkIconInvisible();
			return;
		}
		
		if (!chatBotSteps.checkIconVisible()) {
			return;
		}
		if (!chatBotSteps.clickIcon()) {
			return;
		}
		
//		if (isPRO()) {
			executeOldChatBot();
//		} else {
//			executeNewChatBot();
//		}
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
		String buttonDondeEstaMiPedido = "¿Dónde está mi pedido?";
		String buttonComoSaberMiTalla = "¿Cómo puedo saber mi talla?";
		var optButton = chatBotSteps.isVisibleAnyButton(Arrays.asList(buttonDondeEstaMiPedido, buttonComoSaberMiTalla), 5);
		if (!optButton.isPresent()) {
			return;
		}
		
		var button = optButton.get();
		chatBotSteps.selectButton(optButton.get());
		if (button.compareTo(buttonDondeEstaMiPedido)==0) {
			checkDondeEstaMiPedidoResponse();
		} else {
			checkComoSaberMiTallaResponse();
		}
		checkInputQuestion();
	}
	
	private void checkDondeEstaMiPedidoResponse() {
		var chatBotSteps = new ModalChatBotNewSteps();
		String answerExpected = "Para poder encontrar tu pedido, escribe el e-mail con el que hiciste la compra (ej. nombre@ejemplo.com).";
		chatBotSteps.checkResponseVisible(answerExpected, 3);
		chatBotSteps.close();
	}
	
	private void checkComoSaberMiTallaResponse() {
		var chatBotSteps = new ModalChatBotNewSteps();
		var answersExpected = Arrays.asList(
			"Puedes consultar la guía de tallas",
			"Puedes consultar la talla de cada artículo",
			"Puedes encontrar la guía de tallas",
			"Puedes consultar la \"Guía de tallas\" disponible en cada artículo",
			"Puedes consultar las tallas en las que se ha fabricado de cada artículo");
		chatBotSteps.checkAnyResponseVisible(answersExpected, 6);		
		chatBotSteps.close();
	}
	
	private void checkInputQuestion() {
		var chatBotSteps = new ModalChatBotNewSteps();
		chatBotSteps.clickIcon();
		String question = "Cómo encontrar un producto por su referencia";
		chatBotSteps.inputQuestion(question);
		var answersExpected = Arrays.asList(
			"Puedes encontrar un producto por su referencia utilizando",
			"Puedes encontrar un producto por su referencia introduciendo");
		chatBotSteps.checkAnyResponseVisible(answersExpected, 6);
		
		chatBotSteps.isVisibleYesButton(5);
		chatBotSteps.clickYesButton();
		chatBotSteps.checkResponseVisible("Muchas gracias", "¿Puedo hacer algo más por ti?", 6);
		chatBotSteps.close();
	}
	
}
