package com.mng.robotest.test80.mango.test.appshop;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.getdata.products.GetterProducts;
import com.mng.robotest.test80.mango.test.getdata.products.data.Garment;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.buscador.SecBuscadorStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.mng.robotest.test80.mango.test.utils.testab.TestABOptimizeImpl;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.service.testab.TestABactData;
import com.github.jorge2m.testmaker.service.testab.manager.TestABmanager;

public class Buscador {

    @Test (
        groups={"Buscador", "Canal:all_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Búsqueda artículos existente / no existente")
    @Parameters({"categoriaProdExistente", "catProdInexistente"})
    public void BUS001_Buscador_NoReg(String categoriaProdExistente, String catProdInexistente) 
    throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;
        SecBuscadorStpV secBuscadorStpV = new SecBuscadorStpV(dCtxSh.appE, dCtxSh.channel, driver);
        
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false, driver);
        (new PageHomeMarcasStpV(dCtxSh.channel, dCtxSh.appE, driver)).validateIsPageWithCorrectLineas(dCtxSh.pais);
        
        GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh, driver).build();
        Garment product = getterProducts.getAll().get(0);
        
        secBuscadorStpV.searchArticulo(product);
        secBuscadorStpV.busquedaCategoriaProducto(categoriaProdExistente, true);
        
        //TestABmanager.activateTestAB(TestABactData.getNew(TestABOptimizeImpl.ES_SHOP_XXX_EMP_vs_FH_Search_Desktop, 1), dCtxSh.channel, dCtxSh.appE, driver);
        secBuscadorStpV.busquedaCategoriaProducto(catProdInexistente, false);
    }
    
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
}