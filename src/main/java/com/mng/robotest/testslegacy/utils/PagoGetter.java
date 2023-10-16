package com.mng.robotest.testslegacy.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;
import com.mng.robotest.testslegacy.beans.TypePago;
import com.github.jorge2m.testmaker.conf.Channel;

public class PagoGetter {
	
	private PagoGetter() {}
	
	public static List<PaymentCountry> getListPayments(String codCountry, AppEcom app, boolean isEmpleado) {
		var listCountries = PaisGetter.from(Arrays.asList(codCountry));
		return getListPaymentsFromCountries(listCountries, app, isEmpleado);
	}
	
	public static List<PaymentCountry> getListPayments(List<String> listCodCountries, AppEcom app, boolean isEmpleado) {
		var listCountries = PaisGetter.from(listCodCountries);
		return getListPaymentsFromCountries(listCountries, app, isEmpleado);
	}
	
	public static List<PaymentCountry> getListPayments(AppEcom app, boolean isEmpleado) {
		var listCountries = PaisGetter.getAllCountries();
		return getListPaymentsFromCountries(listCountries, app, isEmpleado);
	}

	public static List<String> getLabelsPaymentsAlphabetically(Channel channel, AppEcom app, boolean isEmpleado) {
		var listPayments = getListPayments(app, isEmpleado);
		return getLabelsPaymentsFromCountries(listPayments, channel, app); 	
	}
	
	public static List<String> getLabelsPaymentsAlphabetically(
			List<String> listCodCountries, Channel channel, AppEcom app, boolean isEmpleado) {
		var listPayments = getListPayments(listCodCountries, app, isEmpleado);
		return getLabelsPaymentsFromCountries(listPayments, channel, app); 
	}
	
	private static List<String> getLabelsPaymentsFromCountries(List<PaymentCountry> listPayments, Channel channel, AppEcom app) {
		List<String> listLabels = new ArrayList<>();
		for (PaymentCountry payment : listPayments) {
			listLabels.add(payment.pago.getNameFilter(channel, app));
		}
		return listLabels.stream()
			.distinct()
			.sorted()
			.toList();
	}
	
	private static List<PaymentCountry> getListPaymentsFromCountries(List<Pais> listCountries, AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPaymentsToReturn = new ArrayList<>();
		for (var pais : listCountries) {
			if (!pais.getTiendasOnlineList().isEmpty() && pais.getTiendasOnlineList().contains(app)) {
				for (var pago : pais.getListPagos()) {
					if ((!isEmpleado || (isEmpleado && pago.isForEmpleado())) &&
						//El storecredito lo mantenemos al margen de la lista pues no aparece como un icono
						(pago.getTypePago()!=TypePago.STORE_CREDIT) && 
						pago.getTiendasList().contains(app)) {
						var paymentCountry = new PaymentCountry(pais.getCodigoPais(), pago);
						listPaymentsToReturn.add(paymentCountry);
					}
				}
			}
		}
		return listPaymentsToReturn;
	}

	public static class PaymentCountry {
		public String codCountry;
		public Pago pago;
		public PaymentCountry(String codCountry, Pago pago) {
			this.codCountry = codCountry;
			this.pago = pago;
		}
	}
}
