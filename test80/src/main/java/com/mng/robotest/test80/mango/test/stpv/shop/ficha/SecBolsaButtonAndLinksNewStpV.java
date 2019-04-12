package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ChecksResult;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea.LineaType;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.ModCompartirNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDetalleProductNew;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.ModCompartirNew.IconSocial;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecBolsaButtonAndLinksNew.LinksAfterBolsa;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecDetalleProductNew.ItemBreadcrumb;
import com.mng.robotest.test80.mango.test.pageobject.shop.ficha.SecProductDescrOld.TypePanel;

public class SecBolsaButtonAndLinksNewStpV {
    
	@Step (
		description="Seleccionar el link <b>Envío y devoluciones</b>",
        expected="Aparece el modal con los datos a nivel de envío y devolución")
    public static void selectEnvioYDevoluciones(WebDriver driver) throws Exception {
        SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.EnvioYDevoluciones, driver);
        ModEnvioYdevolNewStpV.validateIsVisible(driver);
    }
    
	@Step (
		description="Seleccionar el link <b>Detalle del producto</b>",
        expected="Se scrolla hasta el apartado de \"Descripción\"")
    public static void selectDetalleDelProducto(AppEcom app, LineaType lineaType, WebDriver driver) throws Exception {
        SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.DetalleProducto, driver);
                
        checkScrollToDescription(driver);
        checkBreadCrumbs(driver);
        if (TypePanel.KcSafety.getListApps().contains(app) &&
            (lineaType==LineaType.nina || lineaType==LineaType.nino)) {
        	checkKcSafety(driver);
        }
    }
	
	@Validation (
		description="Se scrolla hasta el apartado de \"Descriptión\"",
		level=State.Defect)
	private static boolean checkScrollToDescription(WebDriver driver) {
	    int maxSecondsToWait = 3;
	    return (SecDetalleProductNew.isVisibleUntil(maxSecondsToWait, driver));
	}
	
	@Validation
	private static ChecksResult checkBreadCrumbs(WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	 	validations.add(
			"Figura el bloque de BreadCrumbs",
			SecDetalleProductNew.isVisibleBreadcrumbs(0, driver), State.Warn);
	 	validations.add(
			"Es visible el item " + ItemBreadcrumb.linea,
			SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.linea, driver), State.Warn);
	 	validations.add(
			"Es visible el item " + ItemBreadcrumb.grupo,
			SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.grupo, driver), State.Warn);
	 	validations.add(
			"Es visible el item " + ItemBreadcrumb.galeria,
			SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.galeria, driver), State.Warn);
	 	return validations;
	}
	
	@Validation (
		description="Aparece el bloque de \"KcSafety\"",
		level=State.Defect)
	private static boolean checkKcSafety(WebDriver driver) {
		return (SecDetalleProductNew.isVisibleBlockKcSafety(driver));
	}
    
	@Step (
		description="Seleccionar el link <b>Compartir</b>",
        expected="Aparece el modal para compartir el enlace")
    public static void selectLinkCompartir(String codigoPais, WebDriver driver) throws Exception {
        SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.Compartir, driver);
        checkAppearsModalShareSocial(codigoPais, driver);
    }    
	
	@Validation
	private static ChecksResult checkAppearsModalShareSocial(String codigoPais, WebDriver driver) {
		ChecksResult validations = ChecksResult.getNew();
	    int maxSecondsWait = 1;
	 	validations.add(
	 		"Aparece el modal para compartir a nivel social (lo esperamos hasta " + maxSecondsWait + " segundos) ",
	 		ModCompartirNew.isVisibleUntil(maxSecondsWait, driver), State.Defect);
		
	    boolean isPaisChina = (codigoPais.compareTo("720")==0);
	    for (IconSocial icon : IconSocial.values()) {
	        boolean isVisibleIcon = ModCompartirNew.isVisibleIcon(icon, driver);
	        if (isPaisChina != icon.isSpecificChina()) {
			 	validations.add(
			 		"No es visible el icono de " + icon,
			 		!isVisibleIcon, State.Warn);
	        }
	        else {
			 	validations.add(
			 		"Sí es visible el icono de " + icon,
			 		isVisibleIcon, State.Warn);
	        }
	    }        
	    
	    return validations;
	}
}
