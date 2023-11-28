# Rest API for meals data

Here is the list of REST APIs to work with meals data. It is provided as `curl` examples:

## Get current user specified meal
`GET http://host:port/topjava/rest/meals/{mealId}`
#### Example
`curl -i http://localhost:8080/topjava/rest/meals/{100007}`
Response
```
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8

{"id":100007,"dateTime":"2020-01-31T10:00:00","description":"Завтрак","calories":500,"user":null}

```

## Get current user meal list
`GET http://host:port/topjava/rest/meals`
#### Example
`curl http://localhost:8080/topjava/rest/meals`
Response 
```
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

[
  {
    "id": 100009,
    "description": "Ужин",
    "calories": 510,
    "excess": true,
    "dateTime": "2020-01-31T20:00:00"
  },
  {
    "id": 100008,
    "description": "Обед",
    "calories": 1000,
    "excess": true,
    "dateTime": "2020-01-31T13:00:00"
  },
  {
    "id": 100007,
    "description": "Завтрак",
    "calories": 500,
    "excess": true,
    "dateTime": "2020-01-31T10:00:00"
  },
  {
    "id": 100006,
    "description": "Еда на граничное значение",
    "calories": 100,
    "excess": true,
    "dateTime": "2020-01-31T00:00:00"
  },
  {
    "id": 100005,
    "description": "Ужин",
    "calories": 500,
    "excess": false,
    "dateTime": "2020-01-30T20:00:00"
  },
  {
    "id": 100004,
    "description": "Обед",
    "calories": 1000,
    "excess": false,
    "dateTime": "2020-01-30T13:00:00"
  },
  {
    "id": 100003,
    "description": "Завтрак",
    "calories": 500,
    "excess": false,
    "dateTime": "2020-01-30T10:00:00"
  }
]

```
## Get current user meal list filtered by dates 
`GET http://host:port/topjava/rest/meals?startDate={filterStartDate}&endDate={filterEndDate}&startTime={filterStartTime}&endTime={filterEndTime}"`
#### Example 
`curl -i "/topjava/rest/meals/filter?startDate=&endDate=2020-01-30&startTime=&endTime=23:59:59"`
Response
```
HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked

[
  {
    "id": 100005,
    "description": "Ужин",
    "calories": 500,
    "excess": false,
    "dateTime": "2020-01-30T20:00:00"
  },
  {
    "id": 100004,
    "description": "Обед",
    "calories": 1000,
    "excess": false,
    "dateTime": "2020-01-30T13:00:00"
  },
  {
    "id": 100003,
    "description": "Завтрак",
    "calories": 500,
    "excess": false,
    "dateTime": "2020-01-30T10:00:00"
  }
]
```
## Add current user meal
#### Example
`curl -i -X POST http://host:port/topjava/rest/meals  -H "Accept-Charset: utf-8" -H 'Content-Type:application/json;charset=UTF-8' -H 'Content-Language:ru' -d '{"dateTime":"2020-02-01T18:09:00","description":"Созданный ужин","calories":300}'`
Response
```
HTTP/1.1 201
Location: http://localhost:8080/topjava/rest/meals
Content-Type: application/json;charset=UTF-8

{"id":100015,"dateTime":"2020-02-01T18:09:00","description":"Созданный ужин","calories":300,"user":null
```

## Delete current user meal
`DELETE http://host:port/topjava/rest/meals/{mealId}`
#### Example
`curl -i DELETE http://localhost:8080/topjava/rest/meals/{100006}`
Response
```
1HTTP/1.1 200
Content-Type: application/json;charset=UTF-8

{"id":100006,"dateTime":"2020-01-31T00:00:00","description":"Еда на граничное значение","calories":100,"user":null}
```
## Update current user meal
`PUT http://host:port/topjava/rest/meals/{mealId}`
#### Example
`curl -i -X PUT http://localhost:8080/topjava/rest/meals/{100007} -H 'Content-Type:application/json;charset=UTF-8'  -d '{"id":100007,"dateTime":"2020-01-30T10:02:00","description":"Обновленный завтрак","calories":200}'`
Response
```
HTTP/1.1 204
```


