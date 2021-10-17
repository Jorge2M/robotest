package com.mng.robotest.test80.mango.test.appshop;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.beans.IdiomaPais;
import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

public class Footer {

	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);

	public Footer() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		dCtxSh.pais = españa;
		dCtxSh.idioma = castellano;
		return dCtxSh;
	}

	@Test(
		groups = { "Footer", "Canal:desktop,mobile_App:shop", "Canal:desktop_App:outlet" }, alwaysRun = true, 
		description="Verificar que los links del footer aparecen y redirigen correctamente")
	public void FOOT001_Menu() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;

		AccesoStpV.oneStep(dCtxSh, false, driver);
		String urlInitialPage = driver.getCurrentUrl();
		SecFooterStpV secFooterStpV = new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver);
		secFooterStpV.validaLinksFooter();
        
        List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(dCtxSh.appE, dCtxSh.channel);
        //List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinks(dCtxSh.appE, dCtxSh.channel);
        for (FooterLink footerLinkToValidate : listFooterLinksToValidate) {
        	switch (footerLinkToValidate) {
        	case ayuda:
        		secFooterStpV.clickLinkFooter(footerLinkToValidate, false);
        		secFooterStpV.validaPaginaAyuda();
        		break;
        	case mango_card:
        		secFooterStpV.clickLinkFooter(footerLinkToValidate, false);
                secFooterStpV.checkSolicitarTarjeta();
        		break;
        	default:
                secFooterStpV.clickLinkFooter(footerLinkToValidate, true);
                driver.get(urlInitialPage);
        	}
        	
            driver.get(urlInitialPage);
        }
    }
}
