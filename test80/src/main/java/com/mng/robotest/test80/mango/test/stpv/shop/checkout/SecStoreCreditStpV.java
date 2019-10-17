package com.mng.robotest.test80.mango.test.stpv.shop.checkout;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.mng.testmaker.utils.State;
import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.testmaker.boundary.aspects.step.Step;
import com.mng.testmaker.boundary.aspects.validation.ChecksResult;
import com.mng.testmaker.boundary.aspects.validation.Validation;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.Page1DktopCheckout;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen; 

@SuppressWarnings({"static-access"})
public class SecStoreCreditStpV { 
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);
    
    final static String tagNombrePago = "@TagNombrePago";
    @Step (
    	description="Revisamos el bloque de \"Saldo en cuenta\"", 
        expected="Sólo aparece el método de pago " + tagNombrePago)
    public static void validateInitialStateOk(Channel channel, DataCtxPago dCtxPago, WebDriver driver) 
    throws Exception {
        String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(channel);
        TestMaker.getCurrentStep().replaceInExpected(tagNombrePago, nombrePago);
        
        dCtxPago.getDataPedido().setImporteTotal(PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver));
        validaBloqueSaldoEnCuenta(true, channel, dCtxPago, driver);
    }

    @Step (
    	description="Seleccionamos el bloque de \"Saldo en cuenta\"", 
        expected="El marcado o desmarcado es correcto")
    public static void selectSaldoEnCuentaBlock(Pais pais, DataCtxPago dCtxPago, AppEcom app, Channel channel, WebDriver driver) 
    throws Exception {
        boolean marcadoInicialmente = Page1DktopCheckout.secStoreCredit.isChecked(driver);
        Page1DktopCheckout.secStoreCredit.selectSaldoEnCuenta(driver);
                            
        PageCheckoutWrapperStpV.validateLoadingDisappears(5, driver);
        validaBloqueSaldoEnCuenta(!marcadoInicialmente, channel, dCtxPago, driver);
        if (marcadoInicialmente) {
            boolean isEmpl = dCtxPago.getFTCkout().isEmpl;
            PageCheckoutWrapperStpV.validaMetodosPagoDisponibles(pais, isEmpl, app, channel, driver);
            //if (channel==Channel.desktop) {
                dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(PageCheckoutWrapper.getPrecioTotalSinSaldoEnCuenta(channel, driver));
            //}
        } else {
        	checkAfterMarkSaldoEnCuenta(channel, pais, driver);
            if (channel==Channel.movil_web) {
                dCtxPago.getDataPedido().setImporteTotalSinSaldoCta(PageCheckoutWrapper.getPrecioTotalSinSaldoEnCuenta(channel, driver));
            }
        }
    }
    
    @Validation (
    	description="Aparecen 0 métodos de pago",
    	level=State.Warn)
    private static boolean checkAfterMarkSaldoEnCuenta(Channel channel, Pais pais, WebDriver driver) {
	    int numPagosExpected = 0;
	    return (PageCheckoutWrapper.isNumpagos(numPagosExpected, channel, driver));
    }
   
    @Validation
    public static ChecksResult validaBloqueSaldoEnCuenta(boolean checkedSaldoEnCta, Channel channel, DataCtxPago dCtxPago, WebDriver driver) 
    throws Exception {
    	ChecksResult validations = ChecksResult.getNew();
      	validations.add(
    		"Es visible el bloque correspondiente al pago mediante \"Saldo en cuenta\"",
    		Page1DktopCheckout.secStoreCredit.isVisible(driver), State.Defect);
      	
      	boolean isCheckedBlock = Page1DktopCheckout.secStoreCredit.isChecked(driver);
      	if (checkedSaldoEnCta) {
          	validations.add(
        		"Está marcado el radio del bloque de \"Saldo en cuenta\"",
        		isCheckedBlock, State.Defect);
      	} else {
          	validations.add(
        		"No está marcado el radio del bloque de \"Saldo en cuenta\"",
        		!isCheckedBlock, State.Warn);
      	}
      	
      	if (checkedSaldoEnCta/* || channel==Channel.desktop*/) {
            String impTotResumen = PageCheckoutWrapper.getPrecioTotalFromResumen(channel, driver);
            float impFloat = ImporteScreen.getFloatFromImporteMangoScreen(impTotResumen);
          	validations.add(
        		"Figura un importe total de 0",
        		impFloat==0.0, State.Warn);
      	}

        float saldoCta = dCtxPago.getSaldoCta();
      	validations.add(
    		"Figura un saldo en cuenta de: " + saldoCta,
    		Page1DktopCheckout.secStoreCredit.getImporte(driver)==saldoCta, State.Warn);
      	
      	return validations;
    }
}