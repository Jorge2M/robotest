package com.mng.robotest.domains.micuenta.tests;

import java.util.ArrayList;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.micuenta.pageobjects.PageSuscripciones.NewsLetter;
import com.mng.robotest.domains.micuenta.steps.PageDevolucionesSteps;
import com.mng.robotest.domains.micuenta.steps.PageMiCuentaSteps;
import com.mng.robotest.domains.micuenta.steps.PageMisDatosSteps;
import com.mng.robotest.domains.micuenta.steps.PageSuscripcionesSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;

public class Mic001 extends TestBase {

	private final PageMiCuentaSteps pageMiCuentaSteps = new PageMiCuentaSteps();
	private final PageMisDatosSteps pageMisDatosSteps = new PageMisDatosSteps();
	
	public Mic001(
			Pais pais, IdiomaPais idioma, 
			String userConDevolucionPeroSoloEnPRO, String passwordUserConDevolucion) {
		dataTest.userConnected = userConDevolucionPeroSoloEnPRO;
		dataTest.passwordUser = passwordUserConDevolucion;
		dataTest.userRegistered = true;
	}
	
	@Override
	public void execute() throws Exception {
		accessAndIdentification();
		checkMisDatos();
		checkMisCompras();
		checkSuscripciones();
		if (app!=AppEcom.outlet) {
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
		ArrayList<NewsLetter> listNewsletters = new ArrayList<>();
		listNewsletters.add(NewsLetter.she);
		new PageSuscripcionesSteps().selectNewslettersAndGuarda(listNewsletters);
	}

	private void checkMisCompras() {
		pageMiCuentaSteps.goToMisComprasFromMenu();
	}

	private void checkMisDatos() {
		pageMiCuentaSteps.goToMisDatos(dataTest.userConnected);
		String nombreActual = pageMisDatosSteps.modificaNombreYGuarda("Jorge", "George");
		pageMiCuentaSteps.goToMisDatos(dataTest.userConnected);
		pageMisDatosSteps.validaContenidoNombre(nombreActual);
	}

	private void accessAndIdentification() throws Exception {
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new AccesoSteps().identificacionEnMango();
	}

}
