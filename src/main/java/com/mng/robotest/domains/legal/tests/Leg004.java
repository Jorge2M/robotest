package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;

import static com.mng.robotest.test.data.PaisShop.*;

/**
 * Control textos legales "Suscripción en footer y non-modal":
 * https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-Suscripcinenfooterynonmodal
 *
 */
public class Leg004 extends TestBase {

	@Override
	public void execute() throws Exception {
		checkTextoComunRGPD();
		renewTest();
		checkTurquia();
		renewTest();		
		checkNoRGPD();
	}
	
	private void checkTextoComunRGPD() throws Exception {
		dataTest.setPais(ESPANA.getPais());
		goToNewsletter();
		checkTextoLegal();
	}
	private void checkTurquia() throws Exception {
		dataTest.setPais(TURQUIA.getPais());
		goToNewsletter();
		checkTextoLegal();
	}
	
	private void checkNoRGPD() throws Exception {
		dataTest.setPais(SERBIA.getPais());
		goToNewsletter();
		checkTextoLegal();
	}	
	
	private void goToNewsletter() throws Exception {
		access();
		new SecFooterSteps().clickFooterSubscriptionInput();
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new SecFooter());
	}
	
}
