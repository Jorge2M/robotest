package com.mng.robotest.test80.mango.test.stpv.shop.ficha;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
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
		description="Seleccionar el link <b>Envío gratis a tienda</b>",
		expected="Aparece el modal con los datos a nivel de envío y devolución")
	public static void selectEnvioYDevoluciones(WebDriver driver) throws Exception {
		SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.EnvioGratisTienda, driver);
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
	private static ChecksTM checkBreadCrumbs(WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
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
	private static ChecksTM checkAppearsModalShareSocial(String codigoPais, WebDriver driver) {
		ChecksTM validations = ChecksTM.getNew();
	    int maxSeconds = 1;
	 	validations.add(
	 		"Aparece el modal para compartir a nivel social (lo esperamos hasta " + maxSeconds + " segundos) ",
	 		ModCompartirNew.isVisibleUntil(maxSeconds, driver), State.Defect);
		
		boolean isPaisChina = (codigoPais.compareTo("720")==0);
		for (IconSocial icon : IconSocial.values()) {
			boolean isVisibleIcon = ModCompartirNew.isVisibleIcon(icon, driver);
			if (isPaisChina != icon.isSpecificChina()) {
			 	validations.add(
			 		"No es visible el icono de " + icon,
			 		!isVisibleIcon, State.Warn);
			} else {
			 	validations.add(
			 		"Sí es visible el icono de " + icon,
			 		isVisibleIcon, State.Warn);
			}
		}

		return validations;
	}
}
