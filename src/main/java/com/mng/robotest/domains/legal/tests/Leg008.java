package com.mng.robotest.domains.legal.tests;

import static com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink.AYUDA;

import com.mng.robotest.domains.ayuda.pageobjects.PageAyudaContact;
import com.mng.robotest.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;

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
		new SecFooterSteps().clickLinkFooter(AYUDA);
		
		var ayudaSteps = new AyudaSteps();
		String question = "¿Dónde está mi pedido?";
		ayudaSteps.checkIsQuestionVisible(question);
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
