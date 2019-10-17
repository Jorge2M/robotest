package com.mng.robotest.test80.mango.test.appshop;

import java.net.URI;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.otras.BrowserStpV;
import com.mng.testmaker.service.TestMaker;


public class SEO {

	private InputParams inputParamsSuite = null;

    public SEO() {}

    @BeforeMethod(groups = { "Otras", "Canal:all_App:all" })
    public void login() throws Exception {
    	if (inputParamsSuite==null) {
    		inputParamsSuite = (InputParams)TestMaker.getInputParamsSuite();
    	}
    }
    
    private DataCtxShop getCtxShForTest() throws Exception {
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        return dCtxSh;
    }

    @Test(
        groups = { "Otras", "Canal:desktop_App:shop,outlet"}, 
        description="Comprobar existencia y contenido del fichero robots.txt")
    public void SEO001_check_RobotsSitemap() throws Exception {
    	DataCtxShop dCtxSh = getCtxShForTest();
    	WebDriver driver = TestMaker.getDriverTestCase();
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, driver)) {
            return;
        }
        String urlBaseTest = inputParamsSuite.getUrlBase();
        BrowserStpV.inputRobotsURLandValidate(urlBaseTest, dCtxSh.appE, driver);
        
        URI uriBase = new URI(urlBaseTest);
        String urlSitemap = urlBaseTest.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
        BrowserStpV.inputSitemapURLandValidate(urlSitemap, driver);
    }
}