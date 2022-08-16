package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.mng.robotest.domains.registro.beans.DataNino;
import com.mng.robotest.domains.registro.beans.ListDataNinos;
import com.mng.robotest.domains.registro.beans.DataNino.sexoType;
import com.mng.robotest.domains.registro.steps.PageRegistroDirecStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroFinStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroIniStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroNinosStepsOutlet;
import com.mng.robotest.domains.registro.steps.PageRegistroSegundaStepsOutlet;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.data.Constantes.ThreeState;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecCabeceraSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.steps.shop.micuenta.PageMiCuentaSteps;
import com.mng.robotest.test.steps.shop.modales.ModalSuscripcionSteps;
import com.mng.robotest.test.suites.RegistrosSuite.VersionRegistroSuite;


public class Reg003 extends TestBase {

	private final PageRegistroIniStepsOutlet pageRegistroIniSteps = new PageRegistroIniStepsOutlet();
	private final PageRegistroSegundaStepsOutlet pageRegistroSegundaSteps = new PageRegistroSegundaStepsOutlet();	
	private final PageRegistroNinosStepsOutlet pageRegistroNinosSteps = new PageRegistroNinosStepsOutlet();
	private final PageRegistroDirecStepsOutlet pageRegistroDirecSteps = new PageRegistroDirecStepsOutlet();
	private final PageRegistroFinStepsOutlet pageRegistroFinSteps = new PageRegistroFinStepsOutlet(dataTest);

	private final SecMenusUserSteps userMenusSteps = new SecMenusUserSteps();
	private final SecFooterSteps secFooterSteps = new SecFooterSteps(channel, app, driver);
	private final PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
	private final SecCabeceraSteps secCabeceraSteps = new SecCabeceraSteps(dataTest.pais, channel, app);
	
	private final VersionRegistroSuite version;
	private Map<String, String> dataRegister = new HashMap<>();
	
	public Reg003(Pais pais, IdiomaPais idioma, boolean accessFromFactory) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		dataTest.userRegistered = false;
		
		if (accessFromFactory) {
			version = VersionRegistroSuite.valueOf(inputParamsSuite.getVersion());
		} else {
			version = VersionRegistroSuite.V3;
		}
	}
	
	@Override
	public void execute() throws Exception {
		if (inputParamsSuite.getTypeAccess()==TypeAccess.Bat) {
			return;
		}
		
		AccesoSteps.oneStep(dataTest, false, driver);
		ModalSuscripcionSteps.validaRGPDModal(dataTest, driver);
		
		userMenusSteps.selectRegistrate(dataTest);
		if(version.register()) {
			registerAndGoShoppingSiPubli();
		} else {
			secFooterSteps.validaRGPDFooter(version.register(), dataTest);
		}

	}

	private void registerAndGoShoppingSiPubli() throws Exception {
		firstPageRegister();
		secondPageRegister();
		thirdPageRegister();
		goToShopping();
		if (version.loginAfterRegister()) {
			loginAfterRegister();		
		}
	}

	private void firstPageRegister() throws Exception {
		String emailNonExistent = DataMango.getEmailNonExistentTimestamp();
		dataRegister = 
			pageRegistroIniSteps.sendDataAccordingCountryToInputs(dataTest.pais, emailNonExistent, true);
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, dataRegister);
	}
	
	private void secondPageRegister() throws Exception {
		boolean paisConNinos = dataTest.pais.getShoponline().stateLinea(LineaType.nina, app)==ThreeState.TRUE;
		pageRegistroSegundaSteps.setDataAndLineasRandom("23/4/1974", paisConNinos, 2, dataTest.pais, dataRegister);
		if (paisConNinos) {
			ListDataNinos listaNinos = new ListDataNinos();
			listaNinos.add(new DataNino(sexoType.nina, "Martina Muñoz Rancaño", "11/10/2010"));
			listaNinos.add(new DataNino(sexoType.nina, "Irene Muñoz Rancaño", "29/8/2016"));
			pageRegistroNinosSteps.sendNinoDataAndContinue(listaNinos, dataTest.pais);
		}
	}	
	
	private void thirdPageRegister() throws Exception {
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister, dataTest.pais);
		pageRegistroDirecSteps.clickFinalizarButton(dataTest);
	}
	
	private void goToShopping() throws Exception {
		pageRegistroFinSteps.clickIrDeShoppingButton();
		secCabeceraSteps.selecLogo();
		secFooterSteps.validaRGPDFooter(version.register(), dataTest);
	}
	
	private void loginAfterRegister() throws Exception {
		String emailUsr = dataRegister.get("cfEmail");
		String password = dataRegister.get("cfPass");
		userMenusSteps.logoffLogin(emailUsr, password);

		pageMiCuentaSteps.goToMisDatosAndValidateData(dataRegister, dataTest.pais.getCodigo_pais());
		pageMiCuentaSteps.goToSuscripcionesAndValidateData(dataRegister);
	}	

}
