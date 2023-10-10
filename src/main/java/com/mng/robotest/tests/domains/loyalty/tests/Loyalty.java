package com.mng.robotest.tests.domains.loyalty.tests;

import org.testng.annotations.Test;


public class Loyalty {
	
	@Test (
		groups={"Loyalty", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="Se realiza una compra mediante un usuario loyalty con Likes")
	public void LOY001_Compra_LikesStored() throws Exception {
		new Loy001().execute();
	}
	
	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Exchange mediante donación de Likes")
	public void LOY002_Exhange_Donacion_Likes() throws Exception {
		new Loy002().execute();
	}

	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Compra entrada cine a cambio de Likes")
	public void LOY003_Exhange_Compra_Entrada() throws Exception {
		new Loy003().execute();
	}
	
	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Navegación por Mango Likes You History y Help")
	public void LOY004_History_Help() throws Exception {
		new Loy004().execute();
	}	
	
	@Test (
		groups={"Loyalty", "Canal:desktop,mobile_App:outlet"},
		description="Acceso a la Landing y comprobación de que no existen referencias a Loyalty")
	public void LOY006_Outlet_without_Loyalty() throws Exception {
		new Loy006().execute();
	}	
	

//  Se ha desactivado la operativa de transferencia de Likes
//	@Test (
//		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
//		description="Transferencia de Likes de un cliente a otro")
//	public void LOY005_TransferLikes_To_Another_Client() throws Exception {
//		new Loy005().execute();
//	}

}