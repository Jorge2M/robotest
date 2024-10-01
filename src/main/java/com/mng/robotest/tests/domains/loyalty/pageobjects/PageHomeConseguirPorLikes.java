package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.testslegacy.beans.Pais;

public interface PageHomeConseguirPorLikes {

	public boolean isPage(int seconds);
	public int selectConseguirButton();
	public boolean isVisibleIconOperationDoneUntil(int seconds);
	
	public static PageHomeConseguirPorLikes make(Pais pais) {
		if (LoyTestCommons.isMlyTiers(pais)) {
			return new PageHomeConseguirPorLikesNew();
		}
		return new PageHomeConseguirPorLikesOld();
	}	
	
}
