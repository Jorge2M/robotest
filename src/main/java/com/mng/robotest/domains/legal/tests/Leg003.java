package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.footer.pageobjects.PageMangoCard;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;

import static com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink.*;

/**
 * Control textos legales "Mango card":
 * https://confluence.mango.com/pages/viewpage.action?spaceKey=PIUR&title=Mapeo+de+textos+legales#expand-Mangocard
 *
 */
public class Leg003 extends TestBase {

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
