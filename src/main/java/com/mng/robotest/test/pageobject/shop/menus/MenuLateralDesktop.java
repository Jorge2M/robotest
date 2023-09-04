package com.mng.robotest.test.pageobject.shop.menus;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.conf.AppEcom;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

public interface MenuLateralDesktop {
	public abstract AppEcom getApp();
	public abstract LineaType getLinea();
	abstract SublineaType getSublinea();
	abstract String getNombre();
	abstract String getDataGaLabelMenuLateralDesktop();
	abstract String getDataTestIdMenuSuperiorDesktop();
	public abstract boolean isMenuLateral();
	public abstract boolean isDataForValidateArticleNames();
	public abstract String[] getTextsArticlesGalery();
	public abstract GroupMenu getGroup(Channel channel);
	abstract int getLevel();
	
	public enum Element {
		article, campaign, slider, map, iframe
	}


}
