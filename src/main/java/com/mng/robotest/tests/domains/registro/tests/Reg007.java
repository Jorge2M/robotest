package com.mng.robotest.tests.domains.registro.tests;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.tests.repository.usuarios.UserShop;
import com.mng.robotest.testslegacy.data.DataMango;
import com.mng.robotest.testslegacy.data.PaisShop;

import static com.mng.robotest.tests.domains.menus.entity.LineaType.*;
import static com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType.*;

public class Reg007 extends TestBase {

	private final PageRegistroInitialShopSteps pageRegistroInitSteps = new PageRegistroInitialShopSteps();
	private final PageRegistroPersonalizacionShopSteps pageRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();
	
	private final UserShop userShop = GestorUsersShop.getUser(PaisShop.ESPANA);

	public Reg007() {
		super();
	}
	
	private DataNewRegister getDataRegisterUserExistent() {
		var dataRegister = getDataRegisterOk();
		dataRegister.setEmail(userShop.getUser());
		dataRegister.setPassword("Mangolargo123");
		return dataRegister;
	}
	
	private DataNewRegister getDataRegisterPhoneIncorrect() {
		var dataRegister = getDataRegisterOk();
		dataRegister.setMovil("965015122");
		return dataRegister;
	}	
	
	private DataNewRegister getDataRegisterCodPostalIncorrect() {
		var dataRegister = getDataRegisterOk();
		dataRegister.setPostalCode("087201");
		return dataRegister;
	} 
	
	private DataNewRegister getDataRegisterOk() {
		String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
		String passStandard = GetterSecrets.factory().getCredentials(SHOP_ROBOT_USER).getPassword();
		
		return new DataNewRegister(
			emailNotExistent, 
			passStandard, 
			dataTest.getPais().getTelefono(), 
			true,
			"Jorge",
			dataTest.getPais().getCodpos(),
			"04/02/1974",
			GenderOption.MASCULINO,
			Arrays.asList(SHE, HE, KIDS));		
	}
	
	@Override
	public void execute() throws Exception {
		accesoAndClickRegistrate();
		inputInitialDataUserExistent();
		inputInitialDataPhoneIncorrect();
		inputInitialDataOkAndClickCreate();
		inputPersonalizationDataCodPostalIncorrect();
	}

	private void accesoAndClickRegistrate() throws Exception {
		access();
		new MenusUserSteps().selectRegistrate();
	}	
	
	private void inputInitialDataUserExistent() {
		pageRegistroInitSteps.inputData(getDataRegisterUserExistent());
		pageRegistroInitSteps.clickCreateAccountButtonWithoutCheck();
		pageRegistroInitSteps.checkUserExistsMessage(10);
		pageRegistroInitSteps.closeModalError();
	}
	
	private void inputInitialDataPhoneIncorrect() {
		pageRegistroInitSteps.inputData(getDataRegisterPhoneIncorrect());
		pageRegistroInitSteps.clickCreateAccountButtonWithoutCheck();
		pageRegistroInitSteps.checkPhoneInvalidMessage(10);
	}	
	
	private void inputInitialDataOkAndClickCreate() {
		pageRegistroInitSteps.inputData(getDataRegisterOk());
		pageRegistroInitSteps.clickCreateAccountButton();
	}
	
	private void inputPersonalizationDataCodPostalIncorrect() {
		pageRegistroPersonalizacionSteps.inputData(getDataRegisterCodPostalIncorrect());
		pageRegistroPersonalizacionSteps.clickGuardarNoChecks();
		pageRegistroPersonalizacionSteps.checkCodPostalInvalidMessage(5);
	}

}
