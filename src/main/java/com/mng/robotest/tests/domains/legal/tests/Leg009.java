package com.mng.robotest.tests.domains.legal.tests;

import static com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.chequeregalo.pageobjects.PageChequeRegaloInputDataNew;
import com.mng.robotest.tests.domains.footer.steps.FooterSteps;

/**
 * Control textos legales:
 * https://confluence.mango.com/display/PIUR/Mapeo+de+textos+legales#expand-ChequeregaloPagos
 *
 */
public class Leg009 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();		
		goToChequeRegalo();
		checkTextoLegal();
	}
	
	private void goToChequeRegalo() {
		new FooterSteps().clickLinkFooter(CHEQUE_REGALO);
	}	
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new PageChequeRegaloInputDataNew());
	}

}
