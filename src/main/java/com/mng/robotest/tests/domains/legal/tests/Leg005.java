package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.article.ModalArticleNotAvailable;
import com.mng.robotest.tests.domains.galeria.steps.ModalArticleNotAvailableSteps;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;

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
		new GaleriaSteps().selectTallaNoDisponibleArticulo();
		new ModalArticleNotAvailableSteps().checkVisibleAvisame();
	}
	
	private void clickPoliticaPrivacidad() {
		new ModalArticleNotAvailableSteps().clickPoliticaPrivacidad();
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new ModalArticleNotAvailable());
	}
	
}
