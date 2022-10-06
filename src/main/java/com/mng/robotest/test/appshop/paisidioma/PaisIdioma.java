package com.mng.robotest.test.appshop.paisidioma;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.suites.FlagsNaviationLineas;
import com.mng.robotest.test.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.test.utils.PaisGetter;

import java.io.Serializable;
import java.util.List;

public class PaisIdioma implements Serializable {
	
	private static final long serialVersionUID = 7000361927887748996L;
	
	private String index_fact = "";
	public int prioridad;
	private FlagsNaviationLineas flagsNavigation = VersionPaisSuite.V1;
	private Pais pais = PaisGetter.get(PaisShop.ESPANA);
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	private final List<Linea> linesToTest;

	public PaisIdioma() {
		linesToTest = null;
	}

	//From @Factory
	public PaisIdioma(FlagsNaviationLineas flagsNavigation, Pais pais, IdiomaPais idioma, List<Linea> linesToTest, int prioridad) {
		this.flagsNavigation = flagsNavigation;
		String lineaStr = "";
		if (linesToTest.size()==1) {
			lineaStr = "-" + linesToTest.get(0).getType();
		}
		this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral() + lineaStr;
		this.pais = pais;
		this.idioma = idioma;
		this.linesToTest = linesToTest;
		this.prioridad = prioridad;
	}
	  
	@Test (
		groups={"Lineas", "Canal:all_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todas las líneas/sublíneas del país + selección menú/s")
	public void PAR001_Lineas() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		new Par001(pais, idioma, linesToTest, flagsNavigation).execute();
	}
	
}