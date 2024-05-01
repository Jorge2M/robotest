package com.mng.robotest.tests.domains.chatbot.tests;

import org.testng.annotations.*;

public class Chatbot {

	@Test (
		testName="CHT001",
		groups={"Chatbot", "Smoke", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="Chequear el ChatBot")
	public void chatBot() throws Exception {
		new Cht001().execute();
	}

}