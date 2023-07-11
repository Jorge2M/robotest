package com.mng.robotest.domains.temporal.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;

import static com.mng.robotest.test.data.PaisShop.*;

import java.io.Serializable;

public class Temporal implements Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	private String indexFact = "";
	public int prioridad;
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);

	public Temporal() {}

	//From @Factory
	public Temporal(Pais pais, IdiomaPais idioma, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
	} 

	@Test(
		groups={"Tempral", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, 
		description="Check modal inicio")
	public void MLY001_CheckModalInicio() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Mly001(pais, idioma).execute();
	}

}