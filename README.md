# votingpapers
The services to create and get the voting paper

To build the application run the command inside the votingpapers folder
```
./gradlew build
```
Start the Java application with the following commands:
```
java -jar build/libs/votingpapers-1.0.0-SNAPSHOT.jar --server.port=8180 --spring.profiles.active=dev
```
and open http://localhost:8180/swagger-ui.html in your browser to connect to the vote application.

If you need to start it on a environment production:
```
java -jar build/libs/votingpapers-1.0.0-SNAPSHOT.jar --server.port=8543 --server.ssl.key-store=/${your_path}/keystore.p12 --server.ssl.key-store-password=secret --server.ssl.keyStoreType=PKCS12 --server.ssl.keyAlias=tomcat --spring.profiles.active=prod
```
Before to start the HTTPS you need to create a keystore. You can use the following sample:
```
keytool -genkey -alias tomcat -storetype PKCS12 -keyalg RSA -keysize 2048 -keystore /${your_path}/keystore.p12 -validity 3650 -dname "CN=vota-votingpapers.vige.it, OU=Vige, O=Vige, L=Rome, S=Italy, C=IT" -storepass secret -keypass secret
```
moving the ${your_path} variable to your preferred path where put the keystore and open https://vota-votingpapers.vige.it:8543/swagger-ui.html in your browser to connect to the vote application.

## Eclipse

To make the project as an Eclipse project go in the root folder of the project and run the following command:
```
./gradlew eclipse
```

## Docker

If you need a complete environment you can download docker and import the application through the command:
```
docker pull vige/vota-votingpapers
```
To run the image use the command:
```
docker run -d --name vota-votingpapers -p8543:8543 vige/vota-votingpapers
```
Then open https://vota-votingpapers.vige.it:8543/swagger-ui.html to connect to the vote application
