package com.mng.robotest.test80.mango.test.pageobject.shop.menus;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;

public class MenuTreeAppTest {

	@Test
	public void testGetInitialMenuWithSubmenus() {
		String litMenuVestidos = "vestidos";
		int initialSizeListMenus = MenuTreeApp.of(AppEcom.shop).getMenus1rstLevel().size();
		 Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(AppEcom.shop, KeyMenu1rstLevel.from(LineaType.she, null, litMenuVestidos));
		 int finalSizeListMenus = MenuTreeApp.of(AppEcom.shop).getMenus1rstLevel().size();
		 
		 assertTrue(finalSizeListMenus==initialSizeListMenus);
		 assertTrue(menuVestidos.getNombre().compareTo(litMenuVestidos)==0);
		 assertTrue(menuVestidos.getListMenus2onLevel().size()>0);
	}

	@Test
	public void testGetAndStoreNewMenu() {
		String litMenuShorts = "shorts";
		int initialSizeListMenus = MenuTreeApp.of(AppEcom.shop).getMenus1rstLevel().size();
		Menu1rstLevel menuShorts = MenuTreeApp.getMenuLevel1From(AppEcom.shop, KeyMenu1rstLevel.from(LineaType.she, null, litMenuShorts));
		int finalSizeListMenus = MenuTreeApp.of(AppEcom.shop).getMenus1rstLevel().size();
		
		assertTrue(finalSizeListMenus==(initialSizeListMenus+1));
		assertTrue(menuShorts.getNombre().compareTo(litMenuShorts)==0);
		assertTrue(menuShorts.getListMenus2onLevel().size()==0);
	}
}
