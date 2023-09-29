package com.mng.robotest.tests.domains.chatbot.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.chatbot.steps.ModalChatBotSteps;
import com.mng.robotest.tests.domains.transversal.modales.pageobject.ModalsSubscriptionsSteps;

public class Cht001 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		new ModalsSubscriptionsSteps().closeIfVisible();
		var chatBotSteps = new ModalChatBotSteps();
		if (!chatBotSteps.checkIconVisible()) {
			return;
		}
		if (!chatBotSteps.clickIcon()) {
			return;
		}
		
		String option1 = "Tiendas";
		chatBotSteps.isVisibleOption(option1, 5);
		chatBotSteps.selectOption(option1);
		
		String option2 = "Información sobre tiendas";
		chatBotSteps.isVisibleOption(option2, 5);
		chatBotSteps.selectOption(option2);
		
		chatBotSteps.checkResponseVisible("Si quieres saber el horario, el teléfono o las colecciones disponibles de alguna tienda", 3);
		chatBotSteps.isVisibleButton("¡Sí, gracias!", 5);		
	}
	
}
