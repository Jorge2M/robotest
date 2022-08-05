package com.mng.robotest.domains.loyalty.tests;

import com.mng.robotest.domains.loyalty.beans.User;
import com.mng.robotest.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps;
import com.mng.robotest.domains.loyalty.steps.PageRegalarMisLikesSteps;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.loyalty.steps.PageHomeLikesSteps.DataRegaloPuntos;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusUserSteps;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets;
import com.mng.robotest.test.utils.awssecrets.GetterSecrets.SecretType;


public class Loy005 extends TestBase {

	static final User EMISOR_USER = new User("test.performance23@mango.com", "6875476978997042979", "ES");
	static final User RECEPTOR_USER = new User("test.performance24@mango.com", "6876477022921042981", "ES");
	final boolean isPro = LoyaltyCommons.isPro(app, driver);
	
	final PageHomeLikesSteps pageHomeLikesSteps = new PageHomeLikesSteps(driver);
	final PageRegalarMisLikesSteps pageRegalarMisLikesSteps = new PageRegalarMisLikesSteps(driver);
	
	public Loy005() throws Exception {
		super();
		
		String passwordTestPerformmance = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
				.getPassword();
		dataTest.userConnected = RECEPTOR_USER.getEmail();
		dataTest.passwordUser = passwordTestPerformmance;
		dataTest.userRegistered = true;
	}
	
	@Override
	public void execute() throws Exception {
		if (isPro) {
			return;
		}
		
		AccesoSteps.oneStep(dataTest, false, driver);
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

	private int clickMangoLikesYou() throws Exception {
		return LoyaltyCommons.clickMangoLikesYou(channel, app, driver);
	}
	
	private int givePoints(int pointsRegalar, int iniPointsEmisor) throws Exception {
		if (iniPointsEmisor < pointsRegalar && !isPro) {
			ClientApiLoyaltyPointsDev client = new ClientApiLoyaltyPointsDev();
			client.addLoyaltyPoints(EMISOR_USER, 25000);
			iniPointsEmisor = clickMangoLikesYou();
		}

		pageHomeLikesSteps.clickButtonRegalarMisLikes();
		pageRegalarMisLikesSteps.inputReceptorAndClickContinuar(
			"Regalo a mi usuario ficticio favorito",
			RECEPTOR_USER.getEmail());
		
		pageRegalarMisLikesSteps.inputNumLikesAndClickEnviarRegalo(pointsRegalar);
		
		return iniPointsEmisor;
	}
	
	private void login(User user, String password) throws Exception {
		SecMenusUserSteps secMenusUserSteps = new SecMenusUserSteps(channel, app, driver);
		secMenusUserSteps.logoffLogin(user.getEmail(), password);
	}
}
