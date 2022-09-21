package com.mng.robotest.test.appshop.otras;

import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.steps.shop.PageIniShopJaponSteps;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;
import com.mng.robotest.test.utils.PaisGetter;

public class Otr005 extends TestBase {

	private static final Pais JAPON = PaisGetter.get(PaisShop.JAPON);
	private static final IdiomaPais JAPONES = JAPON.getListIdiomas().get(0);	
	
	@Override
	public void execute() throws Exception {
		dataTest.setPais(JAPON);
		dataTest.setIdioma(JAPONES);
		PagePrehomeSteps pagePrehomeSteps = new PagePrehomeSteps();
		new PagePrehome().previousAccessShopSteps(true);
		pagePrehomeSteps.seleccionPaisIdioma();
		pagePrehomeSteps.entradaShopGivenPaisSeleccionado();
		new PageIniShopJaponSteps().validaPageIniJapon(2);		
	}
	
}
