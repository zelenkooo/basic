# Application guide

Project requirements :
- git ( https://git-scm.com )
- java 8 jdk
- maven ( http://maven.apache.org )

Starting application :
- download code using git ( git clone https://github.com/zelenkooo/basic.git )
- compile code using maven ( mvn clean install )
- run app using ( mvn spring-boot:run )
- check is page available at localhost:8080
- check if db is available at http://localhost:8080/h2_console ( jdbc url : jdbc:h2:mem:testdb ) user: sa

Appendix: Email config :<br>
Create PEM file from from smtp server with : echo | openssl s_client -connect yoursever:port 2>&1 | sed -ne '/-BEGIN CERTIFICATE-/,/-END CERTIFICATE-/p' > yourcert.pem<br>
Create JKS file and import pem certificate using for example KeyStoreExplorer or command<br>
Configure email.server properties with path to the jks file, password and smtps endpoint, example (smtps://host:port?username=myUser&password=myPass&debugMode=true&sslContextParameters=#myMailSslContextParameters&mail.smtp.auth=true&mail.smtp.starttls.enable=true)

