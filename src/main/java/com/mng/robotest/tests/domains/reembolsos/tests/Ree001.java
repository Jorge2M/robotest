package com.mng.robotest.tests.domains.reembolsos.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.reembolsos.pageobjects.PageReembolsos;
import com.mng.robotest.tests.domains.reembolsos.pageobjects.PageReembolsos.TypeReembolso;
import com.mng.robotest.tests.domains.reembolsos.steps.PageReembolsosSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class Ree001 extends TestBase {
	
	private static final Pais EMIRATOS = UNITED_ARAB_EMIRATES.getPais();
	private static final IdiomaPais EMIRATOS_ARABE = EMIRATOS.getListIdiomas().get(0);
	
	@Override
	public void execute() throws Exception {
		dataTest.setPais(EMIRATOS);
		dataTest.setIdioma(EMIRATOS_ARABE);
		
		//TODO hasta que se solvente el https://jira.mangodev.net/jira/browse/GUIL-2311
		if (isPRO()) {
			return;		
		}

		boolean paisConSaldoCta = dataTest.getPais().existsPagoStoreCredit();
		dataTest.setUserConnected("mng_test_SA_pruebaSaldo@mango.com");
		dataTest.setPasswordUser(GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword());

		if (dataTest.getPais().getEmailuser()!=null /*&& dataTest.getPais().getPassuser()!=null*/) {
			dataTest.setUserConnected(dataTest.getPais().getEmailuser());
			//dataTest.setPasswordUser(dataTest.getPais().getPassuser());
		}
			
		accessAndLogin();
		var pageReembolsosSteps = new PageReembolsosSteps();
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
