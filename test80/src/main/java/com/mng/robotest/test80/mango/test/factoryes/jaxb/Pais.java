package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import java.net.URI;
import java.sql.Timestamp;
import java.util.*;
import javax.xml.bind.annotation.*;

import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.factoryes.jaxb.Pago.TypePago;
import com.mng.robotest.test80.mango.test.utils.LevelPais;

@XmlRootElement
public class Pais {

    public static final int MAX_PAGOS = 25;
	
    String nombre_pais;
    String codigo_pais;
        
    //Indica si el país existe en la Shop (sólo es 'n' para el caso de Japón que sólo aparece a nivel de la preHome)
    String exists = "s";
    String codigo_alf;
        
    @XmlElement(name="idioma") 
    List<IdiomaPais> listIdiomas = new LinkedList<>();
        
    Direccharnolatinos direccharnolatinos = new Direccharnolatinos();
    String tarifa;
    String paistop="";
    String rgpd="";
    String codpos;
    String telefono;
    String dni;
    String shop_online;
    String outlet_online;
    String miscompras;
    String moneda;
    String maximo_bolsa;
    String control_net;
    String ticketStore_enabled;
    
    Tienda tienda = new Tienda();
    Shoponline shoponline = new Shoponline();
    AccesoEmpl accesoEmpl = new AccesoEmpl();
    AccesoVOTF accesoVOTF = new AccesoVOTF();
    
    @XmlElement(name="pago") 
    List<Pago> listPagos = new LinkedList<>();
    
    String invertida;
    String marcamng;
    String emailuser;
    String passuser;
    String mobiluser;
    String micuenta;
    
    @XmlElement(name="metodopago") 
    List<String> listMetodopagos = new LinkedList<>();
    
    public String getNombre_pais() {
        return this.nombre_pais;
    }

    @XmlAttribute(name="nombre_pais")
    public void setNombre_pais(String nombre_pais) {
        this.nombre_pais = nombre_pais;
    }        
    
    public String getCodigo_pais() {
        return this.codigo_pais;
    }
    
    @XmlAttribute(name="exists")
    public void setExists(String exists) {
        this.exists = exists;
    }
    
    public String getExists() {
        return this.exists;
    }        
    
    @XmlElement
    public void setCodigo_pais(String codigo_pais) {
        this.codigo_pais = codigo_pais;
    }
    
    public String getCodigo_alf() {
        return this.codigo_alf;
    }

    @XmlElement
    public void setCodigo_alf(String codigo_alf) {
        this.codigo_alf = codigo_alf;
    }
    
    public List<IdiomaPais> getListIdiomas() {
        return this.listIdiomas;
    }

    public String getTarifa() {
        return this.tarifa;
    }
    
    @XmlElement
    public void setTarifa(String tarifa) {
        this.tarifa = tarifa;
    }
    
    public String getPaistop() {
        return this.paistop;
    }

    @XmlElement
    public void setPaistop(String paistop) {
        this.paistop = paistop;
    }        
    
    public String getRgpd() {
        return this.rgpd;
    }

    @XmlElement (name="rgpd")
    public void setRgpd(String rgpd) {
        this.rgpd = rgpd;
    } 
    
    public String getCodpos() {
        return this.codpos;
    }

    @XmlElement
    public void setCodpos(String codpos) {
        this.codpos = codpos;
    }        
    
    public String getTelefono() {
        return this.telefono;
    }

    @XmlElement
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }        
    
    public String getDni() {
        return this.dni;
    }

    @XmlElement
    public void setDni(String dni) {
        this.dni = dni;
    }
    
    public String getShop_online() {
        return this.shop_online;
    }
    
    @XmlElement
    public void setShop_online(String shop_online) {
        this.shop_online = shop_online;
    }
    
    public String getOutlet_online() {
        return this.outlet_online;
    }	
    
    public boolean isOnlineAvailable(AppEcom appE) {
    	return (appE!=AppEcom.outlet || 
    		   (appE==AppEcom.outlet && getOutlet_online().compareTo("true")==0));
    }

    @XmlElement
    public void setOutlet_online(String outlet_online) {
        this.outlet_online = outlet_online;
    }
    
    public String getMiscompras() {
        return this.miscompras;
    }   
    
    public boolean isMisCompras() {
        return ("true".compareTo(getMiscompras())==0);
    }

    @XmlElement
    public void setMiscompras(String miscompras) {
        this.miscompras = miscompras;
    }    
    
    public String getMoneda() {
        return this.moneda;
    }

    @XmlElement
    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMaximo_bolsa() {
        return this.maximo_bolsa;
    }

    @XmlElement
    public void setMaximo_bolsa(String maximo_bolsa) {
        this.maximo_bolsa = maximo_bolsa;
    } 

    public String getControl_net() {
        return this.control_net;
    }
    
    @XmlElement
    public void setControl_net(String control_net) {
        this.control_net = control_net;
    }        
    
    public boolean isTicketStoreEnabled() {
    	return (getTicketStore_enabled()!=null && 
    			"true".compareTo(getTicketStore_enabled())==0);
    }
    
    public String getTicketStore_enabled() {
        return this.ticketStore_enabled;
    }
    
    @XmlElement
    public void setTicketStore_enabled(String ticketStore_enabled) {
        this.ticketStore_enabled = ticketStore_enabled;
    }

    public Direccharnolatinos getDireccharnolatinos() {
        return this.direccharnolatinos;
    }

    @XmlElement(name="direccharnolatinos")
    public void setCheckchar(Direccharnolatinos direccharnolatinos) {
        this.direccharnolatinos = direccharnolatinos;
    }
    
    public Tienda getTienda() {
        return this.tienda;
    }

    @XmlElement(name="tienda")
    public void setTienda(Tienda tienda) {
        this.tienda = tienda;
    }        
 
    public Shoponline getShoponline() {
        return this.shoponline;
    }

    @XmlElement(name="shoponline")
    public void setShoponline(Shoponline shoponline) {
        this.shoponline = shoponline;
    }
    
    public AccesoEmpl getAccesoEmpl() {
        return this.accesoEmpl;
    }

    @XmlElement(name="accesoempl")
    public void setAccesoEmpl(AccesoEmpl accesoEmpl) {
        this.accesoEmpl = accesoEmpl;
    }
    
    public AccesoVOTF getAccesoVOTF() {
        return this.accesoVOTF;
    }

    @XmlElement(name="accesovotf")
    public void setAccesoVOTF(AccesoVOTF accesoVOTF) {
        this.accesoVOTF = accesoVOTF;
    } 
    
    public List<Pago> getListPagos() {
        return this.listPagos;
    }
    
    public String getInvertida() {
        return this.invertida;
    }

    @XmlElement
    public void setInvertida(String invertida) {
        this.invertida = invertida;
    }
    
    public String getMarcamng() {
        return this.marcamng;
    }

    @XmlElement
    public void setMarcamng(String marcamng) {
        this.marcamng = marcamng;
    }
    
    public String getEmailuser() {
        return this.emailuser;
    }

    @XmlElement
    public void setEmailuser(String emailuser) {
        String emailUserReturn = emailuser;
        String marcaTmtp = "[timestamp]";
        
        //Si detectamos la correspondiente marca, la susutituimos por un timestamp. En general para evitar KOs en los pagos por fraude, p.e. en Billpay (Suiza)
        if (emailUserReturn.contains(marcaTmtp)) {
            java.util.Date date= new java.util.Date();
        
            String timestamp = new Timestamp(date.getTime()).toString().trim().replace(":", "").replace(" ", "").replace("-", "").replace(".", "");
            emailUserReturn = emailUserReturn.replace(marcaTmtp, timestamp);
        }
        
        this.emailuser = emailUserReturn;
    }
    
    public String getPassuser() {
        return this.passuser;
    }
    
    @XmlElement
    public void setPassuser(String passuser) {
        this.passuser = passuser;
    }
    
    public String getMobiluser() {
        return this.mobiluser;
    }

    @XmlElement
    public void setMobiluser(String mobiluser) {
        this.mobiluser = mobiluser;
    }
    
    public String getMicuenta() {
        return this.micuenta;
    }

    @XmlElement
    public void setMicuenta(String micuenta) {
        this.micuenta = micuenta;
    }

    public boolean isPaisTop() {
        return ("s".compareTo(this.paistop.toLowerCase())==0);
    }
    
    public boolean isPaisWithMisCompras() {
        return ("true".compareTo(getMiscompras())==0);
    }
    

    
    /**
     * @return si el país tiene asociado el pago de "Store Credit"
     */
    public boolean existsPagoStoreCredit() {
        return (getPagoType(TypePago.StoreCredit)!=null);
    }
    
    /**
     * @return si el país tiene o no venta online
     */
    public boolean isVentaOnline() {
        if (this.shop_online.compareTo("true")==0) {
            return true;
        }
        return false;
    }
    
    public boolean isEspanya() {
    	return ("001".compareTo(getCodigo_pais())==0);
    }
        
    /**
     * Obtiene la lista de pagos correspondientes al Shop, Outlet o votf en el orden en el que se testearán 
     * (como se encuentran en el XML pero dando prioridad a los que tienen no tienen testpago='s')
     */
    public List<Pago> getListPagosTest(AppEcom app, boolean testEmpleado) {
        List<Pago> listPagosApp =  new ArrayList<>();
        List<Pago> listPagosTest = new ArrayList<>();
        Iterator<Pago> itPagos = this.listPagos.iterator();
        while (itPagos.hasNext()) {
            Pago pago = itPagos.next();
    		
            //En caso de empleado filtramos los pagos manteniendo sólo los marcados con empleado="s"
            if (!testEmpleado || (testEmpleado && pago.getEmpleado().compareTo("s")==0)) {
                
                //El storecredito lo mantenemos al margen de la lista pues no aparece como un icono
                if (pago.getTypePago()!=TypePago.StoreCredit) {
                    switch (app) {
                    case votf:
                        if (pago.getVotf()!=null && pago.getVotf().compareTo("n")!=0) {
                            if (pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0) {
                                listPagosTest.add(pago);
                            } else {
                                listPagosApp.add(pago);
                            }
                        }
                        break;
                    case shop:
                        if (pago.getShop().compareTo("n")!=0) {
                            if (pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0) {
                                listPagosTest.add(pago);
                            } else {
                                listPagosApp.add(pago);
                            }
                        }
                        break;
                    case outlet:
                    default:
                        if (pago.getOutlet().compareTo("n")!=0) {
                            if (pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0) {
                                listPagosTest.add(pago);
                            } else {
                                listPagosApp.add(pago);
                            }
                        }                        
                        break;
                    }
                }
            }
        }
    	
        //Concatenamos las listas de pago situando al final los pagos a testear
        for (Pago pago: listPagosTest)
            listPagosApp.add(pago);
    				
        return listPagosApp;
    }
    
    /**Retorna el pago con el nombre especificado en el parámetro
     * @return pago correspondiente al nombre especificado
     */
    public Pago getPago(String nombrePago) {
        Pago pagoReturn = null;
        Iterator<Pago> itPagos = this.listPagos.iterator();
        while (itPagos.hasNext()) {
            Pago pago = itPagos.next();
            if (pago.getNombre().compareTo(nombrePago)==0) {
                pagoReturn = pago;
                break;
            }
        }
        
        return pagoReturn;
    }
    
    /**Retorna el pago con el nombre especificado en el parámetro
     * @return pago correspondiente al nombre especificado
     */
    public Pago getPagoType(TypePago typePago) {
        Pago pagoReturn = null;
        Iterator<Pago> itPagos = this.listPagos.iterator();
        while (itPagos.hasNext()) {
            Pago pago = itPagos.next();
            if (pago.getTypePago()==typePago) {
                pagoReturn = pago;
                break;
            }
        }
        
        return pagoReturn;
    }        
    
    /**
     * Obtiene la lista de pagos válidos para un pedido con artículos del almacén montcada
     */
    public List<Pago> getListPagosValidosMontcada(AppEcom app, boolean testEmpleado) {
        List<Pago> listaPagosReturn = new ArrayList<>();
        List<Pago> listaPagos = this.getListPagosTest(app, testEmpleado);
        Iterator<Pago> itPagos = listaPagos.iterator();
        while (itPagos.hasNext()) {
            Pago pago = itPagos.next();
            if (pago.validoAlmacenMontcada()) {
                listaPagosReturn.add(pago);
            }
        }
    	
        return listaPagosReturn;
    }
    
    /**
     * Genera un string con la concatenación de los pagos disponibles para el país en el orden en que se testearán
     * (como se encuentran en el XML pero dando prioridad a los que no tienen tienen testpago='s')
     */
    public String getStringPagosValidosMontcada(AppEcom app, boolean testEmpleado) {
        String metodosPago = "";
        List<Pago> listPagosApp = getListPagosValidosMontcada(app, testEmpleado); 
        for (int i=0; i<listPagosApp.size(); i++) 
            metodosPago = metodosPago + ",<br> " + listPagosApp.get(i).getNombre();
    	
        return metodosPago;
    }
    
    /**
     * Genera un string con la concatenación de los pagos disponibles para el país en el orden en que se testearán
     * (como se encuentran en el XML pero dando prioridad a los que no tienen tienen testpago='s')
     */
    public String getStringPagosTest(AppEcom app, boolean testEmpleado) {
        String metodosPago = "";
        List<Pago> listPagosApp = getListPagosTest(app, testEmpleado); 
        for (int i=0; i<listPagosApp.size(); i++) {
            //Filtramos el pago "storecredit"
            if (listPagosApp.get(i).getTypePago()!=TypePago.StoreCredit) {
                metodosPago = metodosPago + ",<br> " + listPagosApp.get(i).getNombre();
            }
        }
    	
        return metodosPago;
    }

    /**
     * Retorna la URL de acceso al país sin el -xx donde xx es el código de idioma
     */
    public String getUrlPaisEstandar(String urlBase) throws Exception {
        String urlRes = this.listIdiomas.get(0).getUrlIdioma(urlBase);
        URI url = new URI(urlRes);
        String path = url.getPath();
        if (path.contains("-")) {
        	String pathRes = path.substring(0, path.indexOf("-"));
        	urlRes = urlRes.replace(path, "") + pathRes;
        }
        
        return urlRes;
    }
    
    public LevelPais getLevelPais() {
        if (isPaisTop()) {
            return LevelPais.top;
        }
        if (getShop_online().compareTo("true")==0) {
            return LevelPais.conCompraNoTop;
        }
        return LevelPais.sinCompra;
    }
    
    @Override
    public String toString() {
        return "Pais [nombre_pais="+ this.nombre_pais + ", codigo_pais=" + this.codigo_pais + ", listIdiomas=" + this.listIdiomas + ", tarifa=" + this.tarifa + ", codpos=" + this.codpos + ", telefono=" + this.telefono + ", dni=" + this.dni +
                ", shop_online=" + this.shop_online + ", outlet_online=" + this.outlet_online + ", moneda=" + this.moneda + ", maximo_bolsa=" + this.maximo_bolsa + 
                ", " + this.tienda + ", " + this.shoponline + ", invertida=" + this.invertida + ", marmamng=" + this.marcamng + ", micuenta=" + this.micuenta + 
                ", toString()=" + super.toString() + "]";
    }
}
