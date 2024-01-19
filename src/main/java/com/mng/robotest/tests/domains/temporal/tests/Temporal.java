package com.mng.robotest.tests.domains.temporal.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;

public class Temporal implements TestFromFactory, Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	private String indexFact = "";
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);

	public Temporal() {}

	//From @Factory
	public Temporal(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
	} 

	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	
	@Test(
		groups={"Tempral", "Canal:desktop_App:shop", "SupportsFactoryCountrys"}, 
		description="Check modal inicio")
	public void MLY001_CheckModalInicio() throws Exception {
		new Mly001(pais, idioma).execute();
	}

}