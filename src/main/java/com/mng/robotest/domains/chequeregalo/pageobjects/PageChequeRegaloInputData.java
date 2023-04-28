package com.mng.robotest.domains.chequeregalo.pageobjects;

import com.mng.robotest.domains.base.PageBase;
import com.mng.robotest.domains.legal.pageobjects.FactoryLegalTexts.PageLegalTexts;
import com.mng.robotest.test.beans.Pais;
import com.mng.robotest.test.generic.ChequeRegalo;

public abstract class PageChequeRegaloInputData extends PageBase {
	
	public abstract boolean isPageCorrectUntil(int seconds);
	public abstract void clickImporteCheque(Importe importeToClick);
	public abstract void clickComprarIni();
	public abstract boolean isVisibleDataInput(int seconds);
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
	
	public PageChequeRegaloInputData() {
		super();
	} 
	
	public PageChequeRegaloInputData(PageLegalTexts legalTexts) {
		super(legalTexts);
	}
	
}
