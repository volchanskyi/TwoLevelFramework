node {
  stage 'Checkout'
  git url: 'https://github.com/volchanskyi/TwoLevelFramework.git'

  stage 'build'
  docker.build('mobycounter')

  stage 'deploy'
  sh './deploy.sh'
}
