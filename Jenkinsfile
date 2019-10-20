node {
  //Integration
  stage ('Checkout') {
  git url: 'https://github.com/volchanskyi/TwoLevelFramework.git'
  //run code quality check
  //run unit tests
  //sanity check
  //run security tests
}

  //Delivery
  stage ('build') {
  docker.build('nexus')
  //copy artifacts to the artifact repo
 }
  
  //Deployment to staging env
  stage ('deploy') {
  sh './deploy.sh'
  }
}
