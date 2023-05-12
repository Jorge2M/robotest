package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.pageobjects.ModalArticleNotAvailable;
import com.mng.robotest.domains.galeria.steps.ModalArticleNotAvailableSteps;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;

import static com.mng.robotest.test.data.PaisShop.*;

/**
 * Control textos legales "Avisame + suscripción":
 * https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-Avsamesuscripcin
 *
 */
public class Leg005 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkPaisComun();
	}
	
	private void checkPaisComun() throws Exception {
		dataTest.setPais(ESPANA.getPais());
		goToAvisame();
		clickPoliticaPrivacidad();
		checkTextoLegal();
	}
	
	private void goToAvisame() throws Exception {
		access();
		clickMenu("Camisas");
		new PageGaleriaSteps().selectTallaNoDisponibleArticulo();
		new ModalArticleNotAvailableSteps().checkVisibleAvisame();
	}
	
	private void clickPoliticaPrivacidad() {
		new ModalArticleNotAvailableSteps().clickPoliticaPrivacidad();
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new ModalArticleNotAvailable());
	}
	
}
