package com.mng.robotest.domains.footer.steps;

import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.domains.footer.pageobjects.FactoryPageFromFooter;
import com.mng.robotest.domains.footer.pageobjects.PageFromFooter;
import com.mng.robotest.domains.footer.pageobjects.SecFooter;
import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.pageobject.shop.cabecera.SecCabecera;
import com.mng.robotest.test.steps.shop.modales.ModalCambioPaisSteps;

public class SecFooterSteps extends StepBase {
	
	private final SecFooter secFooter = new SecFooter();
	
	@Validation 
	public ChecksTM validaLinksFooter() throws Exception { 
		ChecksTM checks = ChecksTM.getNew();
		for (FooterLink footerLink : FooterLink.getFooterLinksFiltered(app, channel)) {
			checks.add(
				"Aparecen el link <b>" + footerLink + "</b> en el footer",
				secFooter.checkFooter(footerLink), State.Defect);
		}
		return checks;
	}

	@Step (
		description="Seleccionar el link del footer <b>#{typeFooter}</b>", 
		expected="Se redirige a la pantalla adecuada")
	public void clickLinkFooter(FooterLink typeFooter, boolean closeAtEnd) throws Exception { 
		String windowFatherHandle = secFooter.clickLinkAndGetWindowFatherHandle(typeFooter);
		checkPageCorrectAfterSelectLinkFooter(windowFatherHandle, typeFooter, closeAtEnd);
	}
	 
	@Validation
	private ChecksTM checkPageCorrectAfterSelectLinkFooter(String windowFatherHandle, FooterLink typeFooter, boolean closeAtEnd) {
		ChecksTM checks = ChecksTM.getNew();
		PageFromFooter pageObject = FactoryPageFromFooter.make(typeFooter);
		String windowActualHandle = driver.getWindowHandle();
		boolean newWindowInNewTab = (windowActualHandle.compareTo(windowFatherHandle)!=0);
		int maxSeconds = 5;
		try {
			checks.add(
				"Aparece la página <b>" + pageObject.getName() + "</b> (la esperamos hasta " + maxSeconds + " segundos)",
				pageObject.isPageCorrectUntil(maxSeconds), State.Warn);
			if (typeFooter.pageInNewTab()) {
				checks.add(
					"Aparece la página en una ventana aparte",
					newWindowInNewTab, State.Warn);		
			}
		}
		finally {
			if (typeFooter.pageInNewTab()) {
				if (closeAtEnd && newWindowInNewTab) {
					driver.close();
					driver.switchTo().window(windowFatherHandle);
				}
			}
		}		
		
		return checks;
	}

	 @Step (
		description="Se selecciona el link para el cambio de país", 
		expected="Aparece el modal para el cambio de país")
	 public void cambioPais(Pais newPais, IdiomaPais newIdioma) {
		 secFooter.clickLinkCambioPais();
		 ModalCambioPaisSteps modalCambioPaisSteps = new ModalCambioPaisSteps();
		 if (!modalCambioPaisSteps.validateIsVisible(3)) {
			 //Hay un problema según el cuál en ocasiones no funciona el click así que lo repetimos
			 secFooter.clickLinkCambioPais(); 
		 }
		 modalCambioPaisSteps.validateIsVisible(5);
		 try {
			 modalCambioPaisSteps.cambioPais(newPais, newIdioma);  
		 }
		 catch (Exception e) {
			 System.out.println(e);
		 }
	 }
	 
	 @Step (
		description="Hacer click en el cuadro de suscripción del footer",
		expected="Aparecen los textos legales de RGPD")
	public void validaRGPDFooter(Boolean clickRegister) throws Exception {
		if (!clickRegister) {
			SecCabecera.getNew(channel, app).clickLogoMango();
		}
		secFooter.clickFooterSuscripcion();
		if (dataTest.pais.getRgpd().equals("S")) {
			checkIsRGPDpresent(dataTest.pais.getCodigo_pais());
		} else {
			checkIsNotPresentRGPD(dataTest.pais.getCodigo_pais());
		}
	}
	 
	@Validation (
		description="El texto legal de RGPD <b>SI</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private boolean checkIsRGPDpresent(String codigoPais) {
 		return secFooter.isTextoLegalRGPDPresent();
	}
	
	@Validation (
		description="El texto legal de RGPD <b>NO</b> existe en el modal de suscripción para el pais #{codigoPais}",
		level=State.Defect)
	private boolean checkIsNotPresentRGPD(String codigoPais) {
 		return !secFooter.isTextoLegalRGPDPresent();
	}	

}