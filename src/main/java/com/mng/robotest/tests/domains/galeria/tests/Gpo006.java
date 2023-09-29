package com.mng.robotest.tests.domains.galeria.tests;

import static com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktop.TypeSlider.*;
import static com.mng.robotest.tests.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;

public class Gpo006 extends TestBase {

	private final PageGaleriaSteps pGaleriaSteps = new PageGaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		clickMenu(VESTIDOS_SHE);
		checkSliders();		
	}

	private void checkSliders() throws Exception {
		String src2onImage = pGaleriaSteps.clicksSliderArticuloConColores(1, NEXT);
		pGaleriaSteps.clicksSliderArticuloConColores(1, PREV);
		pGaleriaSteps.clicksSliderArticuloConColores(1, src2onImage, NEXT);
		String srcImgAfterClickColor = pGaleriaSteps.selecColorFromArtGaleriaStep(1, 2);
		pGaleriaSteps.clicksSliderArticuloConColores(1, NEXT, NEXT);
		pGaleriaSteps.clicksSliderArticuloConColores(1, srcImgAfterClickColor, PREV, PREV);
	}

}
