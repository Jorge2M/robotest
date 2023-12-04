package com.mng.robotest.tests.domains.transversal.prehome.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.transversal.acceso.navigations.AccesoFlows;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PageJCAS;
import com.mng.robotest.tests.domains.transversal.prehome.pageobjects.PagePrehome;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.github.jorge2m.testmaker.conf.StoreType.*;
import static com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen.*;

public class PagePrehomeSteps extends StepBase {
	
	private final Pais pais = dataTest.getPais();
	private final PagePrehome pgPrehome = new PagePrehome();
	
	private static final String TAG_PAIS= "@TAGPAIS";
	private static final String TAG_IDIOMA = "@TAGIDIOMA";
	
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>#{dataTest.getNombrePais()}</b>",
		expected="Se selecciona el país/idioma correctamente")
	public void seleccionPaisIdioma() {
		new AccesoFlows().goToInitURL();
		new PageJCAS().identJCASifExists();
		pgPrehome.selecionPais();
		checkPaisSelected();
	}
	
	@Validation
	private ChecksTM checkPaisSelected() {
		var checks = ChecksTM.getNew();
		if (isDesktop()) {
			checks.add(
			    Check.make(
				    "Queda seleccionado el país con código " + pais.getCodigoPais() + " (" + pais.getNombrePais() + ")",
				    pgPrehome.isPaisSelected(), WARN)
			    .store(NONE).build());
		}
		
		boolean isPaisWithMarcaCompra = pgPrehome.isPaisSelectedWithMarcaCompra();
		if (pais.isVentaOnline()) {
			checks.add(
			    Check.make(
				    "El país <b>Sí</b> tiene la marca de venta online\"",
				    isPaisWithMarcaCompra, WARN)
			    .store(NONE).build());
		} else {
			checks.add(
			    Check.make(
				    "El país <b>No</b> tiene la marca de venta online\"",
				    !isPaisWithMarcaCompra, WARN)
			    .store(NONE).build());
		}
		return checks;
	}
	
	@Step (
		description="Si es preciso seleccionamos el idioma y finalmente el botón \"Entrar\"",
		expected="Se accede a la Shop correctamente")
	public void entradaShopGivenPaisSeleccionado() {
		pgPrehome.selecionIdiomaAndEnter();
	}

	public void accessShopViaPreHome(boolean execValidacs, boolean acceptCookies) throws Exception {
		accessShopViaPreHome(dataTest.getPais(), dataTest.getIdioma(), execValidacs, acceptCookies);
	}
	
	@Step (
		description=
			"Acceso <b style=\"color:brown;\">#{pais.getNombrePais()} / #{idioma.getLiteral()}</b> desde la PreHome",
		expected="Se accede correctamente al pais / idioma seleccionados",
		saveNettraffic=ALWAYS)
	private void accessShopViaPreHome(Pais pais, IdiomaPais idioma, boolean execValidacs, boolean acceptCookies) throws Exception {
		replaceStepDescription(TAG_PAIS, pais.getNombrePais());
		replaceStepDescription(TAG_IDIOMA, idioma.getLiteral());
		
		pgPrehome.accesoShopViaPrehome();
		
		checksDefault();
		checksGeneric()
			.googleAnalytics()
			.netTraffic().execute();
		
		if (execValidacs) {
			checkPagePostPreHome();
		}
	}	
	
	@Validation
	private ChecksTM checkPagePostPreHome() {
		var checks = ChecksTM.getNew();
		String title = driver.getTitle().toLowerCase();
		if (isOutlet()) {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>outlet</b>",
				title.contains("outlet"));
		} else {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>mango</b>",
				title.contains("mango"));
		}
		return checks;
	}
}
