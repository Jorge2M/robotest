package com.mng.robotest.test.pageobject.shop.menus;

import java.util.Arrays;
import java.util.List;

import com.mng.robotest.test.pageobject.shop.menus.MenuLateralDesktop.Element;

public enum GroupMenu {
	
	Articles(Arrays.asList(Element.article)),
	Campaigns(Arrays.asList(Element.article, Element.campaign)),
	Unknown(Arrays.asList(Element.article, Element.campaign, Element.slider, Element.map, Element.iframe));
	
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
