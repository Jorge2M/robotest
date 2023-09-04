package com.mng.robotest.domains.micuenta.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisDatosSteps;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.domains.transversal.cabecera.steps.SecCabeceraSteps;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

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
		new SecMenusUserSteps().selectRegistrate();
	}	
	
	private void inputInitialDataAndClickCreate() {
		pageRegistroInitialSteps.inputData(dataNewRegister);
		pageRegistroInitialSteps.clickCreateAccountButton();
	}
	
	private void clickLogoMango() {
		new SecCabeceraSteps().selecLogo();
	}
	
	private void cancelarCuentaAndCheckLoginKO() {
		new PageMiCuentaSteps().goToMisDatosAndValidateData(dataNewRegister);
		new PageMisDatosSteps().cancelarCuenta();
		var secMenusUserSteps = new SecMenusUserSteps();
		secMenusUserSteps.logoff();
		new AccesoSteps().inicioSesionDatosKO(dataNewRegister.getEmail(), dataNewRegister.getPassword());
	}	

}
