package com.mng.robotest.test80.mango.test.pageobject.chequeregalo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.github.jorge2m.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.ElementPage;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;

public class PageChequeRegaloInputDataNew extends PageChequeRegaloInputData implements PageFromFooter {

	private enum ConsultaSaldo implements ElementPage {
		ir("//button[@class='sg-t-btn' and text()[contains(.,'Consultar')]]"),
		numeroTarjeta("//input[@id='cardNumber']"),
		cvvTarjeta("//input[@id='cvvCode']"),
		validar("//button[text()[contains(.,'Validar')]]"),
		volver("//button[@class='gc-header-back']"),
		cvvInputError("//span[@class[contains(.,'gc-error-message--show')]]"),
		mensajeTarjetaSinSaldo("//span[@class[contains(.,'gc-error-message--show')] and text()[contains(.,'no tiene saldo')]]");

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
		titulo(
			"//h1[text()='Tarjeta Regalo']",
			null),
		paginaForm(
			"//h1[text()[contains(.,'Tarjeta Regalo')]]",
			"//h1[text()[contains(.,'Tarjeta Regalo')]]"),
		compraAhora(
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
		dataProof("//p[text()[contains(.,'Rellena')]]"),
		nombre("//input[@id='firstName']"),
		apellidos("//input[@id='surnames']"),
		email("//input[@id='email']"),
		repetirEmail("//input[@id='email2']"),
		mensaje("//textarea[@id='message']"),
		comprar("//button[text()='Comprar ahora']");

		By by;
		InputCheque(String xpath) {
			by = By.xpath(xpath);
		}

		@Override
		public By getBy() {
			return by;
		}
	}
	
	public PageChequeRegaloInputDataNew(WebDriver driver) {
		super(driver);
	}
	
	//Función específica
	public void clickConsultaSaldo() {
		click(ConsultaSaldo.ir.getBy()).exec();
	}
	
	//Función específica
	public boolean isInputTarjetaVisible(int maxSeconds) {
		return state(Visible, ConsultaSaldo.numeroTarjeta.getBy()).wait(maxSeconds).check();
	}
	
	//Función específica
	public void introducirTarjetaConsultaSaldo(String numTarjeta) {
		WebElement inputNumTarjeta = driver.findElement(ConsultaSaldo.numeroTarjeta.getBy());
		inputNumTarjeta.clear();
		inputNumTarjeta.sendKeys(numTarjeta);
		click(ConsultaSaldo.validar.getBy()).exec();
	}
	
	//Función específica
	public void clickBotonValidar() {
		click(ConsultaSaldo.validar.getBy()).exec();
	}
	
	//Función específica
	public boolean isVisibleCvv(int maxSeconds) {
		return state(Visible, ConsultaSaldo.validar.getBy()).wait(maxSeconds).check();
	}
	
	//Función específica
	public void introducirCvc(String cvvNumber) throws Exception {
		WebElement cvvTarjeta = driver.findElement(ConsultaSaldo.cvvTarjeta.getBy());
		cvvTarjeta.clear();
		cvvTarjeta.sendKeys(cvvNumber);
		click(ConsultaSaldo.validar.getBy()).waitLoadPage(3).exec();
	}
	
	//Función específica
	public boolean isTarjetaWithoutSaldo(int maxSeconds) {
		return state(Present, ConsultaSaldo.mensajeTarjetaSinSaldo.getBy()).wait(maxSeconds).check();
	}
	
	//Función específica
	public void clickBotonVolver() {
		click(ConsultaSaldo.volver.getBy()).exec();
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSeconds) {
		return (state(Present, ElementCheque.paginaForm.getBy()).wait(maxSeconds).check());
	}
	
	@Override
	public void clickImporteCheque(Importe importeToClick) {
		By byElem = By.xpath(getXPathRadioImporte(importeToClick));
		click(byElem).exec();
	}
	
	@Override
	public void clickComprarIni() {
		click(ElementCheque.compraAhora.getBy()).exec();
	}
	
	@Override
	public boolean isVisibleDataInput(int maxSeconds) {
		return state(Present, InputCheque.dataProof.getBy()).wait(maxSeconds).check();
	}
	
	@Override
	public void inputDataCheque(ChequeRegalo chequeRegalo) {
		sendKeysWithRetry(chequeRegalo.getNombre(), InputCheque.nombre.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getApellidos(), InputCheque.apellidos.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), InputCheque.email.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getEmail(), InputCheque.repetirEmail.getBy(), 2, driver);
		sendKeysWithRetry(chequeRegalo.getMensaje(), InputCheque.mensaje.getBy(), 2, driver);
	}
	
	@Override
	public void clickComprarFin(ChequeRegalo chequeRegalo) {
		click(ElementCheque.compraAhora.getBy()).exec();

		//Existe un problema en Firefox-Gecko muy extraño: a veces, después de seleccionar el botón "comprar ahora" 
		//te muestra error en todos los campos de input y no avanza a la siguiente página
		for (int i=0; i<10; i++) {
			if (!state(Invisible, ElementCheque.compraAhora.getBy()).wait(3).check()) {
				inputDataCheque(chequeRegalo);
				click(ElementCheque.compraAhora.getBy()).exec();
			} else {
				break;
			}
		}
	}
	
	//----
	
	public static String getXPathRadioImporte(Importe importe) {
		return ("//span[@class='gc-text' and text()[contains(.,'" + importe.getImporte() + "')]]");
	}

	@Override
	public String getName() {
		return "Cheques regalo";
	}
	
	public boolean isPresentInputImportes() {
		for (Importe importe : Importe.values()) {
			String xpathRadio = getXPathRadioImporte(importe);
			if (!state(Present, By.xpath(xpathRadio)).check()) {
				return false;
			}
		}
		return true;
	}
}
