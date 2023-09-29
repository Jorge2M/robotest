package com.mng.robotest.tests.domains.ayuda.tests;

import static com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink.*;

import com.mng.robotest.tests.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;

public class Ayu001 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		checkAyuda();
	}
	
	private void checkAyuda() {
		new SecFooterSteps().clickLinkFooter(AYUDA, false);
		
		var ayudaSteps = new AyudaSteps();
		ayudaSteps.clickIcon("Devoluciones, cambios y reembolsos");
		String question = "¿Cómo puedo cambiar o devolver una compra online?";
		ayudaSteps.checkIsQuestionVisible(question);
		ayudaSteps.clickQuestion(question);
		ayudaSteps.checkIsTextVisible("Devolución gratuita en tienda");		
	}

}
