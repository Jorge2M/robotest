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
	private static final String XPATH_LINK_ANYADIR_DIRECCION = "//*[@data-testid[contains(.,'newAddress.button')]]";
	private static final String XPATH_LINK_EDITAR = "//*[@data-testid[contains(.,'edit.button')]]";
	
	private static final String XPATH_LINE_DIRECTION = "//*[@data-testid[contains(.,'modalAddresses.addressRadio')]]";
	private static final String XPATH_TEXT_PRINCIPAL = "//*[@data-testid[contains(.,'addressExtraInfo')]]";
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
	
	public boolean isVisible() throws Exception {
		waitMillis(1000);
		return state(Visible, XPATH_MODAL_DIRECTIONS).check();
	}
	
	public Optional<Direction> getPrincipalDirection(int seconds) {
		for (int i=0; i<seconds; i++) {
			Optional<Direction> optDireccion = getPrincipalDirection();
			if (optDireccion.isPresent()) {
				return optDireccion;
			}
			waitMillis(1000);
		}
		return Optional.empty();
	}
	
	public Optional<Direction> getPrincipalDirection() {
		for (Direction direction : getDirections()) {
			if (direction.isPrincipal()) {
				return Optional.of(direction);
			}
		}
		return Optional.empty();
	}
	
	public Optional<Direction> getDirection(String address, int seconds) {
		for (int i=0; i<5; i++) {
			Optional<Direction> direction = getDirection(address);
			if (direction.isPresent()) {
				return direction;
			}
			waitMillis(1000);
		}
		return Optional.empty();
	}
	
	public Optional<Direction> getDirection(String address) {
		return getDirections().stream()
			.filter(s -> s.getAddress().contains(address))
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
			return getElement(lineDirection, "." + item.getXPath()).getText();
		}
		return "";
	}
	
	private boolean isPrincipal(WebElement lineDirection) {
		return state(State.Visible, lineDirection).by(By.xpath("." + XPATH_TEXT_PRINCIPAL)).check();
	}
	
	public void clickEditAddress(String address) throws NoSuchElementException {
		Optional<Direction> directionOpt = getDirection(address);
		if (directionOpt.isEmpty()) {
			throw new NoSuchElementException("Direction not found in multidirection modal");
		}
		WebElement directionElem = directionOpt.get().getElement();
		click(directionElem).by(By.xpath(XPATH_LINK_EDITAR)).exec();
	}
}
