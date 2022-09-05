package com.mng.robotest.domains.micuenta.tests;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;

public class Mic002 extends TestBase {
	
	private final String userWithOnlinePurchases;
	private final String userWithStorePurchases;
	private final String passUserWithOnlinePurchases;
	private final String passUserWithStorePurchases;
	
	public Mic002(
			Pais pais, IdiomaPais idioma, 
			String userWithOnlinePurchases, String userWithStorePurchases, 
			String passUserWithOnlinePurchases, String passUserWithStorePurchases) {
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		this.userWithOnlinePurchases = userWithOnlinePurchases;  
		this.userWithStorePurchases = userWithStorePurchases; 
		this.passUserWithOnlinePurchases = passUserWithOnlinePurchases; 
		this.passUserWithStorePurchases = passUserWithStorePurchases;
	}

	@Override
	public void execute() throws Exception {
		compraOnline();
		compraTienda();		
	}

	private void compraOnline() throws Exception {
		dataTest.userConnected = userWithOnlinePurchases;
		dataTest.passwordUser = passUserWithOnlinePurchases;
		dataTest.userRegistered = true;
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango();
		
		new PageMiCuentaSteps().goToMisComprasFromMenu();
		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOfType(TypeTicket.Online, 3);
		pageMisComprasSteps.selectCompraOnline(1, dataTest.pais.getCodigo_pais());
		pageMisComprasSteps.clickDetalleArticulo(1);
		pageMisComprasSteps.gotoMisComprasFromDetalleCompra();
	}
	
	private void compraTienda() throws Exception {
		dataTest.userConnected = userWithStorePurchases;
		dataTest.passwordUser = passUserWithStorePurchases;
		new SecMenusUserSteps().logoff();
		
		//Existe un problema en por el cual si te vuelves a loginar manteniendo el navegador
		//se muestran las compras del anterior usuario
		TestMaker.renewDriverTestCase();
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango();
		
		new PageMiCuentaSteps().goToMisComprasFromMenu();
		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOfType(TypeTicket.Tienda, 3);
		pageMisComprasSteps.selectCompraTienda(1);
		pageMisComprasSteps.clickDetalleArticulo(1);
	}	
	
}
