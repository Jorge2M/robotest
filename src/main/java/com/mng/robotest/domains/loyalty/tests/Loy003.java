package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.steps.PageHomeConseguirPorLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;
import com.mng.robotest.utils.DataTest;


public class Loy003 extends LoyaltyTestBase {

	private final static User USER = new User("test.performance22@mango.com", "6876577027622042923", "ES");
	private final DataCtxShop dataTest;
	
	public Loy003() throws Exception {
		super();
		this.dataTest = getDataTest();
	}
	
	@Override
	public void execute() throws Exception {

		AccesoStpV.oneStep(dataTest, false, driver);
		
		int loyaltyPointsIni = clickMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isPro) {
			loyaltyPointsIni = addLoyaltyPoints(USER);
		}
		new PageHomeLikesSteps(driver).clickSaberMas();
		
		if (!isPro) {
			new PageHomeConseguirPorLikesSteps(driver).selectConseguirButton();
			
			SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps(dataTest.channel, dataTest.appE, driver);
			int loyaltyPointsFin = secMenusUserSteps.clickMenuMangoLikesYou();
			secMenusUserSteps.checkLoyaltyPoints(loyaltyPointsIni, 1200, loyaltyPointsFin);
		}
	}
	
	private DataCtxShop getDataTest() throws Exception {
		DataCtxShop dataTest = DataTest.getData(PaisShop.ESPANA);
		dataTest.userRegistered = true;
		if (isPro) {
			dataTest.userConnected = USER_PRO_WITH_LOY_POINTS;
			dataTest.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_STANDARD_USER)
					.getPassword();
		} else {
			dataTest.userConnected = USER.getEmail();
			dataTest.passwordUser = GetterSecrets.factory()
					.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
					.getPassword();
		}
		return dataTest;
	}

}
