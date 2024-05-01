package com.mng.robotest.tests.domains.loyalty.tests;

import org.testng.annotations.Test;


public class Loyalty {
	
	@Test (
		testName="LOY001",			
		groups={"Loyalty", "Smoke", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="Se realiza una compra con envío a tienda OK comprobando que se aplican correctamente los Likes")
	public void compraLikesStored() throws Exception {
		new Loy001().execute();
	}
	
	@Test (
		testName="LOY002",			
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Exchange mediante donación de Likes")
	public void exhangeDonacionLikes() throws Exception {
		new Loy002().execute();
	}

	@Test (
		testName="LOY003",			
		groups={"Loyalty", "Smoke", "Canal:desktop,mobile_App:shop"},
		description="Compra entrada cine a cambio de Likes")
	public void exhangeCompraEntrada() throws Exception {
		new Loy003().execute();
	}
	
	@Test (
		testName="LOY004",			
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Navegación por Mango Likes You History y Help")
	public void historyHelp() throws Exception {
		new Loy004().execute();
	}	
	
	@Test (
		testName="LOY006",			
		groups={"Loyalty", "Canal:desktop,mobile_App:outlet"},
		description="Acceso a la Landing y comprobación de que no existen referencias a Loyalty")
	public void outletWithoutLoyalty() throws Exception {
		new Loy006().execute();
	}	
	
	@Test (
		testName="LOY007",			
		groups={"Loyalty", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="Se realiza un primer pago KO y después uno con envío a domicilio OK comprobando que se aplican correctamente los Likes")
	public void pagoKoOkLikesStored() throws Exception {
		new Loy007().execute();
	}		
	
	//TODO activarlo cuando se active Loyalty en USA
//	@Test (
//		testName="LOY008",	
//		groups={"Loyalty", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
//		description="Se realiza un primer pago KO y después uno con envío a domicilio OK comprobando que se aplican correctamente los Likes")
//	public void pagoKoOkUsa() throws Exception {
//		new Loy008().execute();
//	}	

//  Se ha desactivado la operativa de transferencia de Likes
//	@Test (
//		testName="LOY005",	
//		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
//		description="Transferencia de Likes de un cliente a otro")
//	public void transferLikesToAnotherClient() throws Exception {
//		new Loy005().execute();
//	}

}
