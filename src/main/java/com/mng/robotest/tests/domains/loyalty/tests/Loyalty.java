package com.mng.robotest.tests.domains.loyalty.tests;

import org.testng.annotations.Test;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.loyalty.tests.testslegacy.Loy901;
import com.mng.robotest.tests.domains.loyalty.tests.testslegacy.Loy902;
import com.mng.robotest.tests.domains.loyalty.tests.testslegacy.Loy903;
import com.mng.robotest.tests.domains.loyalty.tests.testslegacy.Loy904;
import com.mng.robotest.tests.domains.loyalty.tests.testslegacy.Loy906;
import com.mng.robotest.tests.domains.loyalty.tests.testslegacy.Loy907;
import com.mng.robotest.tests.domains.loyalty.tests.testsnew.Loy001;
import com.mng.robotest.tests.domains.loyalty.tests.testsnew.Loy002;
import com.mng.robotest.tests.domains.loyalty.tests.testsnew.Loy003;
import com.mng.robotest.tests.domains.loyalty.tests.testsnew.Loy004;
import com.mng.robotest.tests.domains.loyalty.tests.testsnew.Loy006;
import com.mng.robotest.tests.domains.loyalty.tests.testsnew.Loy007;


public class Loyalty {
	
	@Test (
		testName="LOY001",
		groups={"Loyalty", "Smoke", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="Se realiza una compra con envío a tienda OK comprobando que se aplican correctamente los Likes")
	public void compraLikesStored() throws Exception {
		//Activar cuando suba MLY Tiers
		if (isPro()) {
			return;
		}		
		new Loy001().execute();
	}
	
	@Test (
		testName="LOY901",
		groups={"Loyalty", "Smoke", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="[USA][Legacy Loyalty] Se realiza una compra con envío a tienda OK comprobando que se aplican correctamente los Likes")
	public void compraLikesStoredLegacy() throws Exception {
		new Loy901().execute();
	}	
	
	@Test (
		testName="LOY002",
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Exchange mediante donación de Likes")
	public void exhangeDonacionLikes() throws Exception {
		//Activar cuando suba MLY Tiers
		if (isPro()) {
			return;
		}
		new Loy002().execute();
	}
	
	@Test (
		testName="LOY902",
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="[USA][Legacy Loyalty] Exchange mediante donación de Likes")
	public void exhangeDonacionLikesLegacy() throws Exception {
		new Loy902().execute();
	}	

	@Test (
		testName="LOY003",
		groups={"Loyalty", "Smoke", "Canal:desktop,mobile_App:shop"},
		description="Compra entrada cine a cambio de Likes")
	public void exhangeCompraEntrada() throws Exception {
		//Activar cuando suba MLY Tiers
		if (isPro()) {
			return;
		}		
		new Loy003().execute();
	}
	
	@Test (
		testName="LOY903",
		groups={"Loyalty", "Smoke", "Canal:desktop,mobile_App:shop"},
		description="[USA][Legacy Loyalty] Compra entrada cine a cambio de Likes")
	public void exhangeCompraEntradaLegacy() throws Exception {
		new Loy903().execute();
	}	
	
	@Test (
		testName="LOY004",
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="Navegación por Mango Likes You History y Help")
	public void historyHelp() throws Exception {
		//Activar cuando suba MLY Tiers
		if (isPro()) {
			return;
		}		
		new Loy004().execute();
	}	
	
	@Test (
		testName="LOY904",
		groups={"Loyalty", "Canal:desktop,mobile_App:shop"},
		description="[USA][Legacy Loyalty] Navegación por Mango Likes You History y Help")
	public void historyHelpLegacy() throws Exception {
		new Loy904().execute();
	}	
	
	@Test (
		testName="LOY006",
		groups={"Loyalty", "Canal:desktop,mobile_App:outlet"},
		description="Acceso a la Landing y comprobación de que no existen referencias a Loyalty")
	public void outletWithoutLoyalty() throws Exception {
		new Loy006().execute();
	}	
	
	@Test (
		testName="LOY906",
		groups={"Loyalty", "Canal:desktop,mobile_App:outlet"},
		description="[USA][Legacy Loyalty] Acceso a la Landing y comprobación de que no existen referencias a Loyalty")
	public void outletWithoutLoyaltyLegacy() throws Exception {
		new Loy906().execute();
	}	
	
	@Test (
		testName="LOY007",
		groups={"Loyalty", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="Se realiza un primer pago KO y después uno con envío a domicilio OK comprobando que se aplican correctamente los Likes")
	public void pagoKoOkLikesStoredLegacy() throws Exception {
		//Activar cuando suba MLY Tiers
		if (isPro()) {
			return;
		}
		new Loy007().execute();
	}		
	
	@Test (
		testName="LOY907",			
		groups={"Loyalty", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
		description="[UK][Legacy Loyalty] Se realiza un primer pago KO y después uno con envío a domicilio OK comprobando que se aplican correctamente los Likes")
	public void pagoKoOkLikesStored() throws Exception {
		new Loy907().execute();
	}	
	
	private boolean isPro() {
		return PageBase.isEnvPRO();
	}	
	
	//TODO activarlo cuando se active Loyalty en USA
//	@Test (
//		testName="LOY008",	
//		groups={"Loyalty", "Compra", "Checkout", "Canal:desktop,mobile_App:shop"},
//		description="Se realiza un primer pago KO y después uno con envío a domicilio OK comprobando que se aplican correctamente los Likes")
//	public void pagoKoOkUsa() throws Exception {
//		new Loy008().execute();
//	}	

}
