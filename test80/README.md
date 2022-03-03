[![Build Status](https://cicd-eks.sys.mango/jenkins/buildStatus/icon?job=robotest/develop)](https://cicd-eks.sys.mango/jenkins/view/Robotest/job/robotest/job/develop/)


# Robotest

Confluent Mango Page https://confluence.mango.com/display/TT/Robotest

Project based on Open Source Artifact https://github.com/AubaySpain/testmaker

Based on TestNG / Selenium 4 / Java8+

Implements a library of E2E tests launched from real Chrome/Firefox Browsers. That library covers many functionalities of many ebusiness applications.

Covers the applications: shop, outlet, votf, manto
Covers the web channels: desktop, mobile, tablet

### Infrastructure

[k8s: https://bitbucket.intranet.mango.es/projects/TEST-OL/repos/k8s-tools-robotest/browse](https://bitbucket.intranet.mango.es/projects/TEST-OL/repos/k8s-tools-robotest/browse)

[ArgoCD: https://argocd.dev-k8s-tools.sys.mango/applications/robotest](https://argocd.dev-k8s-tools.sys.mango/applications/robotest)


### Setup maven

    cp jenkins/settings.xml.template ~/.m2/settings.xml
    
    # replace credentials with your own:
    vim ~/.m2/setting.xml 

### Build and run unit tests

    ./mvnw clean package -D skipUnitTests 
    ./mvnw test 
    
### How execute locally from command line

	unzip target/test80.zip
	
	java -jar test80/test80.jar -suite SmokeTest -driver chrome -channel desktop -application shop -url https://shop.mango.com/preHome.faces -tcases FIC001,BOR001
	
	The html report can be found in ./output-library/SmokeTest/{id_testrun}/ReportTSuite.html
	
### How execute locally exposing REST API Server
	
	java -cp test80/test80.jar com.mng.robotest.access.rest.ServerRest -port 80
	
	curl -X POST localhost/suiterun/ -d "suite=SmokeTest&driver=chrome&channel=desktop&application=shop&asyncexec=false&url=https://shop.mango.com/preHome.faces&tcases=FIC001,BOR001"
	
	The html report can be viewed in two manners using the "idExecSuite" field returned by the previous POST call:
		1. Opening ./output-library/SmokeTest/{id_testrun}/ReportTSuite.html
		2. Executing the follow url in the browser: localhost/suiterun/{id_testrun}/report

### How execute locally with Docker

	docker build -t jorge2m/test80:latest .
	
	docker run -d -p 80:8080 --privileged jorge2m/test80:latest
	
	Launch execution is analog to the previous case
	
	The html report can be viewed using the "idExecSuite" field returned by the previous POST call and executing the follow url in the browser: localhost/suiterun/{id_testrun}/report
    
### Run integration tests 
  
    ./mvnw clean verify -DskipUnitTests    
