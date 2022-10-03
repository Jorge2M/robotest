package com.mng.robotest.domains.transversal.menus.steps;

import com.github.jorge2m.testmaker.boundary.aspects.step.Step;
import com.mng.robotest.domains.transversal.StepBase;
import com.mng.robotest.domains.transversal.menus.pageobjects.Menu;

public class MenuSteps extends StepBase {

	public void click(Menu menu) {
		if (menu.getSublinea()==null) {
			clickMenu(menu);
		} else {
			clickMenuSublinea(menu);
		}
	}
	
	@Step (
		description=
			"Selección del menú <b>#{menu.getLinea()} / #{menu.getGroup()} / " + 
			"</b><b <b style=\"color:blue;\">#{menu.getMenu()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickMenu(Menu menu) {
		menu.click();
	}

	@Step (
		description=
			"Selección del menú <b>#{menu.getLinea()} / #{menu.getSublinea()} / #{menu.getGroup()} / " + 
			"</b><b <b style=\"color:blue;\">#{menu.getMenu()}</b>", 
		expected=
			"La selección es correcta")	
	private void clickMenuSublinea(Menu menu) {
		menu.click();
	}
	
}
