package com.mng.robotest.test80.arq.utils.controlTest;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.testng.ITestContext;

import com.mng.robotest.test80.arq.annotations.validation.ListResultValidation;
import com.mng.robotest.test80.arq.utils.NetTrafficMng;
import com.mng.robotest.test80.arq.utils.State;
import com.mng.robotest.test80.arq.utils.otras.Constantes;

@SuppressWarnings("javadoc")
public class DatosStep {

	//Número de paso
	private int step_number = 0;
	
	//Literal descriptivo del paso a realizar
	private String descripcion; 
	
	//Literal descriptivo del resultado esperado
	private String res_expected; 
	
	//Indicador de si es necesario grabar la imagen
	private boolean grab_image = false; 
	
	//Indicador de si es necesario grabar el HTML
	private boolean grab_HTML = false; 
	
	//Indicador de si es necesario grabar el Nettrafic (actuamente mediante HAR Export en Firefox)
	private boolean grab_Nettrafic = false;
	
	//Indicador de si es necesario grabar la excepción en caso de error
	private boolean grab_ErrorPageIfProblem = true; 
	
	//0->HTML, 1->EXCEL, 2->PDF
	private int type_page = 0; 
	
	//Hora de inicio del paso
	private Date hora_inicio; 
	
	//Hora de fin del paso
	private Date hora_fin;
	
	//Resultado del paso: OK, WARNING o ERROR (x defecto ERROR)
	private State result_steps = State.Nok;
	
	//Indicador de si se ha producido una excepción (por defecto sí)
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
    }
    
//    public datosStep(String c_descripcion, String c_res_expected, boolean c_grab_image, boolean c_grab_HTML, boolean c_grab_ErrorPageIfProblem, int c_type_page, boolean c_excep_exists, int c_result_steps) {
//        this.descripcion = c_descripcion;
//        this.res_expected = c_res_expected;
//        this.hora_inicio = new Date(System.currentTimeMillis());
//        this.grab_image = c_grab_image;
//        this.grab_HTML = c_grab_HTML;
//        this.grab_ErrorPageIfProblem = c_grab_ErrorPageIfProblem;
//        this.type_page = c_type_page;
//        this.excep_exists = c_excep_exists;
//        this.result_steps = c_result_steps;
//        this.step_number = 0;
//    }

    public void setStepNumber (int c_step_number) { 
        this.step_number = c_step_number; 
    }

    public void setDescripcion(String c_descripcion) { 
        this.descripcion = c_descripcion; 
    }
    
    public void setResExpected(String c_res_expected) { 
        this.res_expected = c_res_expected; 
    }
    
    public void setGrabImage(boolean c_grab_image) { 
        this.grab_image = c_grab_image; 
    }
    
    public void setGrabHTML(boolean c_grab_HTML) { 
        this.grab_HTML = c_grab_HTML; 
    }
    
    public void setGrabNettrafic(ITestContext context) {
        String netTrafficStr = context.getSuite().getXmlSuite().getParameter(Constantes.paramNetAnalysis);
        this.grab_Nettrafic = "true".compareTo(netTrafficStr)==0;
        if (getGrabNettrafic()) {
        	NetTrafficMng netTraffic = new NetTrafficMng();
        	netTraffic.resetAndStartNetTraffic();
        }
    }    
    
    public void setGrab_ErrorPageIfProblem(boolean c_grab_ErrorPageIfProblem) { 
        this.grab_ErrorPageIfProblem = c_grab_ErrorPageIfProblem; 
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
    
    public boolean getGrabImage() { 
        return this.grab_image; 
    }
    
    public boolean getGrabHTML() { 
        return this.grab_HTML; 
    }
    
    public boolean getGrabNettrafic() { 
        return this.grab_Nettrafic; 
    }    
    
    public boolean getGrab_ErrorPageIfProblem() { 
        return this.grab_ErrorPageIfProblem; 
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

