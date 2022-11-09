package com.mng.robotest.test.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.*;

import org.testng.ITestContext;

import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.domains.compra.pageobjects.envio.TipoTransporteEnum.TipoTransporte;
import com.mng.robotest.test.data.Constantes;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.service.TestMaker;

public class Pago implements Serializable {
	
	private static final long serialVersionUID = -2329928763754362241L;

	public enum TypePago {
		TARJETA_INTEGRADA, 
		TARJETA_MANGO, 
		KREDI_KARTI, 
		BILLPAY, 
		PAYPAL, 
		MERCADOPAGO, 
		AMAZON, 
		POSTFINANCE, 
		TRUSTPAY, 
		MULTIBANCO, 
		PAYTRAIL, 
		DOTPAY, 
		IDEAL, 
		EPS,
		SEPA, 
		GIROPAY, 
		SOFORT, 
		KLARNA,
		KLARNA_UK,
		PAYSECURE_QIWI, 
		ASSIST, 
		PASARELA_OTRAS, 
		KCP,
		CONTRA_REEMBOLSO,
		BANCONTACT,
		PROCESS_OUT,
		YANDEX,
		PAYMAYA,
		STORE_CREDIT, 
		TPV_VOTF
	}
	
	public enum TypeTarj {
		VISAESTANDAR,
		VISAD3D,
		VISAD3D_JP
	}
	
	String type;
	String tiendas;
	String empleado;
	String testpasarela;
	String testpolyvore;
	String almacenmontcada;
	String testpago;
	String tipoenvioshop = "STANDARD";
	String tipoenviooutlet = "STANDARD";
	String provinciaenvio;
	String tipotarj;
	String numtarj;
	String mescad;
	String anycad;
	String titular="Consumer";
	String cvc;
	String dni;
	String cip;
	String usrd3d;
	String passd3d;
	String telefqiwi; 
	String numperklarna;
	String paissofort;
	String bankcode;
	String usrsofort;
	String passsofort; 
	String tansofort;
	String usrpaymaya;
	String passwordpaymaya;
	String otppaymaya;
	String iban;
	String bic;
	String bankidgiropay;
	String useremail;
	String passwordemail;
	String usrcashu;
	String passcashu;
	String usrcaptcha;
	String passcaptcha;
	String nombre;
	String namefilter="";
	String nombremovil="";
	Tpv tpv = new Tpv();
	
	public TypePago getTypePago() {
		switch (this.type) {
		case "TRJintegrada":
			return TypePago.TARJETA_INTEGRADA;
		case "TMango":
			return TypePago.TARJETA_MANGO;			
		case "KrediKarti": 
			return TypePago.KREDI_KARTI;
		case "Billpay":
			return TypePago.BILLPAY;
		case "Paypal":
			return TypePago.PAYPAL;			
		case "Mercadopago":
			return TypePago.MERCADOPAGO;				
		case "Amazon":
			return TypePago.AMAZON;				
		case "Postfinance":
			return TypePago.POSTFINANCE;				
		case "Trustpay":
			return TypePago.TRUSTPAY;				
		case "Multibanco":
			return TypePago.MULTIBANCO;				
		case "Paytrail":
			return TypePago.PAYTRAIL;				
		case "Dotpay":
			return TypePago.DOTPAY;				
		case "Ideal":
			return TypePago.IDEAL;	  
		case "Eps":
			return TypePago.EPS;
		case "Sepa":
			return TypePago.SEPA;				
		case "Giropay":
			return TypePago.GIROPAY;				
		case "Sofort":
			return TypePago.SOFORT;	 
		case "Paymaya":
			return TypePago.PAYMAYA;
		case "Klarna":
			return TypePago.KLARNA;		  
		case "KlarnaUK":
			return TypePago.KLARNA_UK;	
		case "PaysecureQiwi":
			return TypePago.PAYSECURE_QIWI;				
		case "Assist":
			return TypePago.ASSIST;				
		case "PasarelaOtras":
			return TypePago.PASARELA_OTRAS;
		case "KCP":
			return TypePago.KCP;
		case "ContraReembolso":
			return TypePago.CONTRA_REEMBOLSO;
		case "Bancontact":
			return TypePago.BANCONTACT;			
		case "Yandex":
			return TypePago.YANDEX;				
		case "storecredit":
			return TypePago.STORE_CREDIT;
		case "ProcessOut":
			return TypePago.PROCESS_OUT;
		case "tpvvotf":
			return TypePago.TPV_VOTF;
		default:
			return TypePago.TARJETA_INTEGRADA;
		}
	}
	
	@XmlAttribute(name="type")
	public void setType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	@XmlAttribute
	public void setTiendas(String tiendas) {
		this.tiendas = tiendas;
	}
	
	public String getTiendas() {
		return tiendas;
	}
	
	public List<AppEcom> getTiendasList() {
		List<AppEcom> listApps = new ArrayList<>();
		if (getTiendas()!=null) {
			List<String> listAppsStr = Arrays.asList(getTiendas().split(","));
			for (String app : listAppsStr) {
				listApps.add(AppEcom.valueOf(app));
			}
		}
		return listApps;
	}
	
	public String getEmpleado() {
		return this.empleado;
	}
	
	public boolean isForEmpleado() {
		return 
			getEmpleado()!=null && 
			"s".compareTo(getEmpleado())==0;
	}
	
	@XmlAttribute(name="empleado")
	public void setEmpleado(String empleado) {
		this.empleado = empleado;
	}	

	public String getNombre() {
		return this.nombre;
	}
	
	@XmlAttribute(name="nombre")
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getNombremovil() {
		return this.nombremovil;
	}
		
	@XmlAttribute(name="nombremovil")
	public void setNombremovil(String nombremovil) {
		this.nombremovil = nombremovil;
	}	
	
	public String getNameFilter(Channel channel, AppEcom app) {
		if ("".compareTo(this.namefilter)!=0) {
			return this.namefilter;
		}
		return (getNombre(channel, app));
	}
		
	@XmlAttribute(name="namefilter")
	public void setNameFilter(String namefilter) {
		this.namefilter = namefilter;
	}	
	
	public String getTestpasarela() {
		return this.testpasarela;
	}
	
	public boolean isNeededTestPasarelaDependingFilter(Channel channel, AppEcom app, ITestContext ctx) {
		if (getTestpasarela().compareTo("s")==0) {
			String listPayments = TestMaker.getParamTestRun(Constantes.PARAM_PAYMENTS, ctx);
			if (listPayments==null || "".compareTo(listPayments)==0) {
				return true;
			}
			List<String> paymentsArray = new ArrayList<>(Arrays.asList(listPayments.split(",")));
			for (String paymentFilterName : paymentsArray) {
				if (paymentFilterName.compareTo(getNameFilter(channel, app))==0) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@XmlAttribute(name="testpasarela")
	public void setTestpasarela(String testpasarela) {
		this.testpasarela = testpasarela;
	}
	
	public String getTestpolyvore() {
		return this.testpolyvore;
	}
		
	@XmlAttribute(name="testpolyvore")
	public void setTestpolyvore(String testpolyvore) {
		this.testpolyvore = testpolyvore;
	}	
	
	public String getAlmacenmontcada() {
		return this.almacenmontcada;
	}
	
	@XmlAttribute(name="almacenmontcada")
	public void setAlmacenmontcada(String almacenmontcada) {
		this.almacenmontcada = almacenmontcada;
	}
	
	public String getTestpago() {
		return this.testpago;
	}
	
	@XmlAttribute(name="testpago")
	public void setTestpago(String testpago) {
		this.testpago = testpago;
	}

	public TipoTransporte getTipoEnvioType(AppEcom appE) {
		return TipoTransporte.valueOf(getTipoEnvio(appE));
	}	

	public String getTipoEnvio(AppEcom appE) {
		switch (appE) {
		case shop:
			return getTipoEnvioShop();
		case outlet:
			return getTipoEnvioOutlet();
		case votf:
			return "TIENDA";			
		default:
			return "STANDARD";
		}
	}
	
	public String getTipoEnvioShop() {
		return this.tipoenvioshop;
	}
	
	@XmlAttribute(name="tipoenvioshop")
	public void setTipoEnvioShop(String tipoenvioshop) {
		this.tipoenvioshop = tipoenvioshop;
	}	
	
	public void setTipoEnvio(TipoTransporte tipoTransporte) {
		setTipoEnvioShop(tipoTransporte);
		setTipoEnvioOutlet(tipoTransporte);
	}
	
	public void setTipoEnvioShop(TipoTransporte tipoTransporte) {
		this.tipoenvioshop = tipoTransporte.toString();
	}
	
	public String getTipoEnvioOutlet() {
		return this.tipoenviooutlet;
	}
	
	public void setTipoEnvioOutlet(TipoTransporte tipoTransporte) {
		this.tipoenviooutlet = tipoTransporte.toString();
	}
	
	@XmlAttribute(name="tipoenviooutlet")
	public void setTipoEnvioOutlet(String tipoenviooutlet) {
		this.tipoenviooutlet = tipoenviooutlet;
	}	
	
	public String getProvinciaEnvio() {
		return this.provinciaenvio;
	}
	
	@XmlAttribute(name="provinciaenvio")
	public void setProvinciaEnvio(String provinciaenvio) {
		this.provinciaenvio = provinciaenvio;
	}	
	
	public String getTipotarj() {
		return this.tipotarj;
	}
	
	@XmlAttribute(name="tipotarj")
	public void setTipotarj(String tipotarj) {
		this.tipotarj = tipotarj;
	}
	
	public TypeTarj getTipotarjEnum() {
		return TypeTarj.valueOf(getTipotarj());
	}
	
	public String getNumtarj() {
		return this.numtarj;
	}
	
	@XmlAttribute(name="numtarj")
	public void setNumtarj(String numtarj) {
		this.numtarj = numtarj;
	}
	
	public String getMescad() {
		return this.mescad;
	}
	
	@XmlAttribute(name="mescad")
	public void setMescad(String mescad) {
		this.mescad = mescad;
	}
	
	public String getAnycad() {
		return this.anycad;
	}
	
	@XmlAttribute(name="anycad")
	public void setAnycad(String anycad) {
		this.anycad = anycad;
	}
	
	public String getTitular() {
		return this.titular;
	}
		
	@XmlAttribute(name="titular")
	public void setTitular(String titular) {
		this.titular = titular;
	}
	
	public String getCvc() {
		return this.cvc;
	}
	
	@XmlAttribute(name="cvc")
	public void setCvc(String cvc) {
		this.cvc = cvc;
	}
	
	public String getDni() {
		return this.dni;
	}
		
	@XmlAttribute(name="dni")
	public void setDni(String dni) {
		this.dni = dni;
	}	
	
	public String getCip() {
			return this.cip;
	}
			
	@XmlAttribute(name="cip")
	public void setCip(String cip) {
		this.cip = cip;
	}
	
	public String getUsrd3d() {
		return this.usrd3d;
	}
	
	@XmlAttribute(name="usrd3d")
	public void setUsrd3d(String usrd3d) {
		this.usrd3d = usrd3d;
	}
	
	public String getPassd3d() {
		return this.passd3d;
	}
	
	@XmlAttribute(name="passd3d")
	public void setPassd3d(String passd3d) {
		this.passd3d = passd3d;
	}

	public String getUsrsofort() {
		return this.usrsofort;
	}

	@XmlAttribute(name="usrsofort")
	public void setUsrsofort(String usrsofort) {
		this.usrsofort = usrsofort;
	}

	public String getPaissofort() {
		return this.paissofort;
	}

	@XmlAttribute(name="paissofort")
	public void setPaissofort(String paissofort) {
		this.paissofort = paissofort;
	}
	
	public String getUsrpaymaya() {
		return this.usrpaymaya;
	}

	@XmlAttribute(name="usrpaymaya")
	public void setUsrpaymaya(String usrpaymaya) {
		this.usrpaymaya = usrpaymaya;
	}

	public String getPasswordpaymaya() {
		return this.passwordpaymaya;
	}

	@XmlAttribute(name="passwordpaymaya")
	public void setPasswordpaymaya(String passwordpaymaya) {
		this.passwordpaymaya = passwordpaymaya;
	}
	
	public String getOtpdpaymaya() {
		return this.otppaymaya;
	}

	@XmlAttribute(name="otppaymaya")
	public void setOtppaymaya(String otppaymaya) {
		this.otppaymaya = otppaymaya;
	}
	
	public String getUseremail() {
		return this.useremail;
	}

	@XmlAttribute(name="useremail")
	public void setUseremail(String useremail) {
		this.useremail = useremail;
	}
	
	public String getPasswordemail() {
		return this.passwordemail;
	}

	@XmlAttribute(name="passwordemail")
	public void setPasswordemail(String passwordemail) {
		this.passwordemail = passwordemail;
	}
	
	public String getBankcode() {
		return this.bankcode;
	}

	@XmlAttribute(name="bankcode")
	public void setBankcode(String bankcode) {
		this.bankcode = bankcode;
	}
	
	public String getPasssofort() {
		return this.passsofort;
	}
	
	@XmlAttribute(name="passsofort")
	public void setPasssofort(String passsofort) {
		this.passsofort = passsofort;
	}
	
	public String getTansofort() {
		return this.tansofort;
	}
	
	@XmlAttribute(name="tansofort")
	public void setTansofort(String tansofort) {
		this.tansofort = tansofort;
	}
	
	public String getUsrcashu() {
		return this.usrcashu;
	}
	
	@XmlAttribute(name="usrcashu")
	public void setUsrcashu(String usrcashu) {
		this.usrcashu = usrcashu;
	}
	
	public String getPasscashu() {
		return this.passcashu;
	}
	
	@XmlAttribute(name="passcashu")
	public void setPasscashu(String passcashu) {
		this.passcashu = passcashu;
	}
	
	public String getUsrcaptcha() {
		return this.usrcaptcha;
	}
	
	@XmlAttribute(name="usrcaptcha")
	public void setUsrcaptcha(String usrcaptcha) {
		this.usrcaptcha = usrcaptcha;
	}
	
	public String getPasscaptcha() {
		return this.passcaptcha;
	}
	
	@XmlAttribute(name="passcaptcha")
	public void setPasscaptcha(String passcaptcha) {
		this.passcaptcha = passcaptcha;
	}

	public String getIban() {
		return this.iban;
	}
		
		@XmlAttribute(name="iban")
	public void setIban(String iban) {
		this.iban = iban;
	}	

	public String getBic() {
		return this.bic;
	}
	
	@XmlAttribute(name="bic")
	public void setBic(String bic) {
		this.bic = bic;
	}		
	
	public String getBankidgiropay() {
		return this.bankidgiropay;		
	}
	
	@XmlAttribute(name="bankidgiropay")
	public void setBankidgiropay(String bankidgiropay) {
		this.bankidgiropay = bankidgiropay;
	}		

	public Tpv getTpv() {
		return this.tpv;
	}

	@XmlElement(name="tpv")
	public void setTpv(Tpv tpv) {
		this.tpv = tpv;
	}

	public boolean validoAlmacenMontcada() {
		if (this.almacenmontcada==null || this.almacenmontcada.compareTo("s")==0) {
			return true;
		}
		return false;
	}
	
	public String getNumPerKlarna() {
		return this.numperklarna;
	}
	
	@XmlAttribute(name="numperklarna")
	public void setNumPerKlarna(String numperklarna) {
		this.numperklarna = numperklarna;
	}
	
	public String getTelefqiwi() {
		return this.telefqiwi;
	}
	
	@XmlAttribute(name="telefqiwi")
	public void setTelefqiwi(String telefqiwi) {
		this.telefqiwi = telefqiwi;
	}
	
	public String getNombre(Channel channel, AppEcom app) {
		if (channel.isDevice() && !(channel==Channel.tablet && app==AppEcom.outlet) && 
			getNombremovil()!=null && "".compareTo(getNombremovil())!=0) {
			return getNombremovil();
		}
		return getNombre();
	}
	
	public String getNombreInCheckout(Channel channel, AppEcom app) {
		String nombre = getNombre(channel, app);
		if (nombre.contains("mercadopago")) {
			return "mercadopago";
		}
		return nombre;
	}
	
//	private boolean isAdyen() {
//		String numtarj = getNumtarj();
//		return (numtarj!=null && "".compareTo(numtarj)!=0);
//	}
	
	@Override
	public String toString() {
		return "Pago [type="+ this.type + ", nombre=" + this.nombre + 
				", toString()=" + super.toString() + "]";
	}
}