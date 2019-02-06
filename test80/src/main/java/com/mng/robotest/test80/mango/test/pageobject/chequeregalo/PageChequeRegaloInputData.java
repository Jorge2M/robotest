package com.mng.robotest.test80.mango.test.pageobject.chequeregalo;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.mng.robotest.test80.mango.test.pageobject.ElementPage;
import com.mng.robotest.test80.mango.test.pageobject.WebdrvWrapp;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.PageFromFooter;

@SuppressWarnings("javadoc")
public class PageChequeRegaloInputData extends WebdrvWrapp implements PageFromFooter {
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
        paginaForm(
                "//div[@class='gc-form']//h1[text()[contains(.,'Tarjeta Regalo')]]",
                "//div[@class='gc-header-title-container']//h1[text()[contains(.,'Tarjeta Regalo')]]"),
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
    
    public static String getXPathRadioImporte(Importe importe) {
        return ("//span[text()[contains(.,'" + importe.name().replace("euro", "") + "')]]");
    }

	@Override
	public String getName() {
		return "Cheques regalo";
	}
	
	@Override
	public boolean isPageCorrect(WebDriver driver) {
		int maxSecondsToWait = 2;
		return (isElementInStateUntil(ElementCheque.paginaForm, StateElem.Present, maxSecondsToWait, driver));
	}
    
    public static boolean isPresentInputImportes(WebDriver driver) {
        for (Importe importe : Importe.values()) {
            String xpathRadio = getXPathRadioImporte(importe);
            if (!isElementPresent(driver, By.xpath(xpathRadio)))
                return false;
        }
        
        return true;
    }

    public static void clickImporteCheque(WebDriver driver) throws  Exception {
        clickAndWaitLoad(driver, By.xpath(getXPathRadioImporte(Importe.euro50)));
    }

    public static void introducirTarjetaConsultaSaldo(WebDriver driver, String numTarjeta) throws Exception {
        inputDataInElement(ConsultaSaldo.numeroTarjeta, numTarjeta, driver);
        clickAndWait(ConsultaSaldo.validar, driver);
    }

    public static void clickButtonComprar(WebDriver driver) throws Exception {
        clickAndWait(ElementCheque.compraAhora, driver);
        
        //Existe un problema en Firefox-Gecko con este botón: a veces el 1er click no funciona así que ejecutamos un 2o 
        int maxSecondsToWait = 2;
        if (isElementInStateUntil(ElementCheque.compraAhora, StateElem.Present, maxSecondsToWait, driver))
        	clickAndWait(ElementCheque.compraAhora, driver);
    }

    public static void inputDataCheque(ChequeRegalo chequeRegalo, WebDriver driver) throws Exception{
        inputDataInElement(InputCheque.nombre, chequeRegalo.getNombre(), driver);
        inputDataInElement(InputCheque.apellidos, chequeRegalo.getApellidos(), driver);
        inputDataInElement(InputCheque.email, chequeRegalo.getEmail(), driver);
        inputDataInElement(InputCheque.repetirEmail, chequeRegalo.getEmail(), driver);
        inputDataInElement(InputCheque.mensaje, chequeRegalo.getMensaje(), driver);
    }
}
