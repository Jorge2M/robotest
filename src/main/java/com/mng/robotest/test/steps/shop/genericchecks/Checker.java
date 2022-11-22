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
		case JS_ERRORS:
			return new CheckerJSerrors();
		case IMGS_BROKEN:
			return new CheckerImgsBroken();
		case ANALITICA:
			return new CheckerAnalitica();
		case GOOGLE_ANALYTICS:
			return new CheckerGoogleAnalytics();
		case TEXTS_TRADUCED:
			return new CheckerTextsTraduced();
		case NET_TRAFFIC:
			return new CheckerNetTraffic();
		case COOKIES_ALLOWED:
			return new CheckerAllowedCookies();
		}
		
		throw new IllegalArgumentException("The para " + check + "is not covered by the method");
	}
}
