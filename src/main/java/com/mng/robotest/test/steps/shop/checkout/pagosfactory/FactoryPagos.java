package com.mng.robotest.test.steps.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.data.DataTest;
import com.mng.robotest.test.datastored.DataPago;

public class FactoryPagos {
	
	public static PagoSteps makePagoSteps(DataPago dataPago) throws Exception {
		TypePago typePago = dataPago.getDataPedido().getPago().getTypePago();
		
		PagoSteps pagoMaked = null; 
		switch (typePago) {
		case TarjetaIntegrada:
			pagoMaked = new PagoTarjetaIntegrada(dataPago);
			break;
		case KrediKarti:
			pagoMaked = new PagoKrediKarti(dataPago);
			break;
		case TMango:
			pagoMaked = new PagoTMango(dataPago);
			break;
		case Billpay:
			pagoMaked = new PagoBillpay(dataPago);
			break;
		case Paypal:
			pagoMaked = new PagoPaypal(dataPago);
			break;  
		case Mercadopago:
			pagoMaked = new PagoMercadopago(dataPago);
			break;
		case Amazon:
			pagoMaked = new PagoAmazon(dataPago);
			break;			
		case Postfinance:
			pagoMaked = new PagoPostfinance(dataPago);
			break;			
		case Trustpay:
			pagoMaked = new PagoTrustpay(dataPago);
			break;			
		case Multibanco:
			pagoMaked = new PagoMultibanco(dataPago);
			break;			
		case Paytrail:
			pagoMaked = new PagoPaytrail(dataPago);
			break;			
		case Dotpay:
			pagoMaked = new PagoDotpay(dataPago);
			break;			
		case Ideal:
			pagoMaked = new PagoIdeal(dataPago);
			break;   
		case Eps:
			pagoMaked = new PagoEps(dataPago);
			break;  
		case Sepa:
			pagoMaked = new PagoSepa(dataPago);
			break;			
		case Giropay:
			pagoMaked = new PagoGiropay(dataPago);
			break;			
		case Sofort:
			pagoMaked = new PagoSofort(dataPago);
			break;	   
		case PayMaya:
			pagoMaked = new PagoPayMaya(dataPago);
			break;
		case Klarna:
			pagoMaked = new PagoKlarna(dataPago);
			break;
		case KlarnaUK:
			pagoMaked = new PagoKlarnaUK(dataPago);
			break;
		case PaysecureQiwi:
			pagoMaked = new PagoPaysecureQiwi(dataPago);
			break;
		case StoreCredit:
			pagoMaked = new PagoStoreCredit(dataPago);
			break;
		case Assist:
			pagoMaked = new PagoAssist(dataPago);
			break;			
		case Yandex:
			pagoMaked = new PagoYandex(dataPago);
			break;
		case PasarelaOtras:
			pagoMaked = new PagoPasarelaOtras(dataPago);
			break;
		case KCP:
			pagoMaked = new PagoKCP(dataPago);
			break;
		case ContraReembolso:			
			pagoMaked = new PagoContraReembolso(dataPago);
			break;
		case Bancontact:
			pagoMaked = new PagoBancontact(dataPago);
			break;
		case ProcessOut:
			pagoMaked = new PagoProcessOut(dataPago);
			break;
		case TpvVotf:
			pagoMaked = new PagoTpvVotf(dataPago);
			break;
		default:
			break;
		}
		
		return pagoMaked;
	}
}
