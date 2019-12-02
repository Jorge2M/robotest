package com.mng.robotest.test80.mango.test.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.conf.Channel;

public class PagoGetter {
	
	public static List<PaymentCountry> getListPayments(List<String> listCodCountries, AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPaymentsToReturn = new ArrayList<>();
		List<Pais> listCountries = PaisGetter.get(listCodCountries);
		for (Pais pais : listCountries) {
			if (pais.getTiendasOnlineList().contains(app)) {
				for (Pago pago : pais.getListPagos()) {
					if ((!isEmpleado || (isEmpleado && pago.isForEmpleado())) &&
						pago.getTiendasList().contains(app)) {
						PaymentCountry paymentCountry = new PaymentCountry(pais.getCodigo_pais(), pago);
						listPaymentsToReturn.add(paymentCountry);
					}
				}
			}
		}
		return listPaymentsToReturn;
	}
	
	public static List<String> getLabelsPaymentsAlphabetically(
			List<String> listCodCountries, Channel channel, AppEcom app, boolean isEmpleado) {
		List<String> listLabels = new ArrayList<>();
		List<PaymentCountry> listPayments = getListPayments(listCodCountries, app, isEmpleado);
		for (PaymentCountry payment : listPayments) {
			listLabels.add(payment.pago.getNombre(channel));
		}
		List<String> listSortedAndWithoutDuplicates = listLabels.stream()
			.distinct()
			.sorted()
			.collect(Collectors.toList());

		return listSortedAndWithoutDuplicates;
	}

	static class PaymentCountry {
		public String codCountry;
		public Pago pago;
		public PaymentCountry(String codCountry, Pago pago) {
			this.codCountry = codCountry;
			this.pago = pago;
		}
	}
}
