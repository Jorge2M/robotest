package com.mng.robotest.test80.access.rest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.StateExecution;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.domain.suitetree.StepTM;
import com.github.jorge2m.testmaker.domain.suitetree.SuiteBean;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseBean;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Suites;


public class RobotestRestIT extends ServerRestIT {

	@Test
	public void executeTestCase() throws Exception {

		//Given
		InputParamsRobotest inputParams = InputParamsRobotest.from(
				Suites.SmokeTest, 
				"chromehless", 
				Channel.desktop, 
				AppEcom.shop, 
				"https://shop.mango.com/preHome.faces")
				.tcase("BUS001{2-2}")
				.asyncExec(false);
		
		//When
		Thread.sleep(10);
		SuiteBean suiteData = executeTestsAgainstServer(inputParams);

		//Then
		checkResultStandarTestCase(suiteData);
	}
	
	@Test
	public void testFactoryTestCase() throws Exception {
		
		//Given
		InputParamsRobotest inputParams = InputParamsRobotest.from(
				Suites.PagosPaises,
				"chromehless", 
				Channel.desktop, 
				AppEcom.shop, 
				"https://shop.mango.com/preHome.faces")
				.asyncExec(false)
				.version("V1")
				.countrys(Arrays.asList("001","011"));
		
		//When
		SuiteBean suiteData = executeTestsAgainstServer(inputParams);
		
		//Then
		checkResultFactoryTestCase(suiteData);
	}
	
	private void checkResultStandarTestCase(SuiteBean suiteData) {

		//Check Suite
		assertTrue(suiteData!=null);
		assertTrue(suiteData.getResult()==State.Ok || suiteData.getResult()==State.Info || suiteData.getResult()==State.Warn);
		assertEquals(suiteData.getStateExecution(), StateExecution.Finished);
		assertEquals(suiteData.getListTestRun().size(), 1);
		
		//Check TestCases
		List<TestCaseBean> listTestCases = suiteData.getListTestRun().get(0).getListTestCase();
		assertEquals(listTestCases.size(), 2);
		TestCaseBean testCase = listTestCases.get(0);
		assertTrue(testCase.getResult()==State.Ok || suiteData.getResult()==State.Warn || testCase.getResult()==State.Info);
		assertEquals(testCase.getListStep().size(), 5);
		
		//Check Step1
		StepTM step1 = testCase.getListStep().get(0);
		assertTrue(
			"Check descripción Step1 (\"" + step1.getDescripcion() + "\")",
			"Acceder a Mango (España (Península y Baleares)/Castellano)<br>"
			.compareTo(step1.getDescripcion())==0);
		assertTrue(step1.getResultSteps()==State.Ok || step1.getResultSteps()==State.Info);
		assertEquals(step1.getListChecksTM().size(), 0);
		
		//Check Validation1 (from Step2)
		StepTM step2 = testCase.getListStep().get(1);
		ChecksTM checkGroup1 = step2.getListChecksTM().get(0);
		assertTrue(checkGroup1.getStateValidation()==State.Ok || checkGroup1.getStateValidation()==State.Info);
		assertEquals(checkGroup1.getListChecks().size(), 1);
		Check check1 = checkGroup1.getListChecks().get(0);
		assertTrue(
			"Check descripción Step1 (\"" + check1.getDescription() + "\")",
			check1.getDescription().contains("No es visible la sección inferior para la configuración de las cookies"));
		assertTrue(check1.getStateResult()==State.Ok || check1.getStateResult()==State.Info);
		
		checkReporsSuiteExists(suiteData);
	
//		//Check hardcopy Step-1 exists
//		String pathEvidences = getPathEvidences(suiteData, testCase);
//		File step1png = new File(pathEvidences + "/Step-1.png");
//		assertTrue(step1png.exists());
	}
	
	private void checkResultFactoryTestCase(SuiteBean suiteData) throws Exception {
		
		//Check Suite
		assertTrue(suiteData!=null);
		assertEquals(suiteData.getResult(), State.Ok);
		assertEquals(suiteData.getStateExecution(), StateExecution.Finished);
		assertEquals(suiteData.getListTestRun().size(), 1);
		
		//Check TestCases
		List<TestCaseBean> listTestCases = suiteData.getListTestRun().get(0).getListTestCase();
		assertTrue(listTestCases.size()==2);
		assertTrue(
			"Check existe el test para la búsqueda: " + "France (011)", 
			checkExistsTestCase("France (011)", listTestCases));
		assertTrue(
			"Check existe el test para la búsqueda: " + "España (Península y Baleares) (001)", 
			checkExistsTestCase("España (Península y Baleares) (001)", listTestCases));
		
		TestCaseBean testCase = listTestCases.get(0);
		assertTrue(testCase.getResult()==State.Ok || testCase.getResult()==State.Info || testCase.getResult()==State.Warn);
		assertEquals(testCase.getListStep().size(), 8);
		
		//Check Step4
		StepTM step4 = testCase.getListStep().get(3);
		assertTrue(
			"Check descripción Step4 (\"" + step4.getDescripcion() + "\")",
			step4.getDescripcion().contains("Se selecciona el botón"));
		
		assertTrue(step4.getResultSteps()==State.Ok || step4.getResultSteps()==State.Info || step4.getResultSteps()==State.Warn);
		assertEquals(step4.getListChecksTM().size(), 3);
		
		//Check Validation1 (from Step4)
		ChecksTM checkGroup1 = step4.getListChecksTM().get(1);
		assertTrue(checkGroup1.getStateValidation()==State.Ok || checkGroup1.getStateValidation()==State.Info || checkGroup1.getStateValidation()==State.Warn);
		assertEquals(checkGroup1.getListChecks().size(), 1);
		
		checkReporsSuiteExists(suiteData);
	
		//Check Evidences Step-3 exists
//		String pathEvidences = getPathEvidences(suiteData, testCase);
//		File step3png = new File(pathEvidences + "/Step-3.png");
//		File step3html = new File(pathEvidences + "/Step-3.html");
//		assertTrue(step3png.exists());
//		assertTrue(step3html.exists());
	}

}
