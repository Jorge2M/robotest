package com.mng.robotest.tests.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.mng.robotest.tests.conf.suites.RegistrosSuite.VersionRegistroSuite;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.domains.micuenta.steps.MiCuentaSteps;
import com.mng.robotest.tests.domains.registro.beans.DataNino;
import com.mng.robotest.tests.domains.registro.beans.ListDataNinos;
import com.mng.robotest.tests.domains.registro.beans.DataNino.sexoType;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroDirecStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroFinStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroIniStepsOld;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroNinosStepsOutlet;
import com.mng.robotest.tests.domains.registro.steps.PageRegistroSegundaStepsOutlet;
import com.mng.robotest.tests.domains.transversal.cabecera.steps.SecCabeceraSteps;
import com.mng.robotest.tests.domains.transversal.modales.pageobject.ModalSuscripcionSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.DataMango;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.utils.UtilsTest;
import com.mng.robotest.testslegacy.data.Constantes.ThreeState;

public class Reg003 extends TestBase {

	private final PageRegistroIniStepsOld pgRegistroIniSteps = new PageRegistroIniStepsOld();
	private final PageRegistroSegundaStepsOutlet pgRegistroSegundaSteps = new PageRegistroSegundaStepsOutlet();	
	private final PageRegistroNinosStepsOutlet pgRegistroNinosSteps = new PageRegistroNinosStepsOutlet();
	private final PageRegistroDirecStepsOutlet pgRegistroDirecSteps = new PageRegistroDirecStepsOutlet();
	private final PageRegistroFinStepsOutlet pgRegistroFinSteps = new PageRegistroFinStepsOutlet();

	private final MenusUserSteps userMenusSteps = new MenusUserSteps();
	private final MiCuentaSteps pgMiCuentaSteps;
	private final SecCabeceraSteps secCabeceraSteps = new SecCabeceraSteps();
	
	private final VersionRegistroSuite version;
	private Map<String, String> dataRegister = new HashMap<>();
	
	public Reg003(Pais pais, IdiomaPais idioma, boolean accessFromFactory) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		dataTest.setUserRegistered(false);
		pgMiCuentaSteps = new MiCuentaSteps();
		
		if (accessFromFactory) {
			version = VersionRegistroSuite.valueOf(inputParamsSuite.getVersion());
		} else {
			version = VersionRegistroSuite.V3;
		}
	}
	
	@Override
	public void execute() throws Exception {
		access();
		if (!isNotCheckInGermany()) {
			new ModalSuscripcionSteps().checkRGPDModal();
		}
		new MenusUserSteps().selectRegistrate();
		if(version.register()) {
			registerAndGoShoppingSiPubli();
		} else {
			if (!isNotCheckInGermany()) {
				new FooterSteps().clickFooterSubscriptionInput(version.register());
			}
		}
	}
	
	private boolean isNotCheckInGermany() {
		//The Newsletter suscription in the Germany Landing is unactive 
		//until Frontend implements double opt-in in this country
		return (
			PaisShop.DEUTSCHLAND.isEquals(dataTest.getPais()) &&
			UtilsTest.todayBeforeDate("2024-11-01"));
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
		dataRegister = pgRegistroIniSteps.sendDataAccordingCountryToInputs(emailNonExistent, true);
		pgRegistroIniSteps.clickRegistrateButton(dataRegister);
	}
	
	private void secondPageRegister() {
		boolean paisConNinos = dataTest.getPais().getShoponline().stateLinea(LineaType.NINA, app)==ThreeState.TRUE;
		pgRegistroSegundaSteps.setDataAndLineasRandom("23/4/1974", paisConNinos, 2, dataRegister);
		if (paisConNinos) {
			var listaNinos = new ListDataNinos();
			listaNinos.add(new DataNino(sexoType.NINA, "Martina Muñoz Rancaño", "11/10/2010"));
			listaNinos.add(new DataNino(sexoType.NINA, "Irene Muñoz Rancaño", "29/8/2016"));
			pgRegistroNinosSteps.sendNinoDataAndContinue(listaNinos);
		}
	}	
	
	private void thirdPageRegister() {
		pgRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister);
		pgRegistroDirecSteps.clickFinalizarButton();
	}
	
	private void goToShopping() {
		pgRegistroFinSteps.clickIrDeShoppingButton();
		if (!isNotCheckInGermany()) {
			secCabeceraSteps.selecLogo();
			new FooterSteps().clickFooterSubscriptionInput(version.register());
		}
	}
	
	private void loginAfterRegister() {
		String emailUsr = dataRegister.get("cfEmail");
		String password = dataRegister.get("cfPass");
		userMenusSteps.logoffLogin(emailUsr, password);

		pgMiCuentaSteps.goToMisDatosAndValidateData(dataRegister, dataTest.getCodigoPais());
		pgMiCuentaSteps.goToSuscripcionesAndValidateData(dataRegister);
	}	

}
