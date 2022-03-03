package com.mng.robotest.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appshop.PaisIdioma;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.factoryes.Utilidades;
import com.mng.robotest.test.getdata.usuarios.GestorUsersShop;
import com.mng.robotest.test.getdata.usuarios.UserShop;
import com.mng.robotest.test.suites.PaisIdiomaSuite.VersionPaisSuite;
import com.mng.robotest.test.utils.PaisGetter;

public class LineasBannersFactory {
	
	@Factory
	@Parameters({"countrys", "lineas"})
	public Object[] createInstances(String countrysStr, String lineas, ITestContext ctxTestRun) throws Exception {
		ArrayList<PaisIdioma> listTests = new ArrayList<>();
		ArrayList<String> listaPaises = new ArrayList<>();
		try {
			InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
			AppEcom app = (AppEcom)inputData.getApp();
			Channel channel = inputData.getChannel();
			VersionPaisSuite version = VersionPaisSuite.valueOf(inputData.getVersion());
			List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrysStr);
			int prioridad=0;
			for (Pais pais : listCountrys) {
				Iterator<IdiomaPais> itIdiomas = pais.getListIdiomas().iterator();
				while (itIdiomas.hasNext()) {
					IdiomaPais idioma = itIdiomas.next();
					if (paisToTest(pais, app)) {
						listaPaises.add(pais.getNombre_pais().trim());
						List<Linea> lineasAprobar = Utilidades.getLinesToTest(pais, app, lineas);
						DataCtxShop dCtxSh = new DataCtxShop(app, channel, pais, idioma/*, inputData.getUrlBase()*/);
						UserShop userShop = GestorUsersShop.checkoutBestUserForNewTestCase();
						dCtxSh.userConnected = userShop.user;
						dCtxSh.passwordUser = userShop.password;
						listTests.add(new PaisIdioma(version, dCtxSh, lineasAprobar, prioridad));
						System.out.println(
							"Creado Test \"PaisIdioma\" con datos: " + 
							",Pais=" + pais.getNombre_pais() +
							",Idioma=" + idioma.getCodigo().getLiteral() +
							",Num Idiomas=" + pais.getListIdiomas().size());

						prioridad+=1;
					}
				}
			}
		}
		catch (Throwable e) {
			e.printStackTrace();
			throw e;
		}		
		
		return listTests.toArray(new Object[listTests.size()]);
	}
	
	/**
	 * @return si se ha de crear un test para un país concreto
	 */
	protected boolean paisToTest(Pais pais, AppEcom app) {
		return (
			"n".compareTo(pais.getExists())!=0 &&
			pais.isVentaOnline() &&
			pais.getTiendasOnlineList().contains(app));
	}
}