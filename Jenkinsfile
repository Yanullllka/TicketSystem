pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                bat '"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2026.2.2\\plugins\\maven-plugin\\lib\\maven3\\bin\\mvn.cmd" compile'
            }
        }

        stage('Test') {
            steps {
                bat '"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2026.2.2\\plugins\\maven-plugin\\lib\\maven3\\bin\\mvn.cmd" test'
            }
        }

        stage('Verify') {
            steps {
                bat '"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2026.2.2\\plugins\\maven-plugin\\lib\\maven3\\bin\\mvn.cmd" verify -DskipTests'
            }
        }

        stage('Package') {
            steps {
                bat '"C:\\Program Files\\JetBrains\\IntelliJ IDEA 2026.2.2\\plugins\\maven-plugin\\lib\\maven3\\bin\\mvn.cmd" package -DskipTests'
            }
        }
    }
}