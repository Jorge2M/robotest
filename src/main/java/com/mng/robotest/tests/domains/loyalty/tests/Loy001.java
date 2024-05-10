package com.mng.robotest.tests.domains.loyalty.tests;

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
		chargePointsIfNotEnough();
		loyTestCommons.addBagArticleNoRebajadoAndClickComprar();
		var discountLikes = loyTestCommons.inputLoyaltyPoints();
		if (!isPRO()) {
			String idPedido = executeMastercardEnvioTiendaPayment();
			loyTestCommons.checkLoyaltyPointsEnvioTiendaInHistorial(discountLikes.getLikes(), idPedido);
		}
	}
	
	private void chargePointsIfNotEnough() {
		if (!isPRO() && LoyTestCommons.clickMyAccountAndGetPoints() < 3000) { 
			LoyTestCommons.addLoyaltyPoints(USER);
		}
	}
	
    private String executeMastercardEnvioTiendaPayment() throws Exception {
    	executeMastercardPayment();
    	return dataTest.getDataPago().getDataPedido().getCodpedido();
    }

}
