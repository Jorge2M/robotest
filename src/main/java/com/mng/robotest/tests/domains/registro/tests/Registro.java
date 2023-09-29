package com.mng.robotest.tests.domains.registro.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

public class Registro implements Serializable {
	
	private static final long serialVersionUID = 9220128375933995114L;
	
	private String indexFact = "";
	private boolean accessFromFactory = false;
	public int prioridad;
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	public Registro() {	}
	
	//From @Factory
	public Registro(Pais pais, IdiomaPais idioma, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
		this.accessFromFactory = true;
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:shop", "SupportsFactoryCountrys"},
		description="Registro nuevo de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG001_NewRegisterOK() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		new Reg001(pais, idioma).execute();
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:outlet"},
		description="Registro antiguo con errores en la introducción de los datos")
	public void REG002_RegistroNOK() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		new Reg002(pais, idioma).execute();
	}

	@Test (
		groups={"Registro", "Canal:all_App:outlet", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="Registro antiguo de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG003_RegistroOK_publi() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		new Reg003(pais, idioma, accessFromFactory).execute();
	}
	
	@Test (
		groups={"Registro", "Canal:desktop_App:outlet"}, alwaysRun=true, 
		description="Registro antiguo de un usuario (sin seleccionar el link de publicidad)")
	public void REG004_RegistroOK_NoPubli() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		new Reg004(pais, idioma).execute();
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:shop"},
		description="Registro nuevo de un usuario en Corea (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG005_NewRegisterCorea() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		var corea = COREA_DEL_SUR.getPais();
		var ingles = corea.getListIdiomas().get(1);
		new Reg001(corea, ingles).execute();
	}	
	
	@Test (
		groups={"Registro", "Canal:all_App:shop"},
		description="Alta/Registro antiguo de un usuario en Islandia y (seleccionando link de publicidad) posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG006_OldRegisterIslandia() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		Pais islandia = ICELAND.getPais();
		IdiomaPais ingles = islandia.getListIdiomas().get(0);
		new Reg003(islandia, ingles, false).execute();
	}	

	private boolean isPro() {
		return PageBase.isEnvPRO();
	}

		
}
