package com.mng.robotest.test.appshop;


import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.steps.miscelanea.GetProductsSteps;
import com.mng.robotest.test.steps.miscelanea.TestABmanagerSteps;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.utils.Robotest;
import com.mng.robotest.test.utils.testab.TestABGoogleExpImpl;

public class Personalizacion {

	@Test (
		groups={"Personalizacion", "Canal:desktop_App:shop"},
		description="Checkea que la personalización está activa a nivel de las galerías de productos")
	public void PER001_Galeria_Personalizada() throws Exception {

		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = Robotest.getDefaultDataShop();
		AccesoSteps.defaultAccess(driver);

//		TestABmanagerSteps.activateTestAB_GoogleExp(
//			TestABGoogleExpImpl.SHOP_296_PLP_Desktop_Personalizacion_en_listado, 1, dCtxSh.channel, dCtxSh.appE, driver);
		
		SecMenusWrapperSteps.getNew(dCtxSh, driver).selectMenu1rstLevelTypeCatalog(
			MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "camisas")),
			dCtxSh);
		
		GetProductsSteps.callProductListService(LineaType.she, "prendas", "camisas", "14", driver);
		
		PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaSteps.scrollFromFirstPage(dCtxSh);
	}
	
}
