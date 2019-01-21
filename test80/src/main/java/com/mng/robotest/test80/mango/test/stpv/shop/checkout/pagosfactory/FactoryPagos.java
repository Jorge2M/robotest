package com.mng.robotest.test80.mango.test.stpv.shop.checkout.pagosfactory;

import com.mng.robotest.test80.arq.utils.DataFmwkTest;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.datastored.DataCtxPago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;

@SuppressWarnings("javadoc")
public class FactoryPagos {
    
    /**
     * @return Factoría de objetos de tipo 'PagoStpV' en el que se implementan los pasos/validaciones específicos a partir de la página de checkout
     */
    public static PagoStpV makePagoStpV(DataCtxShop dCtxSh, DataCtxPago dCtxPago, DataFmwkTest dFTest) {
        TypePago typePago = dCtxPago.getDataPedido().getPago().getTypePago();
        
        PagoStpV pagoMaked = null; 
        switch (typePago) {
        case TarjetaIntegrada:
            pagoMaked = new PagoTarjetaIntegrada(dCtxSh, dCtxPago, dFTest);
            break;
        case KrediKarti:
            pagoMaked = new PagoKrediKarti(dCtxSh, dCtxPago, dFTest);
            break;
        case TMango:
            pagoMaked = new PagoTMango(dCtxSh, dCtxPago, dFTest);
            break;
        case Billpay:
            pagoMaked = new PagoBillpay(dCtxSh, dCtxPago, dFTest);
            break;
        case Paypal:
            pagoMaked = new PagoPaypal(dCtxSh, dCtxPago, dFTest);
            break;  
        case Mercadopago:
            pagoMaked = new PagoMercadopago(dCtxSh, dCtxPago, dFTest);
            break;
        case Amazon:
            pagoMaked = new PagoAmazon(dCtxSh, dCtxPago, dFTest);
            break;            
        case Postfinance:
            pagoMaked = new PagoPostfinance(dCtxSh, dCtxPago, dFTest);
            break;            
        case Trustpay:
            pagoMaked = new PagoTrustpay(dCtxSh, dCtxPago, dFTest);
            break;            
        case Multibanco:
            pagoMaked = new PagoMultibanco(dCtxSh, dCtxPago, dFTest);
            break;            
        case Paytrail:
            pagoMaked = new PagoPaytrail(dCtxSh, dCtxPago, dFTest);
            break;            
        case Dotpay:
            pagoMaked = new PagoDotpay(dCtxSh, dCtxPago, dFTest);
            break;            
        case Ideal:
            pagoMaked = new PagoIdeal(dCtxSh, dCtxPago, dFTest);
            break;   
        case Eps:
            pagoMaked = new PagoEps(dCtxSh, dCtxPago, dFTest);
            break;  
        case Sepa:
            pagoMaked = new PagoSepa(dCtxSh, dCtxPago, dFTest);
            break;            
        case Giropay:
            pagoMaked = new PagoGiropay(dCtxSh, dCtxPago, dFTest);
            break;            
        case Sofort:
            pagoMaked = new PagoSofort(dCtxSh, dCtxPago, dFTest);
            break;       
        case Klarna:
            pagoMaked = new PagoKlarna(dCtxSh, dCtxPago, dFTest);
            break;
        case KlarnaDeutsch:
            pagoMaked = new PagoKlarnaDeutsch(dCtxSh, dCtxPago, dFTest);
            break;            
        case AssistQiwi:
            pagoMaked = new PagoAssistQiwi(dCtxSh, dCtxPago, dFTest);
            break;
        case StoreCredit:
            pagoMaked = new PagoStoreCredit(dCtxSh, dCtxPago, dFTest);
            break;
        case Assist:
            pagoMaked = new PagoAssist(dCtxSh, dCtxPago, dFTest);
            break;            
        case Yandex:
            pagoMaked = new PagoYandex(dCtxSh, dCtxPago, dFTest);
            break;
        case PasarelaOtras:
            pagoMaked = new PagoPasarelaOtras(dCtxSh, dCtxPago, dFTest);
            break;
        case KoreanCreditCard:
        	pagoMaked = new PagoKoreanCreditCard(dCtxSh, dCtxPago, dFTest);
        	break;
        case ContraReembolso:            
            pagoMaked = new PagoContraReembolso(dCtxSh, dCtxPago, dFTest);
            break;
        case Bancontact:
            pagoMaked = new PagoBancontact(dCtxSh, dCtxPago, dFTest);
            break;            
        case TpvVotf:
            pagoMaked = new PagoTpvVotf(dCtxSh, dCtxPago, dFTest);
            break;
        default:
            break;
        }
        
        return pagoMaked;
    }
}
