# Distributed Systems Project

## Part One 
Create a server which will hash passwords and also validate this password to ensure the hashing has 
been done correctly.
This project was created using IntelliJ IDEA application instead of Eclipse. I found while using this application, I ran into less errors when getting everything to compile. The version used was 2019.2.3 x64.


### Code for running jar file
java -jar grpc-async-project.jar

### Command Line Parameters
Port Number - 50551
If the wrong port number is entered, an error message saying incorrect port number will be displayed 
asking the user to try again


### Github URL
https://github.com/rachelmcclelland/DistributedSystemsProject

## Part Two
Design a REST API for a User Account web service using OpenAPI and SwaggerHub.

### Changes made to part one
My project kept hanging when trying to create a new user and hash the password for that account.
I was able to fix the issue by editing the proto file. In HashRepsonse message, hashedPassword had a type of string. Changing it to type bytes instead resolved the issue allowing me to create a new user successfully and hash the password.

### Swagger Hub API Link
https://app.swaggerhub.com/apis/rachelmcclelland6/UserAPI/1-oas3#free

### Code for running jar file
java -jar grpc-async-project-1.0-SNAPSHOT.jar server userApiConfig.yaml

