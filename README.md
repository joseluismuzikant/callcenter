# Sample application modeling a call center with spring boot 

## Steps to Setup

**1. Clone the application**

	git clone https://github.com/joseluismuzikant/callcenter.git

**2. Start the docker database**
 		
 	sudo docker-compose up


**2.2 You can change the usr/psw in src/main/resources/application.properties **

**3. Package and star the application**

	mvn package
	java -jar target/call-center-0.0.1-SNAPSHOT.jar


	Alternatively, you can run the app without packaging it using:

	mvn spring-boot:run

	The app will start running at <http://localhost:8080>.

**4. Explore Rest APIs**

	The app defines following CRUD APIs.
	
	#Get all the employees
	GET /api/v1/employees

	#Create an operator
	POST /api/v1/operator 

	#Post a new call
    POST  /api/v1/incomingcall/{number}
    
    #Get all waiting calls
    GET  /api/v1/waitingcalls

**5. Documentation and Api rest**

	http://localhost:8080/v2/api-docs
	http://localhost:8080/swagger-ui.html#/call-center-controller
 	
 
    