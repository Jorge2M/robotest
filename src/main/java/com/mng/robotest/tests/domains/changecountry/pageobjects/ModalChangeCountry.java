package com.mng.robotest.tests.domains.changecountry.pageobjects;

import com.mng.robotest.tests.conf.AppEcom;
import com.mng.robotest.tests.domains.base.PageBase;
import com.mng.robotest.testslegacy.beans.IdiomaPais;
import com.mng.robotest.testslegacy.beans.Pais;

public abstract class ModalChangeCountry extends PageBase {
	
	public abstract boolean isVisible(int seconds);
	public abstract void closeModalIfVisible();
	protected abstract void change(Pais pais, IdiomaPais idioma);
	
	public void changeToCountry(Pais pais, IdiomaPais idioma) {
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
		change(pais, idioma);
	}
	
	public static ModalChangeCountry make(AppEcom app) {
		if (app==AppEcom.outlet) {
			return new ModalChangeCountryGenesis();
		}
		return new ModalChangeCountryOld();
	}
	
}
