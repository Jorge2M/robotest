package com.mng.robotest.domains.registro.tests;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;


public class Registro implements Serializable {
	
	private static final long serialVersionUID = 9220128375933995114L;
	
	private String index_fact = "";
	private boolean accessFromFactory = false;
	public int prioridad;
	private Pais pais = PaisGetter.get(PaisShop.ESPANA);
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	public Registro() { }
	
	//From @Factory
	public Registro(Pais pais, IdiomaPais idioma, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.index_fact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
		this.accessFromFactory = true;
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:shop"},
		description="Alta/Registro de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG001_NewRegisterOK() throws Exception {
		new Reg001().execute();
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:outlet"},
		description="Registro con errores en la introducción de los datos")
	public void REG002_RegistroNOK() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		new Reg002(pais, idioma).execute();
	}

	@Test (
		groups={"Registro", "Canal:desktop,mobile_App:outlet", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="Alta/Registro de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG003_RegistroOK_publi() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		new Reg003(pais, idioma, accessFromFactory).execute();
	}
	
	@Test (
		groups={"Registro", "Canal:desktop_App:outlet"}, alwaysRun=true, 
		description="Alta/Registro de un usuario (sin seleccionar el link de publicidad)")
	public void REG004_RegistroOK_NoPubli() throws Exception {
		TestCaseTM.addNameSufix(this.index_fact);
		new Reg004(pais, idioma).execute();
	}
}
