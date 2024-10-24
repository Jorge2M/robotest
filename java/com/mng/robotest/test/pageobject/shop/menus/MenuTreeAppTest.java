package com.mng.robotest.test.pageobject.shop.menus;

import static org.junit.Assert.*;

import org.junit.Test;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;

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
}
