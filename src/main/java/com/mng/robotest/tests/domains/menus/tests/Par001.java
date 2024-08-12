package com.mng.robotest.tests.domains.menus.tests;

import java.util.List;

import com.mng.robotest.tests.conf.suites.FlagsNaviationLineas;
import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.landings.steps.LandingSteps;
import com.mng.robotest.tests.domains.menus.entity.GroupTypeO.GroupType;
import com.mng.robotest.tests.domains.menus.entity.Linea;
import com.mng.robotest.tests.domains.menus.entity.Sublinea;
import com.mng.robotest.tests.domains.menus.entity.Linea.TypeContentDesk;
import com.mng.robotest.tests.domains.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.menus.entity.LineaType;
import com.mng.robotest.tests.domains.menus.entity.SublineaType;
import com.mng.robotest.tests.domains.transversal.acceso.steps.AccesoSteps;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;
import com.mng.robotest.testslegacy.utils.LevelPais;

public class Par001 extends TestBase {

	private final List<Linea> linesToTest;
	private final FlagsNaviationLineas flagsNavigation;

	//Access from @Test
	public Par001(List<Linea> linesToTest, FlagsNaviationLineas flagsNavigation) {
		if (linesToTest==null) {
			linesToTest = dataTest.getPais().getShoponline().getLineasToTest(app);
		}
		this.linesToTest = linesToTest;
		this.flagsNavigation = flagsNavigation;
	}
	
	//Access from @Factory
	public Par001(Pais pais, IdiomaPais idioma, List<Linea> linesToTest, FlagsNaviationLineas flagsNavigation) {
		this.dataTest.setPais(pais);
		this.dataTest.setIdioma(idioma);
		
		if (linesToTest==null) {
			linesToTest = pais.getShoponline().getLineasToTest(app);
		}
		this.linesToTest = linesToTest;
		this.flagsNavigation = flagsNavigation;
	}
	
	@Override
	public void execute() throws Exception {
		new AccesoSteps().accessFromPreHome();
		new LandingSteps().checkIsCountryWithCorrectLineas(2);
		for (Linea linea : linesToTest) {
			if (new UtilsMangoTest().isLineActive(linea) &&
				linea.getType()!=LineaType.NINO) { //This line produces random errors and checking NINA is enough
				checkLinea(linea, null);
				for (Sublinea sublinea : linea.getListSublineas(app)) {
					checkLinea(linea, sublinea);
				}
			}
		}		
	}
	
	private void checkLinea(Linea linea, Sublinea sublinea) throws Exception {
		var lineaType = linea.getType();
		SublineaType sublineaType = null;
		if (sublinea!=null) {
			sublineaType = sublinea.getTypeSublinea();
		}
		
		clickLinea(lineaType, sublineaType);
		if (sublinea==null) {
			testSpecificFeaturesForLinea(linea);
		}

		if (canClickMenuArticles(dataTest.getPais(), linea, sublinea)) {
			clickMenuDependingLine(lineaType, sublineaType);
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
			new LandingSteps().checkPageBanners(maxBannersToTest);
		}
	}
	
	private boolean testBanners(Linea linea) {
		return (
			flagsNavigation.testBanners() && 
			linea.getContentDeskType()==TypeContentDesk.BANNERS);
	}
	
	private int getMaxBannersToTest(LineaType linea) {
		LevelPais levelPais = dataTest.getPais().getLevelPais();
		return levelPais.getNumBannersTest(linea);
	}
	
}
