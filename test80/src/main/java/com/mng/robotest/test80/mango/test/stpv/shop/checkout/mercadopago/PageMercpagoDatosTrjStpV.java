package com.mng.robotest.test80.mango.test.stpv.shop.checkout.mercadopago;

import org.openqa.selenium.WebDriver;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.mercadopago.PageMercpagoDatosTrjMobil;

public class PageMercpagoDatosTrjStpV {
	
	public static class InputData {
		private String numTarjeta;
		private String banco;
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
		public String getBanco() {
			return banco;
		}
		public void setBanco(String banco) {
			this.banco = banco;
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
    
    public static void validaIsPage(Channel channel, WebDriver driver) {
        switch (channel) {
        case movil_web:
            validaIsPageMobil(driver);
            break;
        default:
        case desktop:
        	int maxSecondsWait = 5;
            validaIsPageDesktop(maxSecondsWait, driver);
        }
    }
    
    @Validation (
    	description="Estamos en la página de introducción de los datos de la tarjeta",
    	level=State.Defect)
    public static boolean validaIsPageMobil(WebDriver driver) {
    	return (PageMercpagoDatosTrjMobil.isPage(driver));
    }
    
    @Validation (
    	description="Estamos en la página de introducción del CVC (la esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Defect)
    public static boolean validaIsPageDesktop(int maxSecondsWait, WebDriver driver) {
    	return (PageMercpagoDatosTrjDesktop.isPageUntil(maxSecondsWait, driver));
    }    
    
    public static void inputNumTarjeta(String numTarjeta, Channel channel, WebDriver driver) throws Exception {
        switch (channel) {
        case movil_web:
            inputNumTarjetaMobil(numTarjeta, driver);
            break;
        default:
        case desktop:
            inputNumTarjetaDesktop(numTarjeta, driver);
            break;
        }
    }
    
    @Step (
    	description="Introducir el número de tarjeta #{numTarjeta}", 
        expected="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa")
    public static void inputNumTarjetaMobil(String numTarjeta, WebDriver driver) throws Exception {
        PageMercpagoDatosTrjMobil.sendNumTarj(numTarjeta, driver);
            
        //Validaciones
        int maxSecondsWait = 2;
        isWrapperTarjetaVisibleVisaDataMobil(maxSecondsWait, driver);
    }
    
    @Validation (
    	description="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean isWrapperTarjetaVisibleVisaDataMobil(int maxSecondsWait, WebDriver driver) {
	     return (PageMercpagoDatosTrjMobil.isActiveWrapperVisaUntil(maxSecondsWait, driver));
    }
    
    @Step (
    	description="Introducir el número de tarjeta #{numTarjeta})", 
        expected="Aparece el icono de Visa a la derecha de la tarjeta introducida")
    public static void inputNumTarjetaDesktop(String numTarjeta, WebDriver driver) throws Exception {
    	PageMercpagoDatosTrjDesktop.sendNumTarj(numTarjeta, driver);
            
        //Validaciones
    	int maxSecondsWait = 2;
    	isVisaIconAtRightTrjDesktop(maxSecondsWait, driver);
    }
    
    @Validation (
    	description="Aparece el icono de Visa a la derecha de la tarjeta (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean isVisaIconAtRightTrjDesktop(int maxSecondsWait, WebDriver driver) {
    	return (PageMercpagoDatosTrjDesktop.isVisibleVisaIconUntil(maxSecondsWait, driver));
    }
    
    public static void inputDataAndPay(InputData inputData, Channel channel, WebDriver driver) 
    throws Exception {
        switch (channel) {
        case movil_web:
            inputDataMobil(inputData, driver);
            clickButtonPayMobil(driver);
            break;
        default:
        case desktop:
            inputDataAndPayDesktop(inputData, driver);
            break;
        }
    }
    
    @Step (
    	description=
    		"Introducir la fecha de vencimiento (#{inputData.getMesVencimiento()} / #{inputData.getAnyVencimiento()}), " + 
    	     "el banco #{inputData.getBanco()}, " +   
    	     "el security code #{inputData.codigoSeguridad} " + 
    	     "y confirmar", 
    	expected=
    		"Aparece la página de resultado")
    public static void inputDataMobil(InputData inputData, WebDriver driver) throws Exception {
        String fechaVencimiento = inputData.getMesVencimiento() + "/" + inputData.getAnyVencimiento();        
        PageMercpagoDatosTrjMobil.sendCaducidadTarj(fechaVencimiento, driver);
        PageMercpagoDatosTrjMobil.sendCVC(inputData.codigoSeguridad, driver);            
            
        //Validaciones
        int maxSecondsWait = 2;
        isEnabledButtonNextMobil(maxSecondsWait, driver);
    }
    
    @Validation (
    	description="Aparece activado el botón \"Next\" para continuar con el pago (lo esperamos hasta #{maxSecondsWait} segundos)",
    	level=State.Warn)
    private static boolean isEnabledButtonNextMobil(int maxSecondsWait, WebDriver driver) {
    	return (PageMercpagoDatosTrjMobil.isClickableButtonNextPayUntil(maxSecondsWait, driver));
	}
    
    @Step (
    	description="Seleccionar el botón \"Next\" para pagar", 
        expected="Aparece la página de resultado")
    public static void clickButtonPayMobil(WebDriver driver) throws Exception {
    	PageMercpagoDatosTrjMobil.clickButtonNextPay(driver);
            
        //Validaciones
    	int maxSecondsWait = 5;
        PageMercpagoConfStpV.validaIsPageUntil(maxSecondsWait, Channel.movil_web, driver);
    }    
    
    @Step (
    	description=
    		"Introducir la fecha de vencimiento (#{inputData.getMesVencimiento()} / #{inputData.getAnyVencimiento()}, " + 
            "security code (#{inputData.getCodigoSeguridad(), " + 
            "Banco (#{inputData.getBanco()}) " +
            "y confirmar", 
        expected=
        	"Aparece la página de resultado")
    public static void inputDataAndPayDesktop(InputData inputData, WebDriver driver) 
    throws Exception {
        String fechaVencimiento = inputData.mesVencimiento + "/" + inputData.anyVencimiento;     
        PageMercpagoDatosTrjDesktop.sendCaducidadTarj(fechaVencimiento, driver);
        PageMercpagoDatosTrjDesktop.sendCVC(inputData.codigoSeguridad, driver);
        PageMercpagoDatosTrjDesktop.selectBanco(inputData.banco, driver);
        PageMercpagoDatosTrjDesktop.clickBotonContinuar(driver);

        //Validaciones
        int maxSecondsWait = 30;
        PageMercpagoConfStpV.validaIsPageUntil(maxSecondsWait, Channel.desktop, driver);
    }
}
