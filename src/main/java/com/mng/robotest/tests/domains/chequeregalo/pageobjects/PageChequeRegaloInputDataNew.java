package com.mng.robotest.tests.domains.chequeregalo.pageobjects;

import org.openqa.selenium.By;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.footer.pageobjects.PageFromFooter;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;
import static com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts.CHEQUE_REGALO_PAGOS_LEGAL_TEXTS;

public class PageChequeRegaloInputDataNew extends PageChequeRegaloInputData implements PageFromFooter {

	private enum ConsultaSaldo implements ElementPage {
		IR("//button[@class='sg-t-btn' and text()[contains(.,'Consultar')]]"),
		NUMERO_TARJETA("//input[@id='cardNumber']"),
		CVV_TARJETA("//input[@id='cvvCode']"),
		VALIDAR("//button[text()[contains(.,'Validar')]]"),
		VOLVER("//button[@class='gc-header-back']"),
		CVV_INPUT_ERROR("//span[@class[contains(.,'gc-error-message--show')]]"),
		MENSAJE_TARJETA_SIN_SALDO("//span[@class[contains(.,'gc-error-message--show')] and text()[contains(.,'no tiene saldo')]]");

		By by;
		ConsultaSaldo(String xpath){
			by = By.xpath(xpath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}

	private enum ElementCheque implements ElementPage {
		TITULO(
			"//h1[text()='Tarjeta Regalo']",
			null),
		PAGINA_FORM(
			"//h1[text()[contains(.,'Tarjeta Regalo')]]",
			"//h1[text()[contains(.,'Tarjeta Regalo')]]"),
		COMPRAR_AHORA(
			"//button[text()[contains(.,'Comprar ahora')]]",
			null);

		By byDesktop;
		By byMobil;
		ElementCheque(String xpathDesktop, String xpathMobil) {
			byDesktop = By.xpath(xpathDesktop);
			if (xpathMobil!=null) {
				byMobil = By.xpath(xpathMobil);
			}
		}

		@Override
		public By getBy() {
			return byDesktop;
		}

		@Override
		public By getBy(Channel channel) {
			if (channel.isDevice() && this.byMobil != null) {
				return byMobil;
			}
			return byDesktop;
		}
	}

	private enum InputCheque implements ElementPage {
		DATA_PROOF("//p[text()[contains(.,'Rellena')]]"),
		NOMBRE("//input[@id='firstName']"),
		APELLIDOS("//input[@id='surnames']"),
		EMAIL("//input[@id='email']"),
		REPETIR_EMAIL("//input[@id='email2']"),
		MENSAJE("//textarea[@id='message']"),
		COMPRAR("//button[text()='Comprar ahora']");

		By by;
		InputCheque(String xpath) {
			by = By.xpath(xpath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}
	
	public PageChequeRegaloInputDataNew() {
		super(CHEQUE_REGALO_PAGOS_LEGAL_TEXTS);
	}
	
	public void clickConsultaSaldo() {
		click(ConsultaSaldo.IR.getBy()).exec();
	}
	
	public boolean isInputTarjetaVisible(int seconds) {
		return state(VISIBLE, ConsultaSaldo.NUMERO_TARJETA.getBy()).wait(seconds).check();
	}
	
	public void introducirTarjetaConsultaSaldo(String numTarjeta) {
		var inputNumTarjeta = driver.findElement(ConsultaSaldo.NUMERO_TARJETA.getBy());
		inputNumTarjeta.clear();
		inputNumTarjeta.sendKeys(numTarjeta);
		click(ConsultaSaldo.VALIDAR.getBy()).exec();
	}
	
	public void clickBotonValidar() {
		click(ConsultaSaldo.VALIDAR.getBy()).exec();
	}
	
	public boolean isVisibleCvv(int seconds) {
		return state(VISIBLE, ConsultaSaldo.VALIDAR.getBy()).wait(seconds).check();
	}
	
	public void introducirCvc(String cvvNumber) {
		var cvvTarjeta = driver.findElement(ConsultaSaldo.CVV_TARJETA.getBy());
		cvvTarjeta.clear();
		cvvTarjeta.sendKeys(cvvNumber);
		click(ConsultaSaldo.VALIDAR.getBy()).waitLoadPage(3).exec();
	}
	
	public boolean isTarjetaWithoutSaldo(int seconds) {
		return state(PRESENT, ConsultaSaldo.MENSAJE_TARJETA_SIN_SALDO.getBy()).wait(seconds).check();
	}
	
	public void clickBotonVolver() {
		click(ConsultaSaldo.VOLVER.getBy()).exec();
	}
	
	@Override
	public boolean isPageCorrectUntil(int seconds) {
		return (state(PRESENT, ElementCheque.PAGINA_FORM.getBy()).wait(seconds).check());
	}
	
	@Override
	public void clickImporteCheque(Importe importeToClick) {
		click(getXPathRadioImporte(importeToClick)).exec();
	}
	
	@Override
	public void clickComprarIni() {
		click(ElementCheque.COMPRAR_AHORA.getBy()).exec();
	}
	
	@Override
	public boolean isVisibleDataInput(int seconds) {
		return state(PRESENT, InputCheque.DATA_PROOF.getBy()).wait(seconds).check();
	}
	
	@Override
	public void inputDataCheque(ChequeRegalo chequeRegalo) {
		sendKeysWithRetry(chequeRegalo.getNombre(), InputCheque.NOMBRE.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getApellidos(), InputCheque.APELLIDOS.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), InputCheque.EMAIL.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), InputCheque.REPETIR_EMAIL.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getMensaje(), InputCheque.MENSAJE.getBy(), 2, driver);
	}
	
	@Override
	public void clickComprarFin(ChequeRegalo chequeRegalo) {
		click(ElementCheque.COMPRAR_AHORA.getBy()).exec();

		//Existe un problema en Firefox-Gecko muy extraño: a veces, después de seleccionar el botón "comprar ahora" 
		//te muestra error en todos los campos de input y no avanza a la siguiente página
		for (int i=0; i<10; i++) {
			if (!state(INVISIBLE, ElementCheque.COMPRAR_AHORA.getBy()).wait(3).check()) {
				inputDataCheque(chequeRegalo);
				click(ElementCheque.COMPRAR_AHORA.getBy()).exec();
			} else {
				break;
			}
		}
	}
	
	//----
	
	public static String getXPathRadioImporte(Importe importe) {
		return ("//span[@class='gc-text' and text()[contains(.,'" + importe.getAmmount() + "')]]");
	}

	@Override
	public String getName() {
		return "Cheques regalo";
	}
	
	public boolean isPresentInputImportes() {
		for (Importe importe : Importe.values()) {
			String xpathRadio = getXPathRadioImporte(importe);
			if (!state(PRESENT, xpathRadio).check()) {
				return false;
			}
		}
		return true;
	}
}
