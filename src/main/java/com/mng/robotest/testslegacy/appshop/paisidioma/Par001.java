package com.mng.robotest.testslegacy.appshop.paisidioma;

import java.util.List;

import com.mng.robotest.tests.conf.suites.FlagsNaviationLineas;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.galeria.pageobjects.PageGaleriaDesktopKondo;
import com.mng.robotest.tests.domains.galeria.steps.PageGaleriaSteps;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.tests.domains.transversal.banners.steps.SecBannersSteps;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.tests.domains.transversal.menus.steps.MenuSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Linea;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.beans.Sublinea;
import com.mng.robotest.testslegacy.beans.Linea.TypeContentDesk;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.utils.LevelPais;

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
		new AccesoSteps().accessFromPreHome();
		new PageLandingSteps().checkIsPageWithCorrectLineas();
		for (Linea linea : linesToTest) {
			if (new UtilsMangoTest().isLineActive(linea)) {
				validaLinea(linea, null);
				for (Sublinea sublinea : linea.getListSublineas(app)) {
					validaLinea(linea, sublinea);
				}
			}
		}		
	}
	
	private void validaLinea(Linea linea, Sublinea sublinea) throws Exception {
		var lineaType = linea.getType();
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
					boolean bannerIsLincable = new PageGaleriaDesktopKondo().isBannerHeadLinkable();
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
			new SecBannersSteps(maxBannersToTest)
				.checkPageBanners(maxBannersToTest);
		}
	}
	
	private boolean testBanners(Linea linea) {
		return (
			flagsNavigation.testBanners() && 
			linea.getContentDeskType()==TypeContentDesk.BANNERS);
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
