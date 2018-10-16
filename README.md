# Rest Test Task

### How to run:
#### 1. Clone the project
#### 2. Open the console from the root project directory
#### 3. If your platform encoding is not UTF-8 (for example, Cp1252), then execute next command, otherwise skip this step:
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
For example, for request with POST method on http://localhost:8080/tasks/new url with body like this:
```
{
    "beginDate": "24.01.2018",
    "endDate": "26.10.2018",
    "name": "new task",
    "participants": [
        {
            "id": 1,
            "name": "Светлана"
        },
        {
            "id": 4,
            "name": "Роман"
        },
        {
            "id": 5,
            "name": "Владимир"
        }
    ]
}
```
such response will be receive:
```
{
    "status": "BAD_REQUEST",
    "timestamp": "16-10-2018 09:30:39",
    "message": "The selected participants are working on others tasks during this period, please choose another period",
    "messageDetails": [
        {
            "name": "Владимир",
            "busyFrom": "03.07.2018",
            "busyTo": "21.07.2018"
        },
        {
            "name": "Роман",
            "busyFrom": "03.07.2018",
            "busyTo": "21.07.2018"
        },
        {
            "name": "Светлана",
            "busyFrom": "01.07.2018",
            "busyTo": "10.07.2018"
        }
    ]
}
```
