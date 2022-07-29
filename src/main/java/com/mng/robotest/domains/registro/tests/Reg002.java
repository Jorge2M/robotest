package com.mng.robotest.domains.registro.tests;

import java.util.HashMap;
import java.util.Map;

import com.github.jorge2m.testmaker.domain.InputParamsTM.TypeAccess;
import com.mng.robotest.domains.registro.pageobjects.beans.DataNino;
import com.mng.robotest.domains.registro.pageobjects.beans.ListDataNinos;
import com.mng.robotest.domains.registro.pageobjects.beans.DataNino.sexoType;
import com.mng.robotest.domains.registro.steps.PageRegistroDirecSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroFinSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroIniSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroNinosSteps;
import com.mng.robotest.domains.registro.steps.PageRegistroSegundaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataMango;
import com.mng.robotest.test.data.Constantes.ThreeState;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.SecCabeceraStpV;
import com.mng.robotest.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.stpv.shop.micuenta.PageMiCuentaStpV;
import com.mng.robotest.test.stpv.shop.modales.ModalSuscripcionStpV;
import com.mng.robotest.test.suites.RegistrosSuite.VersionRegistroSuite;


public class Reg002 extends TestBase {

	private final SecMenusUserSteps userMenusSteps = SecMenusUserSteps.getNew(channel, app, driver);
	private final SecFooterStpV secFooterSteps = new SecFooterStpV(channel, app, driver);
	private final PageRegistroIniSteps pageRegistroIniSteps = new PageRegistroIniSteps(driver);
	private final PageRegistroSegundaSteps pageRegistroSegundaSteps = new PageRegistroSegundaSteps(driver);;
	private final PageMiCuentaStpV pageMiCuentaSteps = PageMiCuentaStpV.getNew(channel, app, driver);
	private final PageRegistroNinosSteps pageRegistroNinosSteps = new PageRegistroNinosSteps(driver);
	private final PageRegistroDirecSteps pageRegistroDirecSteps = new PageRegistroDirecSteps(driver);
	private final PageRegistroFinSteps pageRegistroFinSteps;
	private final SecCabeceraStpV secCabeceraSteps = SecCabeceraStpV.getNew(dataTest.pais, channel, app, driver);
	
	private final VersionRegistroSuite version;
	private Map<String, String> dataRegister = new HashMap<>();

	
	public Reg002(Pais pais, IdiomaPais idioma, boolean accessFromFactory) throws Exception {
		super();
		dataTest.pais = pais;
		dataTest.idioma = idioma;
		dataTest.userRegistered = false;
		pageRegistroFinSteps = new PageRegistroFinSteps(dataTest, driver);
		
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
		
		AccesoStpV.oneStep(dataTest, false, driver);
		ModalSuscripcionStpV.validaRGPDModal(dataTest, driver);
		
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
			pageRegistroIniSteps.sendDataAccordingCountryToInputs(dataTest.pais, emailNonExistent, true, channel);
		pageRegistroIniSteps.clickRegistrateButton(dataTest.pais, app, dataRegister);
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
		pageRegistroDirecSteps.sendDataAccordingCountryToInputs(dataRegister, dataTest.pais, channel);
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
