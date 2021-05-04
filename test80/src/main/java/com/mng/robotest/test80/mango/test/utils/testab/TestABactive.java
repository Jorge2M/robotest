package com.mng.robotest.test80.mango.test.utils.testab;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;
import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;

public class TestABactive {

	public static void currentTestABsToActivate(Channel channel, AppEcom app, WebDriver driver) throws Exception {
		List<TestABactData> listTestABsToActivate = new ArrayList<>();
		
		//Activar a 1 este Test A/B tiene muchas implicaciones en el código del PageGaleria
		//listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.GaleriaDesktopReact, 1));
		listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.MVPCheckoutDesktop, 0));
		
		//El forzar este TestAB no tiene efecto al estar junto con el de GaleriaDesktopReact v0. Siempre aparece la versión 0
		//listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.PLP_Desktop_New_filters_v2, 0));
		
		//listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.SHOP_260_Menu_Mobile_Nuevo_Diseño, 0));
		//TODO en breve se activa en PRO la variante-1
		//listTestABsToActivate.add(TestABactData.getNew(TestABGoogleExpImpl.SelectorFichaMobil, 0));
		
		
		//listTestABsToActivate.add(TestABactData.getNew(TestABOptimizeImpl.ES_SHOP_XXX_EMP_vs_FH_Search_Desktop, 0));
		
		TestABmanager.activateTestsAB(listTestABsToActivate, channel, app, driver);
	}
}
