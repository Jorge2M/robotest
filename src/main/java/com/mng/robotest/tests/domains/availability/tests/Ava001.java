package com.mng.robotest.tests.domains.availability.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.SublineaType;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Linea;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.generic.UtilsMangoTest;

import static com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType.*;

import java.util.Arrays;
import java.util.List;

public class Ava001 extends TestBase {
	
	private List<LineaType> linesToCheck = Arrays.asList(SHE, HE, NINA, TEEN, HOME);
	
	public Ava001(Pais pais, IdiomaPais idioma) {
		this.dataTest.setPais(pais);
		this.dataTest.setIdioma(idioma);
	}

	@Override
	public void execute() throws Exception {
		quickAccess();
		checkLanding();
		checkLines();
	}
	
	private void checkLanding() {
		var pageLandingSteps = new PageLandingSteps();
		pageLandingSteps.checkIsPage(5);
		pageLandingSteps.validateIsPageWithCorrectLineas();
	}
	
	private void checkLines() throws Exception {
		for (Linea linea : dataTest.getLineas()) {
			if (isCheckLine(linea)) {
				var sublineaOpt = getFirstSublinea(linea);
				checkLine(linea, sublineaOpt);
			}
		}		
	}
	
	private SublineaType getFirstSublinea(Linea linea) {
		var listLineas = linea.getListSublineas(app);
		if (listLineas==null || listLineas.isEmpty()) {
			return null;
		}
		return listLineas.get(0).getTypeSublinea();
	}
	
	private void checkLine(Linea linea, SublineaType sublinea) throws Exception {
		clickLinea(linea.getType(), sublinea);
		clickMenu(linea.getType(), sublinea);
	}
	
	private boolean isCheckLine(Linea line) {
		return 
			linesToCheck.contains(line.getType()) &&
			new UtilsMangoTest().isLineActive(line);
	}
	
	private void clickMenu(LineaType lineaType, SublineaType sublineaType) {
		var groupType = GroupType.PRENDAS;
		String menu = "pantalones";
		if (lineaType==NINA || lineaType==NINO) {
			menu = "camisas";
		}
		if (lineaType==HOME) {
			groupType = GroupType.DORMITORIO;
			menu = "mantas_dormitorio";
		}
		
		clickMenu(new MenuWeb
			.Builder(menu)
			.linea(lineaType)
			.sublinea(sublineaType)
			.group(groupType)
			.build());
	}	
	
}
