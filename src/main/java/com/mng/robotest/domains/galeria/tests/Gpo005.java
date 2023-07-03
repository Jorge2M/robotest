package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType.*;

public class Gpo005 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		accessAndLogin();
		clickMenu();
		if (!channel.isDevice()) {
			selectPricesInterval();
		}
		if (app!=AppEcom.outlet && !channel.isDevice()) {
			checkCrossSelling();
		}	
		clickSubmenu();
	}

	private void clickMenu() {
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickMenu(CARDIGANS_Y_JERSEIS_SHE);
		} else {
			clickGroup(NEW_NOW);
			pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);
		}
		//checksGeneric().accesibility().execute();
	}
	
	private void selectPricesInterval() throws Exception {
		pageGaleriaSteps.getSecSelectorPreciosSteps().seleccionaIntervalo();
	}
	
	private void checkCrossSelling() {
		pageGaleriaSteps.secCrossSellingSteps.validaIsCorrect(LineaType.SHE, null);
	}
	
	private void clickSubmenu() {
		clickMenu(VESTIDOS_SHE);
		if (app==AppEcom.outlet || channel.isDevice()) {
			clickSubMenu(CARDIGANS_Y_JERSEIS_JERSEIS_SHE);
		} else {
			clickSubMenu(CAMISAS_BASICAS_SHE);
		}		
	}
	
}
