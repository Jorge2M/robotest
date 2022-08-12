package com.mng.robotest.domains.compra.tests;

import java.util.Map;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.beans.ConfigCheckout;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.datastored.DataCtxPago;
import com.mng.robotest.test.steps.navigations.shop.NavigationsSteps;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.BuilderCheckout;
import com.mng.robotest.test.steps.navigations.shop.CheckoutFlow.From;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageMiCuentaSteps;


public class Com005 extends TestBase {
	
	public Com005() throws Exception {}
	
	@Override
	public void execute() throws Exception {
		DataCtxPago dataPago = fromPrehomeToCheckout();
		goToPortada();
		
		if (app!=AppEcom.votf) {
			String usrEmail = dataPago.getDatosRegistro().get("cfEmail");
			String password = dataPago.getDatosRegistro().get("cfPass");
			
			loginWithNewUser(usrEmail, password);
			checkMisDatos(dataPago, usrEmail, password);
		}			
	}

	private DataCtxPago fromPrehomeToCheckout() throws Exception {
		ConfigCheckout configCheckout = ConfigCheckout.config().build();
		DataCtxPago dCtxPago = new DataCtxPago(dataTest, configCheckout);
		dCtxPago = new BuilderCheckout(dataTest, dCtxPago)
			.build()
			.checkout(From.PREHOME);
		return dCtxPago;
	}

	private void goToPortada() throws Exception {
		NavigationsSteps.gotoPortada(dataTest, driver);
	}

	private void loginWithNewUser(String usrEmail, String password) throws Exception {
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dataTest);
		secMenusSteps.getMenusUser().logoffLogin(usrEmail, password);
	}	
	
	private void checkMisDatos(DataCtxPago dataPago, String usrEmail, String password) {
		Map<String,String> datosRegistro = dataPago.getDatosRegistro();
		datosRegistro.put("cfEmail", usrEmail);
		datosRegistro.put("cfPass", password);
		datosRegistro.put("", "Barcelona");
		datosRegistro.put("provinciaPais", "Barcelona");
		new PageMiCuentaSteps()
			.goToMisDatosAndValidateData(datosRegistro, dataTest.pais.getCodigo_pais());
	}	
}
