package com.mng.robotest.tests.domains.loyalty.pageobjects;

import com.mng.robotest.tests.domains.transversal.exceptions.NotImplementedYetException;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class PageMangoLikesYouNew extends PageMangoLikesYou {

	private static final String XP_STATUS_MODULE = "//*[@data-testid='spaceMangoLikesYou.statusModule.section']";
	private static final String XP_NUMBER_POINTS = "//*[@data-testid='spaceMangoLikesYou.statusModule.likesCount']";
	private static final String XP_LINK_HISTORIAL = "//*[@data-testid='spaceMangoLikesYou.statusModule.historyButton']";
	private static final String XP_CARROUSEL_EXPERIENCES = "//*[@data-testid='spaceMangoLikesYou.carouselModule.experiences']";
	private static final String XP_EXPERIENCE = XP_CARROUSEL_EXPERIENCES + "//*[@data-testid[contains(.,'experienceCard')]]";
	private static final String XP_EXPERIENCE_DESCUENTOS_COMPRAS = XP_EXPERIENCE + "//*[text()='Descuentos en tus compras']";
	private static final String XP_CARROUSEL_DONATIONS = "//*[@data-testid='spaceMangoLikesYou.carouselModule.nonProfits']";
	private static final String XP_DONATION = XP_CARROUSEL_DONATIONS + "//*[@data-testid[contains(.,'nonProfitCard')]]";
	private static final String XP_LINK_AYUDA_VER_MAS = "//a[@href[contains(.,'/help')]]";
	
	@Override
	public boolean isPage(int seconds) {
		return state(VISIBLE, XP_STATUS_MODULE).wait(seconds).check();
	}
	@Override
	public int getPoints() {
		String pointsStr = getElement(XP_NUMBER_POINTS).getText();
		return Integer.parseInt(pointsStr.replace("", ""));
	}
	@Override
	public void clickHistorial() {
		click(XP_LINK_HISTORIAL).exec();
	}
	@Override
	public boolean isVisibleCompraConDescuentoExperience(int seconds) {
		return state(VISIBLE, XP_EXPERIENCE_DESCUENTOS_COMPRAS).wait(seconds).check();  
	}
	@Override
	public void clickCompraConDescuentoExperience() {
		click(XP_EXPERIENCE_DESCUENTOS_COMPRAS).exec();
	}
	@Override
	public void clickDonation() {
		click(XP_DONATION).exec();
	}
	@Override
	public void clickExchangeLikesForExperience() {
		click(XP_EXPERIENCE).exec();
	}
	@Override
	public void clickAyuda() {
		click(XP_LINK_AYUDA_VER_MAS).exec();
	}
	@Override
	public boolean isPageAyudaMangoLikesYouVisible(int seconds) {
		throw new NotImplementedYetException();
	}

}
