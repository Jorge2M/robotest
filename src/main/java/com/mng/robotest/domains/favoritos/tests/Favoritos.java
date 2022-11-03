package com.mng.robotest.domains.favoritos.tests;

import org.testng.annotations.*;

import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.data.PaisShop;
import com.mng.robotest.test.utils.PaisGetter;

import java.io.Serializable;

public class Favoritos implements Serializable {

	private static final long serialVersionUID = -3932978752450813757L;
	
	private String indexFact = "";
	public int prioridad;
	private Pais pais = PaisGetter.from(PaisShop.ESPANA);
	private IdiomaPais idioma = pais.getListIdiomas().get(0);

	public Favoritos() {}

	//From @Factory
	public Favoritos(Pais pais, IdiomaPais idioma, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.indexFact = pais.getNombre_pais() + " (" + pais.getCodigo_pais() + ") " + "-" + idioma.getCodigo().getLiteral();
		this.prioridad = prioridad;
	} 

	@Test(
		groups={"Favoritos", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario registrado] Alta favoritos desde la galería")
	public void FAV001_AltaFavoritosDesdeGaleria() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Fav001(pais, idioma).execute();
	}

	@Test(
		groups={"Favoritos", "Canal:desktop,mobile_App:shop", "SupportsFactoryCountrys"}, alwaysRun=true, 
		description="[Usuario no registrado] Alta favoritos desde la galería Mango-Home y posterior identificación")
	public void FAV002_AltaFavoritosDesdeFicha() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Fav002(pais, idioma).execute();
	}
}