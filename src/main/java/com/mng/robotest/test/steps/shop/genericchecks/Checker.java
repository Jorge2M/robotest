package com.mng.robotest.test.steps.shop.genericchecks;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.cookiescheck.steps.CheckerAllowedCookies;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public interface Checker {

	public abstract ChecksTM check(WebDriver driver);
	
	public static Checker make(GenericCheck check) {
		switch (check) {
		case SEO:
			return new CheckerSEO();
		case JSerrors:
			return new CheckerJSerrors();
		case ImgsBroken:
			return new CheckerImgsBroken();
		case Analitica:
			return new CheckerAnalitica();
		case GoogleAnalytics:
			return new CheckerGoogleAnalytics();
		case TextsTraduced:
			return new CheckerTextsTraduced();
		case NetTraffic:
			return new CheckerNetTraffic();
		case CookiesAllowed:
			return new CheckerAllowedCookies();
		}
		
		throw new IllegalArgumentException("The para " + check + "is not covered by the method");
	}
}