package com.mng.robotest.test.beans;

import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;
import java.util.*;
import javax.xml.bind.annotation.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.test.beans.Pago.TypePago;
import com.mng.robotest.test.utils.LevelPais;
import com.mng.robotest.test.utils.PagoGetter;
import com.mng.robotest.test.utils.UtilsTest;
import com.mng.robotest.test.utils.PagoGetter.PaymentCountry;

@XmlRootElement
public class Pais implements Serializable {

	private static final long serialVersionUID = -3152111055294108127L;

	public static final int MAX_PAGOS = 25;
	
	String nombre_pais;
	String codigo_pais;
		
	//Indica si el país existe en la Shop (sólo es 'n' para el caso de Japón que sólo aparece a nivel de la preHome)
	String exists = "s";
	String codigo_alf;
	String codigo_prehome;
		
	@XmlElement(name="idioma") 
	List<IdiomaPais> listIdiomas = new LinkedList<>();
		
	Direccharnolatinos direccharnolatinos = new Direccharnolatinos();
	String paistop="";
	String rgpd="";
	String loyalty="";
	String codpos;
	String address;
	String telefono;
	String dni;
	String tiendas_online;
	String newregister="";
	
	Tienda tienda = new Tienda();
	Shoponline shoponline = new Shoponline();
	
	@XmlElement(name="pago") 
	List<Pago> listPagos = new LinkedList<>();
	
	String multidireccion;
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
	
	@XmlElement
	public void setCodigo_prehome(String codigo_prehome) {
		this.codigo_prehome = codigo_prehome;
	}
	
	public String getCodigo_prehome() {
		if (codigo_prehome==null) {
			return codigo_alf;
		}
		return codigo_prehome;
	}

	public List<IdiomaPais> getListIdiomas() {
		var appOpt = getApp();
		if (appOpt.isPresent()) {
			return getListIdiomas(appOpt.get());
		}
		return this.listIdiomas;
	}	
	private Optional<AppEcom> getApp() {
		try {
			var inputParams = (InputParamsMango)TestMaker.getInputParamsSuite();
			if (inputParams!=null) {
				return Optional.of((AppEcom)inputParams.getApp());
			}
		} catch (Exception e) {
			return Optional.empty();
		}
		return Optional.empty();
	}
	public List<IdiomaPais> getListIdiomas(AppEcom app) {
		return listIdiomas.stream()
				.filter(i -> i.getTiendasList().contains(app)).toList();
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
	
	public boolean loyalty() {
		//Países loyalty localizables en la tabla MNGLOYALTYDB.LOYALTY_TOUCHPOINTS
		return this.loyalty.compareTo("S")==0;
	}

	@XmlElement (name="loyalty")
	public void setloyalty(String loyalty) {
		this.loyalty = loyalty;
	}	
	
	public String getCodpos() {
		if (codpos==null || codpos.compareTo("")==0) {
			return "08720";
		}
		return codpos;
	}

	@XmlElement
	public void setCodpos(String codpos) {
		this.codpos = codpos;
	}	  
	
	public String getAddress() {
		if (address==null || address.compareTo("")==0) {
			return "c./ mossen trens n6 5 1a";
		}
		return address;
	}

	@XmlElement
	public void setAddress(String address) {
		this.address = address;
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
	
	@XmlElement
	public void setTiendas_online(String tiendas_online) {
		this.tiendas_online = tiendas_online;
	}
	
	public String getTiendas_online() {
		return this.tiendas_online;
	}
	
	public List<AppEcom> getTiendasOnlineList() {
		List<AppEcom> listApps = new ArrayList<>();
		if (getTiendas_online()!=null && getTiendas_online().length()>0) {
			var listAppsStr = Arrays.asList(getTiendas_online().split(","));
			for (String app : listAppsStr) {
				listApps.add(AppEcom.valueOf(app));
			}
		}
		return listApps;
	}   

	public boolean newregister() {
		return this.newregister.compareTo("S")==0;
	}

	@XmlElement(name="newregister")
	public void setNewRegister(String newregister) {
		this.newregister = newregister;
	}

	public boolean isMisdirecciones(AppEcom app) {
		if (app!=AppEcom.shop) {
			return false;
		}
		//TODO eliminar el if cuando el nuevo apartado de "Mis Direcciones" esté subido a PRO
		if (PageBase.isEnvPRO() && UtilsTest.todayBeforeDate("2023-04-07")) {
			return false;
		}
		return (getMultidireccion()!=null && 
				"S".compareTo(getMultidireccion())==0);
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
	
	public List<Pago> getListPagos() {
		return this.listPagos;
	}
	
	public String getMultidireccion() {
		return this.multidireccion;
	}

	@XmlElement
	public void setMultidireccion(String multidireccion) {
		this.multidireccion = multidireccion;
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

	public boolean isVentaOnline() {
		return !getTiendasOnlineList().isEmpty();
	}

	/**
	 * @return si el país tiene asociado el pago de "Store Credit"
	 */
	public boolean existsPagoStoreCredit() {
		return (getPagoType(TypePago.STORE_CREDIT)!=null);
	}

	public boolean isEspanya() {
		return ("001".compareTo(getCodigo_pais())==0);
	}

	/**
	 * Obtiene la lista de pagos correspondientes al Shop, Outlet o votf en el orden en el que se testearán 
	 * (como se encuentran en el XML pero dando prioridad a los que tienen no tienen testpago='s')
	 */
	public List<Pago> getListPagosForTest(AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPayments = PagoGetter.getListPayments(this.getCodigo_pais(), app, isEmpleado);
		List<Pago> listPagosFirst =  new ArrayList<>();
		List<Pago> listPagosLast = new ArrayList<>();
		for (PaymentCountry payment : listPayments) {
			Pago pago = payment.pago;
			if (pago.getTestpago()!=null && pago.getTestpago().compareTo("s")==0) {
				listPagosFirst.add(pago);
			} else {
				listPagosLast.add(pago);
			}
		}
		
		listPagosFirst.addAll(listPagosLast);
		return listPagosFirst;
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
		List<Pago> listaPagos = this.getListPagosForTest(app, testEmpleado);
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
		List<Pago> listPagosApp = getListPagosForTest(app, testEmpleado); 
		for (int i=0; i<listPagosApp.size(); i++) {
			//Filtramos el pago "storecredit"
			if (listPagosApp.get(i).getTypePago()!=TypePago.STORE_CREDIT) {
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
			return LevelPais.TOP;
		}
		if (!getTiendasOnlineList().isEmpty()) {
			return LevelPais.CON_COMPRA_NO_TOP;
		}
		return LevelPais.SIN_COMPRA;
	}

	@Override
	public String toString() {
		return "Pais [nombre_pais="+ this.nombre_pais + ", codigo_pais=" + this.codigo_pais + ", listIdiomas=" + this.listIdiomas + ", codpos=" + this.codpos + ", telefono=" + this.telefono + ", dni=" + this.dni +
				", shop_online=" + ", " + this.tienda + ", " + this.shoponline + ", micuenta=" + this.micuenta + 
				", toString()=" + super.toString() + "]";
	}
}
