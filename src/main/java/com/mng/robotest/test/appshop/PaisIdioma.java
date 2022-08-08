package com.mng.robotest.test.appshop;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Linea.TypeContentDesk;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.pageobject.shop.galeria.PageGaleriaDesktop;
import com.mng.robotest.test.pageobject.shop.menus.KeyMenu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.Menu1rstLevel;
import com.mng.robotest.test.pageobject.shop.menus.MenuTreeApp;
import com.mng.robotest.test.pageobject.shop.menus.SecMenusWrap;
import com.mng.robotest.test.steps.shop.PagePrehomeSteps;
import com.mng.robotest.test.steps.shop.banner.SecBannersSteps;
import com.mng.robotest.test.steps.shop.galeria.PageGaleriaSteps;
import com.mng.robotest.test.steps.shop.home.PageHomeMarcasSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusDesktopSteps;
import com.mng.robotest.test.steps.shop.menus.SecMenusWrapperSteps;
import com.mng.robotest.test.suites.FlagsNaviationLineas;
import com.mng.robotest.test.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.test.utils.LevelPais;
import com.mng.robotest.test.utils.PaisGetter;

import java.io.Serializable;
import java.util.List;

import org.openqa.selenium.WebDriver;


public class PaisIdioma implements Serializable {
	
	private static final long serialVersionUID = 7000361927887748996L;
	
	private String index_fact = "";
	private List<Linea> linesToTest = null;
	public int prioridad;
	private FlagsNaviationLineas flagsNavigation;
	private DataCtxShop dCtxSh;
	private static final Pais espana = PaisGetter.get(PaisShop.ESPANA);
	private static final IdiomaPais castellano = espana.getListIdiomas().get(0);

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
			InputParamsMango inputParamsSuite = (InputParamsMango)TestMaker.getInputParamsSuite();
			dCtxSh = new DataCtxShop();
			dCtxSh.pais = espana;
			dCtxSh.idioma = castellano;
			dCtxSh.setAppEcom((AppEcom)inputParamsSuite.getApp());
			dCtxSh.setChannel(inputParamsSuite.getChannel());
		}
		if (linesToTest==null) {
			linesToTest = dCtxSh.pais.getShoponline().getLineasToTest(dCtxSh.appE);
		}
		if (flagsNavigation==null) {
			flagsNavigation = VersionPaisSuite.V1;
		}
	}	
	
	@Test (
		groups={"Lineas", "Canal:desktop,mobile_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todas las líneas/sublíneas del país + selección menú/s")
	public void PAR001_Lineas() throws Exception {
		beforeMethod();
		WebDriver driver = TestMaker.getDriverTestCase();
		TestCaseTM.addNameSufix(this.index_fact);
		
		new PagePrehomeSteps(dCtxSh, driver).seleccionPaisIdiomaAndEnter();
		(new PageHomeMarcasSteps(dCtxSh.channel, dCtxSh.appE)).validateIsPageWithCorrectLineas(dCtxSh.pais);
		for (Linea linea : linesToTest) {
			if (UtilsMangoTest.validarLinea(dCtxSh.pais, linea, dCtxSh.channel, dCtxSh.appE)) {
				validaLinea(linea, null, driver);
				for (Sublinea sublinea : linea.getListSublineas(dCtxSh.appE)) {
					validaLinea(linea, sublinea, driver);
				}
			}
		}
	}
	
	@SuppressWarnings("static-access")
	private void validaLinea(Linea linea, Sublinea sublinea, WebDriver driver) throws Exception {
		LineaType lineaType = linea.getType();
		SublineaType sublineaType = null;
		if (sublinea!=null) {
			sublineaType = sublinea.getTypeSublinea();
		}
		
		SecMenusWrapperSteps secMenusSteps = SecMenusWrapperSteps.getNew(dCtxSh);
		secMenusSteps.seleccionLinea(lineaType, sublineaType, dCtxSh);
		if (sublinea==null) {
			testSpecificFeaturesForLinea(linea, driver);
		}
			
		if (flagsNavigation.testOrderAndTranslationMenus()) {
			secMenusSteps.checkOrderAndTranslationMenus(linea, dCtxSh.idioma.getCodigo());
		}
		
		//Validamos si hemos de ejecutar los pasos correspondientes al recorrido de los menús
		if (testMenus(linea, sublinea)) {
			secMenusSteps.stepsMenusLinea(lineaType, sublineaType);
			if (existsRightBannerMenu(linea, sublinea, dCtxSh.channel)) {
				SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps(dCtxSh.pais, dCtxSh.appE, dCtxSh.channel);
				secMenusDesktopSteps.clickRightBanner(lineaType, sublineaType);
			}
		} else {
			SecMenusWrap secMenus = new SecMenusWrap(dCtxSh.channel, dCtxSh.appE);
			if (secMenus.canClickMenuArticles(dCtxSh.pais, linea, sublinea)) {
				Menu1rstLevel menu = getMenu(lineaType, sublineaType);
				secMenusSteps.selectMenu1rstLevelTypeCatalog(menu, dCtxSh);
				if (flagsNavigation.testMenus()) {
					PageGaleriaSteps pageGaleriaSteps = PageGaleriaSteps.getInstance(dCtxSh.channel, dCtxSh.appE, driver);
					boolean bannerIsLincable = PageGaleriaDesktop.secBannerHead.isLinkable(driver);
					if (bannerIsLincable) {
						pageGaleriaSteps.bannerHead.clickBannerSuperiorIfLinkableDesktop();
					}
				}
			}
		}
	}
	
	private Menu1rstLevel getMenu(LineaType lineaType, SublineaType sublineaType) {
		String menu = "";
		switch (lineaType) {
		case nina:
		case nino:
			menu = "camisas";
			break;
		case home:
			//En el caso de mobile el data-label es distinto
			if (dCtxSh.channel==Channel.mobile) {
				menu = "dormitorio-mantas";
			} else {
				menu = "mantas_dormitorio";
			}
			break;
		default:
			menu = "pantalones";
		}
		return MenuTreeApp.getMenuLevel1From(dCtxSh.appE, KeyMenu1rstLevel.from(lineaType, sublineaType, menu));
	}
	
	/**
	 * Se testean características específicas de la Línea (que no se dan en una Sublínea)
	 *  Testea todos los banners de la página resultante si los hubiera
	 *  Testea todos los carrusels asociados si los hubiera
	 */
	public void testSpecificFeaturesForLinea(Linea linea, WebDriver driver) throws Exception {
		if (testBanners(linea)) {
			int maxBannersToTest = getMaxBannersToTest(dCtxSh.pais, dCtxSh.appE);
			SecBannersSteps secBannersSteps = new SecBannersSteps(maxBannersToTest, driver);
			secBannersSteps.testPageBanners(dCtxSh, maxBannersToTest);
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