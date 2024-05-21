package com.mng.robotest.testslegacy.beans;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public enum TypePago {
	TARJETA_INTEGRADA("TRJintegrada", "credit_card", 0,4), 
	KREDI_KARTI("KrediKarti", "", 0), 
	BILLPAY("Billpay", "", 0), 
	PAYPAL("Paypal", "paypal", 0,4), 
	MERCADOPAGO("Mercadopago", "", 0,4), 
	AMAZON("Amazon", "", 0), 
	POSTFINANCE("Postfinance", "", 0,4), 
	TRUSTPAY("Trustpay", "", 3), 
	MULTIBANCO("Multibanco", "", 3), 
	PAYTRAIL("Paytrail", "", 0,4), 
	DOTPAY("Dotpay", "", 0), 
	IDEAL("Ideal", "", 0,4), 
	EPS("Eps", "", 0,4),
	SEPA("Sepa", "", -1,3), 
	GIROPAY("Giropay", "", 0,4), 
	SOFORT("Sofort", "", 0,4), 
	KLARNA("Klarna", "", 3),
	KLARNA_UK("KlarnaUK", "", 3),
	PAYSECURE_QIWI("PaysecureQiwi", ""), 
	ASSIST("Assist", ""), 
	PASARELA_OTRAS("PasarelaOtras", ""), 
	KCP("KCP", "", 0,4),
	CONTRA_REEMBOLSO("ContraReembolso", "", 0),
	BANCONTACT("Bancontact", "", 0,4),
	PROCESS_OUT("ProcessOut", "", 0,4),
	YANDEX("Yandex", "", 0,4),
	PAYMAYA("Paymaya", "", 0,4),
	STORE_CREDIT("storecredit", "", 0,4);
	
	String name;
	String code;
	Integer[] estados;
	private TypePago(String name, String code, Integer... estados) {
		this.name = name;
		this.code = code;
		this.estados = estados;
	}
	public static TypePago getTypePago(String name) {
		var pagoOpt = Stream.of(TypePago.values())
			.filter(p -> p.getName().compareTo(name)==0)
			.findFirst();
		
		if (pagoOpt.isPresent()) {
			return pagoOpt.get();
		} else {
			return TypePago.TARJETA_INTEGRADA;
		}
	}
	public String getName() {
		return name;
	}
	public String getCode() {
		return code;
	}
	public List<Integer> getEstados() { 
		return Arrays.asList(estados);
	}
}
