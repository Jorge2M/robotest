package com.mng.robotest.tests.domains.compra.payments;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.compra.payments.amazon.PagoAmazon;
import com.mng.robotest.tests.domains.compra.payments.assist.PagoAssist;
import com.mng.robotest.tests.domains.compra.payments.billpay.PagoBillpay;
import com.mng.robotest.tests.domains.compra.payments.contrareembolso.PagoContraReembolso;
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
import com.mng.robotest.tests.domains.compra.payments.visa.PagoBancontact;
import com.mng.robotest.tests.domains.compra.payments.visa.PagoTrjIntegrated;
import com.mng.robotest.tests.domains.compra.payments.visa.PagoTrjIntegratedNew;
import com.mng.robotest.tests.domains.compra.payments.yandex.PagoYandex;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.beans.TypePago;

public class FactoryPagos {

	private FactoryPagos() {}
	
	public static PaymentSteps makePagoSteps(TypePago typePago, Pais pais, AppEcom app) {

		switch (typePago) {
		case TARJETA_INTEGRADA:
			if (pais.isNewcheckout(app)) {
				return new PagoTrjIntegratedNew();
			} else {
				return new PagoTrjIntegrated();
			}
		case KREDI_KARTI:
			return new PagoKrediKarti();
		case BILLPAY:
			return new PagoBillpay();
		case PAYPAL:
			return new PagoPaypal();
		case MERCADOPAGO:
			return new PagoMercadopago();
		case AMAZON:
			return new PagoAmazon();
		case POSTFINANCE:
			return new PagoPostfinance();
		case TRUSTPAY:
			return new PagoTrustpay();
		case MULTIBANCO:
			return new PagoMultibanco();
		case PAYTRAIL:
			return new PagoPaytrail();
		case DOTPAY:
			return new PagoDotpay();
		case IDEAL:
			return new PagoIdeal();
		case EPS:
			return new PagoEps();
		case SEPA:
			return new PagoSepa();
		case GIROPAY:
			return new PagoGiropay();
		case SOFORT:
			return new PagoSofort();
		case PAYMAYA:
			return new PagoPayMaya();
		case KLARNA:
			return new PagoKlarna();
		case KLARNA_UK:
			return new PagoKlarnaUK();
		case PAYSECURE_QIWI:
			return new PagoPaysecureQiwi();
		case ASSIST:
			return new PagoAssist();
		case YANDEX:
			return new PagoYandex();
		case PASARELA_OTRAS:
			return new PagoPasarelaOtras();
		case KCP:
			return new PagoKCP();
		case CONTRA_REEMBOLSO:			
			return new PagoContraReembolso();
		case BANCONTACT:
			return new PagoBancontact();
		case PROCESS_OUT:
			return new PagoProcessOut();
		default:
			return null;
		}
	}
	
}
