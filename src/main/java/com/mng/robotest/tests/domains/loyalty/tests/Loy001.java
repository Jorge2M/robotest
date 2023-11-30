package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

public class Loy001 extends TestBase {

	static final User USER = LoyTestCommons.USER_PRO_WITH_LOY_POINTS;
	
	private final LoyTestCommons loyTestCommons = new LoyTestCommons();
	
	public Loy001() throws Exception {
		super();
		
		dataTest.setUserConnected(USER.getEmail());
		dataTest.setUserRegistered(true);
		dataTest.setPasswordUser(GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword());
	}
	
	@Override
	public void execute() throws Exception {
		accessAndClearData();
		loyTestCommons.addBagArticleNoRebajadoAndClickComprar();
		loyTestCommons.inputLoyaltyPoints();
		if (!PageBase.isEnvPRO()) {
			String idPedido = executeMastercardEnvioTiendaPayment();
			loyTestCommons.checkLoyaltyPointsGenerated(idPedido);
		}
	}
	
    public String executeMastercardEnvioTiendaPayment() throws Exception {
    	return executeMastercardPayment().getDataPedido().getCodpedido();
    }

}
