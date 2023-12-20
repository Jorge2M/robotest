package com.mng.robotest.tests.domains.compra.tests;

import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.APELLIDOS;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.CODPOSTAL;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.DIRECCION;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.EMAIL;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.NAME;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.NIF;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.POBLACION;
import static com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion.DataDirType.TELEFONO;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DataDireccion;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page1IdentCheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.Page2IdentCheckoutSteps;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;

public class Com003 extends TestBase {

	private final CheckoutSteps checkoutSteps = new CheckoutSteps();
	
	@Override
	public void execute() throws Exception {
        access();
		altaArticulosBolsaAndClickComprar();
		goToCheckout();
		
        if (isShop()) {
            checkSolicitarFactura();
        }

        if (!isVotf()) {
            checkEditarDirecEnvio();
        }	        	
	}
	
	private void goToCheckout() {
		var userEmail = getUserEmail();
		var page1IdentCheckoutSteps = new Page1IdentCheckoutSteps(); 
		page1IdentCheckoutSteps.checkIsPage(7);
		page1IdentCheckoutSteps.inputEmailAndContinue(userEmail, true);
		
		var page2IdentCheckoutSteps = new Page2IdentCheckoutSteps();
		page2IdentCheckoutSteps.inputDataPorDefecto(userEmail, false);
		page2IdentCheckoutSteps.clickContinuar(false);
	}
	
	private void checkSolicitarFactura() {
	    checkoutSteps.clickSolicitarFactura();
	    var dataDirFactura = createSampleDataDireccion();
	    checkoutSteps.getModalDirecFacturaSteps().inputDataAndActualizar(dataDirFactura);
	}

	private void checkEditarDirecEnvio() {
	    checkoutSteps.clickEditarDirecEnvio();
	    var dataDirEnvio = createSampleDataDireccion();
	    checkoutSteps.getModalDirecEnvioSteps().inputDataAndActualizar(dataDirEnvio);
	}
	
	private DataDireccion createSampleDataDireccion() {
	    var dataDireccion = new DataDireccion();
	    dataDireccion.put(NIF, "76367949Z");
	    dataDireccion.put(NAME, "Carolina");
	    dataDireccion.put(APELLIDOS, "Rancaño Pérez");
	    dataDireccion.put(CODPOSTAL, "08720");
	    dataDireccion.put(DIRECCION, "c./ mossen trens nº6 5º1ª");
	    dataDireccion.put(EMAIL, getUserForEmail());
	    dataDireccion.put(TELEFONO, "665015122");
        dataDireccion.put(POBLACION, "PEREPAU");
	    return dataDireccion;
	}
	
	private String getUserForEmail() {
	    var userShop = GestorUsersShop.getUser();
	    return userShop.getUser();
	}	

}
