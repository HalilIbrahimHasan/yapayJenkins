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
                    
                    // Prepare report directory
                    sh '''
                        mkdir -p test-output/SparkReport
                        chmod -R 755 test-output
                        
                        # Copy all report files
                        if [ -d "target/cucumber-reports" ]; then
                            cp -r target/cucumber-reports/* test-output/SparkReport/ || true
                        fi
                        
                        # Copy any CSS and JS files
                        find . -name "*.css" -exec cp {} test-output/SparkReport/ \\;
                        find . -name "*.js" -exec cp {} test-output/SparkReport/ \\;
                        
                        # Ensure proper permissions
                        chmod -R 755 test-output/SparkReport
                    '''
                    
                    // Publish HTML Report
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: true,
                        keepAll: true,
                        reportDir: 'test-output/SparkReport',
                        reportFiles: 'Spark.html',
                        reportName: 'Extent Spark Report',
                        reportTitles: 'Test Automation Report',
                        includes: '**/*.html,**/*.css,**/*.js,**/*.png,**/*.jpg',
                        escapeUnderscores: true,
                        allowMissing: false,
                        keepAll: true,
                        enableXHTML: true
                    ])
                    
                    // Archive the reports
                    archiveArtifacts([
                        artifacts: 'test-output/**/*',
                        fingerprint: true,
                        allowEmptyArchive: true
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
                   patterns: [[pattern: 'test-output/**', type: 'INCLUDE']])
        }
        success {
            echo 'Tests executed successfully!'
        }
        failure {
            echo 'Test execution failed!'
        }
    }
} 