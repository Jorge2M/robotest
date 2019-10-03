package com.mng.robotest.test80.mango.test.datastored;

import com.mng.testmaker.utils.State;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pais;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test80.mango.test.stpv.shop.checkout.envio.DataDeliveryPoint;


public class DataPedido {
    String emailCheckout;
    DataBag dataBag = new DataBag();
    private Pago pago;
    private String codpedido;
    private String idcompra;
    private String codtipopago;
    private State resejecucion;
    private String codigopais;
    private String nombrepais;
    private String codigoAlmacen;
    private String importeTotal = "";
    private String importeTotalSinSaldoCta = "";
    private String direccionEnvio = "";
    private TipoTransporte tipoTransporte = TipoTransporte.STANDARD;
    private DataDeliveryPoint dataDeliveryPoint = null;
	
    public DataPedido(Pais pais) {
        setCodigopais(pais.getCodigo_pais());
        setNombrepais(pais.getNombre_pais());
    }
    
    public String getEmailCheckout() {
        return this.emailCheckout;
    }
    
    public void setEmailCheckout(String emailCheckout) {
        this.emailCheckout = emailCheckout;
    }
    
    public DataBag getDataBag() {
        return this.dataBag;
    }
        
    public void setDataBag(DataBag dataBag) {
        this.dataBag = dataBag;
    }
    
    public Pago getPago() {
        return this.pago;
    }
        
    public void setPago(Pago pago) {
        this.pago = pago;
    }
        
    public String getCodpedido() {
        return this.codpedido;
    }
        
    public void setCodpedido(String codpedido) {
        this.codpedido = codpedido;
    }
    
    public String getIdCompra() {
        return this.idcompra;
    }
        
    public void setIdCompra(String idcompra) {
        this.idcompra = idcompra;
    }    
        
    public String getCodtipopago() {
        return this.codtipopago;
    }
        
    public void setCodtipopago(String codtipopago) {
        this.codtipopago = codtipopago;
    }
        
    public State getResejecucion() {
        return this.resejecucion;
    }
        
    public void setResejecucion(State resejecucion) {
        this.resejecucion = resejecucion;
    }   
    
    public boolean isResultadoOk() {
    	return (getResejecucion()==State.Ok);
    }
        
    /**
     * @return el código de pedido regularizado según el país para su búsqueda a nivel de Manto
     */
    public String getCodigoPedidoManto() {
        String codRegularizado = "";
        switch (this.getCodigoPais()) {
        case "400": //USA
            codRegularizado = "40"+getCodpedido();
            break;
        case "075": //Rusia
            codRegularizado = "RU"+getCodpedido();
            break;
        case "052": //Turquía
            codRegularizado = "TR"+getCodpedido();
            break;                  
        case "720": //China
            codRegularizado = "CN"+getCodpedido();
            break;            
        default:
            codRegularizado = getCodpedido();
            break;
        }
            
        return codRegularizado;
    }    
    
    public String getCodigoPais() {
        return this.codigopais;
    }
	
    public String getNombrePais() {
        return this.nombrepais;
    }
	
    public void setCodigopais(String codigopais) {
        this.codigopais = codigopais;
    }	
	
    public void setNombrepais(String nombrepais) {
        this.nombrepais = nombrepais;
    }
    
    public void setCodigoAlmacen(String codigoAlmacen) {
    	this.codigoAlmacen = codigoAlmacen;
    }
	
    public String getCodigoAlmacen() {
    	return this.codigoAlmacen;
    }
    
    public String getImporteTotal() {
        return this.importeTotal;
    }
        
    public void setImporteTotal(String importeTotal) {
        this.importeTotal = importeTotal;
    }    
    
    public String getImporteTotalSinSaldoCta() {
        return this.importeTotalSinSaldoCta;
    }
    
    public void setImporteTotalSinSaldoCta(String importeTotalSinSaldoCta) {
        this.importeTotalSinSaldoCta = importeTotalSinSaldoCta;
    }
    
    public String getImporteTotalManto() {
        if ("".compareTo(this.importeTotalSinSaldoCta)!=0) {
            return this.importeTotalSinSaldoCta;
        }
        if ("".compareTo(this.importeTotal)!=0) {
            return this.importeTotal;
        }
        return this.dataBag.importeTotal;
    }    
	
    public String getDireccionEnvio() {
        return this.direccionEnvio;
    }
    
    public void setDireccionEnvio(String direccionEnvio) {
        this.direccionEnvio = direccionEnvio;
    }
    
    public TipoTransporte getTypeEnvio() {
        return this.tipoTransporte;
    }
    
    public void setTypeEnvio(TipoTransporte tipoTransporte) {
        this.tipoTransporte = tipoTransporte;
    }
    
    public DataDeliveryPoint getDataDeliveryPoint() {
        return this.dataDeliveryPoint;
    }
    
    public void setDataDeliveryPoint(DataDeliveryPoint dataDeliveryPoint) {
        this.dataDeliveryPoint = dataDeliveryPoint;
    }    
}
