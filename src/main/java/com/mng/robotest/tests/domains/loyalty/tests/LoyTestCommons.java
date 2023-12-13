package com.mng.robotest.tests.domains.loyalty.tests;

import static com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus.MenuItem.CARDIGANS_Y_JERSEIS_SHE;
import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb.GroupType.NEW_NOW;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.steps.SecBolsaSteps;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.tests.domains.loyalty.pageobjects.PageMangoLikesYou.TabLink;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageMangoLikesYouSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.domains.transversal.menus.steps.SecMenusUserSteps;

public class LoyTestCommons extends StepBase {
	
	public static final User USER_PRO_WITH_LOY_POINTS = 
			new User("ticket_digital_es@mango.com", "6051483570048367458", "ES");

	public static int clickMangoLikesYou() {
		return new SecMenusUserSteps().clickMenuMangoLikesYou();
	}
	
	public static int addLoyaltyPoints(User user) {
		var client = new ClientApiLoyaltyPointsDev();
		client.addLoyaltyPoints(user, 25000);
		return clickMangoLikesYou();
	}
	
	public void addBagArticleNoRebajadoAndClickComprar() throws Exception {
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
	
	public void inputLoyaltyPoints() {
		var checkoutSteps = new CheckoutSteps();
		checkoutSteps.checkBlockLoyalty();
		checkoutSteps.loyaltyPointsApply();
	}
	
    public void checkLoyaltyPointsGenerated(String idPedido) {
    	var pageResultPagoSteps = new PageResultPagoSteps();
    	int points = pageResultPagoSteps.checkLoyaltyPointsGenerated().getNumberPoints();
		pageResultPagoSteps.clickLinkDescuentosExperiencias();
		new PageMangoLikesYouSteps().click(TabLink.HISTORIAL);
		new PageHistorialLikesSteps().checkPointsForEnvioTiendaPayment(points, idPedido);
    }    

}
