package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;

public class ModalDroppointsStpV {
    
    public static SecSelectDPointStpV secSelectDPoint;
    public static SecConfirmDatosStpV secConfirmDatos;
    
    @SuppressWarnings("static-access")
    @Validation
    public static ChecksTM validaIsVisible(Channel channel, WebDriver driver) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 3;
      	validations.add(
    		"Desaparece el mensaje de \"Cargando...\" (lo esperamos hasta " + maxSeconds + " segundos)",
    		ModalDroppoints.isInvisibleCargandoMsgUntil(maxSeconds, driver), State.Warn);
      	validations.add(
    		"Aparece un 1er Droppoint visible (lo esperamos hasta " + maxSeconds + " segundos)",
    		ModalDroppoints.secSelectDPoint.isDroppointVisibleUntil(1, maxSeconds, driver), State.Info);
      	validations.add(
    		"Sí aparece el modal con el mapa de Droppoints",
    		ModalDroppoints.isVisible(channel, driver), State.Defect);
      	return validations;
    }
    
    @Validation (
    	description="No aparece el modal con el mapa de Droppoints",
    	level=State.Defect)
    public static boolean validaIsNotVisible(Channel channel, WebDriver driver) {
        return (!ModalDroppoints.isVisible(channel, driver));
    }
    
    @SuppressWarnings("static-access")
    public static void fluxSelectDroppoint(DataCtxPago dCtxPago, DataCtxShop dCtxSh, WebDriver driver) throws Exception {
        Pago pago = dCtxPago.getDataPedido().getPago();
    	DataSearchDeliveryPoint dataSearchDp = DataSearchDeliveryPoint.getInstance(pago, dCtxSh.appE, dCtxSh.pais);
        secSelectDPoint.searchPoblacion(dataSearchDp, driver);
        DataDeliveryPoint dataDp = ModalDroppointsStpV.secSelectDPoint.clickDeliveryPointAndGetData(2, driver);
        dCtxPago.getDataPedido().setTypeEnvio(pago.getTipoEnvioType(dCtxSh.appE));
        dCtxPago.getDataPedido().setDataDeliveryPoint(dataDp);
        secSelectDPoint.clickSelectButton(dCtxSh.channel, driver);
        secConfirmDatos.setDataIfNeeded(dCtxSh.pais.getCodigo_pais(), driver);
        secConfirmDatos.clickConfirmarDatosButton(dCtxSh.channel, dCtxPago.getDataPedido(), driver);                
    }
}
