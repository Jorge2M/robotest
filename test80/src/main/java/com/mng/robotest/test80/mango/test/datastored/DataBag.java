package com.mng.robotest.test80.mango.test.datastored;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;

import com.mng.robotest.test80.mango.test.generic.beans.ArticuloScreen;
import com.mng.robotest.test80.mango.test.utils.ImporteScreen;

/**
 * Clase que recopila los datos de la bolsa
 * @author jorge.munoz
 *
 */

public class DataBag {

    ArrayList<ArticuloScreen> listArticulos = new ArrayList<>();
    String importeTotal = "";
    String importeTransp = "";
    
    public ArrayList<ArticuloScreen> getListArticulos() {
        return this.listArticulos;
    }
    
    public String getImporteTotal() {
        return this.importeTotal;
    }
    
    public void setImporteTotal(String importeTotal) {
        this.importeTotal = importeTotal;
    }
    
    public float getImporteTotalFloat() {
        return (ImporteScreen.getFloatFromImporteMangoScreen(this.importeTotal));
    }
    
    public String getImporteTransp() {
        return this.importeTransp;
    }
    
    public void setImporteTransp(String importeTransp) {
        this.importeTransp = importeTransp;
    }
    
    public float getImporteTranspFloat() {
        return (ImporteScreen.getFloatFromImporteMangoScreen(this.importeTransp));
    }    

    public void clearBag() {
        this.listArticulos.clear();
    }
    
    public void addArticulo(ArticuloScreen articulo) {
        this.listArticulos.add(0, articulo);
    }
    
    public void removeArticulo(int index) {
        this.listArticulos.remove(index);
    }
    
    public void removeArticulo(Object article) {
        this.listArticulos.remove(article);
    }    
    
    public ArticuloScreen getArticulo(int index) {
        return this.listArticulos.get(index);
    }
    
    /**
     * Retorna una lista de artículos al modo de lo visualizado en la capa de la bolsa: si tenemos varios artículos iguales (misma referencia, talla y color) los comprimimos en 1 sola línea
     */
    public ArrayList<ArticuloScreen> getListArticlesTypeViewInBolsa() throws Exception {
        ArrayList<ArticuloScreen> listToReturn = new ArrayList<>();
        ListIterator<ArticuloScreen> it = this.listArticulos.listIterator();
        while (it.hasNext()) {
            ArticuloScreen articulo = it.next();
            if (articulo.isPresentInList(listToReturn)) 
            	articulo.incrementarCantidad(1);
            else
            	listToReturn.add(articulo);
        }

        return listToReturn;
    }
    
    /**
     * Metodo que setea el articulo dentro de una lista de articulos a partir de su referencia
     * @param articulo Articulo con precio y referencia a agregar a la lista
     */
    public void updateArticulo(ArticuloScreen articulo) {
        ListIterator<ArticuloScreen> it = this.listArticulos.listIterator();
        ArticuloScreen articuloTemp;
        while (it.hasNext()){
            articuloTemp = it.next();
            if(articuloTemp.getReferencia().compareTo(articulo.getReferencia())==0)
                it.set(articulo);
        }
    }    
    
    /**
     * @return una lista formateada con la lista de artículos + descuentos
     */
    public String getListArtDescHTML() {
        String listaStr = "";
        Iterator<ArticuloScreen> itArt = this.listArticulos.iterator();
        while (itArt.hasNext()) {
            ArticuloScreen art = itArt.next();
            String articuloStr = art.getReferencia() + " (" + art.getNombre() + ") ";
            String descuentoStr = "<b>Sin descuento</b>";
            if (art.getValePais()!=null)        
                descuentoStr = " - Descuento <b>" + art.getValePais().getPorcDescuento() + "%</b>";
                    
            listaStr = listaStr + articuloStr + descuentoStr + "<br>";
        }
        
        return listaStr;
    }
    
    /**
     * @return la suma correspondiente al precio real total (con descuentos aplicados) de la lista de artículos 
     */
    public float getPrecioRealTotalBolsa() {
        float total = 0;
        for (ArticuloScreen art : this.listArticulos)
            total += ImporteScreen.getFloatFromImporteMangoScreen(art.getPrecio()) * art.getNumero();
        
        return total;
    }
    
    /**
     * @return la suma correspondiente al precio original total (sin descuentos aplicados) de la lista de artículos
     */
    public float getPrecioOriginalTotalBolsa() {
        float total = 0;
        for (ArticuloScreen art : this.listArticulos)
            total += ImporteScreen.getFloatFromImporteMangoScreen(art.getPrecioSinDesc()) * art.getNumero();
        
        return total;
    }    
}
