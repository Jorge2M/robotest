package com.mng.robotest.domains.compra.payments;

import com.mng.robotest.domains.compra.payments.amazon.PagoAmazon;
import com.mng.robotest.domains.compra.payments.assist.PagoAssist;
import com.mng.robotest.domains.compra.payments.billpay.PagoBillpay;
import com.mng.robotest.domains.compra.payments.contrareembolso.PagoContraReembolso;
import com.mng.robotest.domains.compra.payments.d3d.PagoBancontact;
import com.mng.robotest.domains.compra.payments.d3d.PagoTarjetaIntegrada;
import com.mng.robotest.domains.compra.payments.dotpay.PagoDotpay;
import com.mng.robotest.domains.compra.payments.eps.PagoEps;
import com.mng.robotest.domains.compra.payments.giropay.PagoGiropay;
import com.mng.robotest.domains.compra.payments.ideal.PagoIdeal;
import com.mng.robotest.domains.compra.payments.kcp.PagoKCP;
import com.mng.robotest.domains.compra.payments.klarna.PagoKlarna;
import com.mng.robotest.domains.compra.payments.klarna.PagoKlarnaUK;
import com.mng.robotest.domains.compra.payments.kredikarti.PagoKrediKarti;
import com.mng.robotest.domains.compra.payments.mercadopago.PagoMercadopago;
import com.mng.robotest.domains.compra.payments.multibanco.PagoMultibanco;
import com.mng.robotest.domains.compra.payments.pasarelaotras.PagoPasarelaOtras;
import com.mng.robotest.domains.compra.payments.paymaya.PagoPayMaya;
import com.mng.robotest.domains.compra.payments.paypal.PagoPaypal;
import com.mng.robotest.domains.compra.payments.paysecureqiwi.PagoPaysecureQiwi;
import com.mng.robotest.domains.compra.payments.paytrail.PagoPaytrail;
import com.mng.robotest.domains.compra.payments.postfinance.PagoPostfinance;
import com.mng.robotest.domains.compra.payments.processout.PagoProcessOut;
import com.mng.robotest.domains.compra.payments.sepa.PagoSepa;
import com.mng.robotest.domains.compra.payments.sofort.PagoSofort;
import com.mng.robotest.domains.compra.payments.tmango.PagoTMango;
import com.mng.robotest.domains.compra.payments.trustpay.PagoTrustpay;
import com.mng.robotest.domains.compra.payments.votftpv.PagoTpvVotf;
import com.mng.robotest.domains.compra.payments.yandex.PagoYandex;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.datastored.DataPago;

public class FactoryPagos {

	private FactoryPagos() {}
	
	public static PagoSteps makePagoSteps(DataPago dataPago) throws Exception {
		TypePago typePago = dataPago.getDataPedido().getPago().getTypePago();
		
		PagoSteps pagoMaked = null; 
		switch (typePago) {
		case TARJETA_INTEGRADA:
			pagoMaked = new PagoTarjetaIntegrada(dataPago);
			break;
		case KREDI_KARTI:
			pagoMaked = new PagoKrediKarti(dataPago);
			break;
		case TARJETA_MANGO:
			pagoMaked = new PagoTMango(dataPago);
			break;
		case BILLPAY:
			pagoMaked = new PagoBillpay(dataPago);
			break;
		case PAYPAL:
			pagoMaked = new PagoPaypal(dataPago);
			break;  
		case MERCADOPAGO:
			pagoMaked = new PagoMercadopago(dataPago);
			break;
		case AMAZON:
			pagoMaked = new PagoAmazon(dataPago);
			break;			
		case POSTFINANCE:
			pagoMaked = new PagoPostfinance(dataPago);
			break;			
		case TRUSTPAY:
			pagoMaked = new PagoTrustpay(dataPago);
			break;			
		case MULTIBANCO:
			pagoMaked = new PagoMultibanco(dataPago);
			break;			
		case PAYTRAIL:
			pagoMaked = new PagoPaytrail(dataPago);
			break;			
		case DOTPAY:
			pagoMaked = new PagoDotpay(dataPago);
			break;			
		case IDEAL:
			pagoMaked = new PagoIdeal(dataPago);
			break;   
		case EPS:
			pagoMaked = new PagoEps(dataPago);
			break;  
		case SEPA:
			pagoMaked = new PagoSepa(dataPago);
			break;			
		case GIROPAY:
			pagoMaked = new PagoGiropay(dataPago);
			break;			
		case SOFORT:
			pagoMaked = new PagoSofort(dataPago);
			break;	   
		case PAYMAYA:
			pagoMaked = new PagoPayMaya(dataPago);
			break;
		case KLARNA:
			pagoMaked = new PagoKlarna(dataPago);
			break;
		case KLARNA_UK:
			pagoMaked = new PagoKlarnaUK(dataPago);
			break;
		case PAYSECURE_QIWI:
			pagoMaked = new PagoPaysecureQiwi(dataPago);
			break;
		case ASSIST:
			pagoMaked = new PagoAssist(dataPago);
			break;			
		case YANDEX:
			pagoMaked = new PagoYandex(dataPago);
			break;
		case PASARELA_OTRAS:
			pagoMaked = new PagoPasarelaOtras(dataPago);
			break;
		case KCP:
			pagoMaked = new PagoKCP(dataPago);
			break;
		case CONTRA_REEMBOLSO:			
			pagoMaked = new PagoContraReembolso(dataPago);
			break;
		case BANCONTACT:
			pagoMaked = new PagoBancontact(dataPago);
			break;
		case PROCESS_OUT:
			pagoMaked = new PagoProcessOut(dataPago);
			break;
		case TPV_VOTF:
			pagoMaked = new PagoTpvVotf(dataPago);
			break;
		default:
			break;
		}
		
		return pagoMaked;
	}
}
