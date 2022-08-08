package com.mng.robotest.test.pageobject.chequeregalo;

import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.generic.ChequeRegalo;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;


public abstract class PageChequeRegaloInputData extends PageObjTM {
	
	public abstract boolean isPageCorrectUntil(int maxSeconds);
	public abstract void clickImporteCheque(Importe importeToClick);
	public abstract void clickComprarIni();
	public abstract boolean isVisibleDataInput(int maxSeconds);
	public abstract void inputDataCheque(ChequeRegalo chequeRegalo);
	public abstract void clickComprarFin(ChequeRegalo chequeRegalo);
	
	public enum Importe {
		EURO_25(25), 
		EURO_50(50), 
		EURO_100(100), 
		EURO_150(150), 
		EURO_200(200), 
		EURO_250(250);
		
		int importe;
		private Importe(int importe) {
			this.importe = importe;
		}
		public int getImporte() {
			return importe;
		}
	}

	public static PageChequeRegaloInputData make(Pais pais) {
		if ("001".compareTo(pais.getCodigo_pais())==0) {
			return new PageChequeRegaloInputDataNew();
		}
		return new PageChequeRegaloInputDataOld();
	}
	
}
