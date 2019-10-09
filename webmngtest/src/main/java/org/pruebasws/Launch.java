package org.pruebasws;
/*
 TEMAS PENDIENTES IMI:
    - IMPORTANTE: Está pendiente encontrar una forma de determinar un TIMEOUT para los 'findElement'. Actualmente, si webdriver no lo encuentra no acaba nunca
 	- Se ha de modificar la función imi.opsGenericas.validacGenericas adaptándola a los errores estándars del IMI
    - Se ha de crear un fichero de properties compare.properties específico por entidad
    - Se ha de buscar una forma de realizar un Kill automático del proceso IEDriverServer.exe
    - Se ha de ajustar el HTML asociado a cada página pues:
    	- Aparecen carácteres extraños
    	- No se lincan correctamente las imágenes
 */

import org.pruebasws.thread.TSuiteThreadsManager;
import org.testng.TestNG;

import com.mng.testmaker.domain.StateRun;
import com.mng.testmaker.jdbc.dao.SuitesDAO;

/**
 * @param args
 *    [0]: Fichero testng.xml
 *    [1]: Indicador de si acceso a BD (0->NO, 1->SI)
 */
@SuppressWarnings("javadoc")
public class Launch {
    public static void lanzar(String[] args) {
        TestNG.privateMain(args, null);
    }
    
    public static boolean stopTSuite(String idThreadGroupTestSuite) throws Exception {
        boolean stopped = false;
        String idExecSuite = TSuiteThreadsManager.getIdExecSuiteFromLocator(idThreadGroupTestSuite);
        stopped = stopTSuiteViaMarkInTableSuite(idExecSuite);
        if (!stopped)
            //Solución más expeditiva y menos controlada. Problema: Los navegadores Chrome no desaparecen.
            stopTSuiteViaKillThread(idThreadGroupTestSuite);
        
        return stopped;
    }
    
    public static boolean stopTSuiteViaMarkInTableSuite(String idExecSuite) throws Exception {
        int timeoutSeconds = 40;
        SuitesDAO.updateStateSuite(StateRun.Stopping, idExecSuite);
        StateRun stateSuiteNew = StateRun.Stopping;
        int i=0;
        do {
            Thread.sleep(3000);
            i+=3;
            stateSuiteNew = SuitesDAO.getStateSuite(idExecSuite);
        }
        while(stateSuiteNew==StateRun.Stopping && i<timeoutSeconds);
        
        return (stateSuiteNew!=StateRun.Stopping); 
    }
    
    public static void stopTSuiteViaKillThread(String idThreadGroupTestSuite) throws Exception {
        TSuiteThreadsManager.stopThreadsTestSuite(idThreadGroupTestSuite);
    }
}
