package com.mng.robotest.tests.domains.loyalty.tests;

import static com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus.MenuItem.CARDIGANS_Y_JERSEIS_SHE;
import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

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
		if (isGroupNewNowSelectable()) {
			clickGroup(NEW_NOW);
		} else {
			clickMenu(CARDIGANS_Y_JERSEIS_SHE);
		}
		new PageGaleriaSteps().selectTallaAvailable();
        new SecBolsaSteps().selectButtonComprar();
	}
	
	private boolean isGroupNewNowSelectable() {
		return new GroupWeb(NEW_NOW).isPresent();
	}	
	
	private void inputLoyaltyPoints() {
		var checkoutSteps = new CheckoutSteps();
		checkoutSteps.validateBlockLoyalty();
		checkoutSteps.loyaltyPointsApply();
	}
	
    private String executeMastercardEnvioTiendaPayment() throws Exception {
    	return executeMastercardPayment().getDataPedido().getCodpedido();
    }
    
    private void checkLoyaltyPointsGenerated(String idPedido) {
    	var pageResultPagoSteps = new PageResultPagoSteps();
    	int points = pageResultPagoSteps.checkLoyaltyPointsGenerated().getNumberPoints();
		pageResultPagoSteps.clickLinkDescuentosExperiencias();
		new PageMangoLikesYouSteps().click(TabLink.HISTORIAL);
		new PageHistorialLikesSteps().checkPointsForEnvioTiendaPayment(points, idPedido);
    }
	
}
