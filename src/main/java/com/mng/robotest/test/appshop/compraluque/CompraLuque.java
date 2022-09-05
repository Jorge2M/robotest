package com.mng.robotest.test.appshop.compraluque;

import org.testng.annotations.Test;

//Más info: https://confluence.mango.com/display/TT/BlackFriday+-+Stress+Tests
public class CompraLuque {
	
	public CompraLuque() throws Exception {}
	
	@Test (
		groups={"Compra", "Canal:desktop,mobile_App:all"}, alwaysRun=true,
		description="Compra USA o Irlanda")
	public void LUQ001_Compra() throws Exception {
		new Luq001().execute();
	}

}
