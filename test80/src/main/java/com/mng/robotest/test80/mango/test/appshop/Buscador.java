package com.mng.robotest.test80.mango.test.appshop;

import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.buscador.SecBuscadorStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;
import com.mng.testmaker.service.TestMaker;

public class Buscador {

	public Buscador() {}

	private DataCtxShop getCtxShForTest() throws Exception {
		InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
		DataCtxShop dCtxSh = new DataCtxShop();
		dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
		dCtxSh.setChannel(inputParamsSuite.getChannel());
		dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
		dCtxSh.pais = PaisGetter.get(PaisShop.España);
		dCtxSh.idioma = dCtxSh.pais.getListIdiomas().get(0);
		return dCtxSh;
	}

    @Test (
        groups={"Buscador", "Canal:all_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Verificar existencia e inexistencia de productos y categorías en el buscador de artículos")
    @Parameters({"categoriaProdExistente", "catProdInexistente"})
    public void BUS001_Buscador_NoReg(String categoriaProdExistente, String catProdInexistente) 
    throws Exception {
    	WebDriver driver = TestMaker.getDriverTestCase();
        DataCtxShop dCtxSh = getCtxShForTest();
        dCtxSh.userRegistered = false;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, driver);
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
        SecBuscadorStpV.searchArticuloAndValidateBasic(TypeArticleStock.articlesWithMoreOneColour, dCtxSh, driver);
        SecBuscadorStpV.searchArticuloAndValidateBasic(TypeArticleStock.articlesNotExistent, dCtxSh, driver);
        SecBuscadorStpV.busquedaCategoriaProducto(categoriaProdExistente, true, dCtxSh.appE, dCtxSh.channel, driver);
        SecBuscadorStpV.busquedaCategoriaProducto(catProdInexistente, false, dCtxSh.appE, dCtxSh.channel, driver);
    }
}