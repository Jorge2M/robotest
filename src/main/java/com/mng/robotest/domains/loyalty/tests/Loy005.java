package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageRegalarMisLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps.DataRegaloPuntos;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test.stpv.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;
import com.mng.robotest.utils.DataTest;


public class Loy005 extends LoyaltyTestBase {

	private final static User EMISOR_USER = new User("test.performance23@mango.com", "6875476978997042979", "ES");
	private final static User RECEPTOR_USER = new User("test.performance24@mango.com", "6876477022921042981", "ES");
	private final DataCtxShop dataTest;
	
	public Loy005() throws Exception {
		super();
		dataTest = getDataTest();
	}
	
	@Override
	public void execute() throws Exception {
		DataCtxShop dataTest = getDataTest();
		if (isPro) {
			return;
		}
		
		AccesoStpV.oneStep(dataTest, false, driver);
		int iniPointsReceptor = clickMangoLikesYou();
		
		login(EMISOR_USER, dataTest.passwordUser);
		int iniPointsEmisor = clickMangoLikesYou();
		int pointsToGive = 2500;
		iniPointsEmisor = givePoints(pointsToGive, iniPointsEmisor);
		int finPointsEmisor = clickMangoLikesYou();
		
		login(RECEPTOR_USER, dataTest.passwordUser);
		int finPointsReceptor = clickMangoLikesYou();
		
		DataRegaloPuntos dataPoints = new DataRegaloPuntos();
		dataPoints.setClienteEmisor(EMISOR_USER.getEmail());
		dataPoints.setClienteReceptor(RECEPTOR_USER.getEmail());
		dataPoints.setPointsRegalados(pointsToGive);
		dataPoints.setIniPointsEmisor(iniPointsEmisor); 
		dataPoints.setIniPointsReceptor(iniPointsReceptor);
		dataPoints.setFinPointsEmisorExpected(iniPointsEmisor - pointsToGive);
		dataPoints.setFinPointsReceptorExpected(iniPointsReceptor + pointsToGive);
		dataPoints.setFinPointsEmisorReal(finPointsEmisor);
		dataPoints.setFinPointsReceptorReal(finPointsReceptor);
		PageHomeLikesSteps.checkRegalarPointsOk(dataPoints);
	}

	private int givePoints(int pointsRegalar, int iniPointsEmisor) throws Exception {
		if (iniPointsEmisor < pointsRegalar && !isPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(EMISOR_USER, 25000);
			iniPointsEmisor = clickMangoLikesYou();
		}
		PageRegalarMisLikesSteps pageRegalarMisLikesSteps = 
			new PageHomeLikesSteps(driver).clickButtonRegalarMisLikes();
		
		pageRegalarMisLikesSteps.inputReceptorAndClickContinuar(
			"Regalo a mi usuario ficticio favorito",
			RECEPTOR_USER.getEmail());
		
		pageRegalarMisLikesSteps.inputNumLikesAndClickEnviarRegalo(pointsRegalar);
		
		return iniPointsEmisor;
	}
	
	private DataCtxShop getDataTest() throws Exception {
		DataCtxShop dataTest = DataTest.getData(PaisShop.ESPANA);
		
		String passwordTestPerformmance = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
				.getPassword();
		dataTest.userConnected = RECEPTOR_USER.getEmail();
		dataTest.passwordUser = passwordTestPerformmance;
		dataTest.userRegistered = true;
		
		return dataTest;
	}
	
	private void login(User user, String password) throws Exception {
		SecMenusUserSteps secMenusUserStpV = new SecMenusUserSteps(channel, app, driver);
		secMenusUserStpV.logoffLogin(user.getEmail(), dataTest.passwordUser);
	}
}
