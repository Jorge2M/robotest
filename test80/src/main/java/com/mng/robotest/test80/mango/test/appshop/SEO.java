package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.net.URI;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.arq.utils.otras.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.otras.BrowserStpV;


public class SEO extends GestorWebDriver {

    boolean isMobile;
    boolean isOutlet;
    private DataCtxShop dCtxSh;

    public SEO() {
    }

    @BeforeMethod(groups = { "Otras", "Canal:all_App:all" })
    @Parameters({ "brwsr-path", "urlBase", "AppEcom", "Channel" })
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros comunes
        dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;

        Utils.storeDataShopForTestMaker(bpath, "", dCtxSh, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod(groups = { "Otras", "Canal:all_App:all" }, alwaysRun = true)
    public void logout(final ITestContext context, final Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }

    @Test(
        groups = { "Otras", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, 
        description="Comprobar existencia y contenido del fichero robots.txt")
    public void SEO001_check_RobotsSitemap() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest.driver)) {
            return;
        }
        String urlBaseTest = (String)dFTest.ctx.getAttribute("appPath");
        BrowserStpV.inputRobotsURLandValidate(urlBaseTest, dCtxSh.appE, dFTest.driver);
        
        URI uriBase = new URI(urlBaseTest);
        String urlSitemap = urlBaseTest.replace(uriBase.getPath(), "") + "/" + "sitemap.xml";
        BrowserStpV.inputSitemapURLandValidate(urlSitemap, dFTest.driver);
    }
}