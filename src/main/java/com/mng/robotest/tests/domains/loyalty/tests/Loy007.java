package com.mng.robotest.tests.domains.loyalty.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compra.tests.CompraSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

public class Loy007 extends TestBase {

	//private static final User USER = new User("test.performance30@mango.com", "6876577027631042977", "ES");
	private static final String usaUserId = "598535048017297955"; //Associated to e2e.us.test@mango.com user
	
	private final LoyTestCommons loyTestCommons = new LoyTestCommons();
	private final String cvcVisaOK = dataTest.getPais().getPago("VISA").getCvc();
	private static final String CVC_VISA_KO = "111";
	
	public Loy007() throws Exception {
		super();
		dataTest.setPais(USA.getPais());
		dataTest.setUserRegistered(true);
	}
	
	@Override
	public void execute() throws Exception {
		if (isPRO()) {
			return;
		}
		accessAndClearData();
		chargePointsIfNotEnough();
		loyTestCommons.addBagArticleNoRebajadoAndClickComprar();
		if (!isEnvPRO()) {
			var discountLikes = loyTestCommons.inputLoyaltyPoints();
			executeVisaEnvioDomicilioPaymentKO();
			String idPedido = executeVisaPaymentOK();
			loyTestCommons.checkLoyaltyPointsInHistorial(discountLikes.getLikes(), idPedido);
		}
	}
	
	private void chargePointsIfNotEnough() {
		if (!isPRO() && LoyTestCommons.clickMangoLikesYou() < 3000) { 
			var user = new User(dataTest.getUserConnected(), usaUserId, "US");
			LoyTestCommons.addLoyaltyPoints(user);
		}
	}	
	
    private void executeVisaEnvioDomicilioPaymentKO() throws Exception {
        var dataPago = getDataPago();
        var pagoVisa = dataTest.getPais().getPago("VISA");
        pagoVisa.setCvc(CVC_VISA_KO);
        dataPago.setPago(pagoVisa);
        
        new CompraSteps().startPayment(dataPago, true);
        new CheckoutSteps().isVisibleMessageErrorPayment(5);
    }
    
    public String executeVisaPaymentOK() throws Exception {
    	var dataPago = getDataPago();
        var pagoVisa = dataTest.getPais().getPago("VISA");
        pagoVisa.setCvc(cvcVisaOK);
        dataPago.setPago(pagoVisa);
        dataPago.setSelectEnvioType(false);
        
        new CompraSteps().startPayment(dataPago, true);
        new PageResultPagoSteps().validateIsPageOk(dataPago);
        return dataPago.getDataPedido().getCodpedido();
    }
	
}
