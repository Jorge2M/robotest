package com.mng.robotest.test.steps.shop;

import java.util.Arrays;
import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.conf.StoreType;
import com.github.jorge2m.testmaker.domain.suitetree.Check;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.pageobject.shop.PageJCAS;
import com.mng.robotest.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test.steps.navigations.shop.AccesoNavigations;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks;
import com.mng.robotest.test.steps.shop.genericchecks.GenericChecks.GenericCheck;

public class PagePrehomeSteps {
	
	private final WebDriver driver;
	private final DataCtxShop dCtxSh;
	private final PagePrehome pagePrehome;
	
	public PagePrehomeSteps(DataCtxShop dCtxSh, WebDriver driver) {
		this.driver = driver;
		this.dCtxSh = dCtxSh;
		this.pagePrehome = new PagePrehome(dCtxSh, driver);
	}
	
	public PagePrehome getPageObject() {
		return this.pagePrehome;
	}
	
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>#{dCtxSh.getNombrePais()}</b>",
		expected="Se selecciona el país/idioma correctamente")
	public void seleccionPaisIdioma() 
	throws Exception {
		AccesoNavigations.goToInitURL(driver);
		new PageJCAS(driver).identJCASifExists();
		pagePrehome.selecionPais();
		checkPaisSelected();
	}
	
	@Validation
	private ChecksTM checkPaisSelected() {
		ChecksTM checks = ChecksTM.getNew();
		if (dCtxSh.channel==Channel.desktop) {
			checks.add(
			    Check.make(
				    "Queda seleccionado el país con código " + dCtxSh.pais.getCodigo_pais() + " (" + dCtxSh.pais.getNombre_pais() + ")",
				    pagePrehome.isPaisSelectedDesktop(), State.Warn)
			    .store(StoreType.None).build());
		}
		
		boolean isPaisWithMarcaCompra = pagePrehome.isPaisSelectedWithMarcaCompra();
		if (dCtxSh.pais.isVentaOnline()) {
			checks.add(
			    Check.make(
				    "El país <b>Sí</b> tiene la marca de venta online\"",
				    isPaisWithMarcaCompra, State.Warn)
			    .store(StoreType.None).build());
		} else {
			checks.add(
			    Check.make(
				    "El país <b>No</b> tiene la marca de venta online\"",
				    !isPaisWithMarcaCompra, State.Warn)
			    .store(StoreType.None).build());
		}
		return checks;
	}
	
	@Step (
		description="Si es preciso seleccionamos el idioma y finalmente el botón \"Entrar\"",
		expected="Se accede a la Shop correctamente")
	public void entradaShopGivenPaisSeleccionado() throws Exception {
		pagePrehome.selecionIdiomaAndEnter();
	}

	public void seleccionPaisIdiomaAndEnter() throws Exception {
		seleccionPaisIdiomaAndEnter(false);
	}
	
	private final String TagPais = "@TAGPAIS";
	private final String TagIdioma = "@TAGIDIOMA";
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>" + TagPais + "</b>, el idioma <b>" + TagIdioma + "</b> y acceder",
		expected="Se accede correctamente al pais / idioma seleccionados",
		saveNettraffic=SaveWhen.Always)
	public void seleccionPaisIdiomaAndEnter(boolean execValidacs) throws Exception {
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagPais, dCtxSh.getNombrePais());
		TestMaker.getCurrentStepInExecution().replaceInDescription(TagIdioma, dCtxSh.getLiteralIdioma());
		
		pagePrehome.accesoShopViaPrehome(true);
		GenericChecks.from(Arrays.asList(
				GenericCheck.CookiesAllowed,
				GenericCheck.GoogleAnalytics,
				GenericCheck.Analitica,
				GenericCheck.SEO,
				GenericCheck.JSerrors,
				GenericCheck.TextsTraduced,
				GenericCheck.NetTraffic)).checks(driver);
		
		if (execValidacs) {
			checkPagePostPreHome();
		}
	}	
	
	@Validation
	private ChecksTM checkPagePostPreHome() {
		ChecksTM checks = ChecksTM.getNew();
		String title = driver.getTitle().toLowerCase();
		if (dCtxSh.appE==AppEcom.outlet) {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>outlet</b>",
				title.contains("outlet"), State.Defect);
		} else {
			checks.add(
				"Aparece una pantalla en la que el título contiene <b>mango</b>",
				title.contains("mango"), State.Defect);
		}
		return checks;
	}
}
