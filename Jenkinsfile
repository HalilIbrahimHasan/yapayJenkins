pipeline {
    agent any
    
    tools {
        maven 'Maven 3.9.6'
    }
    
    environment {
        JAVA_HOME = '/Library/Java/JavaVirtualMachines/jdk-21.jdk/Contents/Home'
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }
    
    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        
        stage('Verify Java') {
            steps {
                sh '''
                    java -version
                    echo "JAVA_HOME: $JAVA_HOME"
                    echo "PATH: $PATH"
                '''
            }
        }
        
        stage('Run Tests') {
            steps {
                sh '''
                    mvn clean test -Dcucumber.filter.tags="@gpt"
                '''
            }
        }
        
        stage('Generate Reports') {
            steps {
                script {
                    // Publish Cucumber Reports
                    cucumber buildStatus: 'UNSTABLE',
                            reportTitle: 'Cucumber Report',
                            fileIncludePattern: '**/CucumberTestReport.json',
                            trendsLimit: 10,
                            classifications: [
                                [
                                    'key': 'Browser',
                                    'value': 'Chrome'
                                ],
                                [
                                    'key': 'Environment',
                                    'value': 'Test'
                                ]
                            ]
                    
                    // Archive Spark Reports
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output/SparkReport',
                        reportFiles: 'Spark.html',
                        reportName: 'Spark Report'
                    ])
                }
            }
        }
    }
    
    post {
        always {
            cleanWs()
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Test execution failed!'
        }
    }
} 