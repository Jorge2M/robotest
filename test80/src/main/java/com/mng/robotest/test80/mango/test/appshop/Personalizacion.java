package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.DataForScrollStep;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.utils.Test80;
import com.mng.robotest.test80.mango.test.utils.testab.TestABGoogleExpImpl;

public class Personalizacion {

    @Test (
        groups={"Personalizacion", "Canal:desktop_App:all"},
        description="Checkea que la personalización está activa a nivel de las galerías de productos")
    public void PER001_Galeria_Personalizada() throws Exception {

    	WebDriver driver = TestMaker.getDriverTestCase();
    	DataCtxShop dCtxSh = Test80.getDefaultDataShop();
    	AccesoStpV.defaultAccess(driver);

		TestABmanager.activateTestAB(
			TestABactData.getNew(TestABGoogleExpImpl.SHOP_296_PLP_Desktop_Personalizacion_en_listado, 1), 
			dCtxSh.channel, dCtxSh.appE, driver);
		
    	//Invocar desde navegador a un productlist/products con personalización y
    	//certificar que llega activado el flag de personalización en como mínimo 2 productos y como máximo 4
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh, driver)
			.linea(LineaType.she)
			.seccion("prendas")
			.galeria("abrigos")
			.familia("15")
			.build();
    	
		SecMenusWrapperStpV.getNew(dCtxSh, driver).selectMenu1rstLevelTypeCatalog(
			MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "abrigos")),
			dCtxSh);
		
		PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		pageGaleriaStpV.scrollFromFirstPage(dCtxSh);
		
		System.out.println("Fin");
    }
	
}
