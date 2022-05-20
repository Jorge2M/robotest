#!groovy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

library("k8s@1.0.0")


pipeline {
    agent any
    environment {
        CURRENT_DATE = sh(returnStdout: true, script: 'echo $(date -u +%Y%m%d%H%M%S) | tr -d "\n"')
        LAST_COMMIT = sh(returnStdout: true, script: 'git rev-parse --short=10 HEAD | tr -d "\n"')
        APP_VERSION = "${CURRENT_DATE}-${LAST_COMMIT}"
    }
    options {
        buildDiscarder(logRotator(numToKeepStr: '5', daysToKeepStr: '30'))
    }
    stages {
        stage('Init') {
            agent any
            steps {
                script {
                    CURRENT_DATE = sh(returnStdout: true, script: 'echo $(date -u +%Y%m%d%H%M%S) | tr -d "\n"')
                    LAST_COMMIT = sh(returnStdout: true, script: 'git rev-parse --short=10 HEAD | tr -d "\n"')
                    NJORD_VERSION = "${CURRENT_DATE}-${LAST_COMMIT}"
                }
            }
        }

        stage('Run Unit Tests') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-17'
                    args '-v /home/ubuntu/.m2:/ubuntu/.m2'
                }
            }
            steps {
            	sh 'chmod -R 777 ./mvnw'
            	sh './mvnw -version'
	        	sh './mvnw clean'
	        	withCredentials([usernamePassword(credentialsId: 'svc.bitbucket.dev', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
	            	sh './mvnw --settings infrastructure/ci/settings.xml test verify -DskipIntegrationTests -DargLine="-Duser.timezone=Europe/Paris"'
	            }
            }
            post {
                success {
                    script {
                        stash includes: '**/target/', name: 'target'
                    }
                }
            }
        }
        
        stage('Integration Tests') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-17'
                    args '-v /home/ubuntu/.m2:/ubuntu/.m2'
                }
            }        
            steps {
            	unstash 'target'
	        	withCredentials([usernamePassword(credentialsId: 'svc.bitbucket.dev', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
	            	sh "./mvnw --settings infrastructure/ci/settings.xml -B verify -DskipUnitTests"
	            }
            }
        }  
        
        stage('Package') {
            agent {
                docker {
                    image 'maven:3.8.4-openjdk-17'
                    args '-v /home/ubuntu/.m2:/ubuntu/.m2'
                }
            }
            steps {
            	unstash 'target'
            	withCredentials([usernamePassword(credentialsId: 'svc.bitbucket.dev', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
	            	sh "./mvnw --settings infrastructure/ci/settings.xml -B package -DskipTests"
	            }
            }
            post {
                success {
                    script {
                        stash includes: '**/target/', name: 'target'
                    }
                }
            } 
        } 

      

//        stage('E2e Tests') {
//            when { anyOf { branch 'master'; branch 'develop' } }
//            agent {
//                docker {
//                 	alwaysPull true
//                    image 'jorge2m/chrome-firefox-jdk8-maven:latest'

//					  Nota: da la impresión que no funciona cambiando el root por ubuntu,
//					        pero no podemos dejar el root porque tiene afectación al Jenkins
//                    args '--privileged --shm-size=1g -v /home/ubuntu/.m2:/root/.m2'
//                }
//            }
//
//            steps {
//            	unstash 'target'
//	        	sh "mvn -B versions:set -DnewVersion='${NJORD_VERSION}' -DgenerateBackupPoms=false"
//	        	withCredentials([usernamePassword(credentialsId: 'svc.bitbucket.dev', usernameVariable: 'NEXUS_USERNAME', passwordVariable: 'NEXUS_PASSWORD')]) {
//	            	sh "mvn --settings infrastructure/ci/settings.xml -B verify -DskipUnitTests"
//	            }
//            }
//
//            post {
//                success {
//                    script {
//                        stash includes: '**/target/', name: 'server-package'
//                    }
//                }
//            }
//        }
        
        stage('Publish') {
      		when { expression { return env.BRANCH_NAME.equals('master') || env.BRANCH_NAME.equals('develop') || env.BRANCH_NAME.contains('release') } }
      		steps {
        		unstash 'target'
        		sh 'chmod -R 777 ./infrastructure/aws/build-publish-docker.sh'
        		sh './infrastructure/aws/build-publish-docker.sh'
      		}
    	}
    	
//        stage('Deploy to DEV') {
//            when { branch 'develop' }
//            steps {
//                k8sDeploy(templates: "./infrastructure/k8s/dev/", stage: 'dev', imageTag: env.APP_VERSION)
//            }
//        }

    }
    post {
        cleanup {
            cleanWs()
        }
    }
}

//def notifyBitbucket(def state) {
//    script {
//        commitHash = sh(script: "echo \$(cat .git/HEAD)", returnStdout: true).trim()
//    }
//    def payload = """
//    {
//        "state": "$state",
//        "key": "$commitHash",
//        "name": "$commitHash",
//        "url": "$BUILD_URL",
//        "description": "Build status notification from Jenkins."
//    }
//    """
//    withCredentials([usernamePassword(credentialsId: 'citools-jenkins', usernameVariable: 'username', passwordVariable: 'password')]) {
//        sh """
//            curl -u ${username}:${password} \\
//                -H "Content-Type: application/json" \\
//                -X POST https://jira.mangodev.net/stash/rest/build-status/1.0/commits/${commitHash} \\
//                -d '${payload}'
//            """
//    }
//}