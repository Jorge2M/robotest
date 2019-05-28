package com.mng.robotest.test80.mango.test.appshop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;

/**
 * Test que comprueba los footers
 *
 */
public class Footer extends GestorWebDriver {

    Pais españa = null;
    IdiomaPais castellano = null;
    String mainWindow = "";

    /**
     * Constructor
     */
    public Footer() {}

    @BeforeMethod(groups = { "Footer", "Canal:all_App:shop", "Canal:desktop_App:outlet" })
    @Parameters({ "brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;
        
        //Si no existe, obtenemos el país España
        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        
        //Almacenamiento final a nivel de Thread (para disponer de 1 x cada @Test)
        TestCaseData.storeData(Constantes.idCtxSh, dCtxSh.clone());
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, "", dCtxSh.channel, context, method);
    }

    @SuppressWarnings({ "unused" })
    @AfterMethod(groups = { "Footer", "Canal:all_App:shop", "Canal:desktop_App:outlet" }, alwaysRun = true)
    public void logout(final ITestContext context, final Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }

    
    @Test(
        groups = { "Footer", "Canal:all_App:shop", "Canal:desktop_App:outlet" }, alwaysRun = true, 
        description="Verificar que los links del footer aparecen y redirigen correctamente")
    public void FOOT001_Menu() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        String urlInitialPage = dFTest.driver.getCurrentUrl();
        SecFooterStpV.validaLinksFooter(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        
        List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(dCtxSh.appE, dCtxSh.channel);
        //List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinks(dCtxSh.appE, dCtxSh.channel);
        for (FooterLink footerLinkToValidate : listFooterLinksToValidate) {
        	switch (footerLinkToValidate) {
        	case ayuda:
        		SecFooterStpV.clickLinkFooter(footerLinkToValidate, false, dCtxSh.channel, dFTest.driver);
        		SecFooterStpV.validaPaginaAyuda(dCtxSh.channel, dFTest.driver);
        		break;
        	case mango_card:
        		SecFooterStpV.clickLinkFooter(footerLinkToValidate, false, dCtxSh.channel, dFTest.driver);
                SecFooterStpV.checkSolicitarTarjeta(dCtxSh.channel, dFTest.driver);
        		break;
        	default:
                SecFooterStpV.clickLinkFooter(footerLinkToValidate, true, dCtxSh.channel, dFTest.driver);
                dFTest.driver.get(urlInitialPage);
        	}
        	
            dFTest.driver.get(urlInitialPage);
        }
    }
}
