package com.mng.robotest.tests.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.ficha.pageobjects.ModCompartirNew;
import com.mng.robotest.tests.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew;
import com.mng.robotest.tests.domains.ficha.pageobjects.SecDetalleProduct;
import com.mng.robotest.tests.domains.ficha.pageobjects.ModCompartirNew.IconSocial;
import com.mng.robotest.tests.domains.ficha.pageobjects.SecProductDescrDevice.TypePanel;
import com.mng.robotest.tests.domains.menus.pageobjects.LineaWeb.LineaType;

import static com.github.jorge2m.testmaker.conf.State.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.LinksAfterBolsa.*;
import static com.mng.robotest.tests.domains.ficha.pageobjects.SecDetalleProduct.ItemBreadcrumb.*;

public class SecBolsaButtonAndLinksNewSteps extends StepBase {

	private final SecBolsaButtonAndLinksNew secBolsaButtonAndLinksNew = new SecBolsaButtonAndLinksNew();
	private final SecDetalleProduct secDetalleProductNew = new SecDetalleProduct();
	
	@Step (
		description="Seleccionar el link <b>Envío gratis a tienda</b>",
		expected="Aparece el modal con los datos a nivel de envío y devolución")
	public void selectEnvioYDevoluciones() {
		secBolsaButtonAndLinksNew.clickLinkAndWaitLoad(ENVIO_GRATIS_TIENDA);
		new ModEnvioYdevolNewSteps().checkIsVisible(2);
	}

	@Step (
		description="Seleccionar el link <b>Detalle del producto</b>",
		expected="Se scrolla hasta el apartado de \"Descripción\"")
	public void selectDetalleDelProducto(LineaType lineaType) {
		secBolsaButtonAndLinksNew.clickLinkAndWaitLoad(DETALLE_PRODUCTO);
		checkScrollToDescription();
		checkBreadCrumbs();
		if (TypePanel.KC_SAFETY.getListApps().contains(app) &&
			(lineaType==LineaType.NINA || lineaType==LineaType.NINO)) {
			checkKcSafety();
		}
	}
	
	@Validation (description="Se scrolla hasta el apartado de \"Descriptión\"")
	private boolean checkScrollToDescription() {
		return secDetalleProductNew.isVisibleUntil(3);
	}
	
	@Validation
	private ChecksTM checkBreadCrumbs() {
		var checks = ChecksTM.getNew();
	 	checks.add(
			"Figura el bloque de BreadCrumbs",
			secDetalleProductNew.isVisibleBreadcrumbs(0), WARN);
	 	
	 	checks.add(
			"Es visible el item " + LINEA,
			secDetalleProductNew.isVisibleItemBreadCrumb(LINEA), WARN);
	 	
	 	checks.add(
			"Es visible el item " + SUBGALERIA,
			secDetalleProductNew.isVisibleItemBreadCrumb(SUBGALERIA), WARN);
	 	
	 	checks.add(
			"Es visible el item " + GALERIA,
			secDetalleProductNew.isVisibleItemBreadCrumb(GALERIA), WARN);
	 	
	 	return checks;
	}
	
	@Validation (description="Aparece el bloque de \"KcSafety\"")
	private boolean checkKcSafety() {
		return secDetalleProductNew.isVisibleBlockKcSafety();
	}

	@Step (
		description="Seleccionar el link <b>Compartir</b>",
		expected="Aparece el modal para compartir el enlace")
	public void selectLinkCompartir(String codigoPais) {
		secBolsaButtonAndLinksNew.clickLinkAndWaitLoad(COMPARTIR);
		checkAppearsModalShareSocial(codigoPais);
	}
	
	@Validation
	private ChecksTM checkAppearsModalShareSocial(String codigoPais) {
		var checks = ChecksTM.getNew();
		int seconds = 2;
	 	checks.add(
	 		"Aparece el modal para compartir a nivel social " + getLitSecondsWait(seconds),
	 		new ModCompartirNew().isVisibleUntil(seconds));
		
		boolean isPaisChina = (codigoPais.compareTo("720")==0);
		for (IconSocial icon : IconSocial.values()) {
			boolean isVisibleIcon = new ModCompartirNew().isVisibleIcon(icon);
			if (isPaisChina != icon.isSpecificChina()) {
			 	checks.add(
			 		"No es visible el icono de " + icon,
			 		!isVisibleIcon, WARN);
			} else {
			 	checks.add(
			 		"Sí es visible el icono de " + icon,
			 		isVisibleIcon, WARN);
			}
		}

		return checks;
	}
}
