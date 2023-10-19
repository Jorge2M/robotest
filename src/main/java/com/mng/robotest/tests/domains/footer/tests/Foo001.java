package com.mng.robotest.tests.domains.footer.tests;

import java.util.List;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;

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
			secFooterSteps.clickLinkFooter(footerLinkToValidate, true);
			driver.get(urlInitialPage);
		}
	}

}
