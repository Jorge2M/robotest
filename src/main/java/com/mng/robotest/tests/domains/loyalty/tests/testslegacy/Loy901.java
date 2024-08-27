package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Loy901 extends TestBase {

	//e2e.us.test@mango.com
	static final User USER_USA = User.from(GestorUsersShop.getUser(USA), "5110765150172975809", "US");
	
	private final LoyTestCommons loyTestCommons = new LoyTestCommons();
	
	public Loy901() throws Exception {
		super();
		
		dataTest.setPais(PaisGetter.from(USA));
		dataTest.setUser(USER_USA);
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
			LoyTestCommons.addLoyaltyPoints(USER_USA);
		}
	}
	
    private String executeMastercardEnvioTiendaPayment() throws Exception {
    	executeMastercardTiendaPayment();
    	return dataTest.getDataPago().getDataPedido().getCodpedido();
    }

}
