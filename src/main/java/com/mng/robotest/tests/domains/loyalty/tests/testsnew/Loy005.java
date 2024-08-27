package com.mng.robotest.tests.domains.loyalty.tests.testsnew;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.loyalty.beans.User;
import com.mng.robotest.tests.domains.loyalty.getdata.ClientApiLoyaltyPointsDev;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps;
import com.mng.robotest.tests.domains.loyalty.steps.PageRegalarMisLikesSteps;
import com.mng.robotest.tests.domains.loyalty.steps.MangoLikesYouSteps.DataRegaloPuntos;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.tests.domains.menus.steps.MenusUserSteps;
import com.mng.robotest.tests.repository.secrets.GetterSecrets;
import com.mng.robotest.tests.repository.secrets.GetterSecrets.SecretType;

public class Loy005 extends TestBase {

	static final User EMISOR_USER = new User("test.performance23@mango.com", "6875476978997042979", "ES");
	static final User RECEPTOR_USER = new User("test.performance24@mango.com", "6876477022921042981", "ES");
	
	private final MangoLikesYouSteps pageHomeLikesSteps = new MangoLikesYouSteps();
	private final PageRegalarMisLikesSteps pageRegalarMisLikesSteps = new PageRegalarMisLikesSteps();
	
	public Loy005() throws Exception {
		super();
		
		String passwordTestPerformmance = GetterSecrets.factory()
				.getCredentials(SecretType.SHOP_PERFORMANCE_USER)
				.getPassword();
		dataTest.setUserConnected(RECEPTOR_USER.getEmail());
		dataTest.setPasswordUser(passwordTestPerformmance);
	}
	
	@Override
	public void execute() throws Exception {
		if (isPRO()) {
			return;
		}
		
		accessAndLogin();
		int iniPointsReceptor = clickMangoLikesYou();
		
		login(EMISOR_USER, dataTest.getPasswordUser());
		int iniPointsEmisor = clickMangoLikesYou();
		int pointsToGive = 2500;
		iniPointsEmisor = givePoints(pointsToGive, iniPointsEmisor);
		int finPointsEmisor = clickMangoLikesYou();
		
		login(RECEPTOR_USER, dataTest.getPasswordUser());
		int finPointsReceptor = clickMangoLikesYou();
		
		var dataPoints = new DataRegaloPuntos();
		dataPoints.setClienteEmisor(EMISOR_USER.getEmail());
		dataPoints.setClienteReceptor(RECEPTOR_USER.getEmail());
		dataPoints.setPointsRegalados(pointsToGive);
		dataPoints.setIniPointsEmisor(iniPointsEmisor); 
		dataPoints.setIniPointsReceptor(iniPointsReceptor);
		dataPoints.setFinPointsEmisorExpected(iniPointsEmisor - pointsToGive);
		dataPoints.setFinPointsReceptorExpected(iniPointsReceptor + pointsToGive);
		dataPoints.setFinPointsEmisorReal(finPointsEmisor);
		dataPoints.setFinPointsReceptorReal(finPointsReceptor);
		MangoLikesYouSteps.checkRegalarPointsOk(dataPoints);
	}

	private int clickMangoLikesYou() {
		return LoyTestCommons.clickMangoLikesYou();
	}
	
	private int givePoints(int pointsRegalar, int iniPointsEmisor) {
		if (iniPointsEmisor < pointsRegalar && !isPRO()) {
			new ClientApiLoyaltyPointsDev().addLoyaltyPoints(EMISOR_USER, 25000);
			iniPointsEmisor = clickMangoLikesYou();
		}

		pageHomeLikesSteps.clickButtonRegalarMisLikes();
		pageRegalarMisLikesSteps.inputReceptorAndClickContinuar(
			"Regalo a mi usuario ficticio favorito",
			RECEPTOR_USER.getEmail());
		
		pageRegalarMisLikesSteps.inputNumLikesAndClickEnviarRegalo(pointsRegalar);
		
		return iniPointsEmisor;
	}
	
	private void login(User user, String password) {
		MenusUserSteps secMenusUserSteps = new MenusUserSteps();
		secMenusUserSteps.logoffLogin(user.getEmail(), password);
	}
}
