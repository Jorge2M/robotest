package com.mng.robotest.tests.domains.menus.pageobjects;

import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.data.PaisShop;

public class IsNewMenu {

	private IsNewMenu() {}
	
	public static boolean is(Pais pais) {
		return 
				PaisShop.ANDORRA.isEquals(pais) ||
				PaisShop.LIECHTENSTEIN.isEquals(pais);
	}

}
