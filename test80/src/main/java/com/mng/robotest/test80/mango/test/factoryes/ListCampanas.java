package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;
import org.testng.annotations.*;

import com.mng.robotest.test80.arq.utils.otras.Channel;
import com.mng.robotest.test80.mango.test.appshop.PaisIdioma;
import com.mng.robotest.test80.mango.test.appshop.campanas.CampanasData;
import com.mng.robotest.test80.mango.test.appshop.campanas.CampanasExcel;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;

public class ListCampanas {
	
    @Factory
    @Parameters({"urlBase", "AppEcom", "Channel", "countrys", "lineas"})
    public Object[] createInstances(String urlAcceso, String appEStr, String channelStr, String countrysFilterStr, String lineasFilter) 
    throws Exception {
        ArrayList<PaisIdioma> listTests = new ArrayList<>();
        AppEcom appE = AppEcom.valueOf(appEStr);
        Channel channel = Channel.valueOf(channelStr);
        
        //Cargamos la lista de campañas del Excel
    	CampanasData dataCamp = new CampanasExcel();
    	//dataCamp.loadCampanas(pathExcel);
    	dataCamp.loadCampanas("PlantillaBanners.xls");
            
        //Realizamos el filtrado de los países del XML
        Response response = getXMLFilteredWithExcelCountrys(countrysFilterStr, dataCamp);
        		
        //Iteramos a nivel de Continentes -> Países -> Idiomas
        int prioridad=0;
        for (Continente continente : response.getResponse()) {
        	for (Pais pais : continente.getPaises()) {
        		ArrayList<IdiomaPais> listIdiomaPais = getIdiomasToTest(pais, dataCamp);
        		for (IdiomaPais idioma : listIdiomaPais) {
	                if (pais.isOnlineAvailable(appE)) {
	                	List<Linea> listLinToTest = getLineasToTestFilteredWithExcelData(pais, idioma, appE, lineasFilter, dataCamp);
           	            DataCtxShop dCtxSh = new DataCtxShop(appE, channel, pais, idioma, urlAcceso);

           	            //Create new Test
                        listTests.add(new PaisIdioma(dataCamp, dCtxSh, listLinToTest, false/*recorreMenus*/, true/*recorreBanners*/, prioridad));
                        System.out.println("Creado Test \"PaisIdioma\" con datos: " + 
                        				   "Continente=" + continente.getNombre_continente() +
                                           ",Pais=" + pais.getNombre_pais() +
                                           ",Idioma=" + idioma.getCodigo().getLiteral());
                        prioridad+=1;                        
	                }
        		}
            }
        }
            		
        return listTests.toArray(new Object[listTests.size()]);
    }
    
    
    /**
     * @param filterCountrys: códigos de país separados por comas
     */
    private Response getXMLFilteredWithExcelCountrys(String filterCountrys, CampanasData dataCamp) throws Exception {
    	List<Integer> listFiterResult = new ArrayList<Integer>();
    	List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(filterCountrys);
    	HashSet<String> listCountrysExcel = dataCamp.getListCodPais();
    	for (String codPais : listCountrysExcel) {
    		int codPaisInt = Integer.valueOf(codPais);
    		if (listaPaisesInt.size()==0 || listaPaisesInt.contains(codPaisInt)) {
    			listFiterResult.add(codPaisInt);
    		}
    	}
    	
    	Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listFiterResult);
    	return (response);
    }
    
    private  ArrayList<IdiomaPais> getIdiomasToTest(Pais pais, CampanasData dataCamp) {
    	ArrayList<IdiomaPais> listIdiomasResult = new ArrayList<>();
    	HashSet<String> setIdiomasPaisCamp = dataCamp.getListIdiomas(pais.getCodigo_pais());
    	List<IdiomaPais> listIdiomaPais = pais.getListIdiomas(); 
    	for (IdiomaPais idiomaPais : listIdiomaPais) {
    		if (setIdiomasPaisCamp.contains(idiomaPais.getCodigo().toString())) {
    			listIdiomasResult.add(idiomaPais);
    		}
    	}
    	
    	return listIdiomasResult;
    }
    
    /**
     * @param filterLines: nombres de línea separadas por comas
     */
    private ArrayList<Linea> getLineasToTestFilteredWithExcelData(Pais pais, IdiomaPais idioma, AppEcom app, String filterLines, CampanasData dataCamp) {
    	ArrayList<Linea> listLineasResult = new ArrayList<>();
    	HashSet<String> setLineasPais = dataCamp.getListLines(pais.getCodigo_pais(), idioma.getCodigo().toString());
    	List<Linea> listLineasPais = Utilidades.getLinesToTest(pais, app, filterLines);
    	for (Linea linea : listLineasPais) {
    		if (setLineasPais.contains(linea.getId()) && Utilidades.lineaToTest(linea, app)) {
    			listLineasResult.add(linea);
    		}
    	}
    	
    	return listLineasResult;
    }
}