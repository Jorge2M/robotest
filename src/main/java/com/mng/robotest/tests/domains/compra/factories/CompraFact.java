package com.mng.robotest.tests.domains.compra.factories;

import static com.mng.robotest.testslegacy.data.PaisShop.*;

import java.io.Serializable;
import org.testng.annotations.Test;

import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.suitetree.TestCaseTM;
import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.compra.tests.Com010;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pago;
import com.mng.robotest.testslegacy.beans.Pais;

public class CompraFact implements Serializable {

	private static final long serialVersionUID = -2440149806957032044L;
	
	public int prioridad;
	private String indexFact = "";
	private Pais pais = ESPANA.getPais();
	private IdiomaPais idioma = pais.getListIdiomas().get(0);
	private Pago pago = null;
	private boolean usrRegistrado = false;
	private boolean empleado = false;
	private boolean testVale = false;
	private boolean manyArticles = false;
	private boolean checkAnulaPedido = false;
	
	public CompraFact() {}
	
	//From @Factory
	public CompraFact(
			Pais pais, IdiomaPais idioma, Pago pago, AppEcom appE, Channel channel, boolean usrRegistrado, 
			boolean empleado, boolean testVale, boolean manyArticles, boolean checkAnulaPedido, int prioridad) {
		this.pais = pais;
		this.idioma = idioma;
		this.pago = pago;
		this.usrRegistrado = usrRegistrado;
		this.empleado = empleado;
		this.testVale = testVale;
		this.manyArticles = manyArticles;
		this.checkAnulaPedido = checkAnulaPedido;
		this.prioridad = prioridad;
		this.indexFact = getIndexFactoria(pais, pago, appE, channel);
	}
	
	private String getIndexFactoria(Pais pais, Pago pago, AppEcom app, Channel channel) {
		String index =
			" " + 
			pais.getNombrePais().replace(" (Península y Baleares)", "") + 
			"-" + 
			pago.getNameFilter(channel, app) +
			"-" +
			pago.getTipoEnvioType(app);
			if (usrRegistrado) {
				index+="-síUsrReg";
			}
			if (empleado) {
				index+="-síEmpl";
			}
			if (testVale) {
				index+="-síVale";
			}
			if (manyArticles) {
				index+="-síManyArt";
			}
			if (checkAnulaPedido) {
				index+="-anulPedido";
			}
		return index;
	}   
	
	@Test (
		groups={"Compra", "Checkout", "Canal:all_App:all"}, alwaysRun=true, priority=1, 
		description="Test de compra (creado desde Factoría) con valores específicos a nivel de Pago, Tipo de Envío, Usuario Conectado y Empleado")
	public void COM010_Pago() throws Exception {
		TestCaseTM.addNameSufix(this.indexFact);
		new Com010(
				pais, idioma, pago, usrRegistrado, testVale, manyArticles, empleado, checkAnulaPedido)
			.execute();
	}
	
}
