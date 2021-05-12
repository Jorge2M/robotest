package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.Arrays;
import org.openqa.selenium.WebDriver;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.boundary.aspects.step.SaveWhen;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;
import com.mng.robotest.test80.mango.test.stpv.navigations.shop.AccesoNavigations;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks;
import com.mng.robotest.test80.mango.test.stpv.shop.genericchecks.GenericChecks.GenericCheck;

public class PagePrehomeStpV {
    
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>#{dCtxSh.getNombrePais()}</b>",
        expected="Se selecciona el país/idioma correctamente")
    public static void seleccionPaisIdioma(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
    	//Temporal para test Canary!!!
    	//AccesoNavigations.goToInitURL(urlPreHome + "?canary=true", driver);
    	AccesoNavigations.goToInitURL(/*urlPreHome,*/ driver);
        PagePrehome.identJCASifExists(driver);
        PagePrehome.selecionPais(dCtxSh, driver);
        checkPaisSelected(dCtxSh, driver);
    }
	
	@Validation
	private static ChecksTM checkPaisSelected(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	    if (dCtxSh.channel==Channel.desktop) {
	    	validations.add(
				"Queda seleccionado el país con código " + dCtxSh.pais.getCodigo_pais() + " (" + dCtxSh.pais.getNombre_pais() + ")",
				PagePrehome.isPaisSelectedDesktop(driver, dCtxSh.pais.getNombre_pais()), State.Warn, true);
	    }
	    
	    boolean isPaisWithMarcaCompra = PagePrehome.isPaisSelectedWithMarcaCompra(driver);
	    if (dCtxSh.pais.isVentaOnline()) {
	    	validations.add(
				"El país <b>Sí</b> tiene la marca de venta online\"",
				isPaisWithMarcaCompra, State.Warn, true);
	    } else {
	    	validations.add(
				"El país <b>No</b> tiene la marca de venta online\"",
				!isPaisWithMarcaCompra, State.Warn, true);	    	
	    }
	    return validations;
	}
    
	@Step (
		description="Si es preciso introducimos la provincia/idioma y finalmente seleccionamos el botón \"Entrar\"",
        expected="Se accede a la Shop correctamente")
    public static void entradaShopGivenPaisSeleccionado(Pais pais, IdiomaPais idioma, Channel channel, WebDriver driver) 
    throws Exception {
		PagePrehome.selecionProvIdiomAndEnter(pais, idioma, channel, driver);
    }

    // Acceso a través de objetos Pais e Iidoma
    public static void seleccionPaisIdiomaAndEnter(DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        PagePrehomeStpV.seleccionPaisIdiomaAndEnter(dCtxSh, false/*execValidacs*/, driver);
    }
    
    @Step (
    	description="Acceder a la página de inicio y seleccionar el país <b>#{dCtxSh.getNombrePais()}</b>, el idioma <b>#{dCtxSh.getLiteralIdioma()}</b> y acceder",
        expected="Se accede correctamente al pais / idioma seleccionados",
        saveNettraffic=SaveWhen.Always)
    public static void seleccionPaisIdiomaAndEnter(DataCtxShop dCtxSh, boolean execValidacs, WebDriver driver) throws Exception {
    	PagePrehome.accesoShopViaPrehome(dCtxSh, driver);
    	
		GenericChecks.from(Arrays.asList(
				GenericCheck.GoogleAnalytics,
				GenericCheck.Analitica,
				GenericCheck.NetTraffic)).checks(driver);
        
        if (execValidacs) {
        	checkPagePostPreHome(dCtxSh.appE, driver);
        }
        
		GenericChecks.from(Arrays.asList(
				GenericCheck.SEO,
				GenericCheck.Analitica,
				GenericCheck.JSerrors)).checks(driver);
    }    
    
    @Validation
    private static ChecksTM checkPagePostPreHome(AppEcom app, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
    	String title = driver.getTitle().toLowerCase();
    	if (app==AppEcom.outlet) {
	    	validations.add(
				"Aparece una pantalla en la que el título contiene <b>outlet</b>",
				title.contains("outlet"), State.Defect);
    	} else {
	    	validations.add(
				"Aparece una pantalla en la que el título contiene <b>mango</b>",
				title.contains("mango"), State.Defect);
    	}
    	return validations;
    }
}
