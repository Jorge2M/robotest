package com.mng.robotest.test80.mango.test.stpv.shop;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.webdriver.wrapper.ElementPageFunctions.StateElem;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.cabecera.SecCabecera;

public class SecCabeceraStpV {

	private final SecCabecera secCabecera;
	private final Pais pais;
	
	private SecCabeceraStpV(Pais pais, Channel channel, AppEcom app, WebDriver driver) {
		this.pais = pais;
		this.secCabecera = SecCabecera.getNew(channel, app, driver);
	}
	
	public static SecCabeceraStpV getNew(Pais pais, Channel channel, AppEcom app, WebDriver driver) {
		return (new SecCabeceraStpV(pais, channel, app, driver));
	}

	@Step (
		description="Seleccionar el logo de Mango", 
		expected="Se accede a la página principal de la línea")
	public void selecLogo() throws Exception {
		secCabecera.clickLogoMango();
	}

	@Validation
	public ChecksResult validateIconoBolsa() {
		ChecksResult validations = ChecksResult.getNew();
		boolean isVisibleIconoBolsa = secCabecera.isInStateIconoBolsa(StateElem.Visible);
		if (pais.isVentaOnline()) {
			validations.add(
				"<b>Sí</b> es posible comprar (aparece la capa relacionada con la bolsa)",
				isVisibleIconoBolsa, State.Warn);
		} else {
			validations.add(
				"<b>No</b> es posible comprar (aparece la capa relacionada con la bolsa)",
				!isVisibleIconoBolsa, State.Warn);
		}
		return validations;
	}
	
	@Step (
		description="Establecer con visibilidad #{setVisible} el menú izquierdo de móvil",
		expected="El menú lateral se establece con visibilidad #{setVisible}")
	public void setVisibilityLeftMenuMobil(boolean setVisible) throws Exception {
		secCabecera.clickIconoMenuHamburguerMobil(setVisible);
	}
}
