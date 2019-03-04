package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataBag;
import com.mng.robotest.test80.mango.test.datastored.DataFavoritos;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test80.mango.test.getdata.usuarios.UserShop;
import com.mng.robotest.test80.mango.test.pageobject.shop.favoritos.PageFavoritos;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.shop.AccesoStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.SecBolsaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.favoritos.PageFavoritosStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.ficha.PageFichaArtStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.LocationArticle;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV.TypeActionFav;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.openqa.selenium.WebDriver;

@SuppressWarnings({ "static-access" })
public class Favoritos extends GestorWebDriver {
    Pais españa = null;
    IdiomaPais castellano = null;
    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
    private String index_fact = "";
    public int prioridad;
    Pais paisFactory = null;
    IdiomaPais idiomaFactory = null;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public Favoritos() {}
    
    /**
     * Constructor para invocación desde @Factory
     */
    public Favoritos(Pais pais, IdiomaPais idioma, int prioridad) {
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
        this.prioridad = prioridad;
    }
      
    @BeforeMethod(groups={"Favoritos", "Canal:all_App:shop", "SupportsFactoryCountrys"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) 
    throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.urlAcceso = urlAcceso;

        //Si el acceso es normal (no es desde una @Factory) utilizaremos el España/Castellano
        if (this.paisFactory==null) {
            if (this.españa==null) {
                Integer codEspanya = Integer.valueOf(1);
                List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
                this.españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
                this.castellano = this.españa.getListIdiomas().get(0);
            }
            
            dCtxSh.pais = this.españa;
            dCtxSh.idioma = this.castellano;
        }
        else {
            dCtxSh.pais = this.paisFactory;
            dCtxSh.idioma = this.idiomaFactory;
        }
        
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, this.index_fact, dCtxSh.channel, context, method);
    }

    @SuppressWarnings("unused")
    @AfterMethod (groups={"Favoritos", "Canal:all_App:shop", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }       

    @Test(
        groups={"Favoritos", "Canal:all_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
        description="[Usuario registrado] Alta favoritos desde la galería")
    public void FAV001_AltaFavoritosDesdeGaleria() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        dCtxSh.userRegistered=true;
        DataFavoritos dataFavoritos = new DataFavoritos();
        DataBag dataBolsa = new DataBag();
        
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        //TestAB.activateTestABfiltrosMobil(1, dCtxSh, getDriver().driver); //!!!!!
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        SecBolsaStpV.clear(dCtxSh, dFTest.driver);
        PageFavoritosStpV.clearAll(dataFavoritos, dCtxSh, dFTest);
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh, dFTest);

        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
        List<Integer> iconsToMark = Arrays.asList(1, 3, 4);  
        pageGaleriaStpV.clickArticlesHearthIcons(iconsToMark, TypeActionFav.Marcar, dataFavoritos);
        
        List<Integer> iconsToUnmark = Arrays.asList(3);
        pageGaleriaStpV.clickArticlesHearthIcons(iconsToUnmark, TypeActionFav.Desmarcar, dataFavoritos);
        
        SecMenusWrapperStpV.secMenuUser.selectFavoritos(dataFavoritos, dCtxSh, dFTest);
        ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
        PageFavoritosStpV.addArticuloToBag(artToPlay, dataBolsa, dCtxSh.channel, dFTest);
        if (dCtxSh.channel==Channel.movil_web) {
            SecBolsaStpV.clickAspaForCloseMobil(dFTest.driver);
            PageFavoritosStpV.validaIsPageOK(dataFavoritos, dFTest);
        }
        
        PageFavoritosStpV.clear(artToPlay, dataFavoritos, dFTest);
        PageFavoritosStpV.clearAll(dataFavoritos, dCtxSh, dFTest);
    }
    
    @Test(
        groups={"Favoritos", "Canal:all_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
        /*dependsOnMethods = {"FAV001_AltaFavoritosDesdeGaleria"},*/ 
        description="[Usuario no registrado] Alta favoritos desde la galería y posterior identificación")
    public void FAV002_AltaFavoritosDesdeFicha() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        dCtxSh.userRegistered=false;
        DataFavoritos dataFavoritos = new DataFavoritos();
        DataBag dataBolsa = new DataBag();
        
        //Script
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        //TestAB.activateTestABfiltrosMobil(1, dCtxSh, getDriver().driver); //!!!!!
        AccesoStpV.accesoAplicacionEnUnPaso(dCtxSh, false/*clearArticulos*/, dFTest);
        SecBolsaStpV.clear(dCtxSh, dFTest.driver);
        PageFavoritosStpV.clearAll(dataFavoritos, dCtxSh, dFTest);
        
        Menu1rstLevel menuVestidos = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(LineaType.she, null, "Vestidos"));
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuVestidos, dCtxSh, dFTest);
        LocationArticle article1 = LocationArticle.getInstanceInCatalog(1);
        
        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest);
        pageGaleriaStpV.selectArticulo(article1, dCtxSh);
        
        PageFichaArtStpV pageFichaArtStpv = new PageFichaArtStpV(dCtxSh.appE, dCtxSh.channel);
        pageFichaArtStpv.selectAnadirAFavoritos(dataFavoritos);
        
        UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
        dCtxSh.userConnected = userShop.user;
        dCtxSh.passwordUser = userShop.password;
        AccesoStpV.identificacionEnMango(dCtxSh, dFTest);
        //TestAB.activateTestABiconoBolsaDesktop(0, dCtxSh, dFTest.driver);
        SecBolsaStpV.clear(dCtxSh, dFTest.driver);
        
        SecMenusWrapperStpV.secMenuUser.selectFavoritos(dataFavoritos, dCtxSh, dFTest);
        
        // TODO
        // Cuando la funcionalidad de "Share Favorites" suba a producción, este if debería eliminarse
        if (PageFavoritos.isShareFavoritesVisible(dFTest.driver)) {
        	// Codigo que se ejecutará solamente si el elemento de compartir favoritos existe
        	PageFavoritosStpV.clickShareIsOk(dFTest);
            PageFavoritosStpV.closeShareModal(dFTest);
        }
        
        ArticuloScreen artToPlay = dataFavoritos.getArticulo(0);
        PageFavoritosStpV.clickArticuloImg(artToPlay, dFTest);
        PageFavoritosStpV.modalFichaFavoritos.addArticuloToBag(artToPlay, dataBolsa, dCtxSh.channel, dCtxSh.appE, dFTest);       
        if (dCtxSh.channel==Channel.movil_web) {
            PageFavoritosStpV.validaIsPageOK(dataFavoritos, dFTest);
        }
        else {
            PageFavoritosStpV.modalFichaFavoritos.closeFicha(artToPlay, dFTest);
        }
        
        PageFavoritosStpV.clear(artToPlay, dataFavoritos, dFTest);
    }
}