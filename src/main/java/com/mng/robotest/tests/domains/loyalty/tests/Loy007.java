package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.tests.CompraSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

public class Loy007 extends TestBase {

	static final User USER = LoyTestCommons.USER_PRO_WITH_LOY_POINTS;
	
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
		accessAndClearData();
		loyTestCommons.addBagArticleNoRebajadoAndClickComprar();
		loyTestCommons.inputLoyaltyPoints();
		if (!isEnvPRO()) {
			executeMastercardPaymentCvcKo();
			String idPedido = executeMastercardEnvioTiendaPayment();
			loyTestCommons.checkLoyaltyPointsGenerated(idPedido);
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
