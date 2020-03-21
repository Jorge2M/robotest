package com.mng.robotest.test80.mango.test.appshop;

import org.testng.annotations.*;

import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.Log4jConfig;
import com.mng.robotest.test80.access.InputParamsMango;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.PaisShop;
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
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.banner.SecBannersStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusDesktopStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;
import com.mng.robotest.test80.mango.test.suites.FlagsNaviationLineas;
import com.mng.robotest.test80.mango.test.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.test80.mango.test.utils.LevelPais;
import com.mng.robotest.test80.mango.test.utils.PaisGetter;

import java.io.Serializable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class PaisIdioma implements Serializable {
	
	private static final long serialVersionUID = 7000361927887748996L;

	static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
	
	private String index_fact = "";
	private List<Linea> linesToTest = null;
	public int prioridad;
	private FlagsNaviationLineas flagsNavigation;
	private DataCtxShop dCtxSh;
	private final static Pais españa = PaisGetter.get(PaisShop.España);
	private final static IdiomaPais castellano = españa.getListIdiomas().get(0);

	//Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
	public PaisIdioma() {}

	/**
	 * Constructor para invocación desde @Factory
	 */
	public PaisIdioma(FlagsNaviationLineas flagsNavigation, DataCtxShop dCtxSh, List<Linea> linesToTest, int prioridad) {
		this.flagsNavigation = flagsNavigation;
		this.dCtxSh = dCtxSh;
		String lineaStr = "";
		if (linesToTest.size()==1) {
			lineaStr = "-" + linesToTest.get(0).getType();
		}
		this.index_fact = dCtxSh.pais.getNombre_pais() + " (" + dCtxSh.pais.getCodigo_pais() + ") " + "-" + dCtxSh.idioma.getCodigo().getLiteral() + lineaStr;
		this.linesToTest = linesToTest;
		this.prioridad = prioridad;
	}
	  
    public void beforeMethod() throws Exception {
        if (dCtxSh==null) {
        	InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getTestCase().getInputParamsSuite();
        	dCtxSh = new DataCtxShop();
            dCtxSh.pais = españa;
            dCtxSh.idioma = castellano;
            dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
            dCtxSh.setChannel(inputParamsSuite.getChannel());
            //dCtxSh.urlAcceso = inputParamsSuite.getUrlBase();
        }
        if (linesToTest==null) {
            linesToTest = dCtxSh.pais.getShoponline().getLineasToTest(dCtxSh.appE);
        }
        if (flagsNavigation==null) {
        	flagsNavigation = VersionPaisSuite.V1;
        }
    }	
	
    @Test (
        groups={"Lineas", "Canal:all_App:shop,outlet"}, 
        description="Acceso desde prehome y navegación por todas las líneas/sublíneas/carrusels del país + selección menú/s")
    public void PAR001_Lineas() throws Exception {
    	beforeMethod();
    	WebDriver driver = TestMaker.getDriverTestCase();
    	TestMaker.getTestCase().setSpecificInputData(index_fact);
    	
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, driver);
        PageHomeMarcasStpV.validateIsPageWithCorrectLineas(dCtxSh.pais, dCtxSh.channel, dCtxSh.appE, driver);
        for (Linea linea : linesToTest) {
            if (UtilsMangoTest.validarLinea(dCtxSh.pais, linea, dCtxSh.appE)) {
                validaLinea(linea, null, driver);
                for (Sublinea sublinea : linea.getListSublineas()) {
                    validaLinea(linea, sublinea, driver);
                }
            }
        }
    }
    
	@SuppressWarnings("static-access")
	private void validaLinea(Linea linea, Sublinea sublinea, WebDriver driver) throws Exception {
        LineaType lineaType = linea.getType();
        SublineaNinosType sublineaType = null;
        if (sublinea!=null) {
            sublineaType = sublinea.getTypeSublinea();
        }
        
        SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        secMenusStpV.seleccionLinea(lineaType, sublineaType, dCtxSh);
        if (sublinea==null) {
            testSpecificFeaturesForLinea(linea, driver);
        }
            
        if (flagsNavigation.testOrderAndTranslationMenus()) {
        	secMenusStpV.checkOrderAndTranslationMenus(linea, dCtxSh.idioma.getCodigo());
        }
        
        //Validamos si hemos de ejecutar los pasos correspondientes al recorrido de los menús
        if (testMenus(linea, sublinea)) {
        	secMenusStpV.stepsMenusLinea(lineaType, sublineaType);
        	if (existsRightBannerMenu(linea, sublinea, dCtxSh.channel)) {
        		SecMenusDesktopStpV secMenusDesktopStpV = SecMenusDesktopStpV.getNew(dCtxSh.pais, dCtxSh.appE, driver);
        		secMenusDesktopStpV.clickRightBanner(lineaType, sublineaType);
        	}
        } else {
        	SecMenusWrap secMenus = SecMenusWrap.getNew(dCtxSh.channel, dCtxSh.appE, driver);
            if (secMenus.canClickMenuArticles(dCtxSh.pais, linea, sublinea)) {
            	Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "pantalones"));
            	secMenusStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh);
                if (flagsNavigation.testMenus()) {
                    PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
					boolean bannerIsLincable = PageGaleriaDesktop.secBannerHead.isLinkable(driver);
                    if (bannerIsLincable) {
                    	pageGaleriaStpV.bannerHead.clickBannerSuperiorIfLinkableDesktop();
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
    public void testSpecificFeaturesForLinea(Linea linea, WebDriver driver) throws Exception {
        LineaType lineaType = linea.getType();
        if (testBanners(linea)) {
        	int maxBannersToTest = getMaxBannersToTest(dCtxSh.pais, dCtxSh.appE);
        	SecBannersStpV secBannersStpV = new SecBannersStpV(maxBannersToTest, driver);
        	secBannersStpV.testPageBanners(dCtxSh, maxBannersToTest);
        }
        
        if (linea.getCarrusels()!=null) {
        	SecMenusWrapperStpV secMenusStpV = SecMenusWrapperStpV.getNew(dCtxSh, driver);
        	secMenusStpV.navSeleccionaCarruselsLinea(dCtxSh.pais, lineaType);
        }
    }
    
    private boolean testBanners(Linea linea) {
        return (
        	flagsNavigation.testBanners() && 
            linea.getContentDeskType()==TypeContentDesk.banners);
    }
    
    private boolean testMenus(Linea linea, Sublinea sublinea) {
        if (flagsNavigation.testMenus()) {
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
    private static int getMaxBannersToTest(Pais pais, AppEcom app) {
    	LevelPais levelPais = pais.getLevelPais();
    	return levelPais.getNumBannersTest(app);
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