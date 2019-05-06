package com.mng.robotest.test80.mango.test.appshop.rebajas;

import org.testng.ITestContext;
import java.lang.reflect.Method;
import org.testng.annotations.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.utils.controlTest.mango.*;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Sublinea.SublineaNinosType;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.pageobject.shop.galeria.PageGaleriaDesktop.TypeArticle;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test80.mango.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test80.mango.test.stpv.shop.PagePrehomeStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV;
import com.mng.robotest.test80.mango.test.stpv.shop.galeria.PageGaleriaStpV.TypeGalery;
import com.mng.robotest.test80.mango.test.stpv.shop.home.BannerSpringIsHere2019StpV;
import com.mng.robotest.test80.mango.test.stpv.shop.home.PageHomeMarcasStpV.TypeHome;
import com.mng.robotest.test80.mango.test.stpv.shop.menus.SecMenusWrapperStpV;

public class RebajasSpringIsHere2019 extends GestorWebDriver /*Funcionalidades genéricas propias de MANGO*/ {

    String baseUrl;
    boolean acceptNextAlert = true;
    private String index_fact;
    public int prioridad;
    Pais paisFactory = null;
    IdiomaPais idiomaFactory = null;
    List<Linea> lineasAprobar = null;
    

    
    final static List<String> countrysWithoutCampaignInHE;
    final static List<String> countrysWithCampaignInNINOS;
    
    static {
    	countrysWithoutCampaignInHE = Arrays.asList(
    	    "043",	/*ANDORRA*/
    		"001",	/*ESPAÑA*/
    		"021",	/*CANARIAS*/
    		"022",	/*CEUTA*/
    		"023",	/*MELILLA*/
    		"010",	/*PORTUGAL*/
	    	"708",	/*FILIPINAS*/
    		"052"	/*TURQUIA*/
    	);
    	
    	countrysWithCampaignInNINOS = Arrays.asList(
    		"706",	/*SINGAPORE*/
    		"701",	/*MALAYSIA*/
	    	"708",	/*FILIPINAS*/
	    	"052",	/*TURQUIA*/
    		"700"	/*INDONESIA*/
        );	
    }
    
    final static List<String> helloSunshineCountrys;
    final static List<String> springIsHereCountrys;
    final static List<String> aPrimaveraeAquiCountrys;
    final static List<String> cestLePrintemsCountrys;
    final static List<String> stigloJeProljeceCountrys;
    
    static {
    	helloSunshineCountrys = Arrays.asList(
	    	"474",	/*ARUBA*/
	    	"708",	/*FILIPINAS*/
	    	"458",	/*GUADALUPE*/
	    	"740",	/*HONG KONG*/
	    	"700",	/*INDONESIA*/
	    	"701",	/*MALASIA*/
	    	"475",	/*MARTINICA*/
	    	"412",	/*MEXICO*/
	    	"706",	/*SINGAPUR*/
	    	"456" 	/*REPUBLICA DOMINICANA*/
    	);
    	
    	springIsHereCountrys = Arrays.asList(
	    	"004",	/*ALEMANIA*/
	    	"043",	/*ANDORRA*/
	    	"038",	/*AUSTRIA*/
	    	"017",	/*BELGICA*/
	    	"093",	/*BOSNIA-HERZEGOVINA*/
	    	"068",	/*BULGARIA*/
	    	"022",	/*CEUTA*/
	    	"600",	/*CHIPRE*/
	    	"092",	/*CROACIA*/
	    	"008",	/*DINAMARCA*/
	    	"063",	/*ESLOVAQUIA*/
	    	"091",	/*ESLOVENIA*/
	    	"001",	/*ESPAÑA*/
	    	"032",	/*FINLANDIA*/
	    	"064",	/*HUNGRIA*/
	    	"007",	/*IRLANDA*/
	    	"021",	/*ISLAS CANARIAS*/
	    	"005",	/*ITALIA*/
	    	"018",	/*LUXEMBURGO*/
	    	"023",	/*MELILLA*/
	    	"028",	/*NORUEGA*/
	    	"003",	/*PAISES BAJOS*/
	    	"060",	/*POLONIA*/
	    	"006",	/*REINO UNIDO*/
	    	"061",	/*REPUBLICA CHECA*/
	    	"066",	/*RUMANIA*/
	    	"030",	/*SUECIA*/
    	    "036",	/*SUIZA*/
	    	"052",	/*TURQUIA*/
	    	"602",	/*CHIPRE NORTE*/
	    	"076",	/*GEORGIA*/
	    	"095",	/*KOSOVO*/
	    	"074",	/*MOLDEVIA*/
	    	"716",	/*MONGOLIA*/
	    	"072" 	/*UCRANIA*/
    	);
    	
    	aPrimaveraeAquiCountrys = Arrays.asList(
    		"010"	/*PORTUGAL*/
    	);
    	
    	cestLePrintemsCountrys = Arrays.asList(
    	    "036",	/*SUIZA*/
	    	"018",	/*LUXEMBURGO*/
    		"011"	/*FRANCIA*/
    	);
    	
    	stigloJeProljeceCountrys = Arrays.asList(
    	    "092"	/*CROACIA*/
    	);
    }

    
    //Si añadimos un constructor para el @Factory hemos de añadir este constructor para la invocación desde SmokeTest
    public RebajasSpringIsHere2019() {}
    
    //Constructor para invocación desde @Factory
    public RebajasSpringIsHere2019(Pais pais, IdiomaPais idioma, List<Linea> lineasAprobar, int prioridad) {
        this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
        this.paisFactory = pais;
        this.idiomaFactory = idioma;
        this.prioridad = prioridad;
        this.lineasAprobar = lineasAprobar;
    }
    
    private static List<String> getPossibleTextPromotion(String codigoPais) {
    	List<String> possibleText = new ArrayList<>();
    	if (helloSunshineCountrys.contains(codigoPais)) {
    		possibleText.addAll(Arrays.asList("Hello sunshine!"));
    	}
    	if (aPrimaveraeAquiCountrys.contains(codigoPais)) {
    		possibleText.addAll(Arrays.asList("A PRIMAVERA é AQUI!", "A PRIMAVERA  é  AQUI!"));
    	}
    	if (springIsHereCountrys.contains(codigoPais)) {
    		possibleText.addAll(Arrays.asList("SPRING is HERE!", "SPRING  is  HERE!"));
    	}
    	if (cestLePrintemsCountrys.contains(codigoPais)) {
    		possibleText.addAll(Arrays.asList("C'EST  le  PRINTEMPS !", "C'EST le PRINTEMPS !"));
    	}
    	if (stigloJeProljeceCountrys.contains(codigoPais)) {
    		possibleText.addAll(Arrays.asList("STIGLO  je  PROLJEĆE!", "STIGLO je PROLJEĆE!"));
    	}
    	
    	return possibleText;
    }
    
    private boolean bannerMustBeInPortada(String codigoPais, Linea linea, Sublinea sublinea) {
    	switch (linea.getType()) {
    	case she:
    	case violeta:
    		return true;
    	case he:
    		return (!countrysWithoutCampaignInHE.contains(codigoPais));
    	case nina:
    	case nino:
    		if (sublinea==null) {
    			return (countrysWithCampaignInNINOS.contains(codigoPais));
    		}
    		return false;
    	default:
    		return false;
    	}
    }
	  
    @BeforeMethod (groups={"RebajasSpringIsHere2019", "Canal:desktop_App:shop", "SupportsFactoryCountrys"})
    @Parameters({"brwsr-path", "urlBase", "AppEcom", "Channel"})
    public void login(String bpath, String urlAcceso, String appEcom, String channel, ITestContext context, Method method) throws Exception {
        //Recopilación de parámetros
        DataCtxShop dCtxSh = new DataCtxShop();
        dCtxSh.setAppEcom(appEcom);
        dCtxSh.setChannel(channel);
        dCtxSh.pais = this.paisFactory;
        dCtxSh.idioma = this.idiomaFactory;
        dCtxSh.urlAcceso = urlAcceso;
        TestCaseData.storeInThread(dCtxSh);
        TestCaseData.getAndStoreDataFmwk(bpath, dCtxSh.urlAcceso, this.index_fact, dCtxSh.channel, context, method);
    }
	
    @SuppressWarnings("unused")
    @AfterMethod (groups={"RebajasSpringIsHere2019", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, alwaysRun = true)
    public void logout(ITestContext context, Method method) throws Exception {
        WebDriver driver = TestCaseData.getWebDriver();
        super.quitWebDriver(driver, context);
    }	
	
    @SuppressWarnings("static-access")
    @Test (groups={"RebajasSpringIsHere2019", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, 
    	   description="Validaciones específicas correspondientes a la promoción <b>Spring Is Here 2019</b>")
    public void REB001_RebajasSpringIsHere2019() throws Exception {
    	WebDriver  driver = TestCaseData.getdFTest().driver;
        DataCtxShop dCtxSh = TestCaseData.getdCtxSh();
        int numLineasPais = dCtxSh.pais.getShoponline().getNumLineasTiendas(dCtxSh.appE);
            
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, driver);
        if (numLineasPais==1) {
            return;
        }

        BannerSpringIsHere2019StpV.clickCircleToForceStopInCampaing(1, driver);
        List<String> textPossible = getPossibleTextPromotion(dCtxSh.pais.getCodigo_pais());
        BannerSpringIsHere2019StpV bannerSpringIsHere2019 = new BannerSpringIsHere2019StpV(textPossible, dCtxSh, driver);
    	boolean bannerExists = bannerSpringIsHere2019.checkBanner(TypeHome.Multimarca).getExistBanner();
    	if (bannerExists) {
    		bannerSpringIsHere2019.clickBanner();
	        if (dCtxSh.pais.isVentaOnline()) {
		        PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
		        pageGaleriaStpV.validaArticlesOfTemporadas(Arrays.asList(4));
		        pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.norebajado, State.Warn, false/*avoidEvidences*/);
		        pageGaleriaStpV.bannerHead.checkBannerHeadSalesOn(dCtxSh.pais, dCtxSh.idioma);
	        }
    	}
        
        for (Linea linea : this.lineasAprobar) {
            if (UtilsMangoTest.validarLinea(dCtxSh.pais, linea, dCtxSh.appE) &&
                linea.getType()!=LineaType.edits &&
                linea.getType()!=LineaType.nuevo) {
                validaLinea(linea, null, ("banners".compareTo(linea.getContentDesk())==0), dCtxSh, driver);
                for (Sublinea sublinea : linea.getListSublineas()) {
                    validaLinea(linea, sublinea, ("banners".compareTo(sublinea.getContentDesk())==0), dCtxSh, driver);
                }
            }
        }
    }
    
    @SuppressWarnings("static-access")
	private void validaLinea(Linea linea, Sublinea sublinea, boolean areBanners, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        LineaType lineaType = linea.getType();
        SublineaNinosType sublineaType = null;
        if (sublinea!=null) {
            sublineaType = sublinea.getTypeSublinea();
        }
        
        SecMenusWrapperStpV.seleccionLinea(lineaType, sublineaType, dCtxSh, driver);
        List<String> textoPromotion = getPossibleTextPromotion(dCtxSh.pais.getCodigo_pais());
        BannerSpringIsHere2019StpV bannerSpringIsHere2019 = new BannerSpringIsHere2019StpV(textoPromotion, dCtxSh, driver);
        if (bannerMustBeInPortada(dCtxSh.pais.getCodigo_pais(), linea, sublinea)) {
	    	boolean bannerExists = bannerSpringIsHere2019.checkBanner(TypeHome.PortadaLinea).getExistBanner();
	    	if (bannerExists && dCtxSh.pais.isVentaOnline()) {
		    	bannerSpringIsHere2019.clickBanner();
	    	}
	    	checkMenuLateralSpringPromotion(lineaType, sublineaType, dCtxSh, driver);
	    	checkMenuSuperiorPantalones(lineaType, sublineaType, dCtxSh, driver); 
        }
        else {
        	bannerSpringIsHere2019.checkIsNotBannerVisible();
        }
    }

    private void checkMenuLateralSpringPromotion(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        Menu1rstLevel menuSpringPromotion = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "Spring Promotion"));
        menuSpringPromotion.setDataGaLabel("promos-spring_promotion");
        SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuSpringPromotion, dCtxSh, driver);
        checkGaleryOfArticlesInPromotion(dCtxSh, driver);
    }
    
    private void checkMenuSuperiorPantalones(LineaType lineaType, SublineaNinosType sublineaType, DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	Menu1rstLevel menuPantalones = MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, "pantalones"));
    	SecMenusWrapperStpV.selectMenu1rstLevelTypeCatalog(menuPantalones, dCtxSh, driver);
    	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
    	pageGaleriaStpV.bannerHead.checkBannerSalesHead(TypeGalery.NoSales, dCtxSh.pais, dCtxSh.idioma);
    	if (dCtxSh.pais.isVentaOnline()) {
	    	pageGaleriaStpV.bannerHead.clickBannerSuperiorIfLinkableDesktop();
	    	checkGaleryOfArticlesInPromotion(dCtxSh, driver);
    	}
    }
    
    private void checkGaleryOfArticlesInPromotion(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
    	PageGaleriaStpV pageGaleriaStpV = PageGaleriaStpV.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
        pageGaleriaStpV.validaArticlesOfTemporadas(Arrays.asList(4));
        pageGaleriaStpV.validaNotArticlesOfTypeDesktop(TypeArticle.norebajado, State.Warn, false/*avoidEvidences*/);
        pageGaleriaStpV.bannerHead.checkBannerSalesHead(TypeGalery.Sales, dCtxSh.pais, dCtxSh.idioma);
        List<String> textoPromotion = getPossibleTextPromotion(dCtxSh.pais.getCodigo_pais());
        pageGaleriaStpV.bannerHead.checkBannerContainsText(textoPromotion);
    }
}