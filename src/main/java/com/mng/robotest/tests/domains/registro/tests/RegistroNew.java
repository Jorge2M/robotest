package com.mng.robotest.tests.domains.registro.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;

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
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
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