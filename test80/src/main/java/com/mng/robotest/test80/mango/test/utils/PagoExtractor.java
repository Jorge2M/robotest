package com.mng.robotest.test80.mango.test.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.conf.Channel;

public class PagoExtractor {
	
	private static final List<PaymentCountry> ListPaymentCountries = getAllPayments();
	
	private static List<PaymentCountry> getAllPayments() {
		List<PaymentCountry> listPaymentsToReturn = new ArrayList<>();
		List<Pais> listAllCountries = PaisGetter.getAllCountries();
		for (Pais pais : listAllCountries) {
			String codPais = pais.getCodigo_pais();
			for (Pago pago : pais.getListPagos()) {
				listPaymentsToReturn.add(new PaymentCountry(codPais, pago));
			}
		}
		return listPaymentsToReturn;
	}
	
	public static List<PaymentCountry> getListPayments(List<String> listCountries, Channel channel, AppEcom app) {
		//Se ha de controlar que el país tenga shop/outlet porque en los pagos siempre está shop,outlet
		List<PaymentCountry> listPaymentsToReturn;
		for (PaymentCountry payment : ListPaymentCountries) {
			if (listCountries.contains(payment.codCountry)) {
				listPaymentsToReturn.add(payment);
			}
		}
		return listPaymentsToReturn;
	}

    public List<String> getPaymentsAllCountries(Channel channel, AppEcom appE) throws Exception {
    	TreeSet<String> countrysTree = getListPagoFilterNames(codCountrysCommaSeparated, channel, appE, false);
    	List<String> listCountrys = new ArrayList<>();
    	for (String country : countrysTree) {
    		listCountrys.add(country);
    	}
    	return listCountrys;
    }
    
    public List<Pago> getListPayments(Channel channel, AppEcom appE) {
    	
    }
    
    private static TreeSet<String> getListNameFilterPagos(Channel channel, AppEcom appE, boolean isEmpl) {
        TreeSet<String> listPagoFilterNames = new TreeSet<>();
        for (Pais pais : listPaises) {
            for (Pago pago : pais.getListPagosTest(appE, isEmpl)) {
                listPagoFilterNames.add(pago.getNameFilter(channel));
            }
        }
        
        return listPagoFilterNames;        
    }
    
    public static TreeSet<String> getListPagoFilterNames(Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
    	List<Integer> listaCodPais = null;
    	List<Pais> listPaises = Utilidades.getListCountrysFiltered(listaCodPais);
        return (getListNameFilterPagos(listPaises, channel, appE, isEmpl));
    }  
    
    public static TreeSet<String> getListPagoFilterNames(String codCountrysCommaSeparated, Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
        if ("".compareTo(codCountrysCommaSeparated)==0 || 
        	"*".compareTo(codCountrysCommaSeparated)==0 || 
        	"X".compareTo(codCountrysCommaSeparated)==0) {
            return (getListPagoFilterNames(channel, appE, isEmpl));
        }
            
        String[] listCountrys = codCountrysCommaSeparated.split(",");
        ArrayList<Integer> listCodCountrys = new ArrayList<>();
        for (String codCountry : listCountrys) {
            listCodCountrys.add(Integer.valueOf(codCountry));
        }
        
        return (getListPagoFilterNames(listCodCountrys, channel, appE, isEmpl));
    }
    
    public static TreeSet<String> getListPagoFilterNames(List<Integer> listCountrysFilter, Channel channel, AppEcom appE, boolean isEmpl) 
    throws Exception {
        List<Pais> listPaises = Utilidades.getListCountrysFiltered(listCountrysFilter);
        return (getListNameFilterPagos(listPaises, channel, appE, isEmpl));
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
