package com.mng.robotest.test80.mango.test.appshop;

import org.testng.ITestContext;

import java.lang.reflect.Method;

import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.*;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.arq.utils.otras.*;
import com.mng.robotest.test80.mango.test.appshop.campanas.CampanasData;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.TypeContentDesk;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test80.mango.test.stpv.shop.PageHomeMarcasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class PaisIdioma extends GestorWebDriver /*Funcionalidades genéricas propias de MANGO*/ {
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);

    String baseUrl;
    boolean acceptNextAlert = true;
    StringBuffer verificationErrors = new StringBuffer();
	
    private String index_fact = "";
    private List<Linea> lineasAprobar;
    private boolean recorreMenus;
    private boolean recorreBanners;
    public int prioridad;
    CampanasData dataCamp;
    DataCtxShop dCtxSh;
    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public PaisIdioma() {}

    /**
     * Invocación desde @Factory de campañas
     */
    public PaisIdioma(CampanasData dataCamp, DataCtxShop dCtxSh, List<Linea> lineasAprobar, boolean recorreMenus, boolean recorreBanners, int prioridad) {
    	this(dCtxSh, lineasAprobar, recorreMenus, recorreBanners, prioridad);
    	this.dataCamp = dataCamp;
    }
    
    /**
     * Constructor para invocación desde @Factory
     */
    public PaisIdioma(DataCtxShop dCtxSh, List<Linea> lineasAprobar, boolean recorreMenus, boolean recorreBanners, int prioridad) {
        this.dCtxSh = dCtxSh;
        String lineaStr = "";
        if (lineasAprobar.size()==1) {
            lineaStr = "-" + lineasAprobar.get(0).getType();
        }   
        this.index_fact = dCtxSh.pais.getNombre_pais() + " (" + dCtxSh.pais.getCodigo_pais() + ") " + "-" + dCtxSh.idioma.getCodigo().getLiteral() + lineaStr;
        this.lineasAprobar = lineasAprobar;
        this.recorreMenus = recorreMenus;
        this.recorreBanners = recorreBanners;
        this.prioridad = prioridad;
    }
	  
    @BeforeMethod(groups={"Lineas", "Canal:all_App:all"}, alwaysRun = true)
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) throws Exception {
        if (this.dCtxSh==null) {
            //Si el acceso es normal (no es desde una @Factory) definiremos los siguientes datos específicos
            this.dCtxSh = new DataCtxShop();
            Integer codEspanya = Integer.valueOf(1);
            List<Pais> listaPaises = UtilsMangoTest.listaPaisesXML(new ArrayList<>(Arrays.asList(codEspanya)));
            Pais españa = UtilsMangoTest.getPaisFromCodigo("001", listaPaises);
            IdiomaPais castellano = españa.getListIdiomas().get(0);
            this.dCtxSh.pais = españa;
            this.dCtxSh.idioma = castellano;
            this.dCtxSh.setAppEcom(appEcom);
            this.dCtxSh.setChannel(channel);
            this.dCtxSh.urlAcceso = urlAcceso;
            this.lineasAprobar = this.dCtxSh.pais.getShoponline().getLineasToTest(this.dCtxSh.appE);
            this.recorreMenus = false;
            this.recorreBanners = false;
        }

        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, this.dCtxSh.urlAcceso, this.index_fact, this.dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"Lineas", "Canal:all_App:all"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        try {
            super.quitWebDriver(driver, context);
        }
        catch (Exception e) {
            pLogger.warn("Problem closing WebDriver", e);
        }
    }	
	
    @Test (
        groups={"Lineas", "Canal:all_App:shop", "Canal:all_App:outlet"}, 
        description="Acceso desde prehome y navegación por todas las líneas/sublíneas/carrusels del país + selección menú/s")
    public void PAR001_Lineas() throws Exception {
    	DataFmwkTest dFTest = TestCaseData.getdFTest();
        DataCtxShop dCtxShI = TestCaseData.getdCtxSh();
            
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxShI, dFTest.driver);
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxShI.pais, dCtxShI.channel, dCtxShI.appE, dFTest.driver);

        //Aplicamos el test a las líneas/sublíneas
        for (Linea linea : this.lineasAprobar) {
            if (UtilsMangoTest.validarLinea(dCtxShI.pais, linea, dCtxShI.appE)) {
                validaLinea(linea, null/*sublinea*/, dCtxShI, dFTest);
                for (Sublinea sublinea : linea.getListSublineas())
                    validaLinea(linea, sublinea, dCtxShI, dFTest);
            }
        }
    }
    
	@SuppressWarnings("static-access")
	private void validaLinea(Linea linea, Sublinea sublinea, DataCtxShop dCtxShI, DataFmwkTest dFTest) throws Exception {
        //Obtenemos el tipo de línea/sublínea
        LineaType lineaType = linea.getType();
        SublineaNinosType sublineaType = null;
        if (sublinea!=null) {
            sublineaType = sublinea.getTypeSublinea();
        }
        
        SecMenusWrapperStpV.seleccionLinea(lineaType, sublineaType, dCtxShI, dFTest);
        if (sublinea==null) {
            testSpecificFeaturesForLinea(linea, dCtxShI, dFTest);
        }
            
        //Validamos si hemos de ejecutar los pasos correspondientes al recorrido de los menús
        if (testMenus(linea, sublinea)) {
            SecMenusWrapperStpV.stepsMenusLinea(lineaType, sublineaType, dCtxShI, dFTest);
        	if (existsRightBannerMenu(linea, sublinea, dCtxShI.channel)) {
                SecMenusDesktopStpV.clickRightBanner(lineaType, sublineaType, dCtxShI.appE, dFTest.driver);
        	}
        } else {
            if (SecMenusWrap.canClickMenuArticles(dCtxShI.pais, linea, sublinea)) {
            	Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "pantalones"));
                SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxShI, dFTest.driver);
                if (this.recorreBanners) {
                    PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, dFTest.driver);
					boolean bannerIsLincable = PageGaleriaDesktop.secBannerHead.isLinkable(dFTest.driver);
                    if (bannerIsLincable) {
                    	pageGaleriaStpV.clickBannerSuperiorIfLinkableDesktop();
                    }
                }
            }
        }
    }
    
    /**
     * Se testean características específicas de la Línea (que no se dan en una Sublínea)
     *  Testea todos los banners de la página resultante si los hubiera
     *  Testea todos los carrusels asociados si los hubiera
     */
    public void testSpecificFeaturesForLinea(Linea linea, DataCtxShop dCtxShI, DataFmwkTest dFTest) throws Exception {
        LineaType lineaType = linea.getType();
        if (testBanners(linea)) {
        	int maxBannersToTest = getMaxBannersToTest(dCtxShI.pais, dCtxShI.appE);
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToTest, dFTest.driver);
        	if (this.dataCamp==null) {
        		secBannersStpV.testPageBanners(dCtxShI, maxBannersToTest, dFTest);
        	} else {
        		secBannersStpV.testCampanas(dataCamp, dCtxShI, lineaType, dFTest);
        	}
        }
        
        if (linea.getCarrusels()!=null) {
            SecMenusWrapperStpV.navSeleccionaCarruselsLinea(dCtxShI.pais, lineaType, dCtxShI.appE, dCtxShI.channel, dFTest);
        }
    }
    
    private boolean testBanners(Linea linea) {
        return (this.recorreBanners && 
                linea.getContentDeskType()==TypeContentDesk.banners);
    }
    
    private boolean testMenus(Linea linea, Sublinea sublinea) {
        if (this.recorreMenus) {
            if (sublinea==null) {
                return linea.getMenus().compareTo("s")==0;
            }
            return sublinea.getMenus().compareTo("s")==0;
        }
        
        return false;
    }
    
    /**
     * Retorna el máximo de banners a probar por cada una de las líneas dependiendo de las características del país
     */
    private static int getMaxBannersToTest(Pais pais, AppEcom appE) {
        switch (appE) {
        case shop:
            return getMaxBannersToTestShop(pais);
        case outlet:
        default:
            return getMaxBannersToTestOutlet(pais);
        }
    }
    
    private static int getMaxBannersToTestShop(Pais pais) {
        if (pais.isPaisTop()) {
            return Constantes.MAX_BAN_PAIS_TOP_SHOP;
        }
        if (pais.getShop_online().compareTo("true")==0) {
            return (Constantes.MAX_BAN_PAIS_SICOMPRA_SHOP);
        }
        return Constantes.MAX_BAN_PAIS_NOCOMPRA_SHOP;
    }
    
    private static int getMaxBannersToTestOutlet(Pais pais) {
        if (pais.isPaisTop()) {
            return Constantes.MAX_BAN_PAIS_TOP_OUTLET;
        }
        if (pais.getShop_online().compareTo("true")==0) {
            return (Constantes.MAX_BAN_PAIS_SICOMPRA_OUTLET);
        }
        return Constantes.MAX_BAN_PAIS_NOCOMPRA_OUTLET;
    }
    
    private boolean existsRightBannerMenu(Linea linea, Sublinea sublinea, Channel channel) {
    	if (channel==Channel.desktop) {
    		if (sublinea!=null) {
    			return ("s".compareTo(sublinea.getMenusart())==0); 
    		}
    		return ("s".compareTo(linea.getMenusart())==0); 
    	}
    	
    	return false;
    }
}