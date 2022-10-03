package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.List;

import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.test.beans.Linea.LineaType;

public class Menu extends PageBase {

	public enum GroupType { PRENDAS, ACCESORIOS, INTIMISSIMI, RITUALS, COLECCIONES, DESTACADOS }
	
	private final LineaType linea;
	private final GroupType group;
	private final String menu;
	private final String subMenu;
	private List<String> subMenus;
	private List<String> articles;
	
	private final MenuActions menuActions = MenuActions.make(channel);
	
	private Menu(LineaType linea, GroupType group, String menu, String subMenu, List<String> subMenus, List<String> articles) {
		this.linea = linea;
		this.group = group;
		this.menu = menu;
		this.subMenu = subMenu;
		this.subMenus = subMenus;
		this.articles = articles;
	}
	
	public void click() {
		menuActions.click(this);
	}
	public boolean isVisible() {
		return menuActions.isVisible(this);
	}
	
	public LineaType getLinea() {
		return linea;
	}

	public GroupType getGroup() {
		return group;
	}

	public String getMenu() {
		return menu;
	}

	public String getSubMenu() {
		return subMenu;
	}

	public MenuActions getMenuActions() {
		return menuActions;
	}	
	
	public List<String> getSubMenus() {
		return subMenus;
	}

	public List<String> getArticles() {
		return articles;
	}	
	
	public static class Builder {
		private LineaType linea;
		private GroupType group;
		private final String menu;
		private String subMenu;
		private List<String> subMenus;
		private List<String> articles;
		
		public Builder(String menu) {
			this.menu = menu;
			this.linea = LineaType.she;
			this.group = GroupType.PRENDAS;
		}
		
		public Menu build() {
			return (
				new Menu(linea, group, menu, subMenu, subMenus, articles));
		}
		
		public Builder linea(LineaType linea) {
			this.linea = linea;
			return this;
		}
		public Builder group(GroupType group) {
			this.group = group;
			return this;
		}
		
		public Builder subMenu (String subMenu) {
			this.subMenu = subMenu;
			return this;
		}
		public Builder subMenus(List<String> subMenus) {
			this.subMenus = subMenus;
			return this;
		}
		public Builder articles(List<String> articles) {
			this.articles = articles;
			return this;
		}
		
	}

}
