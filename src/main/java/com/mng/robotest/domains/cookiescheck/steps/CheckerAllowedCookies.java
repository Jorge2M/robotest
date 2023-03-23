package com.mng.robotest.domains.cookiescheck.steps;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.conf.SendType;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.cookiescheck.services.CookiesChecker;
import com.mng.robotest.test.steps.shop.genericchecks.Checker;

import static com.github.jorge2m.testmaker.conf.State.*;

public class CheckerAllowedCookies implements Checker {

	@Override
	public ChecksTM check(WebDriver driver) {
		var cookiesChecker = new CookiesChecker();
		var resultCheck = cookiesChecker.check(driver);
		var checks = ChecksTM.getNew();
		checks.add(
			Check.make(
				"Se comprueba que todas las cookies existentes en la página están permitidas",
				resultCheck.getLeft(), Info)
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
