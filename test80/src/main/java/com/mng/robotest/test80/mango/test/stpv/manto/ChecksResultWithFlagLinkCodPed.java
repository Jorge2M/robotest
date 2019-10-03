package com.mng.robotest.test80.mango.test.stpv.manto;

import com.mng.testmaker.annotations.validation.ChecksResult;

public class ChecksResultWithFlagLinkCodPed extends ChecksResult {
	boolean existsLinkCodPed;
	private ChecksResultWithFlagLinkCodPed() {
		super();
	}
	public static ChecksResultWithFlagLinkCodPed getNew() {
		return (new ChecksResultWithFlagLinkCodPed());
	}
	
	public boolean getExistsLinkCodPed() {
		return this.existsLinkCodPed;
	}
	
	public void setExistsLinkCodPed(boolean existsLinkCodPed) {
		this.existsLinkCodPed = existsLinkCodPed;
	}
}
