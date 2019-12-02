package com.mng.robotest.test80.mango.test.factoryes.jaxb;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.xml.bind.annotation.*;

import org.testng.ITestContext;

import com.mng.robotest.test80.mango.test.data.Constantes;
import com.mng.testmaker.conf.Channel;
import com.mng.testmaker.service.TestMaker;
import com.mng.robotest.test80.mango.conftestmaker.AppEcom;
import com.mng.robotest.test80.mango.test.pageobject.shop.checkout.envio.TipoTransporteEnum.TipoTransporte;

public class Pago {
    public static enum TypePago {
        TarjetaIntegrada, 
        TMango, 
        KrediKarti, 
        Billpay, 
        Paypal, 
        Mercadopago, 
        Amazon, 
        Postfinance, 
        Trustpay, 
        Multibanco, 
        Paytrail, 
        Dotpay, 
        Ideal, 
        Eps,
        Sepa, 
        Giropay, 
        Sofort, 
        Klarna,
        KlarnaDeutsch,
        AssistQiwi, 
        Assist, 
        PasarelaOtras, 
        KoreanCreditCard,
        ContraReembolso,
        Bancontact,
        Yandex,
        StoreCredit, 
        TpvVotf
    }
    
    public static enum TypeTarj {
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
    String numperklarna;
    String telefklarna; 
    String telefqiwi; 
    String nomklarna; 
    String direcklarna;
    String provinklarna;
    String searchaddklarna;
    String paissofort;
    String bankcode;
    String usrsofort;
    String passsofort; 
    String tansofort;
    String iban;
    String bic;
    String bankidgiropay;
    String scgiropay;
    String extscgiropay;
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
            return TypePago.TarjetaIntegrada;
        case "TMango":
            return TypePago.TMango;	        
        case "KrediKarti": 
            return TypePago.KrediKarti;
        case "Billpay":
            return TypePago.Billpay;
        case "Paypal":
            return TypePago.Paypal;	        
        case "Mercadopago":
            return TypePago.Mercadopago;                
        case "Amazon":
            return TypePago.Amazon;                
        case "Postfinance":
            return TypePago.Postfinance;                
        case "Trustpay":
            return TypePago.Trustpay;                
        case "Multibanco":
            return TypePago.Multibanco;                
        case "Paytrail":
            return TypePago.Paytrail;                
        case "Dotpay":
            return TypePago.Dotpay;                
        case "Ideal":
            return TypePago.Ideal;      
        case "Eps":
            return TypePago.Eps;
        case "Sepa":
            return TypePago.Sepa;                
        case "Giropay":
            return TypePago.Giropay;                
        case "Sofort":
            return TypePago.Sofort;                
        case "Klarna":
            return TypePago.Klarna;                
        case "KlarnaDeutsch":
            return TypePago.KlarnaDeutsch;                
        case "AssistQiwi":
            return TypePago.AssistQiwi;                
        case "Assist":
            return TypePago.Assist;                
        case "PasarelaOtras":
            return TypePago.PasarelaOtras;
        case "KoreanCreditCard":
        	return TypePago.KoreanCreditCard;
        case "ContraReembolso":
            return TypePago.ContraReembolso;
        case "Bancontact":
            return TypePago.Bancontact;	        
        case "Yandex":
            return TypePago.Yandex;                
        case "storecredit":
            return TypePago.StoreCredit;
        case "tpvvotf":
            return TypePago.TpvVotf;
        default:
            return TypePago.TarjetaIntegrada;
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
    
    public String getNameFilter(Channel channel) {
        if ("".compareTo(this.namefilter)!=0) {
            return this.namefilter;
        }
        return (getNombre(channel));
    }
        
    @XmlAttribute(name="namefilter")
    public void setNameFilter(String namefilter) {
        this.namefilter = namefilter;
    }    
	
    public String getTestpasarela() {
        return this.testpasarela;
    }
    
    public boolean isNeededTestPasarelaDependingFilter(Channel channel, ITestContext ctx) {
        if (getTestpasarela().compareTo("s")==0) {
            String listPayments = TestMaker.getParamTestRun(Constantes.paramPayments, ctx);
            if (listPayments==null || "".compareTo(listPayments)==0) {
                return true;
            }
            ArrayList<String> paymentsArray = new ArrayList<>(Arrays.asList(listPayments.split(",")));
            for (String paymentFilterName : paymentsArray) {
                if (paymentFilterName.compareTo(getNameFilter(channel))==0) {
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
	
    public String getNumperklarna() {
        return this.numperklarna;
    }
	
    @XmlAttribute(name="numperklarna")
    public void setNumperklarna(String numperklarna) {
        this.numperklarna = numperklarna;
    }
	
    public String getTelefklarna() {
        return this.telefklarna;
    }
	
    @XmlAttribute(name="telefklarna")
    public void setTelefklarna(String telefklarna) {
        this.telefklarna = telefklarna;
    }
	
    public String getNomklarna() {
        return this.nomklarna;
    }
	
    @XmlAttribute(name="nomklarna")
    public void setNomklarna(String nomklarna) {
        this.nomklarna = nomklarna;
    }
	
    public String getDirecklarna() {
        return this.direcklarna;
    }
	
    @XmlAttribute(name="direcklarna")
    public void setDirecklarna(String direcklarna) {
        this.direcklarna = direcklarna;
    }
	
    public String getProvinklarna() {
        return this.provinklarna;
    }
	
    @XmlAttribute(name="provinklarna")
    public void setProvinklarna(String provinklarna) {
        this.provinklarna = provinklarna;
    }
	
    public String getSearchAddklarna() {
        return this.searchaddklarna;
    }
	
    @XmlAttribute(name="searchaddklarna")
    public void setSearchAddklarna(String searchaddklarna) {
        this.searchaddklarna = searchaddklarna;
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
    
    public String getScgiropay() {
        return this.scgiropay;
    }
    
    @XmlAttribute(name="scgiropay")
    public void setScgiropay(String scgiropay) {
        this.scgiropay = scgiropay;
    }        
    
    public String getExtscgiropay() {
        return this.extscgiropay;
    }
    
    @XmlAttribute(name="extscgiropay")
    public void setExtscgiropay(String extscgiropay) {
        this.extscgiropay = extscgiropay;
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
    
    public String getTelefqiwi() {
        return this.telefqiwi;
    }
	
    @XmlAttribute(name="telefqiwi")
    public void setTelefqiwi(String telefqiwi) {
        this.telefqiwi = telefqiwi;
    }
    
    public String getNombre(Channel channel) {
        if (channel==Channel.movil_web && getNombremovil()!=null && "".compareTo(getNombremovil())!=0) {
            return getNombremovil();
        }
        return getNombre();
    }
    
    public String getNombreInCheckout(Channel channel) {
    	String nombre = getNombre(channel);
    	if (nombre.contains("mercadopago")) {
    		return "mercadopago";
    	}
    	return nombre;
    }
    
//    private boolean isAdyen() {
//    	String numtarj = getNumtarj();
//    	return (numtarj!=null && "".compareTo(numtarj)!=0);
//    }
    
    @Override
    public String toString() {
        return "Pago [type="+ this.type + ", nombre=" + this.nombre + 
                ", toString()=" + super.toString() + "]";
    }
}