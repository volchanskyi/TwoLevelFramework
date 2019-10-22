node {
  //Integration
  stage ('Checkout') {
  git url: 'https://github.com/volchanskyi/TwoLevelFramework.git'
  //run code quality check
  //sanity check
  //run security tests
}

  //Delivery
  stage ('build') {
    //Build app and run unit tests
    //run integration tests
  docker.build('nexus')
  //copy artifacts to the artifact repo
 }
  
  //Deployment to staging env
  stage ('deploy') {
    //make deploy.sh executable
  bash 'chmod +x deploy.sh'
  bash './deploy.sh'
    //Env check tests
    //Integration tests (regression)
    //E2E acceptance tests (UI functional test)
  }
}
