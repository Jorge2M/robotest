package com.mng.robotest.test80.mango.test.stpv.shop.home;

import com.mng.testmaker.annotations.validation.ChecksResult;

public class ChecksResultWithFlagBannerExists extends ChecksResult {
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
