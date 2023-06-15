package com.mng.robotest.domains.cookiescheck.steps;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.genericchecks.Checker;
import com.mng.robotest.domains.cookiescheck.services.CookiesChecker;

public class CheckerAllowedCookies implements Checker {

	private final State level;
	
	public CheckerAllowedCookies(State level) {
		this.level = level;
	}
	
	@Override
	public ChecksTM check(WebDriver driver) {
		var cookiesChecker = new CookiesChecker();
		var resultCheck = cookiesChecker.check();
		var checks = ChecksTM.getNew();
		checks.add(
			Check.make(
				"Se comprueba que todas las cookies existentes en la página están permitidas",
				resultCheck.getLeft(), level)
			.info(getInfoError(resultCheck))
			.code("COOKIE_TRUST")			
			.store(StoreType.None)
			.send(SendType.Alert).build());
		
		return checks;
	}
	
	private static String getInfoError(Pair<Boolean, List<Cookie>> resultCheck) {
		if (!resultCheck.getLeft()) {
			String info ="Se detectan las siguientes cookies no permitidas:<ul>";
			for (Cookie cookie : resultCheck.getRight()) {
				info+="<li>" + cookie.toString() + "</li>";
			}
			info+="</ul>";
			return info;
		}
		return "";
	}

}
