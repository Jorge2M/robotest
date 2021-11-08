package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.beans.Pago.TypePago;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;

public class FactoryPagos {
    
    /**
     * @return Factoría de objetos de tipo 'PagoStpV' en el que se implementan los pasos/validaciones específicos a partir de la página de checkout
     */
    public static PagoStpV makePagoStpV(DataCtxShop dCtxSh, DataCtxPago dCtxPago, WebDriver driver) throws Exception {
        TypePago typePago = dCtxPago.getDataPedido().getPago().getTypePago();
        
        PagoStpV pagoMaked = null; 
        switch (typePago) {
        case TarjetaIntegrada:
            pagoMaked = new PagoTarjetaIntegrada(dCtxSh, dCtxPago, driver);
            break;
        case KrediKarti:
            pagoMaked = new PagoKrediKarti(dCtxSh, dCtxPago, driver);
            break;
        case TMango:
            pagoMaked = new PagoTMango(dCtxSh, dCtxPago, driver);
            break;
        case Billpay:
            pagoMaked = new PagoBillpay(dCtxSh, dCtxPago, driver);
            break;
        case Paypal:
            pagoMaked = new PagoPaypal(dCtxSh, dCtxPago, driver);
            break;  
        case Mercadopago:
            pagoMaked = new PagoMercadopago(dCtxSh, dCtxPago, driver);
            break;
        case Amazon:
            pagoMaked = new PagoAmazon(dCtxSh, dCtxPago, driver);
            break;            
        case Postfinance:
            pagoMaked = new PagoPostfinance(dCtxSh, dCtxPago, driver);
            break;            
        case Trustpay:
            pagoMaked = new PagoTrustpay(dCtxSh, dCtxPago, driver);
            break;            
        case Multibanco:
            pagoMaked = new PagoMultibanco(dCtxSh, dCtxPago, driver);
            break;            
        case Paytrail:
            pagoMaked = new PagoPaytrail(dCtxSh, dCtxPago, driver);
            break;            
        case Dotpay:
            pagoMaked = new PagoDotpay(dCtxSh, dCtxPago, driver);
            break;            
        case Ideal:
            pagoMaked = new PagoIdeal(dCtxSh, dCtxPago, driver);
            break;   
        case Eps:
            pagoMaked = new PagoEps(dCtxSh, dCtxPago, driver);
            break;  
        case Sepa:
            pagoMaked = new PagoSepa(dCtxSh, dCtxPago, driver);
            break;            
        case Giropay:
            pagoMaked = new PagoGiropay(dCtxSh, dCtxPago, driver);
            break;            
        case Sofort:
            pagoMaked = new PagoSofort(dCtxSh, dCtxPago, driver);
            break;       
        case PayMaya:
        	pagoMaked = new PagoPayMaya(dCtxSh, dCtxPago, driver);
        	break;
        case Klarna:
            pagoMaked = new PagoKlarna(dCtxSh, dCtxPago, driver);
            break;
        case KlarnaUK:
            pagoMaked = new PagoKlarnaUK(dCtxSh, dCtxPago, driver);
            break;
        case PaysecureQiwi:
            pagoMaked = new PagoPaysecureQiwi(dCtxSh, dCtxPago, driver);
            break;
        case StoreCredit:
            pagoMaked = new PagoStoreCredit(dCtxSh, dCtxPago, driver);
            break;
        case Assist:
            pagoMaked = new PagoAssist(dCtxSh, dCtxPago, driver);
            break;            
        case Yandex:
            pagoMaked = new PagoYandex(dCtxSh, dCtxPago, driver);
            break;
        case PasarelaOtras:
            pagoMaked = new PagoPasarelaOtras(dCtxSh, dCtxPago, driver);
            break;
        case KCP:
        	pagoMaked = new PagoKCP(dCtxSh, dCtxPago, driver);
        	break;
        case ContraReembolso:            
            pagoMaked = new PagoContraReembolso(dCtxSh, dCtxPago, driver);
            break;
        case Bancontact:
            pagoMaked = new PagoBancontact(dCtxSh, dCtxPago, driver);
            break;
        case ProcessOut:
        	pagoMaked = new PagoProcessOut(dCtxSh, dCtxPago, driver);
        	break;
        case TpvVotf:
            pagoMaked = new PagoTpvVotf(dCtxSh, dCtxPago, driver);
            break;
        default:
            break;
        }
        
        return pagoMaked;
    }
}
