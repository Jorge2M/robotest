#!groovy
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

library("k8s@1.0.0")


pipeline {
    agent any
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

        stage('Run tests') {
            agent {
                docker {
                    image 'maven:3.5.4-jdk-8-alpine'
                    args '-v /home/ubuntu/.m2:/root/.m2'
                }
            }
            steps {
	        	sh 'mvn clean'
	            sh 'mvn test verify -DskipITs -DargLine="-Duser.timezone=Europe/Paris"'
            }
            post {
                success {
                    script {
                        stash includes: '**/target/', name: 'target'
                    }
                }
            }
        }

        stage('Create and zip package') {
            when { anyOf { branch 'master'; branch 'develop' } }
            agent {
                docker {
                    image 'markhobson/maven-chrome:jdk-8'
                    args '--privileged --shm-size=1g -v /home/ubuntu/.m2:/root/.m2'
                }
            }

            steps {
	        	sh "mvn -B versions:set -DnewVersion='${NJORD_VERSION}' -DgenerateBackupPoms=false"
	        	sh "mvn -B clean package -DskipTests"
	            sh "mvn -B failsafe:integration-test"
            }

            post {
                success {
                    script {
                        stash includes: '**/target/', name: 'server-package'
                    }
                }
            }
        }

//        stage('Upload package to test S3') {
//            when { anyOf { branch 'master'; branch 'develop' } }
//            agent any
//            steps {
//                unstash 'server-package'
//                sh "aws configure set default.s3.multipart_threshold 1024MB"
//                sh "aws s3 cp target/ordernotifications-${NJORD_VERSION}.zip s3://test-mng-releases/code/ordernotifications-${NJORD_VERSION}.zip --grants full=id=3daf09e0a965fc4c87482cd9a3ca83031cf90941ecddfa61f262755808d8cc7e"
//            }
//        }

//        stage('Deploy in TEST') {
//            when { anyOf { branch 'develop' } }
//            agent {
//                docker {
//                    image 'byrnedo/alpine-curl'
//                    args '--entrypoint ""'
//                }
//            }
//            steps {
//                script {
//                    corporateJenkins.call(
//                            jobUrl: "https://citools.mangodev.net/jenkins/job/SYSOPS.DEPLOY_DEV/buildWithParameters",
//                            authentication: "citools-jenkins",
//                            params: [
//                                    'ARTIFACT' : "ordernotifications-${NJORD_VERSION}",
//                                    'GROUPNAME': "ordernotifications"
//                            ],
//                            numberOfRetries: 2)
//                    applicationHealth.check(
//                            url: "http://ordernotifications.dev.mango.com/info",
//                            expectedVersionNumber: "${NJORD_VERSION}",
//                            timeoutInMilliseconds: 30000,
//                            sleepTimeBetweenStepsInMilliseconds: 5000)
//                }
//            }
//        }

//        stage('Promote package to PRE S3'){
//            when { anyOf { branch 'master'; branch 'develop' } }
//            agent any
//            steps {
//                httpRequest httpMode: 'POST',
//                        url: "https://citools.mangodev.net/jenkins/job/SYSOPS.PROMOTE-S3_TEST_TO_PRE/buildWithParameters?ZIP_NAME=ordernotifications-${NJORD_VERSION}.zip&ARTIFACT=ordernotifications",
//                        authentication: 'citools-jenkins'
//            }
//        }

//        stage('Deploy in PRE') {
//            when { anyOf { branch 'master'; branch 'develop' } }
//            agent {
//                docker {
//                    image 'byrnedo/alpine-curl'
//                    args '--entrypoint ""'
//                }
//            }
//            steps {
//                script {
//                    corporateJenkins.call(
//                            jobUrl: "https://citools.mangodev.net/jenkins/job/SYSOPS.DEPLOY_PRE/buildWithParameters",
//                            authentication: "citools-jenkins",
//                            params: [
//                                    'ARTIFACT' : "ordernotifications-${NJORD_VERSION}",
//                                    'GROUPNAME': "ordernotifications"
//                            ],
//                            numberOfRetries: 2)
//                    applicationHealth.check(
//                            url: "http://ordernotifications.pre.mango.com/info",
//                            expectedVersionNumber: "${NJORD_VERSION}",
//                            timeoutInMilliseconds: 30000,
//                            sleepTimeBetweenStepsInMilliseconds: 5000)
//                }
//            }
//        }

//        stage('Promote package to PRO S3'){
//            when { anyOf { branch 'master' } }
//            agent any
//            steps {
//                httpRequest httpMode: 'POST',
//                        url: "https://citools.mangodev.net/jenkins/job/SYSOPS.PROMOTE-S3_PRE_TO_PRO/buildWithParameters?ZIP_NAME=ordernotifications-${NJORD_VERSION}.zip&ARTIFACT=ordernotifications",
//                        authentication: 'citools-jenkins'
//            }
//        }
    }
//    post {
//        cleanup {
//        	//TODO
//            //cleanWs()
//        }
//    }
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