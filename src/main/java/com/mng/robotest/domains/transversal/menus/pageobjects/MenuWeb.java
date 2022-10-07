package com.mng.robotest.domains.transversal.menus.pageobjects;

import java.util.List;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.transversal.PageBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;

public class MenuWeb extends PageBase implements MenuActions {
	
	private final LineaType linea;
	private final SublineaType sublinea;
	private final GroupType group;
	private final String menu;
	private final List<String> articles;
	private final String subMenu;
	private final List<String> subMenusShop;
	private final List<String> subMenusOutlet;
	private final List<String> articlesSubMenu;
	
	private String nameScreen = null;
	
	private final MenuActions menuActions;
	
	public static MenuWeb of(String menuLabel) {
		return new Builder(menuLabel).build();
	}
	
	private MenuWeb(
			LineaType linea, SublineaType sublinea, GroupType group, String menu, List<String> articles, 
			String subMenu, List<String> subMenusShop, List<String> subMenusOutlet, List<String> articlesSubMenu) {
		this.linea = linea;
		this.sublinea = sublinea;
		this.group = group;
		this.menu = menu;
		this.articles = articles;		
		this.subMenu = subMenu;
		this.subMenusShop = subMenusShop;
		this.subMenusOutlet = subMenusOutlet;
		this.articlesSubMenu = articlesSubMenu;
		this.menuActions = MenuActions.make(this, channel);
	}
	
	@Override
	public String click() {
		String nameScreen = menuActions.click();
		this.nameScreen = nameScreen;
		return nameScreen;
	}
	@Override
	public void clickSubMenu() {
		menuActions.clickSubMenu();
	}
	@Override
	public boolean isVisibleMenu() {
		return menuActions.isVisibleMenu();
	}
	@Override
	public boolean isVisibleSubMenus() {
		return menuActions.isVisibleSubMenus();
	}
	
	public LineaType getLinea() {
		return linea;
	}
	
	public SublineaType getSublinea() {
		return sublinea;
	}

	public GroupType getGroup() {
		return group;
	}

	public String getMenu() {
		return menu;
	}
	
	public String getNameScreen() {
		if (nameScreen==null) {
			return menu;
		}
		return nameScreen;
	}

	public List<String> getArticles() {
		return articles;
	}		
	
	public String getSubMenu() {
		return subMenu;
	}

	public MenuActions getMenuActions() {
		return menuActions;
	}	
	
	public List<String> getSubMenus() {
		if (app==AppEcom.outlet) {
			return getSubMenusOutlet();
		}
		return getSubMenusShop();
	}
	
	private List<String> getSubMenusShop() {
		return subMenusShop;
	}
	
	private List<String> getSubMenusOutlet() {
		return subMenusOutlet;
	}

	public List<String> getArticlesSubMenu() {
		return articlesSubMenu;
	}	
	
	public static class Builder {
		private LineaType linea;
		private SublineaType sublinea;
		private GroupType group;
		private final String menu;
		private List<String> articles;
		private String subMenu;
		private List<String> subMenusShop;
		private List<String> subMenusOutlet;
		private List<String> articlesSubMenu;
		
		public Builder(String menu) {
			this.menu = menu;
			this.linea = LineaType.she;
			this.group = GroupType.PRENDAS;
		}
		
		public MenuWeb build() {
			return (
				new MenuWeb(linea, sublinea, group, menu, articles, subMenu, subMenusShop, subMenusOutlet, articlesSubMenu));
		}
		
		public Builder linea(LineaType linea) {
			this.linea = linea;
			return this;
		}
		public Builder sublinea(SublineaType sublinea) {
			this.sublinea = sublinea;
			return this;
		}
		public Builder group(GroupType group) {
			this.group = group;
			return this;
		}
		public Builder articles(List<String> articles) {
			this.articles = articles;
			return this;
		}
		public Builder subMenus(List<String> subMenus) {
			this.subMenusShop = subMenus;
			this.subMenusOutlet = subMenus;
			return this;
		}
		public Builder subMenusShop(List<String> subMenusShop) {
			this.subMenusShop = subMenusShop;
			return this;
		}
		public Builder subMenusOutlet(List<String> subMenusOutlet) {
			this.subMenusOutlet = subMenusOutlet;
			return this;
		}
		public Builder subMenu (String subMenu) {
			this.subMenu = subMenu;
			return this;
		}
		public Builder articlesSubMenu(List<String> articlesSubMenu) {
			this.articlesSubMenu = articlesSubMenu;
			return this;
		}
		
	}

}
