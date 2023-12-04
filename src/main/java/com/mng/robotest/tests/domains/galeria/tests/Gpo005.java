package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.conf.AppEcom.*;
import static com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

public class Gpo005 extends TestBase {

	private final PageGaleriaSteps pgGaleriaSteps = new PageGaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu();
		if (!channel.isDevice()) {
			selectPricesInterval();
		}
		if (app!=outlet && !channel.isDevice()) {
			checkCrossSelling();
		}	
		clickSubmenu();
	}

	private void clickMenu() {
		if (isGroupNewNowSelectable()) {
			clickGroup(NEW_NOW);
			pgGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);			
		} else {
			clickMenu(CARDIGANS_Y_JERSEIS_SHE);
		}
	}

	private boolean isGroupNewNowSelectable() {
		return 
			app==shop && !channel.isDevice() &&	
			new GroupWeb(NEW_NOW).isPresent();
	}
	
	private void selectPricesInterval() throws Exception {
		pgGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
	}
	
	private void checkCrossSelling() {
		pgGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);
	}
	
	private void clickSubmenu() {
		clickMenu(VESTIDOS_SHE);
		if (app==outlet || channel.isDevice()) {
			clickSubMenu(CARDIGANS_Y_JERSEIS_JERSEIS_SHE);
		} else {
			clickSubMenu(CAMISAS_BASICAS_SHE);
		}		
	}
	
}
