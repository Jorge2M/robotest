package com.mng.testmaker.domain.suitetree;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;

import com.mng.testmaker.boundary.aspects.step.SaveWhen;
import com.mng.testmaker.conf.State;
import com.mng.testmaker.domain.StateExecution;
import com.mng.testmaker.domain.util.ParsePathClass;
import com.mng.testmaker.service.TestMaker;
import com.mng.testmaker.testreports.html.NetTrafficSaver;
import com.mng.testmaker.testreports.html.StoreStepEvidencies;

public class StepTM {

	private TestCaseTM testCase;
	private TestRunTM testRun;
	private SuiteTM suite;
	
	private List<ChecksTM> listChecksTM = new ArrayList<>(); 
	private String descripcion; 
	private String res_expected; 
	private SaveWhen saveImagePage = SaveWhen.IfProblem;
	private SaveWhen saveErrorPage = SaveWhen.IfProblem;
	private SaveWhen saveHtmlPage = SaveWhen.Never;
	private SaveWhen saveNettraffic= SaveWhen.Never;
	private String pathMethod;
	private int type_page; 
	private Date hora_inicio; 
	private Date hora_fin;
	private State result_steps = State.Ok;
	private boolean excepExists = true;
	private StateExecution state = StateExecution.Started;
	private boolean isStateUpdated = false;
	
	public StepTM() {
		testCase = TestMaker.getTestCase();
		if (testCase!=null) {
			testRun = testCase.getTestRunParent();
			suite = testRun.getSuiteParent();
		} else {
			testRun = null;
			suite = null;
		}
	}
	
	public TestCaseTM getTestCaseParent() {
		return testCase;
	}
	public TestRunTM getTestRunParent() {
		return testRun;
	}
	public SuiteTM getSuiteParent() {
		return suite;
	}
	public WebDriver getDriver() {
		return testCase.getDriver();
	}
	public String getOutputDirectorySuite() {
		return getTestRunParent().getTestNgContext().getOutputDirectory();
	}
	public int getNumber() {
		List<StepTM> listSteps = getTestCaseParent().getListStep();
		for (int i=0; i<listSteps.size(); i++) {
			if (listSteps.get(i)==this) {
				return i+1;
			}
		}
		return -1;
	}
	
	public void end(boolean exceptionReceived) {
		setExcepExists(exceptionReceived); 
		if (exceptionReceived) {
			setResultSteps(State.Nok);
		}
		storeEvidencies();
		setHoraFin(new Date(System.currentTimeMillis()));
		setState(StateExecution.Finished);
	}
	public void storeEvidencies() {
		StoreStepEvidencies storerEvidencies = new StoreStepEvidencies(this);
		storerEvidencies.storeStepEvidencies();
	}
	
	public List<ChecksTM> getListChecksTM() {
		return listChecksTM;
	}
	public void setListChecksTM(List<ChecksTM> listChecksTM) {
		this.listChecksTM = listChecksTM;
	}
	public int getNumChecksTM() {
		return getListChecksTM().size();
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getResExpected() {
		return res_expected;
	}
	public void setResExpected(String res_expected) {
		this.res_expected = res_expected;
	}
	public SaveWhen getSaveImagePage() {
		return saveImagePage;
	}
	public void setSaveImagePage(SaveWhen saveImagePage) {
		this.saveImagePage = saveImagePage;
	}
	public SaveWhen getSaveErrorPage() {
		return saveErrorPage;
	}
	public void setSaveErrorPage(SaveWhen saveErrorPage) {
		this.saveErrorPage = saveErrorPage;
	}
	public SaveWhen getSaveHtmlPage() {
		return saveHtmlPage;
	}
	public void setSaveHtmlPage(SaveWhen saveHtmlPage) {
		this.saveHtmlPage = saveHtmlPage;
	}
	public SaveWhen getSaveNettraffic() {
		return saveNettraffic;
	}
	public void setSaveNettrafic(SaveWhen saveNettraffic) {
		if (suite.getInputParams().isNetAnalysis()) {
			this.saveNettraffic = saveNettraffic;
			NetTrafficSaver netTraffic = new NetTrafficSaver();
			netTraffic.resetAndStartNetTraffic();
		}
	}
	public String getPathMethod() {
		return pathMethod;
	}
	public void setPathMethod(String pathMethod) {
		this.pathMethod = pathMethod;
	}
	public int getTypePage() {
		return type_page;
	}
	public void setTypePage(int type_page) {
		this.type_page = type_page;
	}
	public Date getHoraInicio() {
		return hora_inicio;
	}
	public void setHoraInicio(Date hora_inicio) {
		this.hora_inicio = hora_inicio;
	}
	public Date getHoraFin() {
		return hora_fin;
	}
	public void setHoraFin(Date hora_fin) {
		this.hora_fin = hora_fin;
	}
	public State getResultSteps() {
		return result_steps;
	}
	public void setResultSteps(State c_result_steps) {
		this.result_steps = c_result_steps; 
		this.isStateUpdated = true;
	}
	public boolean isExcepExists() {
		return excepExists;
	}
	public void setExcepExists(boolean excepExists) {
		this.excepExists = excepExists;
	}
	public StateExecution getState() {
		return state;
	}
	public void setState(StateExecution state) {
		this.state = state;
	}
	public boolean isStateUpdated() {
		return isStateUpdated;
	}
	public void setStateUpdated(boolean isStateUpdated) {
		this.isStateUpdated = isStateUpdated;
	}
	
	public void replaceInDescription(String oldChar, String newChar) {
		this.descripcion = this.descripcion.replace(oldChar, newChar);
	}
	public void addDescriptionText(String text) {
		this.descripcion+=text;
	}
	public void addRightDescriptionText(String text) {
		this.descripcion = text + this.descripcion;
	}
	public void addExpectedText(String text) {
		this.res_expected+=text;
	}
	public void replaceInExpected(String oldChar, String newChar) {
		this.res_expected = this.res_expected.replace(oldChar, newChar);
	}
	public String getPathClass() {
		return ParsePathClass.getPathClass(getPathMethod());
	}
	public String getNameClass() {
		return ParsePathClass.getNameClass(getPathClass());
	}
	public String getNameMethod() {
		return ParsePathClass.getNameMethod(getPathMethod());
	}
	public void setNOKstateByDefault() {
		setExcepExists(true); 
	}
	public void addChecksTM(ChecksTM checksTM) {
		setExcepExists(false);
		this.listChecksTM.add(checksTM);
	}
	public SaveWhen getWhenSave(StepEvidence evidencia) {
		switch (evidencia) {
		case html:
			return saveHtmlPage;
		case errorpage:
			return saveErrorPage;
		case har:
		case harp:
			return saveNettraffic;
		case imagen:
		default:
			return saveImagePage;
		}
	}

	public boolean isAllValidationsWithAvoidEvidences() {
		if (listChecksTM==null || listChecksTM.isEmpty()) {
			return false;
		}
		for (ChecksTM check : listChecksTM) {
			if (!check.isAvoidEvidences()) {
				return false;
			}
		}
		return true;
	}
}
