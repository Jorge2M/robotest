package com.mng.robotest.domains.ayuda.tests;

import com.mng.robotest.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;


public class Ayu001 extends TestBase {

	final SecFooterSteps secFooterSteps = new SecFooterSteps(channel, app, driver);
	final AyudaSteps ayudaSteps = new AyudaSteps();
	
	public Ayu001() throws Exception {
		super();
	}
	
	@Override
	public void execute() throws Exception {
		AccesoSteps.oneStep(dataTest, false, driver);
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
