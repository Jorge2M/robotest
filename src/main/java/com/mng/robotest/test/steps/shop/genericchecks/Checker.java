package com.mng.robotest.test.steps.shop.genericchecks;

import org.openqa.selenium.WebDriver;

import com.github.jorge2m.testmaker.domain.suitetree.ChecksTM;

public interface Checker {

	public abstract ChecksTM check(WebDriver driver);

}
