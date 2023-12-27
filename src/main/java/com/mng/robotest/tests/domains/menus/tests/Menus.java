package com.mng.robotest.tests.domains.menus.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.tests.domains.menus.beans.Linea;
import com.mng.robotest.tests.domains.menus.beans.Sublinea;
import com.mng.robotest.tests.domains.menus.pageobjects.GroupWeb.GroupType;
import com.mng.robotest.testslegacy.beans.*;

import java.io.Serializable;

public class Menus implements Serializable {
	
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
		this.indexFact = 
				country.getNombrePais() + "-" + 
				idiom.getLiteral() + "-" +
				line.getType() + "-" +
				group.name();
		
		this.country = country;
		this.idiom = idiom;
		this.line = line;
		this.subline = subline;
		this.group = group;
	}
	  
	@Test (
		groups={"Menus", "Galeria", "Canal:all_App:shop,outlet"}, 
		description="Acceso desde prehome y navegación por todos los menús del país/línea/grupo")
	public void MEN001() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Men001(country, idiom, line, subline, group).execute();
	}
	
}