package com.mng.robotest.test.stpv.manto;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public class ChecksResultWithFlagLinkCodPed extends ChecksTM {
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
