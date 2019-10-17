package com.mng.robotest.test80.mango.test.appshop;

import com.mng.robotest.test80.InputParams;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter;
import com.mng.robotest.test80.mango.test.stpv.ayuda.AyudaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecFooterStpV;
import com.mng.testmaker.service.TestMaker;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeMethod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Ayuda {

	private InputParams inputParamsSuite = null;
	
    public Ayuda() {}

    @BeforeMethod(groups = { "Ayuda", "Canal:all_App:shop", "Canal:desktop_App:outlet" })
    public void login() throws Exception {
    	if (inputParamsSuite==null) {
    		inputParamsSuite = (InputParams)TestMaker.getInputParamsSuite();
    	}
    }

    private DataCtxShop getCtxShForTest() throws Exception {
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
        dCtxSh.setChannel(inputParamsSuite.getChannel());
        dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        Integer codEspanya = Integer.valueOf(1);
        List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codEspanya)));
        dCtxSh.pais = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
        dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
        return dCtxSh;
    }
    
//    @Test(
//        groups = { "Ayuda", "Canal:all_App:shop" }, alwaysRun = true,
//        description="Verificar que los elementos de la página ayuda están correctamente presentes")
    public void AYU001_Data() throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        SecFooterStpV.clickLinkFooter(SecFooter.FooterLink.ayuda, false, dCtxSh.channel, driver);
        AyudaStpV.selectTypeValidaciones(dCtxSh.channel, driver);
    }
}
