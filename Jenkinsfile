node {
stage 'Checkout'
git url : 'https://github.com/volchanskyi/TwoLevelFramework.git'

stage 'Build'
docker.build('hello-world')

stage 'Deploy'
sh './deploy.sh'
}
