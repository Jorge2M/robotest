package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.domains.compra.tests.CompraSteps;
import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.test.datastored.DataPago;
import com.mng.robotest.test.steps.navigations.shop.GaleriaNavigationsSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;

import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.bolsa.steps.SecBolsaSteps;

public class Loy001 extends TestBase {

	static final User USER = LoyaltyCommons.USER_PRO_WITH_LOY_POINTS;
	
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
		addBagArticleNoRebajadoAndClickComprar();
		inputLoyaltyPoints();
		if (!PageBase.isEnvPRO()) {
			String idPedido = executeMastercardEnvioTiendaPayment();
			checkLoyaltyPointsGenerated(idPedido);
		}
	}
	
	private void addBagArticleNoRebajadoAndClickComprar() throws Exception {
		clickGroup(NEW_NOW);
		new GaleriaNavigationsSteps().selectTalla();
        new SecBolsaSteps().selectButtonComprar();
	}
	
	private void inputLoyaltyPoints() {
		var checkoutSteps = new CheckoutSteps();
		checkoutSteps.validateBlockLoyalty();
		checkoutSteps.loyaltyPointsApply();
	}
	
    private String executeMastercardEnvioTiendaPayment() throws Exception {
        DataPago dataPago = getDataPago();
        dataPago.setPago(dataTest.getPais().getPago("MASTERCARD"));
        new CompraSteps().startPayment(dataPago, true);
        new PageResultPagoSteps().validateIsPageOk(dataPago);
        return dataPago.getDataPedido().getCodpedido();
    }
    
    private void checkLoyaltyPointsGenerated(String idPedido) {
    	var pageResultPagoSteps = new PageResultPagoSteps();
    	int points = pageResultPagoSteps.checkLoyaltyPointsGenerated().getNumberPoints();
		pageResultPagoSteps.clickLinkDescuentosExperiencias();
		new PageMangoLikesYouSteps().click(TabLink.HISTORIAL);
		new PageHistorialLikesSteps().checkPointsForEnvioTiendaPayment(points, idPedido);
    }
	
}
