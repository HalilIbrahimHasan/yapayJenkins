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
                    
                    // Create report directories and copy files
                    sh '''
                        # Create directories
                        mkdir -p reports/html-report
                        
                        # Copy all report files
                        cp -r test-output/SparkReport/* reports/html-report/ || true
                        
                        # Copy any CSS and JS files if they exist
                        if [ -d "test-output/SparkReport/css" ]; then
                            cp -r test-output/SparkReport/css reports/html-report/
                        fi
                        if [ -d "test-output/SparkReport/js" ]; then
                            cp -r test-output/SparkReport/js reports/html-report/
                        fi
                        if [ -d "test-output/SparkReport/fonts" ]; then
                            cp -r test-output/SparkReport/fonts reports/html-report/
                        fi
                        
                        # Set permissions
                        chmod -R 755 reports
                        
                        # List contents for debugging
                        echo "Contents of reports/html-report:"
                        ls -la reports/html-report/
                    '''
                    
                    // Publish HTML Report
                    publishHTML(target: [
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'reports/html-report',
                        reportFiles: 'Spark.html',
                        reportName: 'Extent Report',
                        reportTitles: 'Test Automation Report',
                        includes: '**/*'
                    ])
                    
                    // Archive the reports
                    archiveArtifacts(
                        artifacts: 'reports/**/*',
                        fingerprint: true,
                        allowEmptyArchive: false,
                        onlyIfSuccessful: false
                    )
                }
            }
        }
    }
    
    post {
        always {
            // Clean workspace but keep reports
            cleanWs(
                cleanWhenNotBuilt: false,
                deleteDirs: true,
                disableDeferredWipeout: true,
                notFailBuild: true,
                patterns: [
                    [pattern: 'reports/**', type: 'INCLUDE'],
                    [pattern: '**/target/**', type: 'INCLUDE']
                ]
            )
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Test execution failed!'
        }
    }
} 