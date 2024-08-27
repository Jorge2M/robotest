package com.mng.robotest.tests.domains.loyalty.tests.testsnew;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Loy008 extends TestBase {

	private static final User USER = new User("test.usa12122@mango.com", "4533008520221517084", "ES");
	
	private final LoyTestCommons loyTestCommons = new LoyTestCommons();
	
	public Loy008() throws Exception {
		super();
		dataTest.setPais(USA.getPais());
		dataTest.setUserConnected(USER.getEmail());
		dataTest.setPasswordUser("Sirjorge74");
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		if (isPRO()) {
			return;
		}
		accessAndClearData();
		chargePointsIfNotEnough();
		addBagArticle();
		buyWithLoyaltyPoints();
	}

	private void chargePointsIfNotEnough() {
		if (!isPRO() && LoyTestCommons.clickMyAccountAndGetPoints() < 3000) { 
			LoyTestCommons.addLoyaltyPoints(USER);
		}
	}
	
	private void addBagArticle() throws Exception {
		loyTestCommons.addBagArticleNoRebajadoAndClickComprar();
	}

	private void buyWithLoyaltyPoints() throws Exception {
		var discountLikes = loyTestCommons.inputLoyaltyPoints();
		String idPedido = executeMastercardEnvioTiendaPayment();
		loyTestCommons.checkLoyaltyPointsInHistorial(discountLikes.getLikes(), idPedido);
	}
	
    private String executeMastercardEnvioTiendaPayment() throws Exception {
    	executeMastercardPayment();
    	return dataTest.getDataPago().getDataPedido().getCodpedido();
    }
    
    
	
}
