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
and open http://localhost:8180/swagger-ui/index.html in your browser to connect to the vote application.

If you need to start it on a environment production:
```
java -Djavax.net.ssl.trustStore=./application.keystore -Djavax.net.ssl.trustStorePassword=password -jar build/libs/votingpapers-1.0.0-SNAPSHOT.jar --server.ssl.key-store=./application.keystore --server.ssl.key-store-password=password --server.ssl.trust-store=./application.keystore --server.ssl.trust-store-password=password --server.port=8543 --spring.profiles.active=prod
```
and open https://vota-votingpapers.vige.it:8543/swagger-ui/index.html in your browser to connect to the vote application.

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
Then open https://vota-votingpapers.vige.it:8543/swagger-ui/index.html to connect to the vote application
