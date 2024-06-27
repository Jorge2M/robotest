package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;

public class Gpo005 extends TestBase {

	private final GaleriaSteps galeriaSteps = new GaleriaSteps();
	private final CommonsGaleria commonsGaleria = new CommonsGaleria();
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu();
//		if (!channel.isDevice()) {
			selectPricesInterval();
//		}
		clickSubmenu();
	}

	private void clickMenu() {
		if (commonsGaleria.isGroupNewNowSelectable()) {
			clickGroup(NEW_NOW);
		} else {
			clickMenu(JERSEIS_Y_CARDIGANS_SHE);
		}
	}
	
	private void selectPricesInterval() throws Exception {
		galeriaSteps.selectPricesInterval(20, 35);
	}
	
	private void clickSubmenu() {
		clickMenu(VESTIDOS_SHE);
		if (app==outlet || channel.isDevice()) {
			clickSubMenu(JERSEIS_Y_CARDIGANS_JERSEIS_SHE);
		} else {
			clickSubMenu(CAMISAS_BASICAS_SHE);
		}		
	}
	
}
