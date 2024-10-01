package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.testslegacy.beans.Pais;

public interface PageHomeDonateLikes {

	public boolean isPage(int seconds);
	public boolean isVisibleAnyButton(int seconds, Integer... numLikes);
	public boolean isVisibleButton(int numLikes, int seconds);
	public void clickButton(int numLikes);
	public boolean isVisibleIconOperationDoneUntil(int seconds);
	
	public static PageHomeDonateLikes make(Pais pais) {
		if (LoyTestCommons.isMlyTiers(pais)) {
			return new PageHomeDonateLikesNew();
		}
		return new PageHomeDonateLikesOld();
	}	
	
}
