package com.mng.robotest.test.appshop;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

public class Footer {

	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);

	public Footer() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = espana;
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

		AccesoSteps.oneStep(dCtxSh, false, driver);
		String urlInitialPage = driver.getCurrentUrl();
		SecFooterSteps secFooterSteps = new SecFooterSteps(dCtxSh.channel, dCtxSh.appE, driver);
		secFooterSteps.validaLinksFooter();
		
		List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(dCtxSh.appE, dCtxSh.channel);
		//List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinks(dCtxSh.appE, dCtxSh.channel);
		for (FooterLink footerLinkToValidate : listFooterLinksToValidate) {
			switch (footerLinkToValidate) {
			case ayuda:
				secFooterSteps.clickLinkFooter(footerLinkToValidate, false);
				secFooterSteps.validaPaginaAyuda();
				break;
			case mango_card:
				secFooterSteps.clickLinkFooter(footerLinkToValidate, false);
				secFooterSteps.checkSolicitarTarjeta();
				break;
			default:
				secFooterSteps.clickLinkFooter(footerLinkToValidate, true);
				driver.get(urlInitialPage);
			}
			
			driver.get(urlInitialPage);
		}
	}
}
