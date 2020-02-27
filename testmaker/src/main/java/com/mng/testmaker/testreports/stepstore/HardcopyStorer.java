package com.mng.testmaker.testreports.stepstore;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Base64;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.mng.testmaker.conf.Log4jConfig;
import com.mng.testmaker.domain.suitetree.StepTM;

public class HardcopyStorer extends EvidenceStorer {
	
	public HardcopyStorer() {
		super(StepEvidence.imagen);
	}
	
	@Override
	public void captureContent(StepTM step) {
		WebDriver driver = step.getDriver();
		WebDriver newWebDriver = null;
		if (driver.getClass() == RemoteWebDriver.class) {
			newWebDriver = new Augmenter().augment(driver);
		} else {
			newWebDriver = driver;
		}
		this.content = ((TakesScreenshot)newWebDriver).getScreenshotAs(OutputType.BASE64);
	}
	
	@Override
	public void saveContentEvidenceInFile(String content, String pathFile) {
		byte[] bytesPng = Base64.getMimeDecoder().decode(this.content);
		OutputStream stream = null;
		try {
			File file = new File(pathFile);
			stream = new FileOutputStream(file);
			stream.write(bytesPng);
		} 
		catch (Exception e) {
			Log4jConfig.pLogger.warn("Problem saving File " + pathFile, e);
		}
	}
	
}
