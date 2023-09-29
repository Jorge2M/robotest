package com.mng.robotest.tests.domains.compra.payments.mercadopago.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.tests.domains.base.StepBase;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpagoDatosTrj;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpagoDatosTrjDesktop;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.pageobjects.PageMercpagoDatosTrjMobil;
import com.mng.robotest.tests.domains.compra.steps.PageResultPagoSteps;

import static com.github.jorge2m.testmaker.conf.State.*;

public class PageMercpagoDatosTrjSteps extends StepBase {
	
	private final PageMercpagoDatosTrj pageMercpagoDatosTrj;
	
	public PageMercpagoDatosTrjSteps() {
		this.pageMercpagoDatosTrj = PageMercpagoDatosTrj.newInstance(channel);
	}
	
	public PageMercpagoDatosTrj getPageObject() {
		return this.pageMercpagoDatosTrj;
	}

	@Validation (
		description="Estamos en la página de introducción de los datos de la tarjeta " + SECONDS_WAIT)
	public boolean validaIsPage(int seconds) {
		return (pageMercpagoDatosTrj.isPageUntil(seconds));
	}	
	
	public void inputNumTarjeta(String numTarjeta) {
		switch (channel) {
		case mobile:
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
	public void inputNumTarjetaMobil(String numTarjeta) {
		pageMercpagoDatosTrj.sendNumTarj(numTarjeta);
		isWrapperTarjetaVisibleVisaDataMobil(2);
	}
	
	@Validation (
		description="El \"Wrapper\" de la tarjeta se hace visible con los datos de la Visa " + SECONDS_WAIT,
		level=Warn)
	private boolean isWrapperTarjetaVisibleVisaDataMobil(int seconds) {
		 return (((PageMercpagoDatosTrjMobil)pageMercpagoDatosTrj).isActiveWrapperVisaUntil(seconds));
	}
	
	@Step (
		description="Introducir el número de tarjeta #{numTarjeta})", 
		expected="Aparece el icono de Visa a la derecha de la tarjeta introducida")
	public void inputNumTarjetaDesktop(String numTarjeta) {
		pageMercpagoDatosTrj.sendNumTarj(numTarjeta);
		isVisaIconAtRightTrjDesktop(2);
	}
	
	@Validation (
		description="Aparece el icono de Visa a la derecha de la tarjeta " + SECONDS_WAIT,
		level=Warn)
	private boolean isVisaIconAtRightTrjDesktop(int seconds) {
		return (((PageMercpagoDatosTrjDesktop)pageMercpagoDatosTrj).isVisibleVisaIconUntil(seconds));
	}
	
	public void inputDataAndPay(InputData inputData) {
		inputData(inputData);
		clickButtonForPay(false);
	}
	
	public void inputCvcAndPay(String cvc) {
		inputCvc(cvc);
		clickButtonForPay(true);
	}
	
	@Step (
		description="Introducir el CVC <b>#{cvc}</b>",
		expected="El cvc se informa correctamente")
	public void inputCvc(String cvc) {
		pageMercpagoDatosTrj.sendCvc(cvc);
	}
	
	private void inputData(InputData inputData) {
		switch (channel) {
		case mobile:
			inputDataMobil(inputData);
			break;
		default:
		case desktop:
			inputDataDesktop(inputData);
			break;
		}
	}
	
	private void clickButtonForPay(boolean afterTrjGuardada) {
		switch (channel) {
		case mobile:
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
	public void inputDataMobil(InputData inputData) {
		String fechaVencimiento = inputData.getMesVencimiento() + "/" + inputData.getAnyVencimiento();		
		pageMercpagoDatosTrj.sendCaducidadTarj(fechaVencimiento);
		pageMercpagoDatosTrj.sendCvc(inputData.codigoSeguridad);			
		isEnabledButtonNextMobil(2);
	}
	
	@Validation (
		description="Aparece activado el botón \"Next\" para continuar con el pago " + SECONDS_WAIT,
		level=Warn)
	private boolean isEnabledButtonNextMobil(int seconds) {
		return (((PageMercpagoDatosTrjMobil)pageMercpagoDatosTrj).isClickableButtonNextPayUntil(seconds));
	}
	
	@Step (
		description="Seleccionar el botón \"Next\" para pagar", 
		expected="Aparece la página de resultado")
	public void clickButtonForPayMobil(boolean afterTrjGuardada) {
		((PageMercpagoDatosTrjMobil)pageMercpagoDatosTrj).clickButtonForPay();
		if (afterTrjGuardada) {
			new PageResultPagoSteps().validaIsPageUntil(30);
		}
		else {
			new PageMercpagoConfSteps().validaIsPageUntil(5);
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
	public void inputDataDesktop(InputData inputData) {
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
			new PageResultPagoSteps().validaIsPageUntil(30);
		}
		else {
			new PageMercpagoConfSteps().validaIsPageUntil(10);
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
