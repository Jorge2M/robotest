package com.mng.robotest.test80.mango.test.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.github.jorge2m.testmaker.conf.Channel;

public class PagoGetter {
	
	public static List<PaymentCountry> getListPayments(String codCountry, AppEcom app, boolean isEmpleado) {
		List<Pais> listCountries = PaisGetter.get(Arrays.asList(codCountry));
		return getListPaymentsFromCountries(listCountries, app, isEmpleado);
	}
	
	public static List<PaymentCountry> getListPayments(List<String> listCodCountries, AppEcom app, boolean isEmpleado) {
		List<Pais> listCountries = PaisGetter.get(listCodCountries);
		return getListPaymentsFromCountries(listCountries, app, isEmpleado);
	}
	
	public static List<PaymentCountry> getListPayments(AppEcom app, boolean isEmpleado) {
		List<Pais> listCountries = PaisGetter.getAllCountries();
		return getListPaymentsFromCountries(listCountries, app, isEmpleado);
	}

	public static List<String> getLabelsPaymentsAlphabetically(Channel channel, AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPayments = getListPayments(app, isEmpleado);
		return getLabelsPaymentsFromCountries(listPayments, channel, app); 	
	}
	
	public static List<String> getLabelsPaymentsAlphabetically(
			List<String> listCodCountries, Channel channel, AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPayments = getListPayments(listCodCountries, app, isEmpleado);
		return getLabelsPaymentsFromCountries(listPayments, channel, app); 
	}
	
	private static List<String> getLabelsPaymentsFromCountries(List<PaymentCountry> listPayments, Channel channel, AppEcom app) {
		List<String> listLabels = new ArrayList<>();
		for (PaymentCountry payment : listPayments) {
			listLabels.add(payment.pago.getNameFilter(channel, app));
		}
		List<String> listSortedAndWithoutDuplicates = listLabels.stream()
			.distinct()
			.sorted()
			.collect(Collectors.toList());
		return listSortedAndWithoutDuplicates;
	}
	
	private static List<PaymentCountry> getListPaymentsFromCountries(List<Pais> listCountries, AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPaymentsToReturn = new ArrayList<>();
		for (Pais pais : listCountries) {
			if (pais.getTiendasOnlineList().size()>0 && pais.getTiendasOnlineList().contains(app)) {
				for (Pago pago : pais.getListPagos()) {
					if ((!isEmpleado || (isEmpleado && pago.isForEmpleado())) &&
						//El storecredito lo mantenemos al margen de la lista pues no aparece como un icono
						(pago.getTypePago()!=TypePago.StoreCredit) && 
						pago.getTiendasList().contains(app)) {
						PaymentCountry paymentCountry = new PaymentCountry(pais.getCodigo_pais(), pago);
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
