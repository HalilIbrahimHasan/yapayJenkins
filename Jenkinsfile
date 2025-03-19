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
                    
                    // Create report directories
                    sh '''
                        mkdir -p reports/html
                        mkdir -p reports/spark
                        
                        # Copy report files
                        cp -r test-output/SparkReport/* reports/spark/ || true
                        
                        # Create index.html that redirects to the Spark report
                        cat << EOF > reports/html/index.html
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="refresh" content="0; url='../spark/Spark.html'" />
</head>
<body>
    <p>Please wait while you're redirected to the <a href="../spark/Spark.html">test report</a>.</p>
</body>
</html>
EOF
                        
                        # Set permissions
                        chmod -R 755 reports
                    '''
                    
                    // Publish HTML Reports
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'reports/spark',
                        reportFiles: 'Spark.html',
                        reportName: 'Extent Report',
                        reportTitles: 'Test Automation Report'
                    ])
                    
                    // Archive artifacts
                    archiveArtifacts([
                        artifacts: 'reports/**/*',
                        fingerprint: true,
                        allowEmptyArchive: false
                    ])
                }
            }
        }
    }
    
    post {
        always {
            cleanWs(cleanWhenNotBuilt: false,
                   deleteDirs: true,
                   disableDeferredWipeout: true,
                   notFailBuild: true,
                   patterns: [[pattern: 'reports/**', type: 'INCLUDE']])
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Test execution failed!'
        }
    }
} 