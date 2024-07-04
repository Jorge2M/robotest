package com.mng.robotest.tests.domains.registro.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.exceptions.CustomerNotCreatedException;
import com.mng.robotest.tests.domains.registro.steps.CustomerRepositorySteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.tests.repository.customerrepository.entity.Customer;

public class Reg009 extends TestBase {

	private final PageRegistroInitialShopSteps pgRegistroInitialSteps = new PageRegistroInitialShopSteps();
	private final PageRegistroPersonalizacionShopSteps pgRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();
	private final DataNewRegister dataRegister = makeDataRegister();

	private DataNewRegister makeDataRegister() {
		var dataNewRegister = DataNewRegister.makeDefault(dataTest.getPais());
		dataNewRegister.setCheckPromotions(false);
		return dataNewRegister;
	}
	
	@Override
	public void execute() throws Exception {
		makeClientWithContactability();
		accesoAndClickRegistrate();
		inputInitialDataAndClickCreate();
		inputPersonalizedDataAndClickGuardar();
		checkContactability();
	}
	
	private Customer makeClientWithContactability() throws CustomerNotCreatedException {
		return new CustomerRepositorySteps()
				.makeClientWithContactability(dataRegister.getEmail());
	}
	
	private void accesoAndClickRegistrate() throws Exception {
		access();
		new MenusUserSteps().selectRegistrate();
	}	
	
	private void inputInitialDataAndClickCreate() {
		pgRegistroInitialSteps.inputData(dataRegister);
		pgRegistroInitialSteps.clickCreateAccountButton();
	}

	private void inputPersonalizedDataAndClickGuardar() {
		pgRegistroPersonalizacionSteps.inputData(dataRegister);
		pgRegistroPersonalizacionSteps.clickGuardar();
	}
	
	private void checkContactability() {
		new CustomerRepositorySteps().isUserMantainsContactability(dataRegister.getEmail());
	}
	
}
