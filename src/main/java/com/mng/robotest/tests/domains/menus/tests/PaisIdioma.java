package com.mng.robotest.tests.domains.menus.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.tests.conf.suites.FlagsNaviationLineas;
import com.mng.robotest.tests.conf.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.tests.domains.menus.entity.Linea;
import com.mng.robotest.testslegacy.beans.*;

import java.io.Serializable;
import java.util.List;

public class PaisIdioma implements Serializable, TestFromFactory {
	
	private static final long serialVersionUID = 7000361927887748996L;
	
	private FlagsNaviationLineas flagsNavigation = VersionPaisSuite.V1;
	private Pais pais = null; //Default España
	private IdiomaPais idioma = null;
	private final List<Linea> linesToTest;
	private final boolean isFromFactory;

	public PaisIdioma() {
		linesToTest = null;
		isFromFactory = false;
	}
	
	//From @Factory
	public PaisIdioma(FlagsNaviationLineas flagsNavigation, Pais pais, IdiomaPais idioma, List<Linea> linesToTest) {
		this.flagsNavigation = flagsNavigation;
		this.pais = pais;
		this.idioma = idioma;
		this.linesToTest = linesToTest;
		this.isFromFactory = true;
	}
	
	@Override
	public String getIdTestInFactory() {
		if (!isFromFactory) { 
			return "";
		}
		String lineaStr = "";
		if (linesToTest.size()==1) {
			lineaStr = "-" + linesToTest.get(0).getType();
		}
		return pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral() + lineaStr;
	}	
	  
	@Test (
		testName="PAR001",			
		groups={"Menus", "Smoke", "Galeria", "Canal:all_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todas las líneas/sublíneas del país + selección menú/s")
	public void lineas() throws Exception {
		//Access from @Test
		if (pais==null) {
			new Par001(linesToTest, flagsNavigation).execute();
		}
		//Access from @Factory
		else {
			new Par001(pais, idioma, linesToTest, flagsNavigation).execute();
		}
	}
	
}