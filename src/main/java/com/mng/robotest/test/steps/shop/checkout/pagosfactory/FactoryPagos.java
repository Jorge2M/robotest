package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.datastored.DataCtxPago;

public class FactoryPagos {
	
	public static PagoSteps makePagoSteps(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
		TypePago typePago = dCtxPago.getDataPedido().getPago().getTypePago();
		
		PagoSteps pagoMaked = null; 
		switch (typePago) {
		case TarjetaIntegrada:
			pagoMaked = new PagoTarjetaIntegrada(dCtxSh, dCtxPago);
			break;
		case KrediKarti:
			pagoMaked = new PagoKrediKarti(dCtxSh, dCtxPago);
			break;
		case TMango:
			pagoMaked = new PagoTMango(dCtxSh, dCtxPago);
			break;
		case Billpay:
			pagoMaked = new PagoBillpay(dCtxSh, dCtxPago);
			break;
		case Paypal:
			pagoMaked = new PagoPaypal(dCtxSh, dCtxPago);
			break;  
		case Mercadopago:
			pagoMaked = new PagoMercadopago(dCtxSh, dCtxPago);
			break;
		case Amazon:
			pagoMaked = new PagoAmazon(dCtxSh, dCtxPago);
			break;			
		case Postfinance:
			pagoMaked = new PagoPostfinance(dCtxSh, dCtxPago);
			break;			
		case Trustpay:
			pagoMaked = new PagoTrustpay(dCtxSh, dCtxPago);
			break;			
		case Multibanco:
			pagoMaked = new PagoMultibanco(dCtxSh, dCtxPago);
			break;			
		case Paytrail:
			pagoMaked = new PagoPaytrail(dCtxSh, dCtxPago);
			break;			
		case Dotpay:
			pagoMaked = new PagoDotpay(dCtxSh, dCtxPago);
			break;			
		case Ideal:
			pagoMaked = new PagoIdeal(dCtxSh, dCtxPago);
			break;   
		case Eps:
			pagoMaked = new PagoEps(dCtxSh, dCtxPago);
			break;  
		case Sepa:
			pagoMaked = new PagoSepa(dCtxSh, dCtxPago);
			break;			
		case Giropay:
			pagoMaked = new PagoGiropay(dCtxSh, dCtxPago);
			break;			
		case Sofort:
			pagoMaked = new PagoSofort(dCtxSh, dCtxPago);
			break;	   
		case PayMaya:
			pagoMaked = new PagoPayMaya(dCtxSh, dCtxPago);
			break;
		case Klarna:
			pagoMaked = new PagoKlarna(dCtxSh, dCtxPago);
			break;
		case KlarnaUK:
			pagoMaked = new PagoKlarnaUK(dCtxSh, dCtxPago);
			break;
		case PaysecureQiwi:
			pagoMaked = new PagoPaysecureQiwi(dCtxSh, dCtxPago);
			break;
		case StoreCredit:
			pagoMaked = new PagoStoreCredit(dCtxSh, dCtxPago);
			break;
		case Assist:
			pagoMaked = new PagoAssist(dCtxSh, dCtxPago);
			break;			
		case Yandex:
			pagoMaked = new PagoYandex(dCtxSh, dCtxPago);
			break;
		case PasarelaOtras:
			pagoMaked = new PagoPasarelaOtras(dCtxSh, dCtxPago);
			break;
		case KCP:
			pagoMaked = new PagoKCP(dCtxSh, dCtxPago);
			break;
		case ContraReembolso:			
			pagoMaked = new PagoContraReembolso(dCtxSh, dCtxPago);
			break;
		case Bancontact:
			pagoMaked = new PagoBancontact(dCtxSh, dCtxPago);
			break;
		case ProcessOut:
			pagoMaked = new PagoProcessOut(dCtxSh, dCtxPago);
			break;
		case TpvVotf:
			pagoMaked = new PagoTpvVotf(dCtxSh, dCtxPago);
			break;
		default:
			break;
		}
		
		return pagoMaked;
	}
}
