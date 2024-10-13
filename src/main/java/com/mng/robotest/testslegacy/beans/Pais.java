package com.mng.robotest.testslegacy.beans;

import java.io.Serializable;
import java.net.URI;
import java.sql.Timestamp;
import java.util.*;
import javax.xml.bind.annotation.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.mng.robotest.access.InputParamsMango;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.data.PaisShop;
import com.mng.robotest.testslegacy.utils.LevelPais;
import com.mng.robotest.testslegacy.utils.PagoGetter;
import com.mng.robotest.testslegacy.utils.UtilsTest;
import com.mng.robotest.testslegacy.utils.PagoGetter.PaymentCountry;

@XmlRootElement
public class Pais implements Serializable {

	private static final long serialVersionUID = -3152111055294108127L;

	public static final int MAX_PAGOS = 25;
	
	String nombrePais;
	String codigoPais;
		
	//Indica si el país existe en la Shop (sólo es 'n' para el caso de Japón que sólo aparece a nivel de la preHome)
	String exists = "s";
	String codigoAlf;
	String codigoPrehome;
		
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
	String tiendasOnline;
	
	Register register = new Register();
	Shoponline shoponline = new Shoponline();
	
	@XmlElement(name="pago") 
	List<Pago> listPagos = new LinkedList<>();
	
	String multidireccion;
	String newmenu;
	String newcheckout;
	String emailuser;
	String mobiluser;
	
	@XmlElement 
	List<String> listMetodopagos = new LinkedList<>();
	
	public String getNombrePais() {
		return this.nombrePais;
	}

	@XmlAttribute(name="nombre_pais")
	public void setNombrePais(String nombrePais) {
		this.nombrePais = nombrePais;
	}		
	
	public String getCodigoPais() {
		return this.codigoPais;
	}
	
	@XmlElement(name="codigo_pais")
	public void setCodigoPais(String codigoPais) {
		this.codigoPais = codigoPais;
	}	
	
	@XmlAttribute(name="exists")
	public void setExists(String exists) {
		this.exists = exists;
	}
	
	public String getExists() {
		return this.exists;
	}		

	public String getCodigoAlf() {
		return this.codigoAlf;
	}

	@XmlElement(name="codigo_alf")
	public void setCodigoAlf(String codigoAlf) {
		this.codigoAlf = codigoAlf;
	}
	
	@XmlElement(name="codigo_prehome")
	public void setCodigoPrehome(String codigoPrehome) {
		this.codigoPrehome = codigoPrehome;
	}
	
	public String getCodigoPrehome() {
		if (codigoPrehome==null) {
			return codigoAlf;
		}
		return codigoPrehome;
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
		if (this.dni==null) {
			return "";
		}
		return this.dni;
	}

	@XmlElement
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	@XmlElement(name="tiendas_online")
	public void setTiendasOnline(String tiendasOnline) {
		this.tiendasOnline = tiendasOnline;
	}
	
	public String getTiendas_online() {
		return this.tiendasOnline;
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
	
	public Register getRegister() {
		return this.register;
	}

	@XmlElement(name="register")
	public void setRegister(Register register) {
		this.register = register;
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
	
	public boolean isFichaGenesis(Pais pais, AppEcom app) {
		return (!PaisShop.COLOMBIA.isEquals(pais));
	}
	
	public String getNewmenu() {
		return this.newmenu;
	}

	@XmlElement
	public void setNewmenu(String newmenu) {
		this.newmenu = newmenu;
	}
	
	public boolean isNewmenu(AppEcom app) {
		if (app==AppEcom.outlet) {
			return true;
		}
		return getTiendasNewmenu().contains(app);
	}
	
	private List<AppEcom> getTiendasNewmenu() {
		List<AppEcom> listApps = new ArrayList<>();
		if (getNewmenu()!=null && getNewmenu().length()>0) {
			var listAppsStr = Arrays.asList(getNewmenu().split(","));
			for (String app : listAppsStr) {
				listApps.add(AppEcom.valueOf(app));
			}
		}
		return listApps;
	}	
	
	public String getNewcheckout() {
		return this.newcheckout;
	}

	@XmlElement
	public void setNewcheckout(String newcheckout) {
		this.newcheckout = newcheckout;
	}
	
	public boolean isNewcheckout(AppEcom app) {
		return getTiendasNewcheckout().contains(app);
	}
	
	private List<AppEcom> getTiendasNewcheckout() {
		List<AppEcom> listApps = new ArrayList<>();
		if (getNewcheckout()!=null && getNewcheckout().length()>0) {
			var listAppsStr = Arrays.asList(getNewcheckout().split(","));
			for (String app : listAppsStr) {
				listApps.add(AppEcom.valueOf(app));
			}
		}
		return listApps;
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
	
	public String getMobiluser() {
		return this.mobiluser;
	}

	@XmlElement
	public void setMobiluser(String mobiluser) {
		this.mobiluser = mobiluser;
	}
	
	public boolean isPaisTop() {
		return ("s".compareTo(this.paistop.toLowerCase())==0);
	}
	
	public boolean isNewRegister() {
		return register.isNewRegister();
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
		return ("001".compareTo(getCodigoPais())==0);
	}

	/**
	 * Obtiene la lista de pagos correspondientes al Shop u Outlet en el orden en el que se testearán 
	 * (como se encuentran en el XML pero dando prioridad a los que tienen no tienen testpago='s')
	 */
	public List<Pago> getListPagosForTest(AppEcom app, boolean isEmpleado) {
		List<PaymentCountry> listPayments = PagoGetter.getListPayments(this.getCodigoPais(), app, isEmpleado);
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
	public String getUrlAccess(String urlBase) throws Exception {
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
		return "Pais [nombre_pais="+ this.nombrePais + ", codigo_pais=" + this.codigoPais + ", listIdiomas=" + this.listIdiomas + ", codpos=" + this.codpos + ", telefono=" + this.telefono + ", dni=" + this.dni +
				", " + this.shoponline + ", toString()=" + super.toString() + "]";
	}
}
