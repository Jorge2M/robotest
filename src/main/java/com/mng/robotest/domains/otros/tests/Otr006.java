package com.mng.robotest.domains.otros.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.test.steps.shop.modales.ModalChatBotSteps;
import com.mng.robotest.test.steps.shop.modales.ModalNewsletterSteps;

public class Otr006 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		new ModalNewsletterSteps().closeIfVisible();
		var chatBotSteps = new ModalChatBotSteps();
		if (!chatBotSteps.checkIconVisible()) {
			return;
		}
		if (!chatBotSteps.clickIcon()) {
			return;
		}
		
		String option1 = "Club Mango likes you";
		chatBotSteps.isVisibleOption(option1, 5);
		chatBotSteps.selectOption(option1);
		
		String option2 = "Cómo utilizar mis Likes";
		chatBotSteps.isVisibleOption(option2, 5);
		chatBotSteps.selectOption(option2);
		
		chatBotSteps.checkResponseVisible("Utiliza tus Likes como descuento en tienda y online", 3);
		chatBotSteps.isVisibleButton("¡Sí, gracias!", 3);		
	}
	
}
