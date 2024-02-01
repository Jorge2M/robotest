package com.mng.robotest.tests.domains.footer.pageobjects;

import com.mng.robotest.tests.domains.ayuda.pageobjects.PagesAyuda;
import com.mng.robotest.tests.domains.chequeregalo.pageobjects.PageChequeRegaloInputDataNew;
import com.mng.robotest.tests.domains.footer.pageobjects.SecFooter.FooterLink;
import com.mng.robotest.tests.domains.micuenta.pageobjects.PageAccesoMisCompras;
import com.mng.robotest.testslegacy.pageobject.shop.modales.ModalBuscadorTiendas;

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
		case CHEQUE_REGALO, CHEQUE_REGALO_OLD:			
			return (new PageChequeRegaloInputDataNew());
		case APPS:
			return (new PageMangoShoppingApp());
		case EMPRESA:
			return (new PageEmpresa());
		case FRANQUICIAS:
			return (new PageFranquicias());
		case TRABAJA_CON_NOSOTROS: //
			return (new PageTrabajaConNosotros());
		case PRENSA: //
			return (new PageNotasPrensa());
		case MANGO_OUTLET:
			return (new PageMultimarcasOutlet());
//		case PEDIDOS:
//			return (new PageInputPedido());
		case ENVIO: //
			return (new PageEnvio());
		case GUIA_DE_TALLAS:
			return (new PageGuiaDeTallas());
		default:
			return null;
		}
	}
}
