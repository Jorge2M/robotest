package com.mng.robotest.test80.mango.test.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.testmaker.conf.Channel;

public class PagosExtractor {
	
	private final List<Pais> listCountrysFilter;
	
	private PagosExtractor() {
		this.listCountrysFilter = null;
	}
	private PagosExtractor(List<String> listCodCountrysFilter) {
		this.listCountrysFilter = listCountrysFilter;
	}
	public static PagosExtractor fromAllCountrys() {
		return new PagosExtractor();
	}
	public static PagosExtractor fromCountrys(List<Pais> listCountrysFilter) {
		return new PagosExtractor(listCountrysFilter);
	}

    public List<String> getListPaymentNames(Channel channel, AppEcom appE) throws Exception {
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
  
    

}
