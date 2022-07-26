package com.mng.robotest.domains.ficha.steps;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.ficha.pageobjects.ModCompartirNew;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew;
import com.mng.robotest.domains.ficha.pageobjects.SecDetalleProductNew;
import com.mng.robotest.domains.ficha.pageobjects.ModCompartirNew.IconSocial;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.LinksAfterBolsa;
import com.mng.robotest.domains.ficha.pageobjects.SecDetalleProductNew.ItemBreadcrumb;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.test.beans.Linea.LineaType;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

public class SecBolsaButtonAndLinksNewSteps {

	@Step (
		description="Seleccionar el link <b>Envío gratis a tienda</b>",
		expected="Aparece el modal con los datos a nivel de envío y devolución")
	public static void selectEnvioYDevoluciones(WebDriver driver) throws Exception {
		SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.ENVIO_GRATIS_TIENDA, driver);
		(new ModEnvioYdevolNewSteps(driver)).checkIsVisible();
	}

	@Step (
		description="Seleccionar el link <b>Detalle del producto</b>",
		expected="Se scrolla hasta el apartado de \"Descripción\"")
	public static void selectDetalleDelProducto(AppEcom app, LineaType lineaType, WebDriver driver) throws Exception {
		SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.DETALLE_PRODUCTO, driver);
		checkScrollToDescription(driver);
		checkBreadCrumbs(driver);
		if (TypePanel.KC_SAFETY.getListApps().contains(app) &&
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
			"Es visible el item " + ItemBreadcrumb.LINEA,
			SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.LINEA, driver), State.Warn);
	 	validations.add(
			"Es visible el item " + ItemBreadcrumb.GRUPO,
			SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.GRUPO, driver), State.Warn);
	 	validations.add(
			"Es visible el item " + ItemBreadcrumb.GALERIA,
			SecDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.GALERIA, driver), State.Warn);
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
		SecBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.COMPARTIR, driver);
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
