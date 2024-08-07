package com.mng.robotest.tests.domains.micuenta.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MisDatosSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.data.DataMango;

public class Mic003 extends TestBase {

	private final PageRegistroInitialShopSteps pageRegistroInitialSteps = new PageRegistroInitialShopSteps();
	private final String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
	private final String passStandard = GetterSecrets.factory().getCredentials(SecretType.SHOP_ROBOT_USER).getPassword();
	private final DataNewRegister dataNewRegister;

	public Mic003() {
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
		clickLogoMango();
		cancelarCuentaAndCheckLoginKO();
	}

	private void accesoAndClickRegistrate() throws Exception {
		access();
		new MenusUserSteps().selectRegistrate();
	}	
	
	private void inputInitialDataAndClickCreate() {
		pageRegistroInitialSteps.inputData(dataNewRegister);
		pageRegistroInitialSteps.clickCreateAccountButton();
	}
	
	private void cancelarCuentaAndCheckLoginKO() {
		new MiCuentaSteps().goToMisDatosAndCheckData(dataNewRegister);
		new MisDatosSteps().cancelarCuenta();
		new MenusUserSteps().logoff();
		new AccesoSteps().inicioSesionDatosKO(
				dataNewRegister.getEmail(), dataNewRegister.getPassword());
	}	

}
