package com.mng.robotest.domains.galeria.tests;

import java.util.Arrays;

import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.TestBase;

import static com.mng.robotest.domains.transversal.menus.beans.FactoryMenus.MenuItem.*;
import static com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop.TypeSlider.*;

public class Gpo006 extends TestBase {

	private final PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
	
	@Override
	public void execute() throws Exception {
		access();
		clickMenu(VESTIDOS_SHE);
		checkSliders();		
	}

	private void checkSliders() throws Exception {
		String src2onImage = pageGaleriaSteps.clicksSliderArticuloConColores(1, Arrays.asList(NEXT));
		pageGaleriaSteps.clicksSliderArticuloConColores(1, Arrays.asList(PREV));
		pageGaleriaSteps.clicksSliderArticuloConColores(1, Arrays.asList(NEXT), src2onImage);
		String srcImgAfterClickColor = pageGaleriaSteps.selecColorFromArtGaleriaStep(1, 2);
		pageGaleriaSteps.clicksSliderArticuloConColores(1, Arrays.asList(NEXT, NEXT));
		pageGaleriaSteps.clicksSliderArticuloConColores(1, Arrays.asList(PREV, PREV), srcImgAfterClickColor);
	}

}
