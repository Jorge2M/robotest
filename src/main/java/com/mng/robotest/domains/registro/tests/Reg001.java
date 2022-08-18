package com.mng.robotest.domains.registro.tests;

import java.util.Arrays;

import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageMiCuentaSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Reg001 extends TestBase {

	private final PageRegistroInitialShopSteps pageRegistroInitialSteps = new PageRegistroInitialShopSteps();
	private final PageRegistroPersonalizacionShopSteps pageRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();
	
	private final String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
	private final String passStandard = GetterSecrets.factory().getCredentials(SecretType.SHOP_STANDARD_USER).getPassword();
	
	private final DataNewRegister dataNewRegister = new DataNewRegister(
			emailNotExistent, 
			passStandard, 
			"665015122", 
			true,
			"Jorge",
			"08720",
			"23/04/1974",
			GenderOption.MASCULINO,
			Arrays.asList(LineaType.she, LineaType.he, LineaType.kids));

	@Override
	public void execute() throws Exception {
		accesoAndClickRegistrate();
		inputInitialDataAndClickCreate();
		inputPersonalizedDataAndClickGuardar();
		checkLoginAndUserData();
	}

	private void accesoAndClickRegistrate() throws Exception {
		new AccesoSteps().oneStep(dataTest, false);
		new SecMenusUserSteps().selectRegistrate(dataTest);
	}	
	
	private void inputInitialDataAndClickCreate() {
		pageRegistroInitialSteps.inputData(dataNewRegister);
		pageRegistroInitialSteps.clickCreateAccountButton();
	}

	private void inputPersonalizedDataAndClickGuardar() {
		pageRegistroPersonalizacionSteps.inputData(dataNewRegister);
		pageRegistroPersonalizacionSteps.clickGuardar();
	}
	
	private void checkLoginAndUserData() throws Exception {
		new SecMenusUserSteps().logoffLogin(dataNewRegister.getEmail(), dataNewRegister.getPassword());
		PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
		pageMiCuentaSteps.goToMisDatosAndValidateData(dataNewRegister);
		pageMiCuentaSteps.goToSuscripcionesAndValidateData(dataNewRegister.getLineas());
	}	


}
