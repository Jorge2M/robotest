package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;
import static com.mng.robotest.conf.AppEcom.*;

public class Gpo005 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
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
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);			
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
		pageGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
	}
	
	private void checkCrossSelling() {
		pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);
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
