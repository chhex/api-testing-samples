# api-testing-samples
Typical Rest API Usage Scenarios 

Typical Rest API Usage Scenarios with a Spring Boot Restcontroller, and
a Mock Service Implementation to control the Testresultats. Used in a
first Interaction of a API Testing Tooling and Approach Evaluation.

# Preconditions:

- Java 8 : For Building und running the Project
- Ruby (*) : [For running the Ruby Test Script](src/test/ruby/testscenario.rb)
- Curl (*) : For running the [Curl Scripts](src/test/scripts)

(*) to run the scripts the Spring Boot Application needs to be started:

`./gradlew bootRun --info `