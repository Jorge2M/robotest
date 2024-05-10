package com.mng.robotest.tests.domains.compra.payments;

import com.mng.robotest.tests.domains.compra.payments.amazon.PagoAmazon;
import com.mng.robotest.tests.domains.compra.payments.assist.PagoAssist;
import com.mng.robotest.tests.domains.compra.payments.billpay.PagoBillpay;
import com.mng.robotest.tests.domains.compra.payments.contrareembolso.PagoContraReembolso;
import com.mng.robotest.tests.domains.compra.payments.d3d.PagoBancontact;
import com.mng.robotest.tests.domains.compra.payments.d3d.PagoTarjetaIntegrada;
import com.mng.robotest.tests.domains.compra.payments.dotpay.PagoDotpay;
import com.mng.robotest.tests.domains.compra.payments.eps.PagoEps;
import com.mng.robotest.tests.domains.compra.payments.giropay.PagoGiropay;
import com.mng.robotest.tests.domains.compra.payments.ideal.PagoIdeal;
import com.mng.robotest.tests.domains.compra.payments.kcp.PagoKCP;
import com.mng.robotest.tests.domains.compra.payments.klarna.PagoKlarna;
import com.mng.robotest.tests.domains.compra.payments.klarna.PagoKlarnaUK;
import com.mng.robotest.tests.domains.compra.payments.kredikarti.PagoKrediKarti;
import com.mng.robotest.tests.domains.compra.payments.mercadopago.PagoMercadopago;
import com.mng.robotest.tests.domains.compra.payments.multibanco.PagoMultibanco;
import com.mng.robotest.tests.domains.compra.payments.pasarelaotras.PagoPasarelaOtras;
import com.mng.robotest.tests.domains.compra.payments.paymaya.PagoPayMaya;
import com.mng.robotest.tests.domains.compra.payments.paypal.PagoPaypal;
import com.mng.robotest.tests.domains.compra.payments.paysecureqiwi.PagoPaysecureQiwi;
import com.mng.robotest.tests.domains.compra.payments.paytrail.PagoPaytrail;
import com.mng.robotest.tests.domains.compra.payments.postfinance.PagoPostfinance;
import com.mng.robotest.tests.domains.compra.payments.processout.PagoProcessOut;
import com.mng.robotest.tests.domains.compra.payments.sepa.PagoSepa;
import com.mng.robotest.tests.domains.compra.payments.sofort.PagoSofort;
import com.mng.robotest.tests.domains.compra.payments.trustpay.PagoTrustpay;
import com.mng.robotest.tests.domains.compra.payments.yandex.PagoYandex;
import com.mng.robotest.testslegacy.beans.TypePago;

public class FactoryPagos {

	private FactoryPagos() {}
	
	public static PagoSteps makePagoSteps(TypePago typePago) {
		PagoSteps pagoMaked = null; 
		switch (typePago) {
		case TARJETA_INTEGRADA:
			pagoMaked = new PagoTarjetaIntegrada();
			break;
		case KREDI_KARTI:
			pagoMaked = new PagoKrediKarti();
			break;
		case BILLPAY:
			pagoMaked = new PagoBillpay();
			break;
		case PAYPAL:
			pagoMaked = new PagoPaypal();
			break;  
		case MERCADOPAGO:
			pagoMaked = new PagoMercadopago();
			break;
		case AMAZON:
			pagoMaked = new PagoAmazon();
			break;			
		case POSTFINANCE:
			pagoMaked = new PagoPostfinance();
			break;			
		case TRUSTPAY:
			pagoMaked = new PagoTrustpay();
			break;			
		case MULTIBANCO:
			pagoMaked = new PagoMultibanco();
			break;			
		case PAYTRAIL:
			pagoMaked = new PagoPaytrail();
			break;			
		case DOTPAY:
			pagoMaked = new PagoDotpay();
			break;			
		case IDEAL:
			pagoMaked = new PagoIdeal();
			break;   
		case EPS:
			pagoMaked = new PagoEps();
			break;  
		case SEPA:
			pagoMaked = new PagoSepa();
			break;			
		case GIROPAY:
			pagoMaked = new PagoGiropay();
			break;			
		case SOFORT:
			pagoMaked = new PagoSofort();
			break;	   
		case PAYMAYA:
			pagoMaked = new PagoPayMaya();
			break;
		case KLARNA:
			pagoMaked = new PagoKlarna();
			break;
		case KLARNA_UK:
			pagoMaked = new PagoKlarnaUK();
			break;
		case PAYSECURE_QIWI:
			pagoMaked = new PagoPaysecureQiwi();
			break;
		case ASSIST:
			pagoMaked = new PagoAssist();
			break;			
		case YANDEX:
			pagoMaked = new PagoYandex();
			break;
		case PASARELA_OTRAS:
			pagoMaked = new PagoPasarelaOtras();
			break;
		case KCP:
			pagoMaked = new PagoKCP();
			break;
		case CONTRA_REEMBOLSO:			
			pagoMaked = new PagoContraReembolso();
			break;
		case BANCONTACT:
			pagoMaked = new PagoBancontact();
			break;
		case PROCESS_OUT:
			pagoMaked = new PagoProcessOut();
			break;
		default:
			break;
		}
		
		return pagoMaked;
	}
}
