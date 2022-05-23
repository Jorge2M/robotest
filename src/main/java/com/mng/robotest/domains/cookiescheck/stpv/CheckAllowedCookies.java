package com.mng.robotest.domains.cookiescheck.stpv;

import java.util.List;

import org.apache.commons.lang3.tuple.Pair;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.Cookie;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.cookiescheck.service.CookiesChecker;


public class CheckAllowedCookies {

	@Validation (
		description="Todas las cookies existentes están permitidas",
		level=State.Defect)
	public static ChecksTM check(WebDriver driver) {
		CookiesChecker cookiesChecker = new CookiesChecker();
		Pair<Boolean, List<Cookie>> resultCheck = cookiesChecker.check(driver);
		ChecksTM checks = ChecksTM.getNew();
		checks.add(
				getTextValidation(resultCheck),
				resultCheck.getLeft(), State.Defect);
		
		return checks;
	}
	
	private static String getTextValidation(Pair<Boolean, List<Cookie>> resultCheck) {
		String descripcion = 
				"Se comprueba que todas las cookies existentes en la página están permitidas.";
		if (!resultCheck.getLeft()) {
			descripcion+="Se detectan las siguientes cookies no permitidas:";
			for (Cookie cookie : resultCheck.getRight()) {
				descripcion+="<br><br>" + cookie.toString();
			}
		}
		return descripcion;
	}

}
