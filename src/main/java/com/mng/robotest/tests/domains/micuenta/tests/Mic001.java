package com.mng.robotest.tests.domains.micuenta.tests;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageSuscripciones.NewsLetter;
import com.mng.robotest.tests.domains.micuenta.steps.PageDevolucionesSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageMisDatosSteps;
import com.mng.robotest.tests.domains.micuenta.steps.PageSuscripcionesSteps;

public class Mic001 extends TestBase {

	private final PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
	private final PageMisDatosSteps pageMisDatosSteps = new PageMisDatosSteps();
	
	public Mic001(String userConDevolucionPeroSoloEnPRO, String passwordUserConDevolucion) {
		dataTest.setUserConnected(userConDevolucionPeroSoloEnPRO);
		dataTest.setPasswordUser(passwordUserConDevolucion);
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		checkMisDatos();
		checkMisCompras();
		checkSuscripciones();
		if (!isOutlet()) {
			checkDevoluciones();
			checkReembolsos();
		}
	}

	private void checkReembolsos() {
		pageMiCuentaSteps.goToReembolsos();
	}

	private void checkDevoluciones() {
		pageMiCuentaSteps.goToDevoluciones();
		new PageDevolucionesSteps().solicitarRegogidaGratuitaADomicilio();
	}

	private void checkSuscripciones() {
		pageMiCuentaSteps.goToSuscripciones();
		List<NewsLetter> listNewsletters = new ArrayList<>();
		listNewsletters.add(NewsLetter.she);
		new PageSuscripcionesSteps().selectNewslettersAndGuarda(listNewsletters);
	}

	private void checkMisCompras() {
		pageMiCuentaSteps.goToMisComprasFromMenu();
	}

	private void checkMisDatos() {
		pageMiCuentaSteps.goToMisDatos(dataTest.getUserConnected());
		String nombreActual = pageMisDatosSteps.modificaNombreYGuarda("Jorge", "George");
		pageMiCuentaSteps.goToMisDatos(dataTest.getUserConnected());
		pageMisDatosSteps.validaContenidoNombre(nombreActual);
	}

}
