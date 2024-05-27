package com.mng.robotest.tests.domains.galeria.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.GaleriaSteps;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.tests.domains.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType.NEW_NOW;
import static com.mng.robotest.testslegacy.data.PaisShop.MONACO;

public class Gpo009 extends TestBase {

	private final Pais monaco = MONACO.getPais();
	private final GaleriaSteps galeriaSteps;
	private final CommonsGaleria commonsGaleria = new CommonsGaleria();
	
	public Gpo009() {
		dataTest.setPais(monaco);
		galeriaSteps = new GaleriaSteps();
	}
	
	@Override
	public void execute() throws Exception {
		access();
		selectGalery();
		priceFilter();
//		colorFilter();
//		sizeFilter();
//		submenuFilter();
	}
	
	private void priceFilter() throws Exception {
		galeriaSteps.selectPricesInterval();
	}

	private void selectGalery() {
		if (commonsGaleria.isGroupNewNowSelectable()) {
			clickGroup(NEW_NOW);
		} else {
			clickMenu(JERSEIS_Y_CARDIGANS_SHE);
		}
	}

}
