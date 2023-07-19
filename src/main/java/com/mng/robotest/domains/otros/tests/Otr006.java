package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.test.steps.shop.modales.ModalChatBotSteps;
import com.mng.robotest.test.steps.shop.modales.ModalsSubscriptionsSteps;

public class Otr006 extends TestBase {

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
