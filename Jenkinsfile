node {
    stages {
      stage ('Checkout') {
        steps {
          git url : 'https://github.com/volchanskyi/TwoLevelFramework.git'
         }
        }
      //stage ('Build') {
      //  steps {
      //    sh 'echo uname -r'
       //   docker.build('hello-world')
       //  }
       // } 
  
      stage ('Deploy') {
        steps {
          sh './deploy.sh'
         }
        }
      }
}
