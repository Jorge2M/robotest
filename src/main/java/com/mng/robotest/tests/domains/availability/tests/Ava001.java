package com.mng.robotest.tests.domains.availability.tests;

import com.mng.robotest.tests.domains.base.TestBase;
import com.mng.robotest.tests.domains.transversal.home.steps.PageLandingSteps;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.MenuWeb;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.tests.domains.transversal.menus.pageobjects.LineaWeb.LineaType;
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
				checkLine(linea);
			}
		}		
	}
	
	private void checkLine(Linea linea) throws Exception {
		var lineaType = linea.getType();
		clickLinea(lineaType);
		clickMenu(lineaType);
	}
	
	private boolean isCheckLine(Linea line) {
		return 
			linesToCheck.contains(line.getType()) &&
			new UtilsMangoTest().isLineActive(line);
	}
	
	private void clickMenu(LineaType lineaType) {
		var groupType = GroupType.PRENDAS;
		String menu = "pantalones";
		if (lineaType==NINA || lineaType==NINO) {
			menu = "camisas";
		}
		if (lineaType==HOME) {
			menu = "mantas_dormitorio";
		}
		
		clickMenu(new MenuWeb
			.Builder(menu)
			.linea(lineaType)
			.group(groupType)
			.build());
	}	
	
}
