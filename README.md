# Rest Test Task

### How to run  (in my case, this way dosen't work, because the data is saving in DB with the wrong encoding, but if i just run the RestTestTaskApplication class in IDE all works fine):
#### 1. Clone the project
#### 2. Open the console from the root project directory
#### 3. Execute the command: 
```
mvn spring-boot:run
```
#### 4. Then you can test HTTP requests in a browser or Postman with url's:
```
GET     http://localhost:8080/tasks
GET     http://localhost:8080/tasks/participants       
GET     http://localhost:8080/tasks/{id}  
POST    http://localhost:8080/tasks/new  (i know that the correct way is "/tasks", but I think my solution is more readable)
PUT     http://localhost:8080/tasks/{id}
DELETE  http://localhost:8080/tasks/{id}
```
