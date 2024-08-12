package com.mng.robotest.tests.domains.registro.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNewRegister;
import com.mng.robotest.tests.domains.registro.pageobjects.CommonsRegisterObject;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroInitialShopSteps;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroPersonalizacionShopSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Reg001 extends TestBase {

	private final PageRegistroInitialShopSteps pgRegistroInitialSteps;
	private final PageRegistroPersonalizacionShopSteps pgRegistroPersonalizacionSteps;
	private final DataNewRegister dataNewRegister;

	public Reg001(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		pgRegistroInitialSteps = new PageRegistroInitialShopSteps();
		pgRegistroPersonalizacionSteps = new PageRegistroPersonalizacionShopSteps();		
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
		checkNewsletterSuscription();
		checkLoginAndUserData();
	}
	
	private void accesoAndClickRegistrate() throws Exception {
		access();
		new MenusUserSteps().selectRegistrate();
	}	
	
	private void inputBirthDateAndConsentPersonalInfo() {
		pgRegistroInitialSteps.clickConsentPersonalInformationLink();
		pgRegistroInitialSteps.clickConsentPersonalInformationRadio();
		pgRegistroInitialSteps.inputBirthDate(dataNewRegister.getDateOfBirth());
	}
	
	private void selectLinkPoliticaPrivacidad() {
		if (pgRegistroInitialSteps.clickPoliticaPrivacidad()) {
			if (new CommonsRegisterObject().isGenesis()) {
				pgRegistroInitialSteps.clickPoliticaPrivacidadModal();
				pgRegistroInitialSteps.clickCondicionesVenta();
			}
		}
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
		new MenusUserSteps().isVisibleNameUser(dataNewRegister.getName(), 3);
	}
	
	private void checkNewsletterSuscription() {
		new FooterSteps().clickFooterSubscriptionInput(false);
	}
	
	private void checkLoginAndUserData() {
		var menuUserSteps = new MenusUserSteps();
		menuUserSteps.logoffLogin(dataNewRegister.getEmail(), dataNewRegister.getPassword());
		menuUserSteps.isVisibleNameUser(dataNewRegister.getName(), 2);
		
		var miCuentaSteps = new MiCuentaSteps();
		miCuentaSteps.goToMisDatosAndCheckData(dataNewRegister);
		miCuentaSteps.goToSuscripcionesAndValidateData(dataNewRegister.getLineas());
	}	

	private boolean isCorea() {
		return isCountry(COREA_DEL_SUR);
	}

}
