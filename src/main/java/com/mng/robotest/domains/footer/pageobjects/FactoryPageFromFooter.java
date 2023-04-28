package com.mng.robotest.domains.footer.pageobjects;

import com.mng.robotest.domains.ayuda.pageobjects.PagesAyuda;
import com.mng.robotest.domains.chequeregalo.pageobjects.PageChequeRegaloInputDataNew;
import com.mng.robotest.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.domains.micuenta.pageobjects.PageAccesoMisCompras;
import com.mng.robotest.domains.transversal.prehome.pageobjects.PagePrehome;
import com.mng.robotest.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class FactoryPageFromFooter {
	
	private FactoryPageFromFooter() {}
	
	public static PageFromFooter make(FooterLink typeFooterLink) {
		switch (typeFooterLink) {
		case AYUDA:
			return (new PagesAyuda());
		case MIS_COMPRAS:
			return (new PageAccesoMisCompras()); 
		case DEVOLUCIONES:
			return (new PageCambiosYdevoluciones());
		case TIENDAS: 
			return (new ModalBuscadorTiendas());
		case MANGO_CARD:
			return (new PageMangoCard());
		case CHEQUE_REGALO:
		case CHEQUE_REGALO_OLD:			
			return (new PageChequeRegaloInputDataNew());
		case APPS:
			return (new PageMangoShoppingApp());
		case EMPRESA:
			return (new PageEmpresa());
		case FRANQUICIAS:
			return (new PageFranquicias());
		case TRABAJA_CON_NOSOTROS:
			return (new PageTrabajaConNosotros());
		case PRENSA:
			return (new PageNotasPrensa());
		case MANGO_OUTLET:
			return (new PageMultimarcasOutlet());
		case PREGUNTAS_FRECUENTES:
			return (new PagePreguntasFreq());
//		case PEDIDOS:
//			return (new PageInputPedido());
		case ENVIO:
			return (new PageEnvio());
		case FORMAS_DE_PAGO:
			return (new PageFormasDePago());
		case GUIA_DE_TALLAS:
			return (new PageGuiaDeTallas());
		case MANGO:
			return (new PagePrehome());		
		default:
			return null;
		}
	}
}
