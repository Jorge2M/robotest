package com.mng.robotest.domains.galeria.tests;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop.TypeSlider.*;

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
