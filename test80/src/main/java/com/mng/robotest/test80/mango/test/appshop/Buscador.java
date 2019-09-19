package com.mng.robotest.test80.mango.test.appshop;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.testng.ITestContext;
import java.lang.reflect.Method;
import org.testng.annotations.*;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.access.InputParamsTestMaker;
import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.conftestmaker.Utils;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.getdata.productos.ArticleStock;
import com.mng.robotest.test80.mango.test.getdata.productos.ManagerArticlesStock.TypeArticleStock;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.buscador.SecBuscadorStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV;

public class Buscador extends GestorWebDriver {

    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    String passwordUsuario = "";
    ArticleStock articleWithMore1Colour1 = null;
    ArticleStock articleWithMore1Colour2 = null;
    ArticleStock articleWithTotalLook = null;
    boolean isOutlet;
    boolean isVOTF;
                    
    public Buscador() {}      
      
    @BeforeMethod(groups={"Buscador", "Canal:all_App:all"})
    public void login(ITestContext context, Method method) 
    throws Exception {
        InputParamsTestMaker inputData = TestCaseData.getInputDataTestMaker(context);
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom((AppEcom)inputData.getApp());
        dCtxSh.setChannel(inputData.getChannel());
        dCtxSh.urlAcceso = inputData.getUrlBase();

        if (this.españa==null) {
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = Utilidades.getListCountrysFiltered(new ArrayList<>(Arrays.asList(codEspanya)));
            this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            this.castellano = this.españa.getListIdiomas().get(0);
        }
        
        dCtxSh.pais = this.españa;
        dCtxSh.idioma = this.castellano;
        Utils.storeDataShopForTestMaker(inputData.getTypeWebDriver(), "", dCtxSh, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod (groups={"Buscador", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @Test (
        groups={"Buscador", "Canal:all_App:all"}, alwaysRun=true, 
        description="[Usuario no registrado] Verificar existencia e inexistencia de productos y categorías en el buscador de artículos")
    @Parameters({"categoriaProdExistente", "catProdInexistente"})
    public void BUS001_Buscador_NoReg(String categoriaProdExistente, String catProdInexistente) 
    throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = (DataCtxShop)TestCaseData.getData(Constantes.idCtxSh);
        dCtxSh.userRegistered = false;

        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest.driver);
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, dFTest.driver);
        SecBuscadorStpV.searchArticuloAndValidateBasic(TypeArticleStock.articlesWithMoreOneColour, dCtxSh, dFTest.driver);
        SecBuscadorStpV.searchArticuloAndValidateBasic(TypeArticleStock.articlesNotExistent, dCtxSh, dFTest.driver);
        SecBuscadorStpV.busquedaCategoriaProducto(categoriaProdExistente, true/*categoriaExiste*/, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
        SecBuscadorStpV.busquedaCategoriaProducto(catProdInexistente, false/*categoriaExiste*/, dCtxSh.appE, dCtxSh.channel, dFTest.driver);
    }
}