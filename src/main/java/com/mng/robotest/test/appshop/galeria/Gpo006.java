package com.mng.robotest.test.appshop.galeria;

import java.util.ArrayList;
import java.util.List;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeSlider;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;

public class Gpo006 extends TestBase {

	@Override
	public void execute() throws Exception {
		access();
		clickMenu(MenuWeb.VESTIDOS_SHE);
				
		PageGaleriaSteps pageGaleriaSteps = new PageGaleriaSteps();
		List<TypeSlider> typeSliderList = new ArrayList<>();
		typeSliderList.add(TypeSlider.NEXT);
		String src2onImage = pageGaleriaSteps.clicksSliderArticuloConColores(1, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.PREV);
		int numArtConColores = 1;
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.NEXT);
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList, src2onImage);
		
		//Seleccionar el 2o color del artículo
		String srcImgAfterClickColor = pageGaleriaSteps.selecColorFromArtGaleriaStep(numArtConColores, 2);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.NEXT);
		typeSliderList.add(TypeSlider.NEXT);
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList);
		
		typeSliderList.clear();
		typeSliderList.add(TypeSlider.PREV);
		typeSliderList.add(TypeSlider.PREV);
		pageGaleriaSteps.clicksSliderArticuloConColores(numArtConColores, typeSliderList, srcImgAfterClickColor);		
	}

}
