package com.mng.robotest.tests.domains.registro.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Reg001 extends TestBase {

	private final PageRegistroInitialShopSteps pgRegistroInitialSteps = new PageRegistroInitialShopSteps();
	private final PageRegistroPersonalizacionShopSteps pgRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();
	private final DataNewRegister dataNewRegister;

	public Reg001(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		dataNewRegister = DataNewRegister.makeDefault(pais);
	}
	
	@Override
	public void execute() throws Exception {
		accesoAndClickRegistrate();
		if (!isCorea()) {
			selectLinkPoliticaPrivacidad();
		}
		inputInitialDataAndClickCreate();
		inputPersonalizedDataAndClickGuardar();
		checkLoginAndUserData();
	}

	private void accesoAndClickRegistrate() throws Exception {
		access();
		new SecMenusUserSteps().selectRegistrate();
	}	
	
	private void inputBirthDateAndConsentPersonalInfo() {
		pgRegistroInitialSteps.clickConsentPersonalInformationLink();
		pgRegistroInitialSteps.clickConsentPersonalInformationRadio();
		pgRegistroInitialSteps.inputBirthDate(dataNewRegister.getDateOfBirth());
	}
	
	private void selectLinkPoliticaPrivacidad() {
		pgRegistroInitialSteps.clickPoliticaPrivacidad();
		pgRegistroInitialSteps.clickPoliticaPrivacidadModal();
		pgRegistroInitialSteps.clickCondicionesVenta();
	}
	
	private void inputInitialDataAndClickCreate() {
		pgRegistroInitialSteps.inputData(dataNewRegister);
		if (isCorea()) {
			inputBirthDateAndConsentPersonalInfo();	
		}
		pgRegistroInitialSteps.clickCreateAccountButton();
	}

	private void inputPersonalizedDataAndClickGuardar() {
		pgRegistroPersonalizacionSteps.inputData(dataNewRegister);
		pgRegistroPersonalizacionSteps.clickGuardar();
	}
	
	private void checkLoginAndUserData() {
		new SecMenusUserSteps().logoffLogin(dataNewRegister.getEmail(), dataNewRegister.getPassword());
		var pgMiCuentaSteps = new MiCuentaSteps();
		pgMiCuentaSteps.goToMisDatosAndValidateData(dataNewRegister);
		pgMiCuentaSteps.goToSuscripcionesAndValidateData(dataNewRegister.getLineas());
	}	

	private boolean isCorea() {
		return isCountry(COREA_DEL_SUR);
	}

}
