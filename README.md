[![Build Status](https://cicd-eks.sys.mango/jenkins/buildStatus/icon?job=robotest/master)](https://cicd-eks.sys.mango/jenkins/view/QA/job/robotest/job/master/)
[![Quality Gate Status](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=alert_status)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Maintainability Rating](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=sqale_rating)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest)  [![Reliability Rating](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=reliability_rating)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Security Rating](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=security_rating)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Code Smells](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=code_smells)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Coverage](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=coverage)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Duplicated Lines (%)](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=duplicated_lines_density)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Lines of Code](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=ncloc)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest)  [![Technical Debt](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=sqale_index)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest) [![Vulnerabilities](https://sonarqube.pro-k8s-tools.sys.mango/api/project_badges/measure?project=com.mng:robotest&metric=vulnerabilities)](https://sonarqube.pro-k8s-tools.sys.mango/dashboard?id=com.mng:robotest)


# Robotest

Confluent Mango Page https://confluence.mango.com/display/TT/Robotest

Project based on Open Source Artifact https://github.com/AubaySpain/testmaker

Based on TestNG / Selenium 4 / Java17+

Implements a library of E2E tests launched from real Chrome/Firefox Browsers. That library covers many functionalities of many ebusiness applications.

Covers the applications: shop, outlet, votf, manto
Covers the web channels: desktop, mobile, tablet

### Infrastructure

[k8s: https://bitbucket.intranet.mango.es/projects/TEST-OL/repos/k8s-tools-robotest/browse](https://bitbucket.intranet.mango.es/projects/TEST-OL/repos/k8s-tools-robotest/browse)

[ArgoCD: https://argocd.pro-k8s-tools.sys.mango/applications/robotest](https://argocd.dev-k8s-tools.sys.mango/applications/robotest)


### Setup maven

    cp jenkins/settings.xml.template ~/.m2/settings.xml
    
    # replace credentials with your own:
    vim ~/.m2/setting.xml 

### Build and run unit tests
	
	Prerrequisite: install and use the OpenJdk17 
	
    ./mvnw clean package -D skipUnitTests 
    ./mvnw test 
    
### How execute locally from command line

	unzip target/robotest.zip
	
	java -jar robotest/robotest.jar -suite SmokeTest -driver chrome -channel desktop -application shop -url https://shop.mango.com/preHome.faces -tcases FIC001,BOR001
	
	The html report can be found in ./output-library/SmokeTest/{id_testrun}/ReportTSuite.html
	
### How execute locally exposing REST API Server
	
	unzip target/robotest.zip
	
	java -cp robotest/robotest.jar com.mng.robotest.access.rest.ServerRest -port 80
	
	curl -X POST localhost/suiterun/ -d "suite=SmokeTest&driver=chrome&channel=desktop&application=shop&asyncexec=false&url=https://shop.mango.com/preHome.faces&tcases=FIC001,BOR001"
	
	The html report can be viewed in two manners using the "idExecSuite" field returned by the previous POST call:
		1. Opening ./output-library/SmokeTest/{id_testrun}/ReportTSuite.html
		2. Executing the follow url in the browser: localhost/suiterun/{id_testrun}/report

### How execute locally with Docker

	docker build -t jorge2m/robotest:latest .
	
	docker run -d -p 80:8080 --privileged jorge2m/robotest:latest
	
	Launch execution is analog to the previous case
	
	The html report can be viewed using the "idExecSuite" field returned by the previous POST call and executing the follow url in the browser: localhost/suiterun/{id_testrun}/report
	
### How execute locally with Docker in mode debug 
(port 8000 open and visualization of Browser executions)

	install RealVNC Viewer if not yet

	replace file Dockerfile by Dcokerfile_debug
	
	docker build -t jorge2m/robotest:latest .
	
	docker run -d -p 80:8080 -p 5900:5900 -p 8000:8000 --privileged jorge2m/robotest:latest
	
	Open RealVNC Viewer and connecto to localhost:5900 (password 1234)
	
	Execute in the RealVNC session: ./start-server-for-debug.sh
	
	Launch execution is analog to the previous case
	
	The html report can be viewed using the "idExecSuite" field returned by the previous POST call and executing the follow url in the browser: localhost/suiterun/{id_testrun}/report	

### How view and interact with the browsers executed in docker
	modify Dockerfile including the code:
		RUN apt-get update && \
		DEBIAN_FRONTEND=noninteractive apt-get install -y \
		xauth \
		x11-apps
    
		ENV DISPLAY=:0 

	modify docker-entrypoint.sh replacing sentence xvfb-run by:	
		java -cp robotest.jar com.mng.robotest.access.rest.ServerRest -port 8080 -secureport 443 ${PARAM_URL_HUB} ${PARAM_URL_INIT_SLAVE}

	docker run -e DISPLAY=172.28.8.106:0 -p 80:8080 --privileged jorge2m/robotest:latest	

    
### Run integration tests 
  
    ./mvnw clean verify -DskipUnitTests    
