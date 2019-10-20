node {
  stage ('Checkout') {
  git url: 'https://github.com/volchanskyi/TwoLevelFramework.git'
}

  stage ('build') {
 docker.build('hello-world')
 }
  
  stage ('deploy') {
  sh './deploy.sh'
  }
}
