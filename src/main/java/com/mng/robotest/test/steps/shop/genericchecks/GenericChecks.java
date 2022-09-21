package com.mng.robotest.test.steps.shop.genericchecks;

import java.util.Arrays;
import java.util.List;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.transversal.PageBase;

public class GenericChecks extends PageBase {

	private final List<GenericCheck> listChecks;
	
	public enum GenericCheck {
		SEO(State.Info),
		JSerrors(State.Info),
		ImgsBroken(State.Warn),
		Analitica(State.Defect),
		NetTraffic(State.Warn),
		TextsTraduced(State.Defect),
		GoogleAnalytics(State.Warn),
		CookiesAllowed(State.Defect);
		
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
	
	public static void checkDefault() {
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.JSerrors, 
				GenericCheck.TextsTraduced,
				GenericCheck.Analitica)).checks();
	}
	
	public void checks() {
		for (GenericCheck genericCheck : listChecks) {
			if (checkEnabled(genericCheck)) {
				Checker checker = Checker.make(genericCheck);
				ChecksTM checks = checker.check(driver);
				if (checks.size()>0) {
					createValidation(checks);
				}
			}
		}
	}
	
	private boolean checkEnabled(GenericCheck genericCheck) {
		if (genericCheck==GenericCheck.TextsTraduced) {
			return false;
		}
		if (dataTest.getGenericChecksDisabled()!=null && 
			dataTest.getGenericChecksDisabled().contains(genericCheck)) {
			return false;
		}
		return true;
	}
	
	@Validation
	private ChecksTM createValidation(ChecksTM checks) {
		return checks;
	}
}
