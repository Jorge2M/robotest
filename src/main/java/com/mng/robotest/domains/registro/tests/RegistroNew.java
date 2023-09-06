package com.mng.robotest.domains.registro.tests;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

import static com.mng.robotest.test.data.PaisShop.*;

public class RegistroNew implements Serializable {
	
	private static final long serialVersionUID = 9220128375933995114L;
	
	private String indexFact = "";
	public int prioridad;
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	public RegistroNew() {	}
	
	//From @Factory
	public RegistroNew(Pais pais, IdiomaPais idioma, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
	}
	
	@Test (
		groups={"Registro", "Canal:all_App:all", "SupportsFactoryCountrys"},
		description="Registro nuevo de un usuario (seleccionando link de publicidad) y posterior logof + login + consulta en mis datos para comprobar la coherencia de los datos utilizados en el registro")
	public void REG001_NewRegisterOK() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		if (isPro()) {
			return;
		}
		new Reg001(pais, idioma).execute();
	}
	
	private boolean isPro() {
		return PageBase.isEnvPRO();
	}
		
}
