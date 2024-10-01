package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.testslegacy.beans.Pais;

public interface PageMangoLikesYou {

	public abstract boolean isPage(int seconds);
	public abstract int getPoints();
	public abstract void clickHistorial();
	public abstract void closeHistorial();	
	public abstract boolean isVisibleCompraConDescuentoExperience(int seconds);
	public abstract void clickCompraConDescuentoExperience();
	public abstract void clickDonation();
	public abstract void clickExchangeLikesForExperience();
	public abstract void clickExchangeLikesForExperience(int posExperience);
	public abstract void clickAyuda();
	public abstract boolean isPageAyudaMangoLikesYouVisible(int seconds);	
	
	public static PageMangoLikesYou make(Pais pais) {
		if (LoyTestCommons.isMlyTiers(pais)) {
			return new PageMangoLikesYouNew();
		}
		return new PageMangoLikesYouOld();
	}
	
}