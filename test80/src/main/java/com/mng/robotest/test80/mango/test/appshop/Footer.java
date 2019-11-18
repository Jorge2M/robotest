package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.testmaker.service.TestMaker;

/**
 * Test que comprueba los footers
 *
 */
public class Footer {

    private final static Integer codEspanya = Integer.valueOf(1);
    private final static List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codEspanya)));
    private final static Pais españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
    private final static IdiomaPais castellano = españa.getListIdiomas().get(0);

    public Footer() {}
    
    private DataCtxShop getCtxShForTest() throws Exception {
    	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        dCtxSh.pais = españa;
        dCtxSh.idioma = castellano;
        return dCtxSh;
    }

    @Test(
        groups = { "Footer", "Canal:all_App:shop", "Canal:desktop_App:outlet" }, alwaysRun = true, 
        description="Verificar que los links del footer aparecen y redirigen correctamente")
    public void FOOT001_Menu() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        String urlInitialPage = driver.getCurrentUrl();
        SecFooterStpV.validaLinksFooter(dCtxSh.channel, dCtxSh.appE, driver);
        
        List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(dCtxSh.appE, dCtxSh.channel);
        //List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinks(dCtxSh.appE, dCtxSh.channel);
        for (FooterLink footerLinkToValidate : listFooterLinksToValidate) {
        	switch (footerLinkToValidate) {
        	case ayuda:
        		SecFooterStpV.clickLinkFooter(footerLinkToValidate, false, dCtxSh.channel, driver);
        		SecFooterStpV.validaPaginaAyuda(dCtxSh.channel, driver);
        		break;
        	case mango_card:
        		SecFooterStpV.clickLinkFooter(footerLinkToValidate, false, dCtxSh.channel, driver);
                SecFooterStpV.checkSolicitarTarjeta(dCtxSh.channel, driver);
        		break;
        	default:
                SecFooterStpV.clickLinkFooter(footerLinkToValidate, true, dCtxSh.channel, driver);
                driver.get(urlInitialPage);
        	}
        	
            driver.get(urlInitialPage);
        }
    }
}
