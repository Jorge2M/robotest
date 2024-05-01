package com.mng.robotest.tests.domains.menus.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.beans.Sublinea;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.testslegacy.beans.*;

import java.io.Serializable;

public class Menus implements TestFromFactory, Serializable {
	
	private static final long serialVersionUID = 7000361927887748996L;
	
	private String indexFact = "";
	
	private final Pais country;
	private final IdiomaPais idiom;
	private final Linea line;
	private final Sublinea subline;
	private final GroupType group;

	//From @Factory
	public Menus(Pais country, IdiomaPais idiom, Linea line, GroupType group) {
		this(country, idiom, line, null, group);
	}
	
	public Menus(Pais country, IdiomaPais idiom, Linea line, Sublinea subline, GroupType group) {
		this.indexFact = getIndexFact(country, idiom, line, subline, group);
		this.country = country;
		this.idiom = idiom;
		this.line = line;
		this.subline = subline;
		this.group = group;
	}
	
	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	  
	@Test (
		testName="MEN001",			
		groups={"Menus", "Galeria", "Canal:all_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todos los menús del país/línea/grupo")
	public void menus() throws Exception {
		new Men001(country, idiom, line, subline, group).execute();
	}
	
	private String getIndexFact(Pais country, IdiomaPais idiom, Linea line, Sublinea subline, GroupType group) {
	    return country.getNombrePais().replace(" (Península y Baleares)", "") + "-" + 
	            idiom.getLiteral() + "-" +
	            line.getType() + "-" +
	            (subline == null ? line.getType() : subline.getTypeSublinea() + "-") +
	            group.name();
	}
	
}