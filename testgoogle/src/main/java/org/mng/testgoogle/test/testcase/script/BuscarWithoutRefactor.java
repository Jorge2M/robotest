package org.mng.testgoogle.test.testcase.script;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.service.webdriver.wrapper.WebdrvWrapp;

public class BuscarWithoutRefactor {

	@Test (
		groups={"Buscador", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Buscar un literal que devuelva algún resultado")
	public void BUS001_Buscar_literal_con_resultados() throws Exception {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		driver.get(testCase.getInputParamsSuite().getUrlBase());
		
		inputTextAndClickBuscarConGoogle("Wikipedia", driver);
	}
	
	@Step (
		description="Introducimos el texto <b>#{textToInput}</b> y clickamos el botón \"Buscar con Google\"",
		expected="Aparecen resultados de búsqueda")
	public void inputTextAndClickBuscarConGoogle(String textToInput, WebDriver driver) throws Exception {
		By byInputInicio = By.xpath("//input[@title='Buscar']");
		driver
			.findElement(byInputInicio)
			.sendKeys(textToInput);
		
		By byButtonBuscarConGoogle = By.xpath("//input[@class='gNO89b']");
		WebdrvWrapp.clickAndWaitLoad(driver, byButtonBuscarConGoogle);
		
		checkAreResults(driver);
	}
	
	@Validation(
		description="Aparecen resultados de búsqueda",
		level=State.Defect)
	public boolean checkAreResults(WebDriver driver) {
		By byEntradaResultado = By.xpath("//h3[@class='LC20lb']");
		return (
			WebdrvWrapp.isElementVisible(driver, byEntradaResultado));
	}
	
}