package com.mng.robotest.test80.mango.test.stpv.shop.acceptcookies;

import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.conf.State;
import com.mng.robotest.test80.mango.test.pageobject.shop.acceptcookies.SectionCookies;

public class SectionCookiesStpV {

	private final SectionCookies sectionCookies;
	private final ModalSetCookiesStpV modalSetCookiesStpV;
	private final WebDriver driver;
	
	public SectionCookiesStpV(WebDriver driver) {
		sectionCookies = new SectionCookies(driver);
		modalSetCookiesStpV = new ModalSetCookiesStpV(driver);
		this.driver = driver;
	}
	
	@Step (
		description="Seleccionamos el botón \"Aceptar\" de la sección inferior de configuración de cookies",
		expected="Desaparece la sección de configuración de cookies")
	public void accept() {
		sectionCookies.accept();
		checkSectionInvisible();
	}
	
	@Step (
		description="Modificamos el contenido de la cookie <b>OptanonConsent</b> pasando los A1 a A0 en los groups C0002 a C0005",
		expected="El cambio se aplica correctamente")
	public void changeCookie_OptanonConsent() {
		Cookie cookie = driver.manage().getCookieNamed("OptanonConsent");
		driver.manage().deleteCookie(cookie);
		String newValueCookie = cookie.getValue()
			.replace("C0002%3A1", "C0002%3A0")
			.replace("C0003%3A1", "C0003%3A0")
			.replace("C0004%3A1", "C0004%3A0")
			.replace("C0005%3A1", "C0005%3A0");
		
		driver.manage().addCookie(
			  new Cookie.Builder(cookie.getName(), newValueCookie)
				.domain(cookie.getDomain())
				.expiresOn(cookie.getExpiry())
				.path(cookie.getPath())
				.isSecure(cookie.isSecure())
				.build());
	}
	
	@Step (
		description="Seleccionamos el botón \"Configurar cookies\" de la sección inferior de configuración de cookies",
		expected="Aparece el modal para la configuración de las cookies")
	public ModalSetCookiesStpV setCookies() {
		sectionCookies.setCookies();
		modalSetCookiesStpV.isVisible(2);
		return modalSetCookiesStpV;
	}
	
	@Validation (
		description="No es visible la sección inferior para la configuración de las cookies",
		level=State.Warn)
	public boolean checkSectionInvisible() {
		return (sectionCookies.isInvisible(2));
	}
	
}
