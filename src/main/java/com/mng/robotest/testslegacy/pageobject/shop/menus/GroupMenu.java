package com.mng.robotest.testslegacy.pageobject.shop.menus;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.testslegacy.pageobject.shop.menus.MenuLateralDesktop.Element;
import static com.mng.robotest.testslegacy.pageobject.shop.menus.MenuLateralDesktop.Element.*;

public enum GroupMenu {
	
	ARTICLES(Arrays.asList(ARTICLE)),
	CAMPAIGNS(Arrays.asList(ARTICLE, CAMPAIGN)),
	UNKNOWN(Arrays.asList(ARTICLE, CAMPAIGN, SLIDER, MAP, IFRAME));
	
	private List<Element> elementsCanBeContained;
	private GroupMenu(List<Element> elementsCanBeContained) {
		this.elementsCanBeContained = elementsCanBeContained;
	}
	
	public List<Element> getElementsCanBeContained() {
		return this.elementsCanBeContained;
	}
	
	public boolean canContainElement(Element element) {
		return (getElementsCanBeContained().contains(element));
	}
}
