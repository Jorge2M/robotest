package com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.conf.Log4jTM;
import com.github.jorge2m.testmaker.conf.State;
import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;
import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.PageCheckoutWrapper;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.ModalDroppoints;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.SecMetodoEnvioDesktop;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.Page1EnvioCheckoutMobilStpV;

public class SecMetodoEnvioDesktopStpV {

	private final SecMetodoEnvioDesktop secMetodoEnvioDesktop;
    public static ModalDroppointsStpV modalDroppoints;
    
    private final WebDriver driver;
    private final Channel channel;
    
    public SecMetodoEnvioDesktopStpV(Channel channel, WebDriver driver) {
    	this.secMetodoEnvioDesktop = new SecMetodoEnvioDesktop(driver);
    	this.driver = driver;
    	this.channel = channel;
    }
    
    @SuppressWarnings({ "static-access", "unused" })
    @Step (
    	description="<b style=\"color:blue;\">#{nombrePago}</b>:Seleccionamos el método de envío <b>#{tipoTransporte}</b>", 
        expected="Se selecciona el método de envío correctamente")
    public void selectMetodoEnvio(TipoTransporte tipoTransporte, String nombrePago, DataCtxPago dCtxPago) {
        secMetodoEnvioDesktop.selectMetodo(tipoTransporte);
        if (!tipoTransporte.isEntregaDomicilio()) {
        	if (ModalDroppoints.isErrorMessageVisibleUntil(driver)) {
        		ModalDroppoints.searchAgainByUserCp(dCtxPago.getDatosRegistro().get("cfCp"), driver);
        	}
        }

        validaBlockSelectedDesktop(tipoTransporte);
        if (tipoTransporte.isEntregaDomicilio()) {
            modalDroppoints.validaIsNotVisible(Channel.desktop, driver);
        } else {
            modalDroppoints.validaIsVisible(Channel.desktop, driver);
        }
    }
    
    @Validation
    public ChecksTM validaBlockSelectedDesktop(TipoTransporte tipoTransporte) {
    	ChecksTM validations = ChecksTM.getNew();
        int maxSeconds = 5;
      	validations.add(
    		"Desaparece la capa de Loading  (lo esperamos hasta " + maxSeconds + " segundos)",
    		new PageCheckoutWrapper(channel, driver).waitUntilNoDivLoading(maxSeconds), State.Warn);
      	validations.add(
    		"Queda seleccionado el bloque correspondiete a <b>" + tipoTransporte + "</b>",
    		secMetodoEnvioDesktop.isBlockSelectedUntil(tipoTransporte, maxSeconds), State.Warn);
      	return validations;
    }
    
    @Step (
    	description="Seleccionamos la <b>#{posicion}a<b> franja horaria del envío \"Urgente - Horario personalizado\"</b>", 
        expected="La franja horaria se selecciona correctamente")
    public void selectFranjaHorariaUrgente(int posicion) {
    	secMetodoEnvioDesktop.selectFranjaHorariaUrgente(posicion);
    }
    

    public void selectMetodoEnvio(DataCtxPago dCtxPago, String nombrePago, AppEcom appE) throws Exception {
        alterTypeEnviosAccordingContext(dCtxPago, appE);
        Pago pago = dCtxPago.getDataPedido().getPago();
        TipoTransporte tipoTransporte = pago.getTipoEnvioType(appE);
        switch (channel) {
        case desktop:
        case tablet:
        	selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago);
            break;
        case mobile:
        	new Page1EnvioCheckoutMobilStpV(driver).selectMetodoEnvio(tipoTransporte, nombrePago, dCtxPago);
            break;
        }
    }
    
    public boolean fluxSelectEnvio(DataCtxPago dCtxPago, DataCtxShop dCtxSh) throws Exception {
        boolean pagoPintado = false;
        Pago pago = dCtxPago.getDataPedido().getPago();
        if (pago.getTipoEnvio(dCtxSh.appE)!=null) {
            String nombrePago = dCtxPago.getDataPedido().getPago().getNombre(dCtxSh.channel);
            selectMetodoEnvio(dCtxPago, nombrePago, dCtxSh.appE);
            pagoPintado = true;
            TipoTransporte tipoEnvio = pago.getTipoEnvioType(dCtxSh.appE);
            if (tipoEnvio.isDroppoint()) {
            	ModalDroppointsStpV.fluxSelectDroppoint(dCtxPago, dCtxSh, driver);
            }
            if (tipoEnvio.isFranjaHoraria()) {
            	selectFranjaHorariaUrgente();
            }
        }        
        
        return pagoPintado;
    }
    
    public void selectFranjaHorariaUrgente() {
        switch (channel) {
        case desktop:
        case tablet:
            selectFranjaHorariaUrgente(1);
            break;
        case mobile:
        	new Page1EnvioCheckoutMobilStpV(driver).selectFranjaHorariaUrgente(1);
        }    
    }
    
    /**
     * No tenemos posibilidad sencilla de determinar si nos aparecerá el envío de tipo "Urgente" o "SendayNextday" así que si no encontramos uno ejecutamos la prueba con el otro
     */
    private void alterTypeEnviosAccordingContext(DataCtxPago dCtxPago, AppEcom appE) {
    	alterTypeEnviosTiendaStandar(dCtxPago, appE);
    	Pago pago = dCtxPago.getDataPedido().getPago();
        alterTypeEnviosNextaySomedayUntilExists(pago, appE);
    }
    
    private void alterTypeEnviosTiendaStandar(DataCtxPago dCtxPago, AppEcom appE) {
        //If employee and Spain not "Recogida en Tienda"
        Pago pago = dCtxPago.getDataPedido().getPago();
        if (dCtxPago.getFTCkout().isEmpl && 
            "001".compareTo(dCtxPago.getDataPedido().getCodigoPais())==0) {
            if (pago.getTipoEnvioType(appE)==TipoTransporte.TIENDA) {
                pago.setTipoEnvioShop(TipoTransporte.STANDARD);
                pago.setTipoEnvioOutlet(TipoTransporte.STANDARD);
                Log4jTM.getLogger().info("Modificado tipo de envío: " + pago.getTipoEnvioType(appE) + " -> " + TipoTransporte.STANDARD);
            }
            
            //Esto no está muy claro si es correcto, pero la configuración en Manto de los transportes dice que en el caso de Outlet los 
            //empleados no tienen PickPoint así que nos ceñimos a ella.
            if (appE==AppEcom.outlet &&
                pago.getTipoEnvioType(appE)==TipoTransporte.ASM)
                pago.setTipoEnvioOutlet(TipoTransporte.STANDARD);
        }    	
    }
    
    private void alterTypeEnviosNextaySomedayUntilExists(Pago pago, AppEcom appE) {
        //Estos tipos de pago se intercambian constantemente a nivel de configuración en la shop
        if (pago.getTipoEnvioType(appE)==TipoTransporte.NEXTDAY ||
        	pago.getTipoEnvioType(appE)==TipoTransporte.NEXTDAY_FRANJAS ||
        	pago.getTipoEnvioType(appE)==TipoTransporte.SAMEDAY ||
        	pago.getTipoEnvioType(appE)==TipoTransporte.SAMEDAY_NEXTDAY_FRANJAS) {
        	for (int i=0; i<4; i++) {
	            if (new PageCheckoutWrapper(channel, driver).isPresentBlockMetodo(pago.getTipoEnvioType(appE))) {
	            	break;
	            }
            	switch (pago.getTipoEnvioType(appE)) {
            	case NEXTDAY:
                    pago.setTipoEnvioShop(TipoTransporte.NEXTDAY_FRANJAS);
                    break;
            	case NEXTDAY_FRANJAS:
                    pago.setTipoEnvioShop(TipoTransporte.SAMEDAY_NEXTDAY_FRANJAS);
                    break;
            	case SAMEDAY_NEXTDAY_FRANJAS:
                    pago.setTipoEnvioShop(TipoTransporte.SAMEDAY);
                    break;
               	case SAMEDAY:
               	default:
                    pago.setTipoEnvioShop(TipoTransporte.NEXTDAY_FRANJAS);
                    break;                    
            	}
            }
        }
    }
}
