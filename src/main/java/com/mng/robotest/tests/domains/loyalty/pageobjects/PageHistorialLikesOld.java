package com.mng.robotest.tests.domains.loyalty.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import java.util.Optional;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.loyalty.beans.LoyaltyMovement;

public class PageHistorialLikesOld extends PageBase implements PageHistorialLikes {
	
	private static final String XP_LIST_MOVIMIENTOS = "//div[@id='history-list']";
	private static final String XP_MOVIMIENTO = XP_LIST_MOVIMIENTOS + "/div";
	
	private String getXPathMovimiento(int position) {
		return "(" + XP_MOVIMIENTO + ")[" + position + "]"; 
	}
	
	private String getXPathConcepto(int position) {
		return getXPathMovimiento(position) + "/div[3]/div[2]/div"; 
	}
	
	private String getXPathPointsReceived(int position) {
		return getXPathMovimiento(position) + "/div[3]/div[2]/div[2]/div";
	}
	
	private String getXPathPointsUsed(int position) {
		return getXPathMovimiento(position) + "/div[3]/div[2]/div[3]/div";
	}	
	
	@Override
	public boolean isMovimientoVisible(int seconds) {
		return isMovimientoVisible(1, seconds);
	}
	
	@Override
	public boolean isMovimientoVisible(int position, int seconds) {
		return state(VISIBLE, getXPathMovimiento(position)).wait(seconds).check();
	}	
	
	@Override
	public Optional<LoyaltyMovement> getLoyaltyMovement(int position) {
		if (!isMovimientoVisible(position, 0)) {
			return Optional.empty();
		}
		var loyaltyMovement = new LoyaltyMovement(
				getConcepto(position), 
				getPointsReceived(position),
				getPointsUsed(position));
				
		return Optional.of(loyaltyMovement);
	}
	
	private String getConcepto(int position) {
		return getElement(getXPathConcepto(position)).getText();
	}
	
	private int getPointsReceived(int position) {
		var pointsElem = getElementIfExists(getXPathPointsReceived(position));
		if (pointsElem.isPresent()) {
			return getIntegerPoints(pointsElem.get().getText());
		}
		return 0;
	}
	
	private int getPointsUsed(int position) {
		var pointsElem = getElementIfExists(getXPathPointsUsed(position));
		if (pointsElem.isPresent()) {
			return getIntegerPoints(pointsElem.get().getText());
		}
		return 0;
	}	
	
}
