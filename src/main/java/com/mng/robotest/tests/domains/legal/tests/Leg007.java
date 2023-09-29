package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.footer.pageobjects.PageMangoCard;
import com.mng.robotest.tests.domains.footer.steps.SecFooterSteps;

/**
 * Control textos legales "Mango card":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Mangocard
 *
 */
public class Leg007 extends TestBase {

	//TODO no está apareciendo el texto del confluence en pantalla
	
	@Override
	public void execute() throws Exception {
		goToMangoCard();
		checkTextoLegal();
	}
	
	private void goToMangoCard() throws Exception {
		access();
		new SecFooterSteps().clickLinkFooter(MANGO_CARD);
	}	
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new PageMangoCard());
	}

}
