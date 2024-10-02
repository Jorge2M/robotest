package com.mng.robotest.tests.domains.compra.tests;

import java.util.Map;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.testslegacy.steps.navigations.shop.CheckoutFlow.From;

public class Com005 extends TestBase {
	
	@Override
	public void execute() throws Exception {
		fromPrehomeToCheckout();
		goToPortada();
		
		var dataPago = dataTest.getDataPago();
		String usrEmail = dataPago.getDatosRegistro().get("cfEmail");
		String password = dataPago.getDatosRegistro().get("cfPass");
			
		loginWithNewUser(usrEmail, password);
		checkMisDatos(usrEmail, password);
	}

	private void fromPrehomeToCheckout() throws Exception {
		new BuilderCheckout(dataTest.getDataPago())
			.build()
			.checkout(From.PREHOME);
	}

	private void loginWithNewUser(String usrEmail, String password) {
		new MenusUserSteps().logoffLogin(usrEmail, password);
	}	
	
	private void checkMisDatos(String usrEmail, String password) throws Exception {
		var dataPago = dataTest.getDataPago();
		Map<String,String> datosRegistro = dataPago.getDatosRegistro();
		datosRegistro.put("cfEmail", usrEmail);
		datosRegistro.put("cfPass", password);
		datosRegistro.put("", "Barcelona");
		datosRegistro.put("provinciaPais", "Barcelona");
		new MiCuentaSteps().goToMisDatosAndValidateData(datosRegistro, dataTest.getCodigoPais());
	}	
}
