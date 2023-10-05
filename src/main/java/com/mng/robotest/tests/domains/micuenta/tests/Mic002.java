package com.mng.robotest.tests.domains.micuenta.tests;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisComprasSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.utils.UtilsTest;

import static com.mng.robotest.tests.domains.micuenta.pageobjects.PageMisCompras.TypeTicket.*;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuUserItem.UserMenu.*;

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
		//TODO al usuario ticket_digital_es@mango.com ya no le aparece 
		//la compra en tienda 8684 porque es de hace > 4 años
		//descomentar si en algún momento encontramos un usuario con este tipo de compras
		//compraTienda();		
	}

	//TODO activar cuando suba a PRE el tícket https://jira.mango.com/browse/PIUR-5086
	private void compraOnline_PostPIUR5086() throws Exception {
		accessAndClickMisCompras();
		login();
		checkCompraOnline();
	}
	
	private void compraOnline() throws Exception {
		dataTest.setUserConnected(userWithOnlinePurchases);
		dataTest.setPasswordUser(passUserWithOnlinePurchases);
		dataTest.setUserRegistered(true);
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango();
		
		new PageMiCuentaSteps().goToMisComprasFromMenu();
		var pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOfType(ONLINE, 5);
		pageMisComprasSteps.selectCompraOnline(1);
		
		//TODO TestAB (quickview o ficha) que en pro va por la variante-0 y en pre por la 1
		if (!(isPRO() && UtilsTest.todayBeforeDate("2023-09-15"))) {		
			pageMisComprasSteps.clickDetalleArticulo(1);
		}
	}	
	
	private void accessAndClickMisCompras() throws Exception {
		access();
		clickUserMenu(MIS_COMPRAS);
	}
	
	private void login() {
		dataTest.setUserConnected(userWithOnlinePurchases);
		dataTest.setPasswordUser(passUserWithOnlinePurchases);
		dataTest.setUserRegistered(true);
		new AccesoSteps().login();
	}
	
	private void checkCompraOnline() {
		var pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOfType(ONLINE, 5);
		pageMisComprasSteps.selectCompraOnline(1);
		
		//TODO TestAB (quickview o ficha) que en pro va por la variante-0 y en pre por la 1
		if (!(isPRO() && UtilsTest.todayBeforeDate("2023-11-01"))) {		
			pageMisComprasSteps.clickDetalleArticulo(1);
		}
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
		var pageMisComprasSteps = new PageMisComprasSteps();
		pageMisComprasSteps.validateIsCompraOfType(TIENDA, 5);
		pageMisComprasSteps.selectCompraTienda(1);
		pageMisComprasSteps.clickDetalleArticulo(1);
	}	
	
}
