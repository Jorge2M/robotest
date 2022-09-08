package com.mng.robotest.domains.ayuda.tests;

import com.mng.robotest.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.transversal.TestBase;

public class Ayu001 extends TestBase {

	private final AyudaSteps ayudaSteps = new AyudaSteps();
	private final SecFooterSteps secFooterSteps = new SecFooterSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		checkAyuda();
	}
	
	private void checkAyuda() throws Exception {
		secFooterSteps.clickLinkFooter(SecFooter.FooterLink.AYUDA, false);
		ayudaSteps.clickIcon("Devoluciones, cambios y reembolsos");
		String question = "¿Cómo puedo cambiar o devolver una compra online de artículos Mango?";
		ayudaSteps.checkIsQuestionVisible(question);
		ayudaSteps.clickQuestion(question);
		ayudaSteps.checkIsTextVisible("Elige el tipo de devolución que mejor se adapte a tus necesidades");		
	}

}
