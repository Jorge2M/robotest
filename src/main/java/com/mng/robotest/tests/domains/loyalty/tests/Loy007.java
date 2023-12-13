package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.tests.CompraSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

public class Loy007 extends TestBase {

	private static final User USER = new User("test.performance30@mango.com", "6876577027631042977", "ES");
	
	private final LoyTestCommons loyTestCommons = new LoyTestCommons();
	
	public Loy007() throws Exception {
		super();
		
		dataTest.setUserConnected(USER.getEmail());
		dataTest.setUserRegistered(true);
		dataTest.setPasswordUser(GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_STANDARD_USER)
				.getPassword());
	}
	
	@Override
	public void execute() throws Exception {
		if (isPRO()) {
			return;
		}
		accessAndClearData();
		chargePointsIfNotEnough();
		loyTestCommons.addBagArticleNoRebajadoAndClickComprar();
		loyTestCommons.inputLoyaltyPoints();
		if (!isEnvPRO()) {
			executeMastercardPaymentCvcKo();
			String idPedido = executeMastercardEnvioTiendaPayment();
			loyTestCommons.checkLoyaltyPointsGenerated(idPedido);
		}
	}
	
	private void chargePointsIfNotEnough() {
		if (!isPRO() && LoyTestCommons.clickMangoLikesYou() < 3000) { 
			LoyTestCommons.addLoyaltyPoints(USER);
		}
	}	
	
    private void executeMastercardPaymentCvcKo() throws Exception {
        var dataPago = getDataPago();
        var pagoMastercard = dataTest.getPais().getPago("VISA");
        pagoMastercard.setCvc("111");
        dataPago.setPago(pagoMastercard);
        
        new CompraSteps().startPayment(dataPago, true);
        new CheckoutSteps().isVisibleMessageErrorPayment(5);
    }
    
    public String executeMastercardEnvioTiendaPayment() throws Exception {
    	return executeMastercardPayment().getDataPedido().getCodpedido();
    }
	
}
