package com.mng.robotest.test.appshop.reembolsos;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.PageReembolsos;
import com.mng.robotest.test.pageobject.shop.PageReembolsos.TypeReembolso;
import com.mng.robotest.test.steps.shop.PageReembolsosSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

public class Ree001 extends TestBase {
	
	private static final Pais EMIRATOS = PaisGetter.get(PaisShop.UNITED_ARAB_EMIRATES);
	private static final IdiomaPais EMIRATOS_ARABE = EMIRATOS.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		dataTest.pais = EMIRATOS;
		dataTest.idioma = EMIRATOS_ARABE;
		
		//TODO hasta que se solvente el https://jira.mangodev.net/jira/browse/GUIL-2311
		if (isPRO()) {
			return;		
		}

		boolean paisConSaldoCta = dataTest.pais.existsPagoStoreCredit();
		dataTest.userRegistered = true;
		dataTest.userConnected = "mng_test_SA_pruebaSaldo@mango.com";
		dataTest.passwordUser = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword();

		if (dataTest.pais.getEmailuser()!=null && dataTest.pais.getPassuser()!=null) {
			dataTest.userConnected = dataTest.pais.getEmailuser();
			dataTest.passwordUser = dataTest.pais.getPassuser();
		}
			
		access();
		PageReembolsosSteps pageReembolsosSteps = new PageReembolsosSteps();
		pageReembolsosSteps.gotoRefundsFromMenu(paisConSaldoCta);
		if (paisConSaldoCta) {
			if (new PageReembolsos().isCheckedRadio(TypeReembolso.STORE_CREDIT)) {
				pageReembolsosSteps.testConfTransferencia();
				pageReembolsosSteps.selectRadioSalCtaAndRefresh();
			} else {
				pageReembolsosSteps.selectRadioSalCtaAndRefresh();
				pageReembolsosSteps.testConfTransferencia();
			}
		}		
	}

}
