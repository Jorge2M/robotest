package com.mng.robotest.domains.legal.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.chequeregalo.pageobjects.PageChequeRegaloInputDataNew;
import com.mng.robotest.domains.footer.steps.SecFooterSteps;

import static com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink.*;

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
	
	private void goToChequeRegalo() throws Exception {
		new SecFooterSteps().clickLinkFooter(CHEQUE_REGALO);
	}	
	
	private void checkTextoLegal() {
		checkLegalTextsVisible(new PageChequeRegaloInputDataNew());
	}

}
