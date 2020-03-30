package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrj;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjMobil;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.PageResultPagoStpV;

public class PageMercpagoDatosTrjStpV {
	
	private final PageMercpagoDatosTrj pageMercpagoDatosTrj;
	private Channel channel;
	private WebDriver driver;
	
	private PageMercpagoDatosTrjStpV(Channel channel, WebDriver driver) {
		this.channel = channel;
		this.driver = driver;
		this.pageMercpagoDatosTrj = PageMercpagoDatosTrj.newInstance(channel, driver);
	}
	
	public static PageMercpagoDatosTrjStpV newInstance(Channel channel, WebDriver driver) {
		return (new PageMercpagoDatosTrjStpV(channel, driver));
	}
	
	public PageMercpagoDatosTrj getPageObject() {
		return this.pageMercpagoDatosTrj;
	}

    @Validation (
    	description="Estamos en la página de introducción de los datos de la tarjeta (la esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Defect)
    public boolean validaIsPage(int maxSecondsWait) {
    	return (pageMercpagoDatosTrj.isPageUntil(maxSecondsWait));
    }    
    
    public void inputNumTarjeta(String numTarjeta) throws Exception {
        switch (channel) {
        case movil_web:
            inputNumTarjetaMobil(numTarjeta);
            break;
        default:
        case desktop:
            inputNumTarjetaDesktop(numTarjeta);
            break;
        }
    }
    
    @Step (
    	description="Introducir el número de tarjeta #{numTarjeta}", 
        expected="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa")
    public void inputNumTarjetaMobil(String numTarjeta) throws Exception {
        pageMercpagoDatosTrj.sendNumTarj(numTarjeta);
        isWrapperTarjetaVisibleVisaDataMobil(2);
    }
    
    @Validation (
    	description="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private boolean isWrapperTarjetaVisibleVisaDataMobil(int maxSecondsWait) {
	     return (((PageMercpagoDatosTrjMobil)pageMercpagoDatosTrj).isActiveWrapperVisaUntil(maxSecondsWait));
    }
    
    @Step (
    	description="Introducir el número de tarjeta #{numTarjeta})", 
        expected="Aparece el icono de Visa a la derecha de la tarjeta introducida")
    public void inputNumTarjetaDesktop(String numTarjeta) throws Exception {
    	pageMercpagoDatosTrj.sendNumTarj(numTarjeta);
    	isVisaIconAtRightTrjDesktop(2);
    }
    
    @Validation (
    	description="Aparece el icono de Visa a la derecha de la tarjeta (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private boolean isVisaIconAtRightTrjDesktop(int maxSecondsWait) {
    	return (((PageMercpagoDatosTrjDesktop)pageMercpagoDatosTrj).isVisibleVisaIconUntil(maxSecondsWait));
    }
    
    public void inputDataAndPay(InputData inputData) throws Exception {
    	inputData(inputData);
    	clickButtonForPay(false);
    }
    
    public void inputCvcAndPay(String cvc) throws Exception {
    	inputCvc(cvc);
    	clickButtonForPay(true);
    }
    
    @Step (
    	description="Introducir el CVC <b>#{cvc}</b>",
    	expected="El cvc se informa correctamente")
    public void inputCvc(String cvc) {
    	pageMercpagoDatosTrj.sendCvc(cvc);
    }
    
    private void inputData(InputData inputData) throws Exception {
        switch (channel) {
        case movil_web:
            inputDataMobil(inputData);
            break;
        default:
        case desktop:
            inputDataDesktop(inputData);
            break;
        }
    }
    
    private void clickButtonForPay(boolean afterTrjGuardada) throws Exception {
        switch (channel) {
        case movil_web:
            clickButtonForPayMobil(afterTrjGuardada);
            break;
        default:
        case desktop:
            clickButtonForPayDesktop(afterTrjGuardada);
            break;
        }
    }
    
    @Step (
    	description=
    		"Introducir la fecha de vencimiento (#{inputData.getMesVencimiento()} / #{inputData.getAnyVencimiento()}), " + 
    	     "el banco #{inputData.getBanco()}, " +   
    	     "el security code #{inputData.getCodigoSeguridad()} " + 
    	     "y confirmar", 
    	expected=
    		"Aparece la página de resultado")
    public void inputDataMobil(InputData inputData) throws Exception {
        String fechaVencimiento = inputData.getMesVencimiento() + "/" + inputData.getAnyVencimiento();        
        pageMercpagoDatosTrj.sendCaducidadTarj(fechaVencimiento);
        pageMercpagoDatosTrj.sendCvc(inputData.codigoSeguridad);            
        isEnabledButtonNextMobil(2);
    }
    
    @Validation (
    	description="Aparece activado el botón \"Next\" para continuar con el pago (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private boolean isEnabledButtonNextMobil(int maxSecondsWait) {
    	return (((PageMercpagoDatosTrjMobil)pageMercpagoDatosTrj).isClickableButtonNextPayUntil(maxSecondsWait));
	}
    
    @Step (
    	description="Seleccionar el botón \"Next\" para pagar", 
        expected="Aparece la página de resultado")
    public void clickButtonForPayMobil(boolean afterTrjGuardada) {
    	((PageMercpagoDatosTrjMobil)pageMercpagoDatosTrj).clickButtonForPay();
    	if (afterTrjGuardada) {
    		PageResultPagoStpV.validaIsPageUntil(30, channel, driver);
    	}
    	else {
    		PageMercpagoConfStpV.validaIsPageUntil(5, Channel.movil_web, driver);
    	}
    }    
    
    @Step (
    	description=
    		"Introducir la fecha de vencimiento (#{inputData.getMesVencimiento()} / #{inputData.getAnyVencimiento()}, " + 
            "security code (#{inputData.getCodigoSeguridad()}, " + 
            "Banco (#{inputData.getBanco()}) " +
            "y confirmar", 
        expected=
        	"Los campos se informan correctamente")
    public void inputDataDesktop(InputData inputData) 
    throws Exception {
        String fechaVencimiento = inputData.mesVencimiento + "/" + inputData.anyVencimiento;    
        PageMercpagoDatosTrjDesktop pageDesktop = (PageMercpagoDatosTrjDesktop)pageMercpagoDatosTrj;
        pageDesktop.sendCaducidadTarj(fechaVencimiento);
        pageDesktop.sendCvc(inputData.codigoSeguridad);
    }
    
    @Step (
    	description= "Pulsar el botón Continuar/Pagar para efectuar el pago", 
        expected= "Aparece la página de resultado")
    public void clickButtonForPayDesktop(boolean afterTrjGuardada) {  
        PageMercpagoDatosTrjDesktop pageDesktop = (PageMercpagoDatosTrjDesktop)pageMercpagoDatosTrj;
        pageDesktop.clickBotonForContinue();
        if (afterTrjGuardada) {
        	PageResultPagoStpV.validaIsPageUntil(30, channel, driver);
        }
        else {
        	PageMercpagoConfStpV.validaIsPageUntil(10, Channel.desktop, driver);
        }
    }
    
	public static class InputData {
		private String numTarjeta;
		private String nombreYApellido;
		private String mesVencimiento;
		private String anyVencimiento;
		private String codigoSeguridad;
		
		public String getNumTarjeta() {
			return numTarjeta;
		}
		public void setNumTarjeta(String numTarjeta) {
			this.numTarjeta = numTarjeta;
		}
		public String getNombreYApellido() {
			return nombreYApellido;
		}
		public void setNombreYApellido(String nombreYApellido) {
			this.nombreYApellido = nombreYApellido;
		}
		public String getMesVencimiento() {
			return mesVencimiento;
		}
		public void setMesVencimiento(String mesVencimiento) {
			this.mesVencimiento = mesVencimiento;
		}
		public String getAnyVencimiento() {
			return anyVencimiento;
		}
		public void setAnyVencimiento(String anyVencimiento) {
			this.anyVencimiento = anyVencimiento;
		}
		public String getCodigoSeguridad() {
			return codigoSeguridad;
		}
		public void setCodigoSeguridad(String codigoSeguridad) {
			this.codigoSeguridad = codigoSeguridad;
		}
	}
}
