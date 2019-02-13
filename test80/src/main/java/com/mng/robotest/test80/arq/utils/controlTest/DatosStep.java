package com.mng.robotest.test80.arq.utils.controlTest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.testng.ITestContext;

import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.NetTrafficMng;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.ThreadData;
import com.mng.robotest.test80.arq.utils.otras.Constantes;

@SuppressWarnings("javadoc")
public class DatosStep {
	
	public enum SaveWhen {
		Always, 
		Never, 
		IfProblem;
		
		boolean IfProblemSave() {
			return (this==Always || this==IfProblem);
		}
	}

	private int step_number = 0;
	private String descripcion; 
	private String res_expected; 
	private SaveWhen saveImagePage = SaveWhen.IfProblem;
	private SaveWhen saveErrorPage = SaveWhen.IfProblem;
	private SaveWhen saveHtmlPage = SaveWhen.Never;
	private SaveWhen saveNettraffic = SaveWhen.Never;
	private int type_page = 0; 
	private Date hora_inicio; 
	private Date hora_fin;
	private State result_steps = State.Nok;
	private boolean excep_exists = true;
	private ListResultValidation listResultValidations;
	private String nameMethodWithFactory = "";

    public DatosStep() {
        this.step_number = 0;
    }

    public DatosStep(String c_descripcion, String c_res_expected) {
        this.descripcion = c_descripcion;
        this.res_expected = c_res_expected;
        this.hora_inicio = new Date(System.currentTimeMillis());
        ThreadData.storeInThread(this);
    }

    public void setStepNumber (int c_step_number) { 
        this.step_number = c_step_number; 
    }

    public void setDescripcion(String c_descripcion) { 
        this.descripcion = c_descripcion; 
    }
    
    public void setResExpected(String c_res_expected) { 
        this.res_expected = c_res_expected; 
    }
    
    public void setSaveImagePage(SaveWhen saveImagePage) { 
        this.saveImagePage = saveImagePage; 
    }
    
    public void setSaveErrorPage(SaveWhen saveErrorPage) { 
        this.saveErrorPage = saveErrorPage; 
    }
    
    public void setSaveHtmlPage(SaveWhen saveHtmlPage) { 
        this.saveHtmlPage = saveHtmlPage; 
    }
    
    public void setSaveNettrafic(SaveWhen saveNettraffic, ITestContext context) {
        String netTrafficStr = context.getSuite().getXmlSuite().getParameter(Constantes.paramNetAnalysis);
        if ("true".compareTo(netTrafficStr)==0) {
        	this.saveNettraffic = saveNettraffic;
        	NetTrafficMng netTraffic = new NetTrafficMng();
        	netTraffic.resetAndStartNetTraffic();
        }
    }    
    
    public void setTypePage(int c_type_page) { 
        this.type_page = c_type_page; 
    }
    
    public void setHoraInicio(Date c_hora_inicio) { 
        this.hora_inicio = c_hora_inicio; 
    }
    
    public void setHoraFin(Date c_hora_fin) {
        this.hora_fin = c_hora_fin; 
    }
    
    public void setResultSteps(State c_result_steps) {
        this.result_steps = c_result_steps; 
    }

    public void setExcepExists(boolean c_excep_exists) {
        this.excep_exists = c_excep_exists; 
    }
    
    public int getStepNumber() { 
        return this.step_number; 
    }
    
    public String getDescripcion() { 
        return this.descripcion; 
    }
    
    public String getResExpected() { 
        return this.res_expected; 
    }
    
    public SaveWhen getSaveImagePage() { 
        return this.saveImagePage; 
    }
    
    public SaveWhen getSaveHtmlPage() { 
        return this.saveHtmlPage; 
    }
    
    public SaveWhen getSaveErrorPage() { 
        return this.saveErrorPage; 
    }
    
    public SaveWhen getSaveNettrafic() { 
        return this.saveNettraffic; 
    }    
    
    public int getTypePage() { 
        return this.type_page; 
    }
    
    public Date getHoraInicio() { 
        return this.hora_inicio; 
    }
    
    public Date getHoraFin() { 
        return this.hora_fin; 
    }
    
    public State getResultSteps() { 
        return this.result_steps; 
    }
    
    public boolean getExcepExists() { 
        return this.excep_exists; 
    }
    
    public void setNOKstateByDefault() {
    	setExcepExists(true); 
    	setResultSteps(State.Nok);
    }
    
    public void setNameMethodWithFactory(String nameMethodWithFactory) {
    	this.nameMethodWithFactory = nameMethodWithFactory;
    }
    
    public String getNameMethodWithFactory() {
    	return this.nameMethodWithFactory;
    }
    
    public void setListResultValidations(ListResultValidation listResultValidations) {
    	setExcepExists(false);
    	this.listResultValidations = listResultValidations;
    }
    
    public List<Integer> getListCodeNumStateValidations() {
    	List<Integer> result = new ArrayList<>();
    	if (this.listResultValidations!=null) {
    		result = this.listResultValidations.getListCodeNumStateValidations();
    	}
    	
    	return result;
    }
}
