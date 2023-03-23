package com.mng.robotest.domains.ficha.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.mng.robotest.domains.base.StepBase;
import com.mng.robotest.domains.ficha.pageobjects.ModCompartirNew;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew;
import com.mng.robotest.domains.ficha.pageobjects.SecDetalleProduct;
import com.mng.robotest.domains.ficha.pageobjects.ModCompartirNew.IconSocial;
import com.mng.robotest.domains.ficha.pageobjects.SecBolsaButtonAndLinksNew.LinksAfterBolsa;
import com.mng.robotest.domains.ficha.pageobjects.SecDetalleProduct.ItemBreadcrumb;
import com.mng.robotest.domains.ficha.pageobjects.SecProductDescrOld.TypePanel;
import com.mng.robotest.domains.transversal.menus.pageobjects.LineaWeb.LineaType;

import static com.github.jorge2m.testmaker.conf.State.*;

public class SecBolsaButtonAndLinksNewSteps extends StepBase {

	private final SecBolsaButtonAndLinksNew secBolsaButtonAndLinksNew = new SecBolsaButtonAndLinksNew();
	private final SecDetalleProduct secDetalleProductNew = new SecDetalleProduct();
	
	@Step (
		description="Seleccionar el link <b>Envío gratis a tienda</b>",
		expected="Aparece el modal con los datos a nivel de envío y devolución")
	public void selectEnvioYDevoluciones() {
		secBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.ENVIO_GRATIS_TIENDA);
		new ModEnvioYdevolNewSteps().checkIsVisible();
	}

	@Step (
		description="Seleccionar el link <b>Detalle del producto</b>",
		expected="Se scrolla hasta el apartado de \"Descripción\"")
	public void selectDetalleDelProducto(LineaType lineaType) {
		secBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.DETALLE_PRODUCTO);
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
			secDetalleProductNew.isVisibleBreadcrumbs(0), Warn);
	 	
	 	checks.add(
			"Es visible el item " + ItemBreadcrumb.LINEA,
			secDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.LINEA), Warn);
	 	
	 	checks.add(
			"Es visible el item " + ItemBreadcrumb.SUBGALERIA,
			secDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.SUBGALERIA), Warn);
	 	
	 	checks.add(
			"Es visible el item " + ItemBreadcrumb.GALERIA,
			secDetalleProductNew.isVisibleItemBreadCrumb(ItemBreadcrumb.GALERIA), Warn);
	 	
	 	return checks;
	}
	
	@Validation (description="Aparece el bloque de \"KcSafety\"")
	private boolean checkKcSafety() {
		return (secDetalleProductNew.isVisibleBlockKcSafety());
	}

	@Step (
		description="Seleccionar el link <b>Compartir</b>",
		expected="Aparece el modal para compartir el enlace")
	public void selectLinkCompartir(String codigoPais) {
		secBolsaButtonAndLinksNew.clickLinkAndWaitLoad(LinksAfterBolsa.COMPARTIR);
		checkAppearsModalShareSocial(codigoPais);
	}
	
	@Validation
	private ChecksTM checkAppearsModalShareSocial(String codigoPais) {
		var checks = ChecksTM.getNew();
		int seconds = 1;
	 	checks.add(
	 		"Aparece el modal para compartir a nivel social (lo esperamos hasta " + seconds + " segundos) ",
	 		new ModCompartirNew().isVisibleUntil(seconds));
		
		boolean isPaisChina = (codigoPais.compareTo("720")==0);
		for (IconSocial icon : IconSocial.values()) {
			boolean isVisibleIcon = new ModCompartirNew().isVisibleIcon(icon);
			if (isPaisChina != icon.isSpecificChina()) {
			 	checks.add(
			 		"No es visible el icono de " + icon,
			 		!isVisibleIcon, Warn);
			} else {
			 	checks.add(
			 		"Sí es visible el icono de " + icon,
			 		isVisibleIcon, Warn);
			}
		}

		return checks;
	}
}
