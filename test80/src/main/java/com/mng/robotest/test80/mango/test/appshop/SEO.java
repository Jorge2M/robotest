package com.mng.robotest.test80.mango.test.appshop;

import java.net.URI;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.otras.BrowserStpV;
import com.mng.testmaker.domain.suitetree.TestCaseTM;
import com.mng.testmaker.service.TestMaker;


public class SEO {

	public SEO() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		return dCtxSh;
	}

	@Test(
		groups = { "Otras", "Canal:desktop_App:shop,outlet"}, 
		description="Comprobar existencia y contenido del fichero robots.txt")
	public void SEO001_check_RobotsSitemap() throws Exception {
		TestCaseTM testCase = TestCaseTM.getTestCaseInExecution();
		DataCtxShop dCtxSh = getCtxShForTest();
		WebDriver driver = testCase.getDriver();
		if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
			return;
		}
		
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		String urlBase = inputParamsSuite.getUrlBase();
		if (testCase.getInputParamsSuite().getApp()==AppEcom.shop) {
			BrowserStpV.inputRobotsURLandValidate(urlBase, dCtxSh.appE, driver);
		}

		URI uriBase = new URI(urlBase);
		String urlSitemap = urlBase.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
		BrowserStpV.inputSitemapURLandValidate(urlSitemap, driver);
	}
}