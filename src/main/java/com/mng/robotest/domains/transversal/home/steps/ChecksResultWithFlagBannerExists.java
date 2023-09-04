package com.mng.robotest.domains.transversal.home.steps;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class ChecksResultWithFlagBannerExists extends ChecksTM {
	boolean existBanner;
	private ChecksResultWithFlagBannerExists() {
		super();
	}
	public static ChecksResultWithFlagBannerExists getNew() {
		return (new ChecksResultWithFlagBannerExists());
	}
	
	public boolean getExistBanner() {
		return this.existBanner;
	}
	
	public void setexistBanner(boolean existBanner) {
		this.existBanner = existBanner;
	}
}
