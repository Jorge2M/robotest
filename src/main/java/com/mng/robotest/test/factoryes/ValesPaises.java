package com.mng.robotest.test.factoryes;

import java.util.*;

import org.testng.ITestContext;
import org.testng.annotations.*;

import com.github.jorge2m.testmaker.service.TestMaker;
import com.github.jorge2m.testmaker.conf.Channel;
import com.github.jorge2m.testmaker.domain.InputParamsTM;
import com.mng.robotest.conftestmaker.AppEcom;
import com.mng.robotest.test.appshop.PaisAplicaVale;
import com.mng.robotest.test.beans.*;
import com.mng.robotest.test.data.DataCtxShop;
import com.mng.robotest.test.data.ValesData;
import com.mng.robotest.test.data.ValesData.Campanya;
import com.mng.robotest.test.generic.beans.ValePais;
import com.mng.robotest.test.suites.ValesPaisesSuite.VersionValesSuite;
import com.mng.robotest.test.utils.PaisGetter;

public class ValesPaises {

	List<ValePais> listaPaisesVales = new ArrayList<>();
	
	@Factory
	@Parameters({"countrys"})
	public Object[] createInstances(String countrys, ITestContext ctxTestRun) throws Exception {
		List<Object> listTests = new ArrayList<>();
		InputParamsTM inputData = TestMaker.getInputParamsSuite(ctxTestRun);
		VersionValesSuite version = VersionValesSuite.valueOf(inputData.getVersion());
		Calendar currDtCal = Calendar.getInstance();
		for (Campanya campanya : Campanya.values()) {
			if (!version.filtroCalendario() || 
				currDtCal.getTimeInMillis() > Campanya.MNGVIP.getFechaInit().getTimeInMillis()) {
				List<ValePais> listaVales = ValesData.getListVales(campanya, version.filtroCalendario());
				if (listaVales!=null) {
					this.listaPaisesVales.addAll(ValesData.getListVales(campanya, version.filtroCalendario()));
				}
			}
		} 
		
		List<Pais> listCountrys = PaisGetter.getFromCommaSeparatedCountries(countrys);
		int prioridad=0;
		for (Pais pais : listCountrys) {
			IdiomaPais idioma = pais.getListIdiomas().get(0);
			List<ValePais> listaValesPais = listValesPais(pais.getCodigo_pais());
			for (ValePais valePais : listaValesPais) {
				DataCtxShop dCtxSh = new DataCtxShop((AppEcom)inputData.getApp(), Channel.desktop, pais, idioma, valePais);
				listTests.add(new PaisAplicaVale(version, dCtxSh, prioridad));
				prioridad+=1;
								
				System.out.println(
					"Creado Test con datos: " +
					",Pais=" + pais.getNombre_pais() +
					",Idioma=" + idioma.getCodigo().getLiteral() +
					",Vale=" + valePais.getCodigoVale() +
					",Valido? " + valePais.isValid() + 
					",Porcentaje Descuento=" + valePais.getPorcDescuento()
				);
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
			pais.isVentaOnline());
	}

	public List<ValePais> listValesPais(String codigo_pais) {	  
		List<ValePais> listaValesPais = new ArrayList<>();
		for (ValePais valePais : listaPaisesVales) {
			if (valePais.getPais().getCodigo_pais().compareTo(codigo_pais)==0) {
				listaValesPais.add(valePais);
			}
		}
				
		return listaValesPais;
	}
}