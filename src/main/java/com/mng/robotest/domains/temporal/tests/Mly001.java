package com.mng.robotest.domains.temporal.tests;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

import com.github.jorge2m.testmaker.boundary.aspects.validation.Validation;
import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.base.TestBase;
import com.mng.robotest.test.beans.IdiomaPais;
import com.mng.robotest.test.beans.Pais;

public class Mly001 extends TestBase {

	private static final List<CountryModal> LIST_COUNTRY_MODAL = Arrays.asList(
			new CountryModal("684", "21/06/2023"),
			new CountryModal("690", "21/06/2023"),
			new CountryModal("720", "24/06/2023"),
			new CountryModal("632", "24/06/2023"),
			new CountryModal("636", "26/06/2023"),
			new CountryModal("649", "26/06/2023"),
			new CountryModal("644", "26/06/2023"),
			new CountryModal("640", "26/06/2023"),
			new CountryModal("728", "27/06/2023"),
			new CountryModal("696", "28/06/2023"),
			new CountryModal("070", "30/06/2023"),
			new CountryModal("090", "30/06/2023"),
			new CountryModal("628", "30/06/2023"),
			new CountryModal("068", "05/07/2023"),
			new CountryModal("008", "06/07/2023"),
			new CountryModal("091", "06/07/2023"),
			new CountryModal("708", "05/07/2023"),
			new CountryModal("032", "05/07/2023"),
			new CountryModal("740", "05/07/2023"),
			new CountryModal("700", "05/07/2023"),
			new CountryModal("743", "05/07/2023"),
			new CountryModal("701", "05/07/2023"),
			new CountryModal("028", "06/07/2023"),
			new CountryModal("066", "05/07/2023"),
			new CountryModal("706", "05/07/2023"),
			new CountryModal("030", "06/07/2023"),
			new CountryModal("680", "05/07/2023"),
			new CountryModal("480", "06/07/2023"),
			new CountryModal("500", "06/07/2023"),
			new CountryModal("077", "05/07/2023"),
			new CountryModal("078", "05/07/2023"),
			new CountryModal("083", "05/07/2023"),
			new CountryModal("412", "09/07/2023"),
			new CountryModal("052", "09/07/2023"),
			new CountryModal("456", "11/07/2023"),
			new CountryModal("003", "11/07/2023"),
			new CountryModal("022", "12/07/2023"),
			new CountryModal("001", "12/07/2023"),
			new CountryModal("021", "12/07/2023"),
			new CountryModal("023", "12/07/2023"),
			new CountryModal("010", "12/07/2023"),
			new CountryModal("667", "11/07/2023"),
			new CountryModal("004", "12/07/2023"),
			new CountryModal("038", "12/07/2023"),
			new CountryModal("092", "12/07/2023"),
			new CountryModal("037", "12/07/2023"),
			new CountryModal("060", "12/07/2023"),
			new CountryModal("036", "12/07/2023"),
			new CountryModal("019", "12/07/2023"),
			new CountryModal("006", "12/07/2023"),
			new CountryModal("404", "12/07/2023"),
			new CountryModal("400", "12/07/2023"),
			new CountryModal("800", "12/07/2023"),
			new CountryModal("716", "12/07/2023"),
			new CountryModal("076", "12/07/2023"),
			new CountryModal("602", "12/07/2023"),
			new CountryModal("074", "12/07/2023"),
			new CountryModal("063", "13/07/2023"),
			new CountryModal("061", "13/07/2023"),
			new CountryModal("064", "13/07/2023"),
			new CountryModal("007", "13/07/2023"),
			new CountryModal("474", "13/07/2023"),
			new CountryModal("436", "13/07/2023"),
			new CountryModal("416", "13/07/2023"),
			new CountryModal("428", "13/07/2023"),
			new CountryModal("600", "12/07/2023"),
			new CountryModal("093", "13/07/2023"),
			new CountryModal("432", "13/07/2023"),
			new CountryModal("094", "13/07/2023"),
			new CountryModal("075", "12/07/2023"),
			new CountryModal("512", "13/07/2023"),
			new CountryModal("504", "13/07/2023"),
			new CountryModal("018", "14/07/2023"),
			new CountryModal("043", "14/07/2023"),
			new CountryModal("053", "16/07/2023"),
			new CountryModal("054", "16/07/2023"),
			new CountryModal("054", "16/07/2023"),
			new CountryModal("054", "16/07/2023"),
			new CountryModal("055", "16/07/2023"),
			new CountryModal("664", "17/07/2023"),
			new CountryModal("011", "19/07/2023"),
			new CountryModal("015", "19/07/2023"),
			new CountryModal("046", "19/07/2023"),
			new CountryModal("496", "19/07/2023"),
			new CountryModal("647", "19/07/2023"),
			new CountryModal("220", "20/07/2023"),
			new CountryModal("442", "20/07/2023"),
			new CountryModal("017", "21/07/2023"),
			new CountryModal("024", "22/07/2023"),
			new CountryModal("005", "26/07/2023"),
			new CountryModal("009", "30/07/2023")
		);
	
	private static class CountryModal {
		private String country;
		private String finish;
		
		public CountryModal(String country, String finish) {
			this.country = country;
			this.finish = finish;
		}
		
		public String getCountry() {
			return country;
		}
		public Calendar getTimeFinish(String country) throws Exception {
			var calendar = Calendar.getInstance();
			var dateFormat = new SimpleDateFormat("dd/MM/yyyy");
			dateFormat.setTimeZone(calendar.getTimeZone());
			calendar.setTime(dateFormat.parse(finish)); 
			return calendar;
		}
	}
	
	public Mly001(Pais pais, IdiomaPais idioma) {
		super();
		dataTest.setPais(pais);
		dataTest.setIdioma(idioma);
	}
	
	@Override
	public void execute() throws Exception {
		access();
		checkModal();
	}
	
	private boolean checkModal() throws Exception {
		if (dateFinishBeforeNow()) {
			if (dataTest.getPais().loyalty()) {
				return checkModalMangoLikesYou();
			} else {
				return checkModalDiscountNewsletterSubscription();
			}
		}
		return checkModalDoesntAppear();
	}
	
	private boolean dateFinishBeforeNow() throws Exception {
		var dateFinish = getDateFinish();
		return Calendar.getInstance().after(dateFinish);
	}
	
	private Calendar getDateFinish() throws Exception {
		String country = dataTest.getCodigoPais();
		Optional<CountryModal> countryModal = LIST_COUNTRY_MODAL.stream()
			.filter(c -> c.getCountry().compareTo(country)==0)
			.findFirst();
		
		if (countryModal.isPresent()) {
			return countryModal.get().getTimeFinish(country);
		}
		return null;
	}
	
	private static final String XPATH_MODAL = "//micro-frontend[@id[contains(.,'newsletterSubscriptionModal')]]"; 
	
	@Validation(description="Aparece el modal de Mango Likes You")
	private boolean checkModalMangoLikesYou() {
		return isModalMangoLikesYouVisible(3);
	}
	
	@Validation(description="Aparece el modal de 10% de descuento por suscripción a la NewsLetter")
	private boolean checkModalDiscountNewsletterSubscription() {
		return isModalDiscouNewsletterSubscriptionVisible(3);
	}	
	
	@Validation(description="No aparece ningún modal")
	private boolean checkModalDoesntAppear() {
		return 
			!isModalMangoLikesYouVisible(0) &&
			!isModalDiscouNewsletterSubscriptionVisible(0);
	}
	
	private boolean isModalMangoLikesYouVisible(int seconds) {
		return state(State.Visible, XPATH_MODAL + "//a[@data-testid='newsletterSubscriptionModal.feedback.createAccount']").wait(seconds).check();
	}
	private boolean isModalDiscouNewsletterSubscriptionVisible(int seconds) {
		return state(State.Visible, XPATH_MODAL + "//input[@data-testid='newsletterSubscriptionModal.nonModal.toggle']").wait(seconds).check();
	}
}
