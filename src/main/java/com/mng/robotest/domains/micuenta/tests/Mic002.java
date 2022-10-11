package com.mng.robotest.domains.micuenta.tests;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.micuenta.pageobjects.PageMisCompras.TypeTicket;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;

public class Mic002 extends TestBase {
	
	private final String userWithOnlinePurchases;
	private final String userWithStorePurchases;
	private final String passUserWithOnlinePurchases;
	private final String passUserWithStorePurchases;
	
	public Mic002(
			Pais pais, IdiomaPais idioma, 
			String userWithOnlinePurchases, String userWithStorePurchases, 
			String passUserWithOnlinePurchases, String passUserWithStorePurchases) {
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
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
		dataTest.setUserConnected(userWithOnlinePurchases);
		dataTest.setPasswordUser(passUserWithOnlinePurchases);
		dataTest.setUserRegistered(true);
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango();
		
		new PageMiCuentaSteps().goToMisComprasFromMenu();
		PageMisComprasSteps pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOfType(TypeTicket.Online, 3);
		pageMisComprasSteps.selectCompraOnline(1, dataTest.getCodigoPais());
		pageMisComprasSteps.clickDetalleArticulo(1);
		pageMisComprasSteps.gotoMisComprasFromDetalleCompra();
	}
	
	private void compraTienda() throws Exception {
		dataTest.setUserConnected(userWithStorePurchases);
		dataTest.setPasswordUser(passUserWithStorePurchases);
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
