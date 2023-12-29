package com.mng.robotest.tests.domains.menus.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.tests.conf.suites.FlagsNaviationLineas;
import com.mng.robotest.tests.conf.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.testslegacy.beans.*;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;
import java.util.List;

public class PaisIdioma implements Serializable {
	
	private static final long serialVersionUID = 7000361927887748996L;
	
	private String indexFact = "";
	private FlagsNaviationLineas flagsNavigation = VersionPaisSuite.V1;
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	private final List<Linea> linesToTest;

	public PaisIdioma() {
		linesToTest = null;
	}

	//From @Factory
	public PaisIdioma(FlagsNaviationLineas flagsNavigation, Pais pais, IdiomaPais idioma, List<Linea> linesToTest) {
		this.flagsNavigation = flagsNavigation;
		String lineaStr = "";
		if (linesToTest.size()==1) {
			lineaStr = "-" + linesToTest.get(0).getType();
		}
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral() + lineaStr;
		this.pais = pais;
		this.idioma = idioma;
		this.linesToTest = linesToTest;
	}
	  
	@Test (
		groups={"Menus", "Galeria", "Canal:all_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todas las líneas/sublíneas del país + selección menú/s")
	public void PAR001_Lineas() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Par001(pais, idioma, linesToTest, flagsNavigation).execute();
	}
	
}