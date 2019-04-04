package com.mng.robotest.test80.mango.test.factoryes;

import java.util.*;

import org.testng.annotations.*;

import com.mng.robotest.test80.mango.test.appshop.PaisAplicaVale;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum;
import com.mng.robotest.test80.mango.test.data.DataCtxShop;
import com.mng.robotest.test80.mango.test.data.ValesData;
import com.mng.robotest.test80.mango.test.data.AppEcomEnum.AppEcom;
import com.mng.robotest.test80.mango.test.data.ChannelEnum.Channel;
import com.mng.robotest.test80.mango.test.data.ValesData.Campanya;
import com.mng.robotest.test80.mango.test.factoryes.Utilidades;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.*;
import com.mng.robotest.test80.mango.test.generic.UtilsMangoTest;
import com.mng.robotest.test80.mango.test.generic.beans.ValePais;


public class ValesPaises {

    List<ValePais> listaPaisesVales = new ArrayList<>();
    
    @Factory
    @Parameters({"urlBase", "countrys", "AppEcom", "filtroCalendar"})
    public Object[] createInstances(String urlAcceso, String countrys, String appStr, String filtroCalendarStr) 
    throws Exception {
    	AppEcom app = AppEcomEnum.getAppEcom(appStr);
        Calendar currDtCal = Calendar.getInstance();
        boolean filterCal = false;
        if ("true".compareTo(filtroCalendarStr)==0) {
            filterCal = true;
        }
        
        if (!filterCal ||
        	currDtCal.getTimeInMillis() > Campanya.GLAM19.getFechaInit().getTimeInMillis()) {
        	this.listaPaisesVales.addAll(ValesData.getListVales(Campanya.GLAM19, filterCal));
        }
//        if (!filterCal ||
//        	currDtCal.getTimeInMillis() > Campanya.VIP18.getFechaInit().getTimeInMillis()) {
//        	this.listaPaisesVales.addAll(ValesData.getListVales(Campanya.VIP18, filterCal));
//        }
//        
//        if (!filterCal ||
//            currDtCal.getTimeInMillis() > Campanya.VIPMNG.getFechaInit().getTimeInMillis()) {
//        	this.listaPaisesVales.addAll(ValesData.getListVales(Campanya.VIPMNG, filterCal));
//        }

        ArrayList<PaisAplicaVale> listTests = new ArrayList<>();
                
        //Obtenemos la lista de países como lista de enteros y la utilizamos para filtrar el XML
        List<Integer> listaPaisesInt = UtilsMangoTest.getListaPaisesInt(countrys);
        Response response = Utilidades.filtradoListaPaises(false/*todosPaises*/, listaPaisesInt);    
        
        //Iteramos a nivel de Continentes -> Países -> Idioma (sólo el 1o) -> Vales
        int prioridad=0;
        for (Continente continente : response.getResponse()) {
            for (Pais pais : continente.getPaises()) {
                IdiomaPais idioma = pais.getListIdiomas().get(0);
                List<ValePais> listaValesPais = listValesPais(pais.getCodigo_pais());
                for (ValePais valePais : listaValesPais) {
                    DataCtxShop dCtxSh = new DataCtxShop(app, Channel.desktop, pais, idioma, valePais, urlAcceso);
                    listTests.add(new PaisAplicaVale(dCtxSh, continente, null, false, prioridad));
                    prioridad+=1;
                                    
                    System.out.println(
                        "Creado Test con datos: " +
                        "Continente=" + continente.getNombre_continente() +
                        ",Pais=" + pais.getNombre_pais() +
                        ",Idioma=" + idioma.getCodigo().getLiteral() +
                        ",Vale=" + valePais.getCodigoVale() +
                        ",Valido? " + valePais.isValid() + 
                        ",Porcentaje Descuento=" + valePais.getPorcDescuento()
                    );
                }
            }
        }
                        
        return listTests.toArray(new Object[listTests.size()]);
    }   
    
    /**
     * @return si se ha de crear un test para un país concreto
     */
    protected boolean paisToTest(Pais pais) {
        return (
            pais.getExists().compareTo("n")!=0 &&
            pais.getShop_online().compareTo("true")==0
        );
    }
    
    public List<ValePais> listValesPais(String codigo_pais) {      
        List<ValePais> listaValesPais = new ArrayList<>();
        for (ValePais valePais : listaPaisesVales) {
            if (valePais.getPais().getCodigo_pais().compareTo(codigo_pais)==0)
                listaValesPais.add(valePais);
        }
                
        return listaValesPais;
    }
}
