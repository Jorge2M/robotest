package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import com.mng.testmaker.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputData;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.pedidos.PageInputPedido;

public class FactoryPageFromFooter {
	
	public static PageFromFooter make(FooterLink typeFooterLink, Channel channel) {
        switch (typeFooterLink) {
        case ayuda:
        	return (new PagePregFrecuentes());
        case miscompras:
        	return (new PageAccesoMisCompras());      
        case devoluciones:
        	return (new PageCambiosYdevoluciones());    
        case tiendas: 
        	return (new ModalBuscadorTiendas(channel));
        case mango_card:
        	return (new PageMangoCard());
        case cheque_regalo:
        	return (new PageChequeRegaloInputData());
        case apps:
        	return (new PageMangoShoppingApp());
        case empresa:
        	return (new PageEmpresa());
        case franquicias:
        	return (new PageFranquicias());
        case trabaja_con_nosotros_shop:
        case trabaja_con_nosotros_outlet:
        	return (new PageTrabajaConNosotros());
        case prensa:
        	return (new PageNotasPrensa());
        case mango_outlet:
        	return (new PageMultimarcasOutlet());
        case preguntas_frecuentes:
        	return (new PagePreguntasFreq());
        case pedidos:
        	return (new PageInputPedido());
        case envio:
        	return (new PageEnvio());
        case formas_de_pago:
        	return (new PageFormasDePago());
        case guia_de_tallas:
        	return (new PageGuiaDeTallas());
        case mango:
        	return (new PageMultimarcasShop());    	
        default:
            return null;
        }
	}
}
