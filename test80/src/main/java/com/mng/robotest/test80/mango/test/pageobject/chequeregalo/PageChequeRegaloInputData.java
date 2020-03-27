package com.mng.robotest.test80.mango.test.pageobject.chequeregalo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.mng.testmaker.conf.Channel;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.testmaker.service.webdriver.pageobject.PageObjTM;
import com.mng.testmaker.service.webdriver.wrapper.ElementPage;

import static com.mng.testmaker.service.webdriver.pageobject.StateElement.State.*;

import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;

public class PageChequeRegaloInputData extends PageObjTM implements PageFromFooter {
	
    public enum Importe {euro25, euro50, euro100, euro150, euro200, euro250}

    public enum ConsultaSaldo implements ElementPage {
        ir("//button[@class='sg-t-btn' and text()[contains(.,'Consultar')]]"),
        numeroTarjeta("//input[@id='cardNumber']"),
        cvvTarjeta("//input[@id='cvvCode']"),
        validar("//button[text()[contains(.,'Validar')]]"),
        volver("//button[@class='gc-header-back']"),
        cvvInputError("//span[@class[contains(.,'gc-error-message--show')]]"),
        mensajeTarjetaSinSaldo("//span[@class[contains(.,'gc-error-message--show')] and text()[contains(.,'no tiene saldo')]]");

        String element;

        ConsultaSaldo(String element){
            this.element = element;
        }

        @Override
        public String getXPath() {
            return this.element;
        }
    }

    public enum ElementCheque implements ElementPage {
    	titulo(
    		"//h1[text()='Tarjeta Regalo']",
    		null),
        paginaForm(
        	"//h1[text()[contains(.,'Tarjeta Regalo')]]",
        	"//h1[text()[contains(.,'Tarjeta Regalo')]]"),
        compraAhora(
        	"//button[text()[contains(.,'Comprar ahora')]]",
        	null);

        String element;
        String mobile_element;
        ElementCheque(String element, String mobile_element) {
            this.element = element;
            this.mobile_element = mobile_element;
        }

        @Override
        public String getXPath() {
            return this.element;
        }

        @Override
        public String getXPath(Channel channel) {
            if (channel == Channel.movil_web && this.mobile_element != null) {
                return this.mobile_element;
            }
            return this.element;
        }
    }

    public enum InputCheque implements ElementPage {
        dataProof("//p[text()[contains(.,'Rellena')]]"),
        nombre("//input[@id='firstName']"),
        apellidos("//input[@id='surnames']"),
        email("//input[@id='email']"),
        repetirEmail("//input[@id='email2']"),
        mensaje("//textarea[@id='message']"),
        comprar("//button[text()='Comprar ahora']");

        String element;

        InputCheque(String element) {
            this.element = element;
        }

        @Override
        public String getXPath() {
            return this.element;
        }
    }
    
    public PageChequeRegaloInputData(WebDriver driver) {
    	super(driver);
    }
    
    public static String getXPathRadioImporte(Importe importe) {
        return ("//span[text()[contains(.,'" + importe.name().replace("euro", "") + "')]]");
    }

	@Override
	public String getName() {
		return "Cheques regalo";
	}
	
	@Override
	public boolean isPageCorrectUntil(int maxSecondsWait) {
		return (isElementInStateUntil(ElementCheque.paginaForm, Present, maxSecondsWait, driver));
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

    public void clickImporteCheque(Importe importeToClick) throws  Exception {
        clickAndWaitLoad(driver, By.xpath(getXPathRadioImporte(importeToClick)));
    }

    public void introducirTarjetaConsultaSaldo(String numTarjeta) throws Exception {
    	WebElement inputNumTarjeta = driver.findElement(By.xpath(ConsultaSaldo.numeroTarjeta.getXPath()));
    	inputNumTarjeta.clear();
    	inputNumTarjeta.sendKeys(numTarjeta);
        //inputDataInElement(ConsultaSaldo.numeroTarjeta, numTarjeta, driver);
        clickAndWait(ConsultaSaldo.validar, driver);
    }
    
	public void introducirCvc(String cvvNumber) throws Exception {
		WebElement cvvTarjeta = driver.findElement(By.xpath(ConsultaSaldo.cvvTarjeta.getXPath()));
		cvvTarjeta.clear();
		cvvTarjeta.sendKeys(cvvNumber);
		clickAndWait(ConsultaSaldo.validar, 3, driver);
	}

    public void clickButtonComprar(ChequeRegalo chequeRegalo) throws Exception {
        clickAndWait(ElementCheque.compraAhora, driver);
        
        //Existe un problema en Firefox-Gecko muy extraño: a veces, después de seleccionar el botón "comprar ahora" 
        //te muestra error en todos los campos de input y no avanza a la siguiente página
        for (int i=0; i<10; i++) {
        	if (!PageObjTM.state(Invisible, By.xpath(ElementCheque.compraAhora.getXPath()), driver)
        		.wait(3).check()) {
	        	inputDataCheque(chequeRegalo);
        		clickAndWait(ElementCheque.compraAhora, driver);
	        } else {
	        	break;
	        }
        }
    }
    
    public void inputDataCheque(ChequeRegalo chequeRegalo) throws Exception {
        inputDataInElement(InputCheque.nombre, chequeRegalo.getNombre(), driver);
        inputDataInElement(InputCheque.apellidos, chequeRegalo.getApellidos(), driver);
        inputDataInElement(InputCheque.email, chequeRegalo.getEmail(), driver);
        inputDataInElement(InputCheque.repetirEmail, chequeRegalo.getEmail(), driver);
        inputDataInElement(InputCheque.mensaje, chequeRegalo.getMensaje(), driver);
    }
}
