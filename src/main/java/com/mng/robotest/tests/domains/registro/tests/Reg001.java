package com.mng.robotest.tests.domains.registro.tests;

import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

import java.util.Arrays;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.DataMango;
import com.mng.robotest.testslegacy.data.PaisShop;

public class Reg001 extends TestBase {

	private final PageRegistroInitialShopSteps pgRegistroInitialSteps = new PageRegistroInitialShopSteps();
	private final PageRegistroPersonalizacionShopSteps pgRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();
	
	private final String emailNotExistent = DataMango.getEmailNonExistentTimestamp();
	private final String passStandard = GetterSecrets.factory().getCredentials(SecretType.SHOP_ROBOT_USER).getPassword();
	
	private final DataNewRegister dataNewRegister;

	public Reg001(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		
		dataNewRegister = new DataNewRegister(
				emailNotExistent, 
				passStandard, 
				pais.getTelefono(), 
				true,
				"Jorge",
				pais.getCodpos(),
				"04/02/1974",
				GenderOption.MASCULINO,
				Arrays.asList(SHE, HE, KIDS));
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
		var pgMiCuentaSteps = new PageMiCuentaSteps();
		pgMiCuentaSteps.goToMisDatosAndValidateData(dataNewRegister);
		pgMiCuentaSteps.goToSuscripcionesAndValidateData(dataNewRegister.getLineas());
	}	

	private boolean isCorea() {
		return PaisShop.getPais(dataTest.getPais())==PaisShop.COREA_DEL_SUR;
	}

}
