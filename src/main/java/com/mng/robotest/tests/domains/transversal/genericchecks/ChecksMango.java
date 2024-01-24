package com.mng.robotest.tests.domains.transversal.genericchecks;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.service.genericchecks.Checker;
import com.github.jorge2m.testmaker.service.genericchecks.GenericChecks;
import com.github.jorge2m.testmaker.service.genericchecks.GenericChecks.BuilderCheck;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.cookiesallowed.steps.CheckerAllowedCookies;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ChecksMango extends PageBase {

	private final GenericChecks genericChecks;
	private final State accesibility;
	private final State cookiesAllowed;
	private final State seo;
	private final State analitica;
	private final State textsTraduced;
	private final State googleAnalytics;
	private final State imgsBroken;
	
	private ChecksMango(
			GenericChecks genericChecks, 
			State accesibility, State cookiesAllowed, State seo, State analitica, State textsTraduced, State googleAnalytics, State imgsBroken) {
		this.genericChecks = genericChecks;
		this.accesibility = accesibility;
		this.cookiesAllowed = cookiesAllowed;
		this.seo = seo;
		this.analitica = analitica;
		this.textsTraduced = textsTraduced;
		this.googleAnalytics = googleAnalytics;
		this.imgsBroken = imgsBroken;
	}
	
	public void checks() {
		if (inputParamsSuite.getGenericChecks()) {
			execChecks();
		}
	}
	private void execChecks() {
		genericChecks.checks();
		
		if (accesibility!=null) {
			//executeCheck(new CheckerAccesibility(accesibility));
		}
		if (cookiesAllowed!=null) {
			executeCheck(new CheckerAllowedCookies(cookiesAllowed, app));
		}
		if (seo!=null) {
			executeCheck(new CheckerSEO(seo));
		}
		if (analitica!=null) {
			executeCheck(new CheckerAnalitica(analitica));
		}
		if (textsTraduced!=null) {
			executeCheck(new CheckerTextsTraduced(textsTraduced));
		}
		if (googleAnalytics!=null) {
			executeCheck(new CheckerGoogleAnalytics(googleAnalytics));
		}
		if (imgsBroken!=null) {
			executeCheck(new CheckerImgsBroken(imgsBroken));
		}		
	}
	
	private void executeCheck(Checker chequer) {
		if (checkEnabled(chequer)) {
			genericChecks.executeCheck(chequer);
		}
	}
	
	private boolean checkEnabled(Checker chequer) {
		return !(chequer instanceof CheckerTextsTraduced);
	}	

	
	public static class BuilderChecksMango extends BuilderCheck {
		
		private State accesibility;
		private State cookiesAllowed;
		private State seo;
		private State analitica;
		private State textsTraduced;
		private State googleAnalytics;		
		private State imgsBroken;
		
		public BuilderChecksMango accesibility(State level) {
			accesibility = level;
			return this;
		}
		public BuilderChecksMango accesibility() {
			accesibility = WARN;
			return this;
		}
		
		public BuilderChecksMango cookiesAllowed(State level) {
			cookiesAllowed = level;
			return this;
		}
		public BuilderChecksMango cookiesAllowed() {
			cookiesAllowed = DEFECT;
			return this;
		}

		public BuilderChecksMango seo(State level) {
			seo = level;
			return this;
		}
		public BuilderChecksMango seo() {
			seo = INFO;
			return this;
		}
		
		public BuilderChecksMango analitica(State level) {
			analitica = level;
			return this;
		}
		public BuilderChecksMango analitica() {
			analitica = DEFECT;
			return this;
		}
		
		public BuilderChecksMango textsTraduced(State level) {
			textsTraduced = level;
			return this;
		}
		public BuilderChecksMango textsTraduced() {
			textsTraduced = DEFECT;
			return this;
		}
		
		public BuilderChecksMango googleAnalytics(State level) {
			googleAnalytics = level;
			return this;
		}
		public BuilderChecksMango googleAnalytics() {
			googleAnalytics = WARN;
			return this;
		}

		public BuilderChecksMango imgsBroken(State level) {
			imgsBroken = level;
			return this;
		}
		public BuilderChecksMango imgsBroken() {
			imgsBroken = WARN;
			return this;
		}
		
		@Override
		public BuilderChecksMango jsErrors(State level) {
			jsErrors = level;
			return this;
		}
		@Override
		public BuilderChecksMango jsErrors() {
			jsErrors = INFO;
			return this;
		}

		@Override
		public BuilderChecksMango netTraffic(State level) {
			netTraffic = level;
			return this;
		}
		@Override
		public BuilderChecksMango netTraffic() {
			netTraffic = WARN;
			return this;
		}
		
		public void execute() {
			var genericChecks = super.build();
			new ChecksMango(
					genericChecks, 
					accesibility, cookiesAllowed, seo, analitica, textsTraduced, googleAnalytics, imgsBroken)
				.checks();
		}
	}
	
}
