package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.TestCaseData;
import com.mng.robotest.test80.arq.annotations.step.Step;
import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.annotations.validation.Validation;
import com.mng.robotest.test80.arq.utils.controlTest.fmwkTest;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen; 

@SuppressWarnings({"static-access"})
public class SecStoreCreditStpV { 
    static Logger pLogger = LogManager.getLogger(fmwkTest.log4jLogger);
    
    final static String tagNombrePago = "@TagNombrePago";
    @Step (
    	description="Revisamos el bloque de \"Saldo en cuenta\"", 
        expected="Sólo aparece el método de pago " + tagNombrePago)
    public static void validateInitialStateOk(Channel channel, DataCtxPago dCtxPago, WebDriver driver) 
    throws Exception {
        String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(channel);
        TestCaseData.getDatosCurrentStep().replaceInExpected(tagNombrePago, nombrePago);
        
        dCtxPago.getDataPedido().setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver));
        validaBloqueSaldoEnCuenta(true/*marcado*/, channel, dCtxPago, driver);
    }

    @Step (
    	description="Seleccionamos el bloque de \"Saldo en cuenta\"", 
        expected="Aparecen el resto de métodos de pago")
    public static void selectSaldoEnCuentaBlock(Pais pais, DataCtxPago dCtxPago, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
        boolean marcadoInicialmente = Page1DktopCheckout.secStoreCredit.isChecked(driver);
        Page1DktopCheckout.secStoreCredit.selectSaldoEnCuenta(driver);
                            
        PageCheckoutWrapperStpV.validateLoadingDisappears(5, driver);
        validaBloqueSaldoEnCuenta(!marcadoInicialmente, channel, dCtxPago, driver);
        if (marcadoInicialmente) {
            boolean isEmpl = dCtxPago.getFTCkout().isEmpl;
            PageCheckoutWrapperStpV.validaMetodosPagoDisponibles(pais, isEmpl, app, channel, driver);
            if (channel==Channel.desktop) {
                dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver));
            }
        }
        else {
        	checkAfterMarkSaldoEnCuenta(channel, pais, driver);
            if (channel==Channel.movil_web) {
                dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver));
            }
        }
    }
    
    @Validation (
    	description="Aparecen 0 métodos de pago",
    	level=State.Warn)
    private static boolean checkAfterMarkSaldoEnCuenta(Channel channel, Pais pais, WebDriver driver) {
	    int numPagosExpected = 0;
	    return (PageCheckoutWrapper.isNumpagos(numPagosExpected, channel, pais, driver));
    }
   
    @Validation
    public static ListResultValidation validaBloqueSaldoEnCuenta(boolean checkedSaldoEnCta, Channel channel, DataCtxPago dCtxPago, WebDriver driver) 
    throws Exception {
    	ListResultValidation validations = ListResultValidation.getNew();
      	validations.add(
    		"Es visible el bloque correspondiente al pago mediante \"Saldo en cuenta\"<br>",
    		Page1DktopCheckout.secStoreCredit.isVisible(driver), State.Defect);
      	
      	boolean isCheckedBlock = Page1DktopCheckout.secStoreCredit.isChecked(driver);
      	if (checkedSaldoEnCta) {
          	validations.add(
        		"Está marcado el radio del bloque de \"Saldo en cuenta\"<br>",
        		isCheckedBlock, State.Defect);
      	}
      	else {
          	validations.add(
        		"No está marcado el radio del bloque de \"Saldo en cuenta\"<br>",
        		!isCheckedBlock, State.Warn);
      	}
      	
      	if (!checkedSaldoEnCta || channel==Channel.desktop) {
            String impTotResumen = PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver);
            float impFloat = ImporteScreen.getFloatFromImporteMangoScreen(impTotResumen);
          	validations.add(
        		"Figura un importe total de 0<br>",
        		impFloat==0.0, State.Warn);
      	}

        float saldoCta = dCtxPago.getSaldoCta();
      	validations.add(
    		"Figura un saldo en cuenta de: " + saldoCta,
    		Page1DktopCheckout.secStoreCredit.getImporte(driver)==saldoCta, State.Warn);
      	
      	return validations;
    }
}