node {
  stage ('Checkout') {
  git url: 'https://github.com/volchanskyi/TwoLevelFramework.git'
}

  stage ('build') {
 docker.build('selenium-base')
 }
  
  stage ('deploy') {
  sh './deploy.sh'
  }
}
