package com.mng.robotest.tests.domains.manto.tests;

import com.mng.robotest.tests.domains.base.TestMantoBase;
import com.mng.robotest.tests.domains.manto.steps.PageMenusMantoSteps;
import com.mng.robotest.tests.domains.manto.tests.MenusFact.Section;

public class Man900 extends TestMantoBase {

	private final Section section;
	
	public Man900(Section section) {
		this.section = section;
	}
	
	@Override
	public void execute() {
		accesoAlmacenEspanya();
		navigateMenus();
	}
	
	private void navigateMenus() {
		new PageMenusMantoSteps().comprobarMenusManto(section);
	}

}
