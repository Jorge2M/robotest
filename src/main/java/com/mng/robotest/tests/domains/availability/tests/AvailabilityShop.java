package com.mng.robotest.tests.domains.availability.tests;

import static com.mng.robotest.testslegacy.data.PaisShop.ESPANA;

import java.io.Serializable;

import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public class AvailabilityShop implements Serializable {

	private static final long serialVersionUID = 7000361927887748996L;
	
	private String indexFact = "";
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	
	//From @Factory
	public AvailabilityShop(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
	}	
	
	@Test (
		groups={"Availability", "Canal:all_App:shop,outlet"}, 
		description="Acceso al país y chequeo disponibilidad galerías y fichas")
	public void AVA001() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Ava001(pais, idioma).execute();
	}

}
