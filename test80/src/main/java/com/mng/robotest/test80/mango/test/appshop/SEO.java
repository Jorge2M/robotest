package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.ThreadData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.GestorWebDriver;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.stpv.otras.BrowserStpV;

@SuppressWarnings("javadoc")
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

        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        ThreadData.storeInThread(dCtxSh);        
        ThreadData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod(groups = { "Otras", "Canal:all_App:all" }, alwaysRun = true)
    public void logout(final ITestContext context, final Method method) throws Exception {
        WebDriver driver = ThreadData.getWebDriver();
        super.quitWebDriver(driver, context);
    }

    @Test(
        groups = { "Otras", "Canal:desktop_App:shop", "Canal:desktop_App:outlet"}, 
        description="Comprobar existencia y contenido del fichero robots.txt")
    public void SEO001_check_RobotsSitemap(final ITestContext context, final Method method) throws Exception {
    	DataFmwkTest dFTest = ThreadData.getdFTest();
        DataCtxShop dCtxSh = ThreadData.getdCtxSh();
        
        //Este test sólo aplica al entorno productivo
        if (!UtilsMangoTest.isEntornoPRO(dCtxSh.appE, dFTest))
            return;
        
        String urlBaseTest = (String)dFTest.ctx.getAttribute("appPath");
        BrowserStpV.inputRobotsURLandValidate(urlBaseTest, dCtxSh.appE, dFTest);
        BrowserStpV.inputSitemapURLandValidate(urlBaseTest, dFTest);
    }
}