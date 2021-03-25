package com.mng.robotest.test80.mango.test.appshop;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.stpv.ayuda.AyudaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

import org.openqa.selenium.WebDriver;

public class Ayuda {
	
	public Ayuda() {}  

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		//dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();

		dCtxSh.pais = PaisGetter.get(PaisShop.España);
		dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
		return dCtxSh;
	}
    
//	@Test(
//		groups = { "Ayuda", "Canal:all_App:shop" }, alwaysRun = true,
//		description="Verificar que los elementos de la página ayuda están correctamente presentes")
	public void AYU001_Data() throws Exception {
		WebDriver driver = TestMaker.getDriverTestCase();
		DataCtxShop dCtxSh = getCtxShForTest();
		dCtxSh.userRegistered = false;

		AccesoStpV.oneStep(dCtxSh, false, driver);
		(new SecFooterStpV(dCtxSh.channel, dCtxSh.appE, driver)).clickLinkFooter(SecFooter.FooterLink.ayuda, false);
		AyudaStpV ayudaStpV = AyudaStpV.getNew(driver);
		ayudaStpV.selectTypeValidaciones(dCtxSh.channel);
	}
}
