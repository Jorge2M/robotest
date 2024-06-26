package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink.AYUDA;

import com.mng.robotest.tests.domains.ayuda.pageobjects.PageAyudaContact;
import com.mng.robotest.tests.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;

/**
 * Control textos legales: Formulario de ayuda (Genki)
 * https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-FormulariodeayudaGenki
 *
 */
public class Leg008 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		goToAyudaWithLegalText();
		checkTextoLegal();
	}
	
	private void goToAyudaWithLegalText() {
		new FooterSteps().clickLinkFooter(AYUDA);
		
		var ayudaSteps = new AyudaSteps();
		String question = "¿Dónde está mi pedido?";
		ayudaSteps.checkIsQuestionVisible(question, 1);
		ayudaSteps.clickQuestion(question);

		ayudaSteps.clickContactarButton();
		ayudaSteps.clickEscribenosUnMensaje();
		ayudaSteps.inputsInFormularioAyuda(
				"Tienda física", 
				"Información sobre cambios y devoluciones");
	}
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new PageAyudaContact());
	}	
}
