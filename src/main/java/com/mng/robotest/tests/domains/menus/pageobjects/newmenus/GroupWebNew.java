package com.mng.robotest.tests.domains.menus.pageobjects.newmenus;

import com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.menus.entity.GroupResponse.*;

public class GroupWebNew extends GroupWeb {
	
	private static final String XP_GROUP_DESKTOP = "//li[@data-testid[contains(.,'menu.section')]]";
	private static final String XP_SUBMENU_DEVICE = "//li[@data-testid[contains(.,'menu.family.')]]//..//self::ul";

	public GroupWebNew(GroupType group) {
		super(group);
	}
	public GroupWebNew(LineaType linea, SublineaType sublinea, GroupType group) {
		super(linea, sublinea, group);
	}
	
	private String getXPathGroupSelectedDesktop() {
		return getXPathGroup() + "//self::*[@class[contains(.,'_active_')]]";
	}
	
	private String getXPathGroup() {
		if (group.getGroupResponse()==ARTICLES) {
			String dataTestId = "[@data-testid[contains(.,'" + group.getId() + ".link')]]";
			return XP_GROUP_DESKTOP + "//a" + dataTestId;
		} else {
			String dataTestId = "[@data-testid[contains(.,'" + group.getId() + "_" + getIdLinea() + "')]]";
			return XP_GROUP_DESKTOP + "//button" + dataTestId;
		}
	}

	private String getXPathSubmenu() {
		if (channel.isDevice()) {
			return XP_SUBMENU_DEVICE;
		}
		return getXPathSubmenuDesktop(); 
	}
	
	private String getXPathSubmenuDesktop() {
		return "//ul[@data-testid='menu.family." + group + "_" + getIdLinea() + "']";
	}
	
	@Override
	public void click() {
		hoverLinea(); 
		clickGroup();
	}
	
	@Override
	public void hover() {
		for (int i=0; i<3; i++) {
			hoverLinea(); 
			hoverGroup();
			if (isGroupCorrectlySelected()) {
				break;
			}
		}
	}	
	
	@Override
	public boolean isPresent() {
		hoverLinea();
		return state(VISIBLE, getXPathGroup()).wait(1).check();
	}

	@Override
	public boolean isVisibleSubMenus() {
		return state(VISIBLE, getXPathSubmenu()).check();
	}
	
	private void clickGroup() {
		state(VISIBLE, getXPathGroup()).wait(2).check();
		click(getXPathGroup()).state(VISIBLE).exec(); 
		if (!isVisibleSubMenus() && group.getGroupResponse()==MENUS) {
			waitMillis(1000);
			click(getXPathGroup()).exec();
		}
	}
	
	private void hoverGroup() {
		state(VISIBLE, getXPathGroup()).wait(2).check();
		moveToElement(getXPathGroup()); 
	}
	
	private boolean isGroupCorrectlySelected() {
		return (isGroupSelected() &&
			   (group.getGroupResponse()!=MENUS || isVisibleSubMenus()));
	}
	private boolean isGroupSelected() {
		if (isDesktop()) {
			return state(VISIBLE, getXPathGroupSelectedDesktop()).wait(1).check();
		}
		return true; //Impossible to view if group is selected
	}
	
	private void hoverLinea() {
		var lineaWeb = new LineaWebNew(linea, sublinea);
		lineaWeb.hoverLinea(); 
		if (sublinea!=null) {
			lineaWeb.hoverSublinea();
		}
	}

}

