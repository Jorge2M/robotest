package com.mng.robotest.domains.compra.tests;

import java.util.Map;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.NavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;


public class Com005 extends TestBase {
	
	@Override
	public void execute() throws Exception {
		DataPago dataPago = fromPrehomeToCheckout();
		goToPortada();
		
		if (app!=AppEcom.votf) {
			String usrEmail = dataPago.getDatosRegistro().get("cfEmail");
			String password = dataPago.getDatosRegistro().get("cfPass");
			
			loginWithNewUser(usrEmail, password);
			checkMisDatos(dataPago, usrEmail, password);
		}			
	}

	private DataPago fromPrehomeToCheckout() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config().build();
		DataPago dataPago = getDataPago(configCheckout);
		dataPago = new BuilderCheckout(dataPago)
			.build()
			.checkout(From.PREHOME);
		return dataPago;
	}

	private void goToPortada() throws Exception {
		new NavigationsSteps().gotoPortada();
	}

	private void loginWithNewUser(String usrEmail, String password) throws Exception {
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		secMenusSteps.getMenusUser().logoffLogin(usrEmail, password);
	}	
	
	private void checkMisDatos(DataPago dataPago, String usrEmail, String password) {
		Map<String,String> datosRegistro = dataPago.getDatosRegistro();
		datosRegistro.put("cfEmail", usrEmail);
		datosRegistro.put("cfPass", password);
		datosRegistro.put("", "Barcelona");
		datosRegistro.put("provinciaPais", "Barcelona");
		new PageMiCuentaSteps()
			.goToMisDatosAndValidateData(datosRegistro, dataTest.pais.getCodigo_pais());
	}	
}
