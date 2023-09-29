package com.mng.robotest.tests.domains.chatbot.tests;

import org.testng.annotations.*;

public class Chatbot {

	//TODO cuando lo activen en Tablet añadir ese canal
	@Test (
		groups={"Chatbot", "Canal:desktop,mobile_App:shop"}, 
		description="Chequear el ChatBot")
	public void CHT001_chatBot() throws Exception {
		new Cht001().execute();
	}

}