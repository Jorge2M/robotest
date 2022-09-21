package com.mng.robotest.domains.compra.pageobject;

import static com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State.Visible;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.NoSuchElementException;

import com.github.jorge2m.testmaker.service.webdriver.pageobject.StateElement.State;
import com.mng.robotest.domains.compra.beans.Direction;
import com.mng.robotest.domains.transversal.PageBase;

public class ModalMultidirection extends PageBase {

	private static final String XPATH_MODAL_DIRECTIONS ="//*[@data-testid='checkout.multiAddress.modalAddresses']";
	private static final String XPATH_LINK_ANYADIR_DIRECCION = "//[@data-testid[contains(.,'newAddress.button')]]";
	private static final String XPATH_LINK_EDITAR = "//*[@data-testid[contains(.,'edit.button')]]";
	
	private static final String XPATH_LINE_DIRECTION = "//label[@for[contains(.,'address-')]]";
	private static final String XPATH_TEXT_PRINCIPAL = "//*[@date-testid[contains(.,'addressExtraInfo')]]";
	public enum DirectionItem {
		NAME("addressName"),
		ADDRESS("addressDirection"),
		LOCATION("addressLocation"),
		PHONE("addressPhone");
		
		private String testid;
		private DirectionItem(String testid) {
			this.testid = testid;
		}
		public String getXPath() {
			return String.format("//*[@data-testid[contains(.,'%s')]]", testid);
		}
	}
	
//	private static final String XPATH_CHECKOUT_DELIVERY_ADDRESS ="//*[@data-testid='checkout.delivery.address']";
//
//	private static final String XPATH_DIRECTIONS ="//*[@data-testid='checkout.multiAddress.modalAddresses.addressRadio']";
//	private static final String XPATH_MAIN_DIRECTION ="//*[@data-testid='checkout.multiAddress.modalAddresses.addressExtraInfo']";
//	private static final String XPATH_DIRECTION ="//*[@data-testid='checkout.multiAddress.modalAddresses.addressDirection']";
//	private static final String XPATH_CLOSE_MODAL ="//*[@data-testid='modal.close.button']";
//
//	private static final String XPATH_NAME ="//*[@data-testid='checkout.multiAddress.modalAddresses.addressName']";
//	private static final String XPATH_PROVINCE ="//*[@data-testid='checkout.multiAddress.modalAddresses.addressLocation']";
//	private static final String XPATH_TFN ="//*[@data-testid='checkout.multiAddress.modalAddresses.addressPhone']";
	
	
	public boolean isVisible() throws Exception {
		return state(Visible, XPATH_MODAL_DIRECTIONS).check();
	}
	
	public Optional<Direction> getPrincipalDirection() {
		for (Direction direction : getDirections()) {
			if (direction.isPrincipal()) {
				return Optional.of(direction);
			}
		}
		return Optional.empty();
	}
	
	public Optional<Direction> getDirection(String address) {
		return getDirections().stream()
			.filter(s -> s.getAddress().compareTo(address)==0)
			.findAny();
	}
	
	public List<Direction> getDirections() {
		List<Direction> listDirections = new ArrayList<>();
		for (WebElement directionElem : getElements(XPATH_LINE_DIRECTION)) {
			Direction direction = new Direction();
			direction.setName(getDirectionItem(directionElem, DirectionItem.NAME));
			direction.setAddress(getDirectionItem(directionElem, DirectionItem.ADDRESS));
			direction.setLocation(getDirectionItem(directionElem, DirectionItem.LOCATION));
			direction.setPhone(getDirectionItem(directionElem, DirectionItem.PHONE));
			direction.setPrincipal(isPrincipal(directionElem));
			direction.setElement(directionElem);
			listDirections.add(direction);
		}
		return listDirections;
	}
	
	public void clickAnyadirOtraDireccion() {
		click(XPATH_LINK_ANYADIR_DIRECCION).exec();
	}
	
	private String getDirectionItem(WebElement lineDirection, DirectionItem item) {
		if (state(State.Visible, lineDirection).by(By.xpath(item.getXPath())).check()) {
			return getElement(lineDirection, item.getXPath()).getText();
		}
		return "";
	}
	
	private boolean isPrincipal(WebElement lineDirection) {
		return state(State.Visible, lineDirection).by(By.xpath(XPATH_TEXT_PRINCIPAL)).check();
	}
	
	public void clickEditAddress(String address) throws NoSuchElementException {
		Optional<Direction> directionOpt = getDirection(address);
		if (directionOpt.isEmpty()) {
			throw new NoSuchElementException("Direction not found in multidirection modal");
		}
		WebElement directionElem = directionOpt.get().getElement();
		click(directionElem).by(By.xpath(XPATH_LINK_EDITAR)).exec();
	}
	
//	public String getAddress() throws Exception {
//		String addressCheckout = "";
//		waitLoadPage();
//		List<WebElement> addressModal = getElements(XPATH_DIRECTIONS);
//		List<WebElement> address1 = getElements(XPATH_DIRECTION);
//
//		Iterator<WebElement> it = addressModal.iterator();
//		while (it.hasNext()) {
//			WebElement address = it.next();
//			String addressLista= address.getAttribute("data-testid");
//			if (addressLista.contains(XPATH_MAIN_DIRECTION)) {
//				Iterator<WebElement> it2 = address1.iterator();
//				while (it.hasNext()) {
//					WebElement address2 = it2.next();
//					String addressModalPrincipal= address2.getAttribute("data-testid");
//					if (addressModalPrincipal.contains(XPATH_DIRECTION)) {
//						 addressCheckout = address2.getText();
//					}
//				}
//			}
//		}
//		return addressCheckout;
//	}
//	
//	public boolean isDirectionsPrincipal() throws Exception {
//		click(XPATH_CLOSE_MODAL).exec();
//		return true;
//		//TODO
//		//String directionCheckout= getTextDireccionEnvioCompleta();
//		//TODO
////		if (getAddress().equals(directionCheckout)) {
////			return true;
////		}else{
////			return false;
////		}
//
//	}
//	
//	public void clickBtnCta() throws Exception {
//		click(XPATH_BTN_ADDRESS).exec();
//	}

	

}
