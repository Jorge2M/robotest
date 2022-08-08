package com.mng.robotest.test.pageobject.shop.footer;

import com.github.jorge2m.testmaker.conf.Channel;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputDataNew;
import com.mng.robotest.test.pageobject.chequeregalo.PageChequeRegaloInputDataOld;
import com.mng.robotest.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test.pageobject.shop.miscompras.PageInputPedido;
import com.mng.robotest.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class FactoryPageFromFooter {
	
	public static PageFromFooter make(FooterLink typeFooterLink, Channel channel, AppEcom app, WebDriver driver) {
		switch (typeFooterLink) {
		case AYUDA:
			return (new PagePregFrecuentes(driver));
		case MIS_COMPRAS:
			return (new PageAccesoMisCompras()); 
		case DEVOLUCIONES:
			return (new PageCambiosYdevoluciones(driver));
		case TIENDAS: 
			return (new ModalBuscadorTiendas(channel, app, driver));
		case MANGO_CARD:
			return (new PageMangoCard(driver));
		case CHEQUE_REGALO:
			return (new PageChequeRegaloInputDataNew());
		case CHEQUE_REGALO_OLD:
			return (new PageChequeRegaloInputDataOld());
		case APPS:
			return (new PageMangoShoppingApp(driver));
		case EMPRESA:
			return (new PageEmpresa(driver));
		case FRANQUICIAS:
			return (new PageFranquicias(driver));
		case TRABAJA_CON_NOSOTROS_SHOP:
		case TRABAJA_CON_NOSOTROS_OUTLET:
			return (new PageTrabajaConNosotros(driver));
		case PRENSA:
			return (new PageNotasPrensa(driver));
		case MANGO_OUTLET:
			return (new PageMultimarcasOutlet(driver));
		case PREGUNTAS_FRECUENTES:
			return (new PagePreguntasFreq(driver));
		case PEDIDOS:
			return (new PageInputPedido());
		case ENVIO:
			return (new PageEnvio(driver));
		case FORMAS_DE_PAGO:
			return (new PageFormasDePago(driver));
		case GUIA_DE_TALLAS:
			return (new PageGuiaDeTallas(driver));
		case MANGO:
			return (new PageMultimarcasShop(driver));		
		default:
			return null;
		}
	}
}
