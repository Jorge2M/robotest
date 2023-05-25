package com.mng.robotest.test.steps.shop.genericchecks;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.robotest.domains.accesibilidad.steps.CheckerAccesibility;

import static com.github.jorge2m.testmaker.conf.State.*;

public class GenericChecks extends PageObjTM {

	private final State accesibility;
	private final State jsErrors;
	private final State imgsBroken;
	private final State netTraffic;
	
	private GenericChecks(
			State accesibility, State jsErrors, State imgsBroken, State netTraffic) {
		this.accesibility = accesibility;
		this.jsErrors = jsErrors;
		this.imgsBroken = imgsBroken;
		this.netTraffic = netTraffic;
	}
	
	public void checks() {
		if (accesibility!=null) {
			executeCheck(new CheckerAccesibility(accesibility));
		}
		if (jsErrors!=null) {
			executeCheck(new CheckerJSerrors(jsErrors));
		}
		if (imgsBroken!=null) {
			executeCheck(new CheckerImgsBroken(imgsBroken));
		}
		if (netTraffic!=null) {
			executeCheck(new CheckerNetTraffic(netTraffic));
		}
	}
	
	public void executeCheck(Checker checker) {
		ChecksTM checks = checker.check(driver);
		if (checks.size()>0) {
			createValidation(checks);
		}
	}
	
	@Validation
	private ChecksTM createValidation(ChecksTM checks) {
		return checks;
	}
	
	public static class BuilderCheck {
		
		protected State accesibility;
		protected State jsErrors;
		protected State imgsBroken;
		protected State netTraffic;
		
		public BuilderCheck accesibility(State level) {
			accesibility = level;
			return this;
		}
		public BuilderCheck accesibility() {
			accesibility = Warn;
			return this;
		}

		public BuilderCheck jsErrors(State level) {
			jsErrors = level;
			return this;
		}
		public BuilderCheck jsErrors() {
			jsErrors = Info;
			return this;
		}

		public BuilderCheck imgsBroken(State level) {
			imgsBroken = level;
			return this;
		}
		public BuilderCheck imgsBroken() {
			imgsBroken = Warn;
			return this;
		}

		public BuilderCheck netTraffic(State level) {
			netTraffic = level;
			return this;
		}
		public BuilderCheck netTraffic() {
			netTraffic = Warn;
			return this;
		}
		
		public GenericChecks build() {
			return new GenericChecks(accesibility, jsErrors, imgsBroken, netTraffic);
		}
		
	}
	
}
