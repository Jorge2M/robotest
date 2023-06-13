package com.mng.robotest.domains.manto.factories;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.domains.manto.tests.MenusFact;
import com.mng.robotest.domains.manto.tests.MenusFact.Section;

public class MenusMantoFact {

	@Factory(
        groups={"Manto", "Canal:desktop_App:all"}, 
		description="Factoría que incluye 1 tests por cada uno de los grupos de menús del Manto") 
	public Object[] MAN900_MenusManto(ITestContext ctxTestRun) throws Exception {
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		var listTests = new ArrayList<MenusFact>();
		int prioridad=0;
		for (Section section : Section.values()) {
			System.out.println("Creado Test con datos: URL=" + inputData.getUrlBase() + ", cabeceraMenuName=" + section.getCabecera());
			listTests.add(new MenusFact(section, prioridad));
			prioridad+=1;
		}
	
		return listTests.toArray(new Object[listTests.size()]);
	}
}
