package com.mng.robotest.domains.transversal.menus.pageobjects;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.galeria.pageobjects.SecSubmenusGallery;

public class MenuActionsDesktop extends PageBase implements MenuActions {

	private final MenuWeb menu;
	
	private String getXPathMenu() {
		return 
			"(" + getXPathMenuStandard() + ") | " + 
			"(" + getXPathMenuAlternative() + ")";
	}
	
	//TODO eliminar la parte Old cuando los nuevos data-testids suban a PRO (03-05-23)
	private String getXPathMenuStandard() {
		return "(" + getXPathMenuStandardOld() + " | " + getXPathMenuStandardNew() + ")";
	}
	
	private String getXPathMenuStandardOld() {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}
			
		String nameMenu = menu.getMenu().toLowerCase();
		String xpath =  
			"//ul/li//a[@data-testid='header.section.link." + 
			nameMenu + "_" + idLinea + "'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='header.section.link." + menuIni + "_" + idLinea + "'"; 
		}
		xpath+="]";
		return xpath;
	}
	
	private String getXPathMenuStandardNew() {
		String idLinea = menu.getLinea().name().toLowerCase();
		if (menu.getSublinea()!=null) {
			idLinea = menu.getSublinea().getId(app);
		}
			
		String nameMenu = menu.getMenu().toLowerCase();
		String xpath =  
			"//ul/li//a[@data-testid='menu.family." + 
			nameMenu + "_" + idLinea + ".link'";
		
		if (nameMenu.contains(" ")) {
			String menuIni = nameMenu.substring(0, menu.getMenu().indexOf(" "));
			xpath+=" or @data-testid='menu.family." + menuIni + "_" + idLinea + ".link'"; 
		}
		xpath+="]";
		return xpath;
	}	
	
	//TODO eliminar la parte Old cuando los nuevos data-testids suban a PRO (03-05-23)
	private String getXPathMenuAlternative() {
		return "(" + getXPathMenuAlternativeOld() + " | " + getXPathMenuAlternativeNew() + ")";
	}
	private String getXPathMenuAlternativeOld() {
		return "//ul/li//a[" + 
				"@data-testid[contains(.,'header.section.link.')]]" +
			    "//self::*[text()[contains(.,'" + menu.getMenu() + "')]]";
	}
	private String getXPathMenuAlternativeNew() {
		return "//ul/li//a[" + 
				"@data-testid[contains(.,'menu.family.')]]" +
			    "//self::*[text()[contains(.,'" + menu.getMenu() + "')]]";
	}	
	
	public MenuActionsDesktop(MenuWeb menu) {
		this.menu = menu;
	}
	
	@Override
	public String click() {
		hoverGroup();
		return clickMenuSuperior();
	}
	@Override
	public void clickSubMenu() {
		new SecSubmenusGallery().clickSubmenu(menu.getSubMenu());
	}
	@Override
	public boolean isVisibleMenu() {
		return state(Visible, getXPathMenu()).check();
	}
	@Override
	public boolean isVisibleSubMenus() {
		var secSubmenusGallery = new SecSubmenusGallery();
		for (String subMenu : menu.getSubMenus()) {
			if (!secSubmenusGallery.isVisibleSubmenu(subMenu)) {
				return false;
			}
		}
		return true;
	}

	private void hoverGroup() {
		new GroupWeb(menu.getLinea(), menu.getSublinea(), menu.getGroup())
			.hover();
	}	
	
	private String clickMenuSuperior() {
		waitMillis(200);
		String nameMenu = getElement(getXPathMenu()).getText(); 
		click(getXPathMenu()).exec();
		return nameMenu;
	}	

}
