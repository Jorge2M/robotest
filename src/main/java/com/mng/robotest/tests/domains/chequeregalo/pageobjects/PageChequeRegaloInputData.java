package com.mng.robotest.tests.domains.chequeregalo.pageobjects;

import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.tests.domains.chequeregalo.beans.ChequeRegalo;
import com.mng.robotest.tests.domains.legal.legaltexts.FactoryLegalTexts.PageLegalTexts;
import com.mng.robotest.testslegacy.beans.Pais;

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
		
		int ammount;
		private Importe(int ammount) {
			this.ammount = ammount;
		}
		public int getAmmount() {
			return ammount;
		}
	}

	public static PageChequeRegaloInputData make(Pais pais) {
		if ("001".compareTo(pais.getCodigo_pais())==0) {
			return new PageChequeRegaloInputDataNew();
		}
		return new PageChequeRegaloInputDataOld();
	}
	
	protected PageChequeRegaloInputData() {
		super();
	} 
	
	protected PageChequeRegaloInputData(PageLegalTexts legalTexts) {
		super(legalTexts);
	}
	
}
