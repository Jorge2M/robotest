package com.mng.robotest.test.appshop.paisidioma;

import java.util.List;

import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.domains.galeria.pageobjects.PageGaleriaDesktop;
import com.mng.robotest.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.domains.transversal.prehome.steps.PagePrehomeSteps;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Linea;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.beans.Sublinea;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.test.beans.Linea.TypeContentDesk;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.test.generic.UtilsMangoTest;
import com.mng.robotest.test.steps.shop.banner.SecBannersSteps;
import com.mng.robotest.test.steps.shop.home.PageHomeMarcasSteps;
import com.mng.robotest.test.suites.FlagsNaviationLineas;
import com.mng.robotest.test.utils.LevelPais;

public class Par001 extends TestBase {

	private final List<Linea> linesToTest;
	private final FlagsNaviationLineas flagsNavigation;
	
	public Par001(Pais pais, IdiomaPais idioma, List<Linea> linesToTest, FlagsNaviationLineas flagsNavigation) {
		if (linesToTest==null) {
			linesToTest = pais.getShoponline().getLineasToTest(app);
		}
		this.dataTest.setPais(pais);
		this.dataTest.setIdioma(idioma);
		this.linesToTest = linesToTest;
		this.flagsNavigation = flagsNavigation;
	}
	
	@Override
	public void execute() throws Exception {
		new PagePrehomeSteps().seleccionPaisIdiomaAndEnter();
		new PageHomeMarcasSteps().validateIsPageWithCorrectLineas();
		for (Linea linea : linesToTest) {
			if (new UtilsMangoTest().validarLinea(linea)) {
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
		
		clickLinea(lineaType, sublineaType);
		if (sublinea==null) {
			testSpecificFeaturesForLinea(linea);
		}

		//Validamos si hemos de ejecutar los pasos correspondientes al recorrido de los menús
		if (testMenus(linea, sublinea)) {
			new MenuSteps().clickAllMenus(new LineaWeb(lineaType, sublineaType));
		} else {
			if (canClickMenuArticles(dataTest.getPais(), linea, sublinea)) {
				clickMenuDependingLine(lineaType, sublineaType);
				if (flagsNavigation.testMenus()) {
					boolean bannerIsLincable = new PageGaleriaDesktop().getSecBannerHead().isLinkable();
					if (bannerIsLincable) {
						new PageGaleriaSteps().bannerHead.clickBannerSuperiorIfLinkableDesktop();
					}
				}
			}
		}
	}
	
	private boolean canClickMenuArticles(Pais paisI, Linea linea, Sublinea sublinea) {
		if (paisI.isVentaOnline()) {
			if (sublinea==null) {
				return (linea.getMenusart().compareTo("s")==0);
			}
			return sublinea.getMenusart().compareTo("s")==0;
		}
		return false;
	}	
	
	private void clickMenuDependingLine(LineaType lineaType, SublineaType sublineaType) {
		String menu = "";
		GroupType groupType = GroupType.PRENDAS;
		switch (lineaType) {
		case NINA, NINO:
			menu = "camisas";
			break;
		case HOME:
			groupType = GroupType.DORMITORIO;
			menu = "mantas_dormitorio";
			break;
		default:
			menu = "pantalones";
		}
		clickMenu(new MenuWeb
				.Builder(menu)
				.linea(lineaType)
				.sublinea(sublineaType)
				.group(groupType)
				.build());
	}
	
	public void testSpecificFeaturesForLinea(Linea linea) throws Exception {
		if (testBanners(linea)) {
			int maxBannersToTest = getMaxBannersToTest(linea.getType());
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
	
	private int getMaxBannersToTest(LineaType linea) {
		LevelPais levelPais = dataTest.getPais().getLevelPais();
		return levelPais.getNumBannersTest(linea);
	}
	
}
