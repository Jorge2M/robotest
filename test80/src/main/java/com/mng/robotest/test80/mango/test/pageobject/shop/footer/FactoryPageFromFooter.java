package com.mng.robotest.test80.mango.test.pageobject.shop.footer;

import com.github.jorge2m.testmaker.conf.Channel;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputDataNew;
import com.mng.robotest.test80.mango.test.pageobject.chequeregalo.PageChequeRegaloInputDataOld;
import com.mng.robotest.test80.mango.test.pageobject.shop.footer.SecFooter.FooterLink;
import com.mng.robotest.test80.mango.test.pageobject.shop.micuenta.PageAccesoMisCompras;
import com.mng.robotest.test80.mango.test.pageobject.shop.miscompras.PageInputPedido;
import com.mng.robotest.test80.mango.test.pageobject.shop.modales.ModalBuscadorTiendas;

public class FactoryPageFromFooter {
	
	public static PageFromFooter make(FooterLink typeFooterLink, Channel channel, WebDriver driver) {
		switch (typeFooterLink) {
		case ayuda:
			return (new PagePregFrecuentes(driver));
		case miscompras:
			return (new PageAccesoMisCompras(driver)); 
		case devoluciones:
			return (new PageCambiosYdevoluciones(driver));
		case tiendas: 
			return (new ModalBuscadorTiendas(channel, driver));
		case mango_card:
			return (new PageMangoCard(driver));
		case cheque_regalo:
			return (new PageChequeRegaloInputDataNew(driver));
		case cheque_regalo_old:
			return (new PageChequeRegaloInputDataOld(driver));
		case apps:
			return (new PageMangoShoppingApp(driver));
		case empresa:
			return (new PageEmpresa(driver));
		case franquicias:
			return (new PageFranquicias(driver));
		case trabaja_con_nosotros_shop:
		case trabaja_con_nosotros_outlet:
			return (new PageTrabajaConNosotros(driver));
		case prensa:
			return (new PageNotasPrensa(driver));
		case mango_outlet:
			return (new PageMultimarcasOutlet(driver));
		case preguntas_frecuentes:
			return (new PagePreguntasFreq(driver));
		case pedidos:
			return (new PageInputPedido(driver));
		case envio:
			return (new PageEnvio(driver));
		case formas_de_pago:
			return (new PageFormasDePago(driver));
		case guia_de_tallas:
			return (new PageGuiaDeTallas(driver));
		case mango:
			return (new PageMultimarcasShop(driver));		
		default:
			return null;
		}
	}
}
