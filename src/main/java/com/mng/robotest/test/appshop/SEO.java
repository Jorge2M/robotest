package com.mng.robotest.test.appshop;

import java.net.URI;
import java.util.Optional;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.exceptions.NotFoundException;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.stpv.otras.BrowserStpV;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.github.jorge2m.testmaker.service.TestMaker;


public class SEO {

	public SEO() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		return dCtxSh;
	}

	@Test(
		groups = { "Otras", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar existencia y contenido del fichero robots.txt")
	public void SEO001_check_RobotsSitemap() throws Exception {
		TestCaseTM testCase = getTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = testCase.getDriver();
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			return;
		}
		
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		String urlBase = inputParamsSuite.getUrlBase();
		if (testCase.getInputParamsSuite().getApp()==AppEcom.shop) {
			BrowserStpV.inputRobotsURLandValidate(urlBase, dCtxSh.appE, driver);
		}

		URI uriBase = new URI(urlBase);
		String urlSitemap = urlBase.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
		BrowserStpV.inputSitemapURLandValidate(urlSitemap, driver);
	}
	
	private TestCaseTM getTestCase() throws NotFoundException {
		Optional<TestCaseTM> testCaseOpt = TestMaker.getTestCase();
		if (testCaseOpt.isEmpty()) {
		  throw new NotFoundException("Not found TestCase");
		}
		return testCaseOpt.get();
	}
}