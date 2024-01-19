package com.mng.robotest.tests.domains.menus.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.tests.conf.suites.FlagsNaviationLineas;
import com.mng.robotest.tests.conf.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.testslegacy.beans.*;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;
import java.util.List;

public class PaisIdioma implements Serializable, TestFromFactory {
	
	private static final long serialVersionUID = 7000361927887748996L;
	
	private FlagsNaviationLineas flagsNavigation = VersionPaisSuite.V1;
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
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
		groups={"Menus", "Galeria", "Canal:all_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todas las líneas/sublíneas del país + selección menú/s")
	public void PAR001_Lineas() throws Exception {
		new Par001(pais, idioma, linesToTest, flagsNavigation).execute();
	}
	
}