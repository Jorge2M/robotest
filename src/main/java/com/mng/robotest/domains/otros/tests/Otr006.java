package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.shop.modales.ModalChatBotSteps;
import com.mng.robotest.test.steps.shop.modales.ModalNewsletterSteps;

public class Otr006 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		new ModalNewsletterSteps().closeIfVisible();
		ModalChatBotSteps chatBotSteps = new ModalChatBotSteps();
		if (!chatBotSteps.checkIconVisible()) {
			return;
		}
		if (!chatBotSteps.clickIcon()) {
			return;
		}
		
		String option1 = "Estado de mi pedido";
		chatBotSteps.isVisibleOption(option1, 5);
		chatBotSteps.selectOption(option1);
		
		String option2 = "Retraso de mi pedido";
		chatBotSteps.isVisibleOption(option2, 5);
		chatBotSteps.selectOption(option2);
		
		chatBotSteps.checkResponseVisible("Si has recibido un e-mail de retraso de tu pedido no te preocupes", 3);
		chatBotSteps.isVisibleButton("¡Sí, gracias!", 3);		
	}
	
}
