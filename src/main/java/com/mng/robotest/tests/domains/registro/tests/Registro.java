package com.mng.robotest.tests.domains.registro.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.github.jorge2m.testmaker.domain.TestFromFactory;

public class Registro implements TestFromFactory, Serializable {
	
	private static final long serialVersionUID = 9220128375933995114L;
	
	private String indexFact = "";
	private boolean accessFromFactory = false;
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	public Registro() {	}
	
	//From @Factory
	public Registro(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.accessFromFactory = true;
	}
	
	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	
	@Test (
		groups={"Registro", "Smoke", "Canal:all_App:shop,outlet", "SupportsFactoryCountrys"},
		description="Registro nuevo de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG001_NewRegisterOK() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg001(pais, idioma).execute();
	}
	
	//TODO eliminar REG002,REG003,REG004
	@Test (
		enabled=false,
		groups={"Registro", "Canal:all_App:outlet"},
		description="Registro antiguo con errores en la introducción de los datos")
	public void REG002_RegistroNOK() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg002(pais, idioma).execute();
	}

	@Test (
		enabled=false,
		groups={"Registro", "Canal:all_App:outlet", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="Registro antiguo de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG003_RegistroOK_publi() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg003(pais, idioma, accessFromFactory).execute();
	}
	
	@Test (
		enabled=false,
		groups={"Registro", "Canal:desktop_App:outlet"}, alwaysRun=true, 
		description="Registro antiguo de un usuario (sin seleccionar el link de publicidad)")
	public void REG004_RegistroOK_NoPubli() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg004(pais, idioma).execute();
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:shop"},
		description="Registro nuevo de un usuario en Corea (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG005_NewRegisterCorea() throws Exception {
		if (isPro()) {
			return;
		}
		var corea = COREA_DEL_SUR.getPais();
		var ingles = corea.getListIdiomas().get(1);
		new Reg001(corea, ingles).execute();
	}	
	
	@Test (
		groups={"Registro", "Canal:all_App:shop"},
		description="Alta/Registro antiguo de un usuario en Deutschland y (seleccionando link de publicidad) posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG006_OldRegisterDeutschland() throws Exception {
		if (isPro()) {
			return;
		}
		Pais deutschland = DEUTSCHLAND.getPais();
		IdiomaPais deutsch = deutschland.getListIdiomas().get(0);
		new Reg003(deutschland, deutsch, false).execute();
	}	
	
	@Test (
		groups={"Registro", "Canal:all_App:shop,outlet"},
		description="Registro con errores en la introducción de los datos (España)")
	public void REG007_NewRegisterNOK() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg007().execute();
	}		
	
	@Test (
		groups={"Bolsa", "Registro", "Checkout", "Canal:mobile_App:shop,outlet"}, 
		description="Flujo bolsa -> identificación -> checkout")
	public void REG008_Bolsa_Registro() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg008().execute();
	}
	
	@Test (
		groups={"Registro", "Canal:desktop_App:shop,outlet"}, 
		description="Registro sin publicidad no pierde contactabilidad")
	public void REG009_Contactability_Persists() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg009().execute();
	}	
	
	@Test (
		groups={"Registro", "Loyalty", "Canal:all_App:shop"}, 
		description="Registro a través de la página del Club Mango Likes You")
	public void REG010_From_MLY_Club() throws Exception {
		if (isPro()) {
			return;
		}
		new Reg010().execute();
	}	

	private boolean isPro() {
		return PageBase.isEnvPRO();
	}
		
}
