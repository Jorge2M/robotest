package com.mng.robotest.test80.mango.test.stpv.shop.genericchecks;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class GenericChecks {

	private final List<GenericCheck> listChecks;
	
	public enum GenericCheck {
		SEO(State.Info),
		JSerrors(State.Info),
		ImgsBroken(State.Warn),
		Analitica(State.Defect),
		NetTraffic(State.Warn),
		GoogleAnalytics(State.Warn);
		
		State state;
		private GenericCheck(State state) {
			this.state = state;
		}
		
		public State getLevel() {
			return state;
		}
	}
	
	private GenericChecks(List<GenericCheck> listChecks) {
		this.listChecks = listChecks;
	}
	
	public static GenericChecks from(List<GenericCheck> listChecks) {
		return new GenericChecks(listChecks);
	}
	
	public void checks(WebDriver driver) {
		for (GenericCheck genericCheck : listChecks) {
			Checker checker = Checker.make(genericCheck);
			ChecksTM checks = checker.check(driver);
			if (checks.size()>0) {
				createValidation(checks);
			}
		}
	}
	
	@Validation
	private ChecksTM createValidation(ChecksTM checks) {
		return checks;
	}
}
