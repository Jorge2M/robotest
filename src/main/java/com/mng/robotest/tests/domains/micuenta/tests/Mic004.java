package com.mng.robotest.tests.domains.micuenta.tests;

import java.net.URISyntaxException;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisDatosSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.domains.transversal.cabecera.steps.SecCabeceraSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.data.DataMango;

public class Mic004 extends TestBase {

	private final String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
	private final String passStandard = GetterSecrets.factory().getCredentials(SecretType.SHOP_ROBOT_USER).getPassword();
	private final DataNewRegister dataNewRegister;

	public Mic004() {
		super();
		dataNewRegister = new DataNewRegister(
				emailNotExistent, 
				passStandard, 
				dataTest.getPais().getTelefono());
	}
	
	@Override
	public void execute() throws Exception {
		accesoAndClickRegistrate();
		inputInitialDataAndClickCreate();
		clickLogoMangoAndLogoff();
		openUrlAccountBajaInNewBrowser();
		loginRemoveAccountAndCheck();
	}

	private void accesoAndClickRegistrate() throws Exception {
		access();
		new MenusUserSteps().selectRegistrate();
	}	
	
	private void inputInitialDataAndClickCreate() {
		var pageRegistroInitialSteps = new PageRegistroInitialShopSteps();
		pageRegistroInitialSteps.inputData(dataNewRegister);
		pageRegistroInitialSteps.clickCreateAccountButton();
	}
	
	private void clickLogoMangoAndLogoff() {
		new SecCabeceraSteps().selecLogo();
		new MenusUserSteps().logoff();
	}
	
	private void openUrlAccountBajaInNewBrowser() throws URISyntaxException {
		new AccesoSteps().inputUrlAccountBajaAndAcceptCookiesInNewBrowser();
	}
	
	private void loginRemoveAccountAndCheck() {
		String user = dataNewRegister.getEmail();
		String password = dataNewRegister.getPassword();
		
		new AccesoSteps().login(user, password);		
		new PageMisDatosSteps().confirmCancelarCuenta();		
		new MenusUserSteps().logoff();
		new AccesoSteps().inicioSesionDatosKO(user, password);
	}	

}
