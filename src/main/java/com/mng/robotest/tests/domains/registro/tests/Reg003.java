package com.mng.robotest.tests.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.tests.conf.suites.RegistrosSuite.VersionRegistroSuite;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNino;
import com.mng.robotest.tests.domains.registro.beans.ListDataNinos;
import com.mng.robotest.tests.domains.registro.beans.DataNino.sexoType;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroDirecStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroFinStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroNinosStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroSegundaStepsOutlet;
import com.mng.robotest.tests.domains.transversal.cabecera.steps.SecCabeceraSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;
import com.mng.robotest.tests.domains.transversal.modales.pageobject.ModalSuscripcionSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.DataMango;
import com.mng.robotest.testslegacy.data.Constantes.ThreeState;

public class Reg003 extends TestBase {

	private final PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
	private final PageRegistroSegundaStepsOutlet pageRegistroSegundaSteps = new PageRegistroSegundaStepsOutlet();	
	private final PageRegistroNinosStepsOutlet pageRegistroNinosSteps = new PageRegistroNinosStepsOutlet();
	private final PageRegistroDirecStepsOutlet pageRegistroDirecSteps = new PageRegistroDirecStepsOutlet();
	private final PageRegistroFinStepsOutlet pageRegistroFinSteps = new PageRegistroFinStepsOutlet();

	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	private final PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
	private final SecCabeceraSteps secCabeceraSteps = new SecCabeceraSteps();
	
	private final VersionRegistroSuite version;
	private Map<String, String> dataRegister = new HashMap<>();
	
	public Reg003(Pais pais, IdiomaPais idioma, boolean accessFromFactory) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		dataTest.setUserRegistered(false);

		if (accessFromFactory) {
			version = VersionRegistroSuite.valueOf(inputParamsSuite.getVersion());
		} else {
			version = VersionRegistroSuite.V3;
		}
	}
	
	@Override
	public void execute() throws Exception {
		access();
		new ModalSuscripcionSteps().validaRGPDModal();
		new SecMenusUserSteps().selectRegistrate();
		if(version.register()) {
			registerAndGoShoppingSiPubli();
		} else {
			new SecFooterSteps().clickFooterSubscriptionInput(version.register());
		}

	}

	private void registerAndGoShoppingSiPubli() {
		firstPageRegister();
		secondPageRegister();
		thirdPageRegister();
		goToShopping();
		if (version.loginAfterRegister()) {
			loginAfterRegister();		
		}
	}

	private void firstPageRegister() {
		String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
		dataRegister = pageRegistroIniSteps.sendDataAccordingCountryToInputs(emailNonExistent, true);
		pageRegistroIniSteps.clickRegistrateButton(dataRegister);
	}
	
	private void secondPageRegister() {
		boolean paisConNinos = dataTest.getPais().getShoponline().stateLinea(LineaType.NINA, app)==ThreeState.TRUE;
		pageRegistroSegundaSteps.setDataAndLineasRandom("23/4/1974", paisConNinos, 2, dataRegister);
		if (paisConNinos) {
			var listaNinos = new ListDataNinos();
			listaNinos.add(new DataNino(sexoType.nina, "Martina Muñoz Rancaño", "11/10/2010"));
			listaNinos.add(new DataNino(sexoType.nina, "Irene Muñoz Rancaño", "29/8/2016"));
			pageRegistroNinosSteps.sendNinoDataAndContinue(listaNinos);
		}
	}	
	
	private void thirdPageRegister() {
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister);
		pageRegistroDirecSteps.clickFinalizarButton();
	}
	
	private void goToShopping() {
		pageRegistroFinSteps.clickIrDeShoppingButton();
		secCabeceraSteps.selecLogo();
		new SecFooterSteps().clickFooterSubscriptionInput(version.register());
	}
	
	private void loginAfterRegister() {
		String emailUsr = dataRegister.get("cfEmail");
		String password = dataRegister.get("cfPass");
		userMenusSteps.logoffLogin(emailUsr, password);

		pageMiCuentaSteps.goToMisDatosAndValidateData(dataRegister, dataTest.getCodigoPais());
		pageMiCuentaSteps.goToSuscripcionesAndValidateData(dataRegister);
	}	

}