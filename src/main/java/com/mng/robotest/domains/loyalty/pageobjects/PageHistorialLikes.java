package com.mng.robotest.domains.loyalty.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.Optional;

import com.mng.robotest.domains.loyalty.beans.LoyaltyMovement;
import com.mng.robotest.domains.transversal.PageBase;

public class PageHistorialLikes extends PageBase {
	
	private static final String XPATH_LIST_MOVIMIENTOS = "//div[@id='history-list']";
	
	//TODO solicitar data-testid en lugar de React
	private static final String XPATH_MOVIMIENTO = XPATH_LIST_MOVIMIENTOS + "/div";	
	
	private String getXPathMovimiento(int position) {
		return "(" + XPATH_MOVIMIENTO + ")[" + position + "]"; 
	}
	private String getXPathConcepto(int position) {
		return getXPathMovimiento(position) + "/div[3]/div[2]/div"; 
	}
	private String getXPathPoints(int position) {
		return getXPathMovimiento(position) + "/div[3]/div[2]/div[2]/div";
	}
	
	public boolean isMovimientoVisible(int seconds) {
		return isMovimientoVisible(1, seconds);
	}
	public boolean isMovimientoVisible(int position, int seconds) {
		return state(Visible, getXPathMovimiento(position)).wait(seconds).check();
	}	
	
	public Optional<LoyaltyMovement> getLoyaltyMovement(int position) {
		if (!isMovimientoVisible(position, 0)) {
			return Optional.empty();
		}
		return Optional.of(new LoyaltyMovement(getConcepto(position), getPoints(position)));
	}
	
	private String getConcepto(int position) {
		return getElement(getXPathConcepto(position)).getText();
	}
	private int getPoints(int position) {
		return Integer.valueOf(getElement(getXPathPoints(position)).getText());
	}

}
