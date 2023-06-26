package com.mng.robotest.domains.footer.tests;

import java.util.List;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;
import com.mng.robotest.domains.footer.steps.TarjetaMangoSteps;

import static com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink.*;

public class Foo001 extends TestBase {

	private final SecFooterSteps secFooterSteps = new SecFooterSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		secFooterSteps.validaLinksFooter();
		checkLinksSelection();		
	}

	private void checkLinksSelection() {
		String urlInitialPage = driver.getCurrentUrl();
		List<FooterLink> listFooterLinksToValidate = FooterLink.getFooterLinksFiltered(app, channel);
		for (var footerLinkToValidate : listFooterLinksToValidate) {
			if (footerLinkToValidate == MANGO_CARD) { 
				secFooterSteps.clickLinkFooter(footerLinkToValidate, false);
				new TarjetaMangoSteps().checkSolicitarTarjeta();
			} else {
				secFooterSteps.clickLinkFooter(footerLinkToValidate, true);
			}
			driver.get(urlInitialPage);
		}
	}

}
