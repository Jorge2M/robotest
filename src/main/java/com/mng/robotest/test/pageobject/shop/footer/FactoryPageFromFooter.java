package com.mng.robotest.test.pageobject.shop.footer;

import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputDataNew;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputDataOld;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test.pageobject.shop.miscompras.PageInputPedido;
import com.mng.robotest.test.pageobject.shop.modales.ModalBuscadorTiendas;


public class FactoryPageFromFooter {
	
	public static PageFromFooter make(FooterLink typeFooterLink) {
		switch (typeFooterLink) {
		case AYUDA:
			return (new PagePregFrecuentes());
		case MIS_COMPRAS:
			return (new PageAccesoMisCompras()); 
		case DEVOLUCIONES:
			return (new PageCambiosYdevoluciones());
		case TIENDAS: 
			return (new ModalBuscadorTiendas());
		case MANGO_CARD:
			return (new PageMangoCard());
		case CHEQUE_REGALO:
			return (new PageChequeRegaloInputDataNew());
		case CHEQUE_REGALO_OLD:
			return (new PageChequeRegaloInputDataOld());
		case APPS:
			return (new PageMangoShoppingApp());
		case EMPRESA:
			return (new PageEmpresa());
		case FRANQUICIAS:
			return (new PageFranquicias());
		case TRABAJA_CON_NOSOTROS_SHOP:
		case TRABAJA_CON_NOSOTROS_OUTLET:
			return (new PageTrabajaConNosotros());
		case PRENSA:
			return (new PageNotasPrensa());
		case MANGO_OUTLET:
			return (new PageMultimarcasOutlet());
		case PREGUNTAS_FRECUENTES:
			return (new PagePreguntasFreq());
		case PEDIDOS:
			return (new PageInputPedido());
		case ENVIO:
			return (new PageEnvio());
		case FORMAS_DE_PAGO:
			return (new PageFormasDePago());
		case GUIA_DE_TALLAS:
			return (new PageGuiaDeTallas());
		case MANGO:
			return (new PageMultimarcasShop());		
		default:
			return null;
		}
	}
}
