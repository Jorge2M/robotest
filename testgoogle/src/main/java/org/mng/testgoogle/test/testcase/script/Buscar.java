package org.mng.testgoogle.test.testcase.script;

import org.mng.testgoogle.test.testcase.stpv.GoogleMainPageStpV;
import org.mng.testgoogle.test.testcase.stpv.ResultsGooglePageStpV;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.testmaker.domain.TestCaseTM;

public class Buscar {

	@Test (
		groups={"Buscar", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Se busca un literal que generará varias páginas de resultados")
	public void BUS001_Search_With_Results() throws Exception {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		driver.get(testCase.getInputParamsSuite().getUrlBase());
		
		GoogleMainPageStpV googlePageStpV = GoogleMainPageStpV.getNew(driver);
		ResultsGooglePageStpV resultsPageStpV = 
				googlePageStpV.searchTextWithManyResultPages("Legend of Zelda Breath of The Wild", driver);
	}	
	
	@Test (
		groups={"Buscar", "Canal:desktop_App:google"}, alwaysRun=true, 
		description="Se busca un literal que no generará varias páginas de resultados")
	public void BUS002_Search_Without_Results() throws Exception {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		WebDriver driver = testCase.getDriver();
		driver.get(testCase.getInputParamsSuite().getUrlBase());
		
		GoogleMainPageStpV googlePageStpV = GoogleMainPageStpV.getNew(driver);
		ResultsGooglePageStpV resultsPageStpV = 
				googlePageStpV.searchTextWithoutManyResultPages("43rf4sdfsdf4444", driver);
	}	
}
