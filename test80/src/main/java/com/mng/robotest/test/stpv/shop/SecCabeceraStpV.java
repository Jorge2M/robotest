package com.mng.robotest.test.stpv.shop;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.stpv.shop.landing.PageLandingStpV;

public class SecCabeceraStpV {

	private final SecCabecera secCabecera;
	private final WebDriver driver;
	private final Pais pais;
	
	private SecCabeceraStpV(Pais pais, Channel channel, AppEcom app, WebDriver driver) {
		this.pais = pais;
		this.secCabecera = SecCabecera.getNew(channel, app, driver);
		this.driver = driver;
	}
	
	public static SecCabeceraStpV getNew(Pais pais, Channel channel, AppEcom app, WebDriver driver) {
		return (new SecCabeceraStpV(pais, channel, app, driver));
	}
	
	public SecCabecera getSecCabecera() {
		return secCabecera;
	}

	@Step (
		description="Seleccionar el logo de Mango", 
		expected="Se accede a la página principal de la línea")
	public void selecLogo() throws Exception {
		secCabecera.clickLogoMango();
		(new PageLandingStpV(driver)).checkIsPage(5);
	}

	@Validation
	public ChecksTM validateIconoBolsa() {
		ChecksTM validations = ChecksTM.getNew();
		boolean isVisibleIconoBolsa = secCabecera.isInStateIconoBolsa(Visible, 2);
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
