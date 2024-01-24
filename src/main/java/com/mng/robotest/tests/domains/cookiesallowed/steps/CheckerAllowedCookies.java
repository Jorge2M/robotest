package com.mng.robotest.tests.domains.cookiesallowed.steps;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.genericchecks.Checker;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.cookiesallowed.services.CookiesChecker;

import static com.github.jorge2m.testmaker.conf.StoreType.*;

public class CheckerAllowedCookies implements Checker {

	private final State level;
	private final AppEcom app;
	
	public CheckerAllowedCookies(State level, AppEcom app) {
		this.level = level;
		this.app = app;
	}
	
	@Override
	public ChecksTM check(WebDriver driver) {
		if (app!=AppEcom.votf) {
			return checkCookies();
		}
		return ChecksTM.getNew();
	}

	private ChecksTM checkCookies() {
		var cookiesChecker = new CookiesChecker();
		var resultCheck = cookiesChecker.check();
		var checks = ChecksTM.getNew();
		checks.add(
			Check.make(
				"Se comprueba que todas las cookies existentes en la página están permitidas",
				resultCheck.getLeft(), level)
			.info(getInfoError(resultCheck))
			.code("COOKIE_TRUST")			
			.store(NONE)
			.send(SendType.ALERT).build());
		
		return checks;
	}
	
	private static String getInfoError(Pair<Boolean, List<Cookie>> resultCheck) {
		if (Boolean.FALSE.equals(resultCheck.getLeft())) {
			String info ="Se detectan las siguientes cookies no permitidas:<ul>";
			for (var cookie : resultCheck.getRight()) {
				info+="<li>" + cookie.toString() + "</li>";
			}
			info+="</ul>";
			return info;
		}
		return "";
	}

}
