package com.mng.robotest.domains.ayuda.tests;

import com.mng.robotest.domains.ayuda.steps.AyudaSteps;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.utils.DataTest;

import com.github.jorge2m.testmaker.service.TestMaker;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;


public class Ayuda {
	
	public Ayuda() {}  
	
	@Test(
		groups = { "Ayuda", "Canal:all_App:shop" }, alwaysRun = true,
		description="Verificar que los elementos de la página ayuda están correctamente presentes")
	public void AYU001_Data() throws Exception {
		
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dataTest = DataTest.getData(PaisShop.ESPANA);

		SecFooterStpV secFooterSteps = new SecFooterStpV(dataTest.channel, dataTest.appE, driver);
		AyudaSteps ayudaSteps = new AyudaSteps(driver);
		
		AccesoStpV.oneStep(dataTest, false, driver);
		secFooterSteps.clickLinkFooter(SecFooter.FooterLink.ayuda, false);
		ayudaSteps.clickIcon("Devoluciones, cambios y reembolsos");
		String question = "¿Cómo puedo cambiar o devolver una compra online de artículos Mango?";
		ayudaSteps.checkIsQuestionVisible(question);
		ayudaSteps.clickQuestion(question);
		ayudaSteps.checkIsTextVisible("Elige el tipo de devolución que mejor se adapte a tus necesidades");
	}
}
