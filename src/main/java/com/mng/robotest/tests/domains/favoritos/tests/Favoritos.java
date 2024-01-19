package com.mng.robotest.tests.domains.favoritos.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.TestFromFactory;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;

public class Favoritos implements TestFromFactory, Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	private String indexFact = "";
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);

	public Favoritos() {}

	//From @Factory
	public Favoritos(Pais pais, IdiomaPais idioma) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombrePais() + " (" + pais.getCodigoPais() + ") " + "-" + idioma.getCodigo().getLiteral();
	} 

	@Override
	public String getIdTestInFactory() {
		return indexFact;
	}
	
	@Test(
		groups={"Favoritos", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario registrado] Alta favoritos desde la galería")
	public void FAV001_AltaFavoritosDesdeGaleria() throws Exception {
		new Fav001(pais, idioma).execute();
	}

	@Test(
		groups={"Favoritos", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario no registrado] Alta favoritos desde la galería Mango-Home y posterior identificación")
	public void FAV002_AltaFavoritosDesdeFicha() throws Exception {
		new Fav002(pais, idioma).execute();
	}
}