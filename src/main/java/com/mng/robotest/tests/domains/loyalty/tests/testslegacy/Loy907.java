package com.mng.robotest.tests.domains.loyalty.tests.testslegacy;

import static com.mng.robotest.testslegacy.data.PaisShop.USA;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.compra.tests.CompraSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.repository.usuarios.GestorUsersShop;
import com.mng.robotest.testslegacy.utils.PaisGetter;

public class Loy907 extends TestBase {

	//e2e.us.test@mango.com
	static final User USER_USA = User.from(GestorUsersShop.getUser(USA), "5110765150172975809", "US");
	
	private final LoyTestCommons loyTestCommons = new LoyTestCommons();
	private final String cvcVisaOK = dataTest.getPais().getPago("MASTERCARD").getCvc();
	private static final String CVC_VISA_KO = "111";
	
	public Loy907() throws Exception {
		super();
		
		dataTest.setPais(PaisGetter.from(USA));
		dataTest.setUser(USER_USA);
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
			LoyTestCommons.addLoyaltyPoints(USER_USA);
		}
	}	
	
    private void executeVisaEnvioDomicilioPaymentKO() throws Exception {
        var pagoVisa = dataTest.getPais().getPago("VISA");
        pagoVisa.setCvc(CVC_VISA_KO);
        dataTest.getDataPago().setPago(pagoVisa);
        
        new CompraSteps().startPayment(true);
        new CheckoutSteps().isVisibleMessageErrorPayment(5);
    }
    
    public String executeVisaPaymentOK() throws Exception {
        var pagoVisa = dataTest.getPais().getPago("VISA");
        pagoVisa.setCvc(cvcVisaOK);
        var dataPago = dataTest.getDataPago();
        dataPago.setPago(pagoVisa);
        dataPago.setSelectEnvioType(false);
        
        new CompraSteps().startPayment(true);
        new PageResultPagoSteps().checkIsPageOk();
        return dataPago.getDataPedido().getCodpedido();
    }
	
}
