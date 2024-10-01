package com.mng.robotest.tests.domains.loyalty.pageobjects;

import java.util.Optional;

import com.mng.robotest.tests.domains.loyalty.beans.LoyaltyMovement;
import com.mng.robotest.tests.domains.loyalty.tests.LoyTestCommons;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.utils.ImporteScreen;

public interface PageHistorialLikes {

	public boolean isMovimientoVisible(int seconds);
	public boolean isMovimientoVisible(int position, int seconds);
	public Optional<LoyaltyMovement> getLoyaltyMovement(int position);
	
	public static PageHistorialLikes make(Pais pais) {
		if (LoyTestCommons.isMlyTiers(pais)) {
			return new PageHistorialLikesNew();
		}
		return new PageHistorialLikesOld();
	}
	
	public default int getIntegerPoints(String pointsText) {
		float importFloat = ImporteScreen.getFloatFromImporteMangoScreen(pointsText);
		return Math.round(importFloat);		
	}	
	
}
