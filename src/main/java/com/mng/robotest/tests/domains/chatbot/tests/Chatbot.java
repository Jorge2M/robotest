package com.mng.robotest.tests.domains.chatbot.tests;

import org.testng.annotations.*;

public class Chatbot {

	@Test (
		groups={"Chatbot", "Smoke", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="Chequear el ChatBot")
	public void CHT001_chatBot() throws Exception {
		new Cht001().execute();
	}

}