def call(String stageName){
  
  if ("${stageName}" == "Build")
     {
       sh "mvn clean package"
     }
  else if ("${stageName}" == "SonarQube Report")
     {
       sh "mvn clean sonar:sonar"
     }
  else if ("${stageName}" == "Upload Into Nexus")
     {
       sh "mvn clean deploy"
     }
   else if ("${stageName}" == "Upload Into UAT")
     {
       deploy adapters: [tomcat9(credentialsId: 'tomcat-cred', path: '', url: 'http://172.31.19.63:7000/')], contextPath: 'SEG-app', war: 'target/*.war'
       }
  else if ("${stageName}" == "Approve")
     {
         timeout(time:5, unit:'DAYS'){
        input message: "Approval for Production"
         }
     }
  else if ("${stageName}" == "Upload Into Prod")
     {
       deploy adapters: [tomcat9(credentialsId: 'tomcat-cred', path: '', url: 'http://172.31.19.63:7000/')], contextPath: 'SEG-app', war: 'target/*.war'
       }
  else if ("${stageName}" == "Email Notification")
     {
       emailext body: 'Build success!', subject: 'Project Status-SEG-app', to: 'chuksife@gmail.com' 
       }
}

