package com.mng.robotest.test.appshop;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.getdata.products.GetterProducts;
import com.mng.robotest.test.getdata.products.data.GarmentCatalog;
import com.mng.robotest.test.steps.shop.AccesoSteps;
import com.mng.robotest.test.steps.shop.buscador.SecBuscadorSteps;
import com.mng.robotest.test.steps.shop.home.PageHomeMarcasSteps;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.service.TestMaker;

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
		SecBuscadorSteps secBuscadorSteps = new SecBuscadorSteps(dCtxSh.appE, dCtxSh.channel, driver);
		
		AccesoSteps.oneStep(dCtxSh, false, driver);
		(new PageHomeMarcasSteps(dCtxSh.channel, dCtxSh.appE, driver)).validateIsPageWithCorrectLineas(dCtxSh.pais);
		
		GetterProducts getterProducts = new GetterProducts.Builder(dCtxSh.pais.getCodigo_alf(), dCtxSh.appE, driver).build();
		GarmentCatalog product = getterProducts.getAll().get(0);
		
		secBuscadorSteps.searchArticulo(product, dCtxSh.pais);
		secBuscadorSteps.busquedaCategoriaProducto(categoriaProdExistente, true);
		
		secBuscadorSteps.busquedaCategoriaProducto(catProdInexistente, false);
	}
	
	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.pais = PaisGetter.get(PaisShop.ESPANA);
		dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
		return dCtxSh;
	}
}