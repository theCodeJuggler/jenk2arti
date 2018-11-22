node {
   def mvnHome
   stage('Preparation') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/nishadmehendale/Spring-Boot-Freestyle'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'maven3'
   }
   stage('Build') {
      // Run the maven build
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
   }
   stage('Pushing to Artifactory'){
      withCredentials([[$class: 'UsernamePasswordMultiBinding', credentialsId:'Artifactory', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD']]) {
         sh"echo $USERNAME"
         sh"echo ${Artifactory}"
         sh"curl -X PUT -u ${Artifactory} -T ${workspace}/target/BalanceSheetApp-0.0.1-SNAPSHOT.jar 'http://ec2-54-201-29-152.us-west-2.compute.amazonaws.com:8081/artifactory/libs-release-local/nishad/Balance.jar'"
      }
   }
}
