package com.mng.robotest.tests.domains.compra.tests;

import java.util.Map;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.tests.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.testslegacy.datastored.DataPago;
import com.mng.robotest.testslegacy.steps.navigations.shop.NavigationsSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Com005 extends TestBase {
	
	@Override
	public void execute() throws Exception {
		DataPago dataPago = fromPrehomeToCheckout();
		goToPortada();
		
		if (!isVotf()) {
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

	private void goToPortada() {
		new NavigationsSteps().gotoPortada();
	}

	private void loginWithNewUser(String usrEmail, String password) {
		new SecMenusUserSteps().logoffLogin(usrEmail, password);
	}	
	
	private void checkMisDatos(DataPago dataPago, String usrEmail, String password) {
		Map<String,String> datosRegistro = dataPago.getDatosRegistro();
		datosRegistro.put("cfEmail", usrEmail);
		datosRegistro.put("cfPass", password);
		datosRegistro.put("", "Barcelona");
		datosRegistro.put("provinciaPais", "Barcelona");
		new PageMiCuentaSteps().goToMisDatosAndValidateData(datosRegistro, dataTest.getCodigoPais());
	}	
}
