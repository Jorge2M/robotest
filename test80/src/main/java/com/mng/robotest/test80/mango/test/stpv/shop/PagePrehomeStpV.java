package com.mng.robotest.test80.mango.test.stpv.shop;

import java.util.EnumSet;
import org.openqa.selenium.WebDriver;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.utils.State;
import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.IdiomaPais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.generic.PasosGenAnalitica;
import com.mng.robotest.test80.mango.test.pageobject.shop.PagePrehome;

public class PagePrehomeStpV {
    
	@Step (
		description="Acceder a la página de inicio y seleccionar el país <b>#{dCtxSh.getNombrePais()}</b>",
        expected="Se selecciona el país/idioma correctamente")
    public static void seleccionPaisIdioma(DataCtxShop dCtxSh, WebDriver driver) 
    throws Exception {
        PagePrehome.goToPagePrehome(dCtxSh.urlAcceso, driver);
        PagePrehome.selecionPais(dCtxSh, driver);
        checkPaisSelected(dCtxSh, driver);
    }
	
	@Validation
	private static ChecksResult checkPaisSelected(DataCtxShop dCtxSh, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
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
    	
        EnumSet<Constantes.AnalyticsVal> analyticSet = EnumSet.of(
            Constantes.AnalyticsVal.GoogleAnalytics,
            Constantes.AnalyticsVal.NetTraffic, 
            Constantes.AnalyticsVal.DataLayer);        
        PasosGenAnalitica.validaHTTPAnalytics(dCtxSh.appE, LineaType.she, analyticSet, driver);
        
        if (execValidacs) {
        	checkPagePostPreHome(dCtxSh.appE, driver);
        }
        
        StdValidationFlags flagsVal = StdValidationFlags.newOne();
        flagsVal.validaSEO = true;
        flagsVal.validaJS = true;
        flagsVal.validaImgBroken = false;
        AllPagesStpV.validacionesEstandar(flagsVal, driver);
    }    
    
    @Validation
    private static ChecksResult checkPagePostPreHome(AppEcom app, WebDriver driver) {
    	ChecksResult validations = ChecksResult.getNew();
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
