package com.mng.robotest.test80.mango.test.pageobject.chequeregalo;

import org.openqa.selenium.WebDriver;

import com.mng.robotest.test80.mango.test.beans.Pais;
import com.mng.robotest.test80.mango.test.generic.ChequeRegalo;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.PageObjTM;


public abstract class PageChequeRegaloInputData extends PageObjTM {
	
	public abstract boolean isPageCorrectUntil(int maxSeconds);
	public abstract void clickImporteCheque(Importe importeToClick);
	public abstract void clickComprarIni();
	public abstract boolean isVisibleDataInput(int maxSeconds);
	public abstract void inputDataCheque(ChequeRegalo chequeRegalo);
	public abstract void clickComprarFin(ChequeRegalo chequeRegalo);
	
	public enum Importe {
		euro25(25), 
		euro50(50), 
		euro100(100), 
		euro150(150), 
		euro200(200), 
		euro250(250);
		
		int importe;
		private Importe(int importe) {
			this.importe = importe;
		}
		public int getImporte() {
			return importe;
		}
	}
	
	protected PageChequeRegaloInputData(WebDriver driver) {
		super(driver);
	}

	public static PageChequeRegaloInputData make(Pais pais, WebDriver driver) {
		if ("001".compareTo(pais.getCodigo_pais())==0) {
			return new PageChequeRegaloInputDataNew(driver);
		}
		return new PageChequeRegaloInputDataOld(driver);
	}
	
}
