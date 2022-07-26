package com.mng.robotest.domains.ayuda.tests;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.SecFooterStpV;


public class Ayu001 extends TestBase {

	final SecFooterStpV secFooterSteps = new SecFooterStpV(channel, app, driver);
	final AyudaSteps ayudaSteps = new AyudaSteps(driver);
	
	public Ayu001() throws Exception {
		super();
	}
	
	@Override
	public void execute() throws Exception {
		AccesoStpV.oneStep(dataTest, false, driver);
		checkAyuda(driver);
	}
	
	private void checkAyuda(WebDriver driver) throws Exception {
		secFooterSteps.clickLinkFooter(SecFooter.FooterLink.ayuda, false);
		ayudaSteps.clickIcon("Devoluciones, cambios y reembolsos");
		String question = "¿Cómo puedo cambiar o devolver una compra online de artículos Mango?";
		ayudaSteps.checkIsQuestionVisible(question);
		ayudaSteps.clickQuestion(question);
		ayudaSteps.checkIsTextVisible("Elige el tipo de devolución que mejor se adapte a tus necesidades");		
	}

}
