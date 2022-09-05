package com.mng.robotest.test.appshop.footer;

import java.util.List;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.SecFooterSteps;

public class Foo001 extends TestBase {

	@Override
	public void execute() throws Exception {
		dataTest.userRegistered = false;

		new AccesoSteps().oneStep(false);
		String urlInitialPage = driver.getCurrentUrl();
		SecFooterSteps secFooterSteps = new SecFooterSteps();
		secFooterSteps.validaLinksFooter();
		
		List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
		for (FooterLink footerLinkToValidate : listFooterLinksToValidate) {
			switch (footerLinkToValidate) {
			case AYUDA:
				secFooterSteps.clickLinkFooter(footerLinkToValidate, false);
				secFooterSteps.validaPaginaAyuda();
				break;
			case MANGO_CARD:
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
