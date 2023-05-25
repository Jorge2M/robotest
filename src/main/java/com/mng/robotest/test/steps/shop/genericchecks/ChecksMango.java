package com.mng.robotest.test.steps.shop.genericchecks;

import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.cookiescheck.steps.CheckerAllowedCookies;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.BuilderCheck;

import static com.github.jorge2m.testmaker.conf.State.*;

public class ChecksMango extends PageBase {

	private final GenericChecks genericChecks;
	private final State cookiesAllowed;
	private final State seo;
	private final State analitica;
	private final State textsTraduced;
	private final State googleAnalytics;
	
	private ChecksMango(
			GenericChecks genericChecks, 
			State cookiesAllowed, State seo, State analitica, State textsTraduced, State googleAnalytics) {
		this.genericChecks = genericChecks;
		this.cookiesAllowed = cookiesAllowed;
		this.seo = seo;
		this.analitica = analitica;
		this.textsTraduced = textsTraduced;
		this.googleAnalytics = googleAnalytics;
	}
	
	public void checks() {
		genericChecks.checks();
		if (cookiesAllowed!=null) {
			executeCheck(new CheckerAllowedCookies(cookiesAllowed));
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
	}
	
	private void executeCheck(Checker chequer) {
		if (checkEnabled(chequer)) {
			genericChecks.executeCheck(chequer);
		}
	}
	
	private boolean checkEnabled(Checker chequer) {
		if (chequer instanceof CheckerTextsTraduced) {
			return false;
		}
		return true;
	}	

	
	public static class BuilderChecksMango extends BuilderCheck {
		
		private State cookiesAllowed;
		private State seo;
		private State analitica;
		private State textsTraduced;
		private State googleAnalytics;		
		
		public BuilderChecksMango cookiesAllowed(State level) {
			cookiesAllowed = level;
			return this;
		}
		public BuilderChecksMango cookiesAllowed() {
			cookiesAllowed = Defect;
			return this;
		}

		public BuilderChecksMango seo(State level) {
			seo = level;
			return this;
		}
		public BuilderChecksMango seo() {
			seo = Info;
			return this;
		}
		
		public BuilderChecksMango analitica(State level) {
			analitica = level;
			return this;
		}
		public BuilderChecksMango analitica() {
			analitica = Defect;
			return this;
		}
		
		public BuilderChecksMango textsTraduced(State level) {
			textsTraduced = level;
			return this;
		}
		public BuilderChecksMango textsTraduced() {
			textsTraduced = Defect;
			return this;
		}
		
		public BuilderChecksMango googleAnalytics(State level) {
			googleAnalytics = level;
			return this;
		}
		public BuilderChecksMango googleAnalytics() {
			googleAnalytics = Warn;
			return this;
		}		
		
		@Override
		public BuilderChecksMango accesibility(State level) {
			accesibility = level;
			return this;
		}
		@Override
		public BuilderChecksMango accesibility() {
			accesibility = Warn;
			return this;
		}

		@Override
		public BuilderChecksMango jsErrors(State level) {
			jsErrors = level;
			return this;
		}
		@Override
		public BuilderChecksMango jsErrors() {
			jsErrors = Info;
			return this;
		}

		@Override
		public BuilderChecksMango imgsBroken(State level) {
			imgsBroken = level;
			return this;
		}
		@Override
		public BuilderChecksMango imgsBroken() {
			imgsBroken = Warn;
			return this;
		}
		
		@Override
		public BuilderChecksMango netTraffic(State level) {
			netTraffic = level;
			return this;
		}
		@Override
		public BuilderChecksMango netTraffic() {
			netTraffic = Warn;
			return this;
		}
		
		public void execute() {
			var genericChecks = super.build();
			new ChecksMango(genericChecks, cookiesAllowed, seo, analitica, textsTraduced, googleAnalytics).checks();
		}
	}
	
}
