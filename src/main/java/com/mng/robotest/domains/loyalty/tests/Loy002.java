package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.pageobjects.PageHomeDonateLikes.ButtonLikes;
import com.mng.robotest.domains.loyalty.steps.PageHomeDonateLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;
import com.mng.robotest.utils.DataTest;

public class Loy002 extends LoyaltyTestBase {

	public final static User USER = new User("test.performance21@mango.com", "6877377061230042978", "ES");
	private final DataCtxShop dataTest;
	
	public Loy002() throws Exception {
		super();
		dataTest = getDataTest();
	}
	
	@Override
	public void execute() throws Exception {
		SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps(dataTest.channel, dataTest.appE, driver);
		PageHomeLikesSteps pageHomeLikesSteps = new PageHomeLikesSteps(driver);
		
		AccesoStpV.oneStep(dataTest, false, driver);
		
		int loyaltyPointsIni = clickMangoLikesYou();
		if (loyaltyPointsIni < 3000 && !isPro) {
			loyaltyPointsIni = addLoyaltyPoints(USER);
		}
		pageHomeLikesSteps.clickButtonDonarLikes();
		
		if (!isPro) {
			PageHomeDonateLikesSteps pageHomeDonateLikesSteps = new PageHomeDonateLikesSteps(driver);
			
			ButtonLikes donateButton = ButtonLikes.BUTTON_100_LIKES;
			pageHomeDonateLikesSteps.selectDonateButton(donateButton);
			int loyaltyPointsFin = secMenusUserSteps.clickMenuMangoLikesYou();
			
			secMenusUserSteps.checkLoyaltyPoints(
					loyaltyPointsIni, donateButton.getNumLikes(), loyaltyPointsFin);
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
