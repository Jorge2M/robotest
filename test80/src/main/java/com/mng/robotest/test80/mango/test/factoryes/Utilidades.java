package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.mng.testmaker.jdbc.dao.ParamsDAO;
import com.mng.testmaker.utils.conf.Log4jConfig;
import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Continente;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Linea;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Response;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;


public class Utilidades {
	
    static Logger pLogger = LogManager.getLogger(Log4jConfig.log4jLogger);

    /**
     * Get the lines to test
     * @param lineasParam list of lines, comma separated
     */
    public static List<Linea> getLinesToTest(Pais pais, AppEcom appE, String lineasParam) {
        List<Linea> lineasXML = pais.getShoponline().getLineasToTest(appE);
        if ("".compareTo(lineasParam)==0) {
            return lineasXML;
        }
        
        List<Linea> lineasToTest = new ArrayList<>();
        ArrayList<String> lineasArray = new ArrayList<>(Arrays.asList(lineasParam.split(",")));
        for (Linea linea : lineasXML) {
             if (lineasArray.contains(linea.getId())) {
                 lineasToTest.add(linea);
             }
        }
        
        return lineasToTest;
    }
    
    //Determina si un determinado país pertenece al Top (en estos momentos top-6)
    public static boolean isTop(String codigoPais, int numTops) {
        ArrayList<String> listTop10 = new ArrayList<>();
        listTop10.add("001"); //España (Península y Baleares)
        listTop10.add("004"); //Deutschland
        listTop10.add("006"); //United Kingdom
        listTop10.add("011"); //France métropolitaine
        listTop10.add("052"); //Türkiye
        listTop10.add("400"); //USA
        listTop10.add("003"); //Nederland
        listTop10.add("060"); //Poland
        listTop10.add("005"); //Italia
        listTop10.add("075"); //Россия (Российская Федерация)
        boolean encontrado = false;
        for (int i=0; i<numTops && i<10; i++) {
            if (listTop10.get(i).compareTo(codigoPais)==0) {
            	encontrado = true;
            }
        }
                
        return encontrado;
    }
	
    //Determina si un determinado país pertenece al Top 10 (-> Lo ideal sería sacarlo de algún sitio)
    public static boolean isTop10(int codigo_pais) {	
        ArrayList<Integer> listTop = new ArrayList<>();
        listTop.add(Integer.valueOf(004)); //Deutschland");
        listTop.add(Integer.valueOf(001)); //España (Península y Baleares)");
        listTop.add(Integer.valueOf(011)); //France métropolitaine");
        listTop.add(Integer.valueOf(003)); //Nederland");
        listTop.add(Integer.valueOf(060)); //Poland ");
        listTop.add(Integer.valueOf(052)); //Türkiye");
        listTop.add(Integer.valueOf(006)); //United Kingdom");
        listTop.add(Integer.valueOf(400)); //USA ");
        listTop.add(Integer.valueOf(075)); //Россия (Российская Федерация)");
        listTop.add(Integer.valueOf(720)); //中国");
        boolean encontrado = false;
        if (listTop.contains(Integer.valueOf(codigo_pais))) {
            encontrado = true;
        }
        return encontrado;
    }
	
    public static String rtrim(String s) {
        int i = s.length()-1;
        while (i >= 0 && Character.isWhitespace(s.charAt(i))) {
            i--;
        }
        return s.substring(0,i+1);
    }

    public static String ltrim(String s) {
        int i = 0;
        while (i < s.length() && Character.isWhitespace(s.charAt(i))) {
            System.out.println("s.charAt(i)  "+s.charAt(i));
            i++;
        }
        return s.substring(i);
    }		
    
    /**
     * @param countrysToMaintain: lista de países en formato "001,030..." (puedes ser "*")
     */
    public static List<Pais> getListCountrysFiltered(String countrysToMaintain) throws Exception {
        List<Integer> listCountrysToMaintain = UtilsMangoTest.getListaPaisesInt(countrysToMaintain);
        return (getListCountrysFiltered(listCountrysToMaintain));
    }
    
    public static List<Pais> getListCountrysFiltered(List<Integer> listaCodPais) {
    	List<Pais> listToReturn = new ArrayList<>();
    	boolean applyFilter = true;
    	if (listaCodPais==null || listaCodPais.size()==0) {
    		applyFilter = false;
    	}
    	
    	Response response;
    	try {
    		response = filtradoListaPaises(!applyFilter, listaCodPais);
    	}
    	catch (JAXBException e) {
    		pLogger.error("Problem filtering Countrys List", e);
    		return null;
    	}
    	
    	Iterator<Continente> itContinentes = response.getResponse().iterator();
        while (itContinentes.hasNext()) {
            Continente continente = itContinentes.next();
            Iterator<Pais> itPaises = continente.getPaises().iterator();
            while (itPaises.hasNext()) {
            	listToReturn.add(itPaises.next());
            }
        }
        
        return listToReturn;
    }
    
    /**
     * Función que retorna la lista de países obtenidos de un XML filtrada
     * @param xmlPaises: archivo XML en la que se encuentra la definición de todos los países
     * @param todosPaises: indicador de si se han de retornar o no todos los países
     * @param listaCodPais: países que se han de retornar
     * @return lista de países filtrada
     * @throws Exception
     */
    private static Response filtradoListaPaises(boolean todosPaises, List<Integer> listaCodPais) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Response.class);
        Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
	
        Response response;
        String xmlPaisesXentorno = Utilidades.xmlPaisesXentorno(Constantes.XMLPaises);
        response = (Response) jaxbUnmarshaller.unmarshal(Utilidades.class.getResourceAsStream(xmlPaisesXentorno));	
        if (!todosPaises && listaCodPais!=null && listaCodPais.size()>0) {
            //Iteramos a nivel de Continentes -> Países
            Iterator<Continente> itContinentes = response.getResponse().iterator();
            while (itContinentes.hasNext()) {
                Continente continente = itContinentes.next();
                Iterator<Pais> itPaises = continente.getPaises().iterator();
                while (itPaises.hasNext()) {
                    Pais pais = itPaises.next(); 
                    if (!listaCodPais.contains(Integer.valueOf(pais.getCodigo_pais()))) {
                        itPaises.remove();
                    }
                }
            }
        }
	
        return response;
    }

    
    //Obtenemos la lista de países
    public static List<Pais> getListaPaises(Iterator<Continente> itContinentes) {
    	List<Pais> listPaises = new ArrayList<>();
    	while (itContinentes.hasNext()) {
    	    Continente continente = itContinentes.next();
    	    Iterator<Pais> itPaises = continente.getPaises().iterator();
    	    while (itPaises.hasNext()) {
    	        Pais pais = itPaises.next();
	        	
    	        //Algunos de los nombres del XML no coinciden con los de la WEB. De momento los regularizamos
    	        //Utilidades.normalizaPais(pais, false, isOutlet);
	        	
    	        listPaises.add(pais);
    	    }
    	}
    	
    	return listPaises;
    }
    
    public static String xmlPaisesXentorno(String nombreXML) {
    	String resultado = nombreXML.substring(0,nombreXML.indexOf(".xml")) + ".xml";
    	return (resultado);
    }
    
    /**
     * @return nos indica si está activo el flag que indica que se ha de validar la pestaña de rebajas
     */
    public static boolean validarRebajasFromBD() {
        boolean rebajas = false;
        HashMap<String, String> listParams = ParamsDAO.listParams();
        if (listParams.get("REBAJAS_VALIDAR")!=null) {
            rebajas = ((listParams.get("REBAJAS_VALIDAR")).compareTo("1")==0);
        }
        return rebajas;
    }    
    
    public static boolean lineaToTest(Linea linea, AppEcom appE) {
        if (appE==AppEcom.outlet && linea.getOutlet().compareTo("s")!=0) {
            return false;
        }
        if (appE!=AppEcom.outlet && linea.getShop().compareTo("s")!=0) {
            return false;
        }
        if (linea.getMenus().compareTo("s")!=0 && !linea.existsSublineas()) {
            return false;
        }
        return true;
    }    
}
