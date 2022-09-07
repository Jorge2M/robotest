package com.mng.robotest.domains.footer.tests;

import java.util.List;

import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.footer.steps.TarjetaMangoSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.steps.shop.AccesoSteps;

public class Foo001 extends TestBase {

	private final SecFooterSteps secFooterSteps = new SecFooterSteps();
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().oneStep(false);
		secFooterSteps.validaLinksFooter();
		checkLinksSelection();		
	}

	private void checkLinksSelection() throws Exception {
		String urlInitialPage = driver.getCurrentUrl();
		List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
		for (FooterLink footerLinkToValidate : listFooterLinksToValidate) {
			switch (footerLinkToValidate) {
			case MANGO_CARD:
				secFooterSteps.clickLinkFooter(footerLinkToValidate, false);
				new TarjetaMangoSteps().checkSolicitarTarjeta();
				break;
			default:
				secFooterSteps.clickLinkFooter(footerLinkToValidate, true);
				driver.get(urlInitialPage);
			}
			driver.get(urlInitialPage);
		}
	}

}
