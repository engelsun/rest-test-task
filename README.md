# Rest Test Task

### How to run:
#### 1. Clone the project
#### 2. Open the console from the root project directory
#### 3. If your platform encoding is not UTF-8 (for example Cp1252), then execute next command, otherwise skip this step:
```
set MAVEN_OPTS= -Dfile.encoding=UTF-8
```
#### 4. Execute the command: 
```
mvn spring-boot:run
```
#### 5. Then you can test HTTP requests in a browser or Postman with url's:
```
GET     http://localhost:8080/tasks
GET     http://localhost:8080/tasks/participants       
GET     http://localhost:8080/tasks/{id}  
POST    http://localhost:8080/tasks/new  (i know that the correct way is "/tasks", but I think my solution is more readable)
PUT     http://localhost:8080/tasks/{id}
DELETE  http://localhost:8080/tasks/{id}
```
