package com.mng.robotest.test.appshop.paisidioma;

import java.util.List;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.domains.transversal.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Sublinea;
import com.mng.robotest.test.beans.Linea.LineaType;
import com.mng.robotest.test.beans.Linea.TypeContentDesk;
import com.mng.robotest.test.beans.Sublinea.SublineaType;
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
import com.mng.robotest.test.utils.LevelPais;

public class Par001 extends TestBase {

	private final List<Linea> linesToTest;
	private final FlagsNaviationLineas flagsNavigation;
	
	public Par001(Pais pais, IdiomaPais idioma, List<Linea> linesToTest, FlagsNaviationLineas flagsNavigation) {
		if (linesToTest==null) {
			linesToTest = pais.getShoponline().getLineasToTest(app);
		}
		this.dataTest.pais = pais;
		this.dataTest.idioma = idioma;
		this.linesToTest = linesToTest;
		this.flagsNavigation = flagsNavigation;
	}
	
	@Override
	public void execute() throws Exception {
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new PageHomeMarcasSteps().validateIsPageWithCorrectLineas();
		for (Linea linea : linesToTest) {
			if (new UtilsMangoTest().validarLinea(dataTest.pais, linea)) {
				validaLinea(linea, null);
				for (Sublinea sublinea : linea.getListSublineas(app)) {
					validaLinea(linea, sublinea);
				}
			}
		}		
	}
	
	private void validaLinea(Linea linea, Sublinea sublinea) throws Exception {
		LineaType lineaType = linea.getType();
		SublineaType sublineaType = null;
		if (sublinea!=null) {
			sublineaType = sublinea.getTypeSublinea();
		}
		
		SecMenusWrapperSteps secMenusSteps = new SecMenusWrapperSteps();
		secMenusSteps.seleccionLinea(lineaType, sublineaType);
		if (sublinea==null) {
			testSpecificFeaturesForLinea(linea);
		}
			
		if (flagsNavigation.testOrderAndTranslationMenus()) {
			secMenusSteps.checkOrderAndTranslationMenus(linea, dataTest.idioma.getCodigo());
		}
		
		//Validamos si hemos de ejecutar los pasos correspondientes al recorrido de los menús
		if (testMenus(linea, sublinea)) {
			secMenusSteps.stepsMenusLinea(lineaType, sublineaType);
			if (existsRightBannerMenu(linea, sublinea)) {
				SecMenusDesktopSteps secMenusDesktopSteps = new SecMenusDesktopSteps();
				secMenusDesktopSteps.clickRightBanner(lineaType, sublineaType);
			}
		} else {
			SecMenusWrap secMenus = new SecMenusWrap();
			if (secMenus.canClickMenuArticles(dataTest.pais, linea, sublinea)) {
				Menu1rstLevel menu = getMenu(lineaType, sublineaType);
				secMenusSteps.selectMenu1rstLevelTypeCatalog(menu);
				if (flagsNavigation.testMenus()) {
					boolean bannerIsLincable = new PageGaleriaDesktop().getSecBannerHead().isLinkable();
					if (bannerIsLincable) {
						new PageGaleriaSteps().bannerHead.clickBannerSuperiorIfLinkableDesktop();
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
			if (channel==Channel.mobile) {
				menu = "dormitorio-mantas";
			} else {
				menu = "mantas_dormitorio";
			}
			break;
		default:
			menu = "pantalones";
		}
		return MenuTreeApp.getMenuLevel1From(app, KeyMenu1rstLevel.from(lineaType, sublineaType, menu));
	}
	
	public void testSpecificFeaturesForLinea(Linea linea) throws Exception {
		if (testBanners(linea)) {
			int maxBannersToTest = getMaxBannersToTest();
			SecBannersSteps secBannersSteps = new SecBannersSteps(maxBannersToTest);
			secBannersSteps.testPageBanners(maxBannersToTest);
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
	
	private int getMaxBannersToTest() {
		LevelPais levelPais = dataTest.pais.getLevelPais();
		return levelPais.getNumBannersTest(app);
	}
	
	private boolean existsRightBannerMenu(Linea linea, Sublinea sublinea) {
		if (channel==Channel.desktop) {
			if (sublinea!=null) {
				return ("s".compareTo(sublinea.getMenusart())==0); 
			}
			return ("s".compareTo(linea.getMenusart())==0); 
		}
		return false;
	}	

}
