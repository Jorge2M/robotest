package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.conf.AppEcom.shop;
import static com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType.NEW_NOW;

import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb;

public class CommonsGaleria extends StepBase {

	public boolean isGroupNewNowSelectable() {
		return 
			app==shop && !channel.isDevice() &&	
			new GroupWeb(NEW_NOW).isPresent();
	}

}
