package com.mng.robotest.tests.domains.loyalty.tests;

import static com.mng.robotest.tests.domains.menus.entity.FactoryMenus.MenuItem.JERSEIS_Y_CARDIGANS_SHE;
import static com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType.*;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.bolsa.steps.BolsaSteps;
import com.mng.robotest.tests.domains.compra.pageobjects.beans.DiscountLikes;
import com.mng.robotest.tests.domains.compra.steps.CheckoutSteps;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.tests.domains.loyalty.steps.PageHistorialLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;

public class LoyTestCommons extends StepBase {
	
	public static final User USER_PRO_WITH_LOY_POINTS = 
			new User("ticket_digital_es@mango.com", "6051483570048367458", "ES");
	
	public static int clickMangoLikesYou() {
		return new MenusUserSteps().clickMenuMangoLikesYou();
	}
	
	public static int clickMyAccountAndGetPoints() {
		return new MenusUserSteps().clickMyAccountAndGetPoints();
	}
	
	public static int addLoyaltyPoints(User user) {
		var client = new ClientApiLoyaltyPointsDev();
		client.addLoyaltyPoints(user, 25000);
		return clickMyAccountAndGetPoints();
	}
	
	public void addBagArticleNoRebajadoAndClickComprar() throws Exception {
		if (isGroupNewNowSelectable()) {
			clickGroup(NEW_NOW);
		} else {
			clickMenu(JERSEIS_Y_CARDIGANS_SHE);
		}
		new GaleriaSteps().selectTallaAvailable();
        new BolsaSteps().selectButtonComprar();
	}
	
	private boolean isGroupNewNowSelectable() {
		return GroupWeb.make(NEW_NOW, dataTest.getPais(), app).isPresent();
	}	
	
	public DiscountLikes inputLoyaltyPoints() {
		var checkoutSteps = new CheckoutSteps();
		checkoutSteps.checkBlockLoyalty();
		return checkoutSteps.loyaltyPointsApply();
	}
	
    public void checkLoyaltyPointsInHistorial(int pointsUsed, String idPedido) {
    	var pointsGenerated = goToLoyaltyPointsHistorial();
		new PageHistorialLikesSteps().checkPointsPayment(pointsUsed, pointsGenerated, idPedido);
    }    
    
    public void checkLoyaltyPointsEnvioTiendaInHistorial(int pointsUsed, String idPedido) {
    	var pointsGenerated = goToLoyaltyPointsHistorial();
		new PageHistorialLikesSteps().checkPointsForEnvioTiendaPayment(pointsUsed, pointsGenerated, idPedido);    	
    }
    
    public static boolean isMlyTiers(Pais pais) {
		return 
			PaisShop.ESPANA.isEquals(pais) ||
			PaisShop.FRANCE.isEquals(pais);
    }
    
    private int goToLoyaltyPointsHistorial() {
    	var pageResultPagoSteps = new PageResultPagoSteps();
    	int pointsGenerated = pageResultPagoSteps.checkLoyaltyPointsGenerated().getNumberPoints();
		pageResultPagoSteps.clickLinkDescubrirVentajas();
		new MangoLikesYouSteps().clickHistorial();
		return pointsGenerated;
    }

}
