package com.mng.robotest.tests.domains.footer.tests;

import java.util.List;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;

public class Foo001 extends TestBase {

	private final FooterSteps secFooterSteps = new FooterSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		secFooterSteps.checkLinksFooter();
		checkLinksSelection();		
	}

	private void checkLinksSelection() {
		String urlInitialPage = driver.getCurrentUrl();
		List<FooterLink> listFooterLinksToCheck = FooterLink.getFooterLinksFiltered(app, channel);
		for (var footerLinkToValidate : listFooterLinksToCheck) {
			secFooterSteps.clickLinkFooter(footerLinkToValidate, true);
			driver.get(urlInitialPage);
		}
	}

}
