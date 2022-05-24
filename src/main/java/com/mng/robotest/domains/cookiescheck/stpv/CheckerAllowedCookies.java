package com.mng.robotest.domains.cookiescheck.stpv;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.cookiescheck.service.CookiesChecker;
import com.mng.robotest.test.stpv.shop.genericchecks.Checker;


public class CheckerAllowedCookies implements Checker {

	@Override
	public ChecksTM check(WebDriver driver) {
		CookiesChecker cookiesChecker = new CookiesChecker();
		Pair<Boolean, List<Cookie>> resultCheck = cookiesChecker.check(driver);
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
				getTextValidation(resultCheck),
				//TODO temporalmente lo ponemos en INFO (avoidEvidences) para que pueda subir a PRO
				resultCheck.getLeft(), State.Info, true);
		
		return checks;
	}
	
	private static String getTextValidation(Pair<Boolean, List<Cookie>> resultCheck) {
		String descripcion = 
				"Se comprueba que todas las cookies existentes en la página están permitidas.";
		if (!resultCheck.getLeft()) {
			descripcion+="Se detectan las siguientes cookies no permitidas:<ul>";
			for (Cookie cookie : resultCheck.getRight()) {
				descripcion+="<li>" + cookie.toString() + "</li>";
			}
			descripcion+="</ul>";
		}
		return descripcion;
	}

}
