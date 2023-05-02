package com.mng.robotest.domains.registro.tests;

import java.util.Arrays;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.registro.beans.DataNewRegister;
import com.mng.robotest.domains.registro.pageobjects.PageRegistroPersonalizacionShop.GenderOption;
import com.mng.robotest.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Reg001 extends TestBase {

	private final PageRegistroInitialShopSteps pageRegistroInitialSteps = new PageRegistroInitialShopSteps();
	private final PageRegistroPersonalizacionShopSteps pageRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();
	
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
				"04/23/1974",
				GenderOption.MASCULINO,
				Arrays.asList(LineaType.SHE, LineaType.HE, LineaType.KIDS));
	}
	
	@Override
	public void execute() throws Exception {
		accesoAndClickRegistrate();
		if (isCorea()) {
			inputBirthDateAndConsentPersonalInfo();			
		} else {
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
		pageRegistroInitialSteps.clickConsentPersonalInformationLink();
		pageRegistroInitialSteps.clickConsentPersonalInformationRadio();
		pageRegistroInitialSteps.inputBirthDate(dataNewRegister.getDateOfBirth());
	}
	
	private void selectLinkPoliticaPrivacidad() {
		pageRegistroInitialSteps.clickPoliticaPrivacidad();
		pageRegistroInitialSteps.clickPoliticaPrivacidadModal();
//		pageRegistroInitialSteps.closeModalPoliticaPrivacidad();
	}
	
	private void inputInitialDataAndClickCreate() {
		pageRegistroInitialSteps.inputData(dataNewRegister);
		pageRegistroInitialSteps.clickCreateAccountButton();
	}

	private void inputPersonalizedDataAndClickGuardar() {
		pageRegistroPersonalizacionSteps.inputData(dataNewRegister);
		pageRegistroPersonalizacionSteps.clickGuardar();
	}
	
	private void checkLoginAndUserData() {
		new SecMenusUserSteps().logoffLogin(dataNewRegister.getEmail(), dataNewRegister.getPassword());
		var pageMiCuentaSteps = new PageMiCuentaSteps();
		pageMiCuentaSteps.goToMisDatosAndValidateData(dataNewRegister);
		pageMiCuentaSteps.goToSuscripcionesAndValidateData(dataNewRegister.getLineas());
	}	

	private boolean isCorea() {
		return PaisShop.getPais(dataTest.getPais())==PaisShop.COREA_DEL_SUR;
	}

}
