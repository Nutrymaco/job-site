# job-site

#TABLE OF CONTENTS
1. [Vacancy API](#vacancy-api)
    
    1.1 [get vacancies](#vacancy-get)
    
    1.2 [create new vacancy]

2. [Filters API](#filters-api)

    2.1 [get filters](#filters-get)

3. [Users API](#users-api)
    
    3.1 [register user](#user-post)
    
4. [Autosearches API](#autosearches-api)
    
    4.1 [get autosearches by user](#user-autosearches-get)
    
    4.2 [create autosearches by user](#user-autosearches-post)
    
    4.3 [get vacancies by autosearch and user](#autosearches-vacancies-get)

# API DOCUMENTATION

## Vacancies API<a name="vacancy-api"></a>

### examples of requests

<a name="vacancy-get">GET /api/v1/vacancies<a>

request with curl without parameters
```sh
$ curl http://89.223.94.132/api/v1/vacancies
```
request with curl with parameters
```sh
$ curl http://89.223.94.132/api/v1/vacancies\?text=senior%20developer\&salary=100000\&experience=3\&cityId=3\&workScheduleId=4
```

### parameters of request description
 
|   parameter name   |       example      |  type  |                                     description                                  |
|--------------------|--------------------|--------|----------------------------------------------------------------------------------|
| text               | senior developer   | string | text for search by title and description                                         |
| salary             | 10000              | int    | min salary in vacancy (salaryFrom in vacancy cant be lower than this value)      |      
| experience         | 3                  | int    | max experience required in vacancy (experienceTo cant be higher than this value) |
| cityId             | 2                  | int[]  | city in vacancy must match with one city by cityId in cityId list                |
| workScheduleId     | 4                  | int[]  | same as in the city's description                                                |
| includeDescription | false (by default) | bool   | include or not description in response                                           |
| page               | 1                  | int    | page number. starts from 1
| size               | 10                 | int    | amount of objects in response. starts from 1

## Filters API

### examples of requests

GET /api/v1/filters <a name="filters-get"></a>

request with curl
```sh
$ curl http://89.223.94.132/api/v1/advanced_filters
```

### parameters of response description

|    parameter name   |    example    |         type        |                                     description                                  |
|---------------------|---------------|---------------------|----------------------------------------------------------------------------------|
| name                | salary        | string              | name of filter                                                                   |
| type                | RANGE         | RANGE/SELECT        | type of filter                                                                   |      
| (1) rangeBoundType  | SINGLE        | SINGLE/DOUBLE       | range has single or double constraint                                            |
| (1) from            | 0.0           | double              | min value in range                                                               |
| (1) to              | 0.0           | double              | max value in range                                                               |
| (2) options         | {'1' : 'one'} | Map<String, String> | options to choose where first value is id to sent in request and second is value |
| (2) multiple        | true          | bool                | user can or not choose multiple options                                          |

(1) - if type of filter equals 'RANGE'
<br>
(2) - if type of filter equals 'SELECT'

### example of response

```json
{
    "filters": [
        {
            "name": "salary",
            "type": "RANGE",
            "rangeBoundType": "SINGLE",
            "from": 0.0,
            "to": 1000000.0
        },
        {
            "name": "experience",
            "type": "RANGE",
            "rangeBoundType": "DOUBLE",
            "from": 0.0,
            "to": 10.0
        },
        {
            "name": "city",
            "type": "SELECT",
            "options": {
                "2": "Тюмень",
                "3": "Москва"
            },
            "multiple": true
        },
        {
            "name": "work schedule",
            "type": "SELECT",
            "options": {
                "4": "FULL",
                "5": "PART",
                "6": "FLEX",
                "7": "REMOTE",
                "8": "OTHER"
            },
            "multiple": true
        }
    ]
}
```

## Users API

### API Methods
 <a name="user-post"></a>
#### register user

```shell script
$ curl --location --request POST '89.223.94.132/api/v1/users'
```
Authorization: Bearer token (Google JWT)

## Autosearches API

### API methods
<a name="user-autosearches-get"></a>
#### get user's autosearches 

```
GET /api/v1/users/{userId}/autosearches/

$ curl --location --request GET '89.223.94.132/api/v1/users/1/autosearches/'
``` 

example of response

```json5
{
    "autosearchesByUser": [
        {
            "autosearch": {
                "id": 9,
                "text": "java senior",
                "experience": 6,
                "salary": 100000,
                "cities": [
                    {
                        "id": 3,
                        "name": "Москва",
                        "country": {
                            "id": 1,
                            "name": "russia"
                        }
                    }
                ],
                "workSchedules": [
                    {
                        "id": 4,
                        "name": "FULL"
                    }
                ],
                "lastDaySelectedBySearch": [
                    "yGsSgXQBfy6s0hUbh7-L",
                    "umsSgXQBfy6s0hUb7MLB",
                    "gGsSgXQBfy6s0hUb5sK-",
                    "lGsSgXQBfy6s0hUb6MKC"
                ]
            },
            "countOfNewVacancies": 4
        }
    ]
}
```
 <a name="user-autosearches-post"></a>
#### add autosearch for user

```
POST /api/v1/users/1/autosearches
```
example of request body
```json5
{
    "text" : "java",
    "experience" : 0,
    "salary" : 20000,
    "cities" : [2, 3], //cities' id list from advanced filters response
    "workSchedules" : [4, 5, 6, 7] //work schedules' id list from advanced filter response
}
```

```
curl --location --request POST '89.223.94.132/api/v1/users/1/autosearches' \
--header 'Content-Type: application/json' \
--data-raw '{
    "text" : "java",
    "experience" : 0,
    "salary" : 20000,
    "cities" : [2, 3],
    "workSchedules" : [4, 5, 6, 7]
}'
```
 <a name="autosearches-vacancies-get"></a>
#### get autosearch's vacancies

##### DESCRIPTION : 
return list of vacancies based on autosearch's filter and user's history it means that vacancy's which presented in this history won't appear in this list  

```
GET /api/v1/users/{userId}/autosearches/{autosearchId}/vacancies

$ curl --location --request GET '89.223.94.132/api/v1/users/1/autosearches/9/vacancies'
``` 

```json5
{
  "vacancies" : [
                    {
                        "id": "yGsSgXQBfy6s0hUbh7-L",
                        "title": "Java - разработчик (Middle, Senior)",
                        "company": "Коннектив Геймс",
                        "shortDescription": null,
                        "description": null,
                        "experienceFrom": 3,
                        "experienceTo": 6,
                        "salaryFrom": 100000,
                        "salaryTo": 300000,
                        "currency": "BTC",
                        "city": "Москва",
                        "cityId": 3,
                        "workSchedule": "FULL",
                        "workScheduleId": 4,
                        "url": null,
                        "date": null
                    },
                    {
                        "id": "umsSgXQBfy6s0hUb7MLB",
                        "title": "Senior Perl разработчик",
                        "company": "Ассоциация IPChain",
                        "shortDescription": null,
                        "description": null,
                        "experienceFrom": 1,
                        "experienceTo": 3,
                        "salaryFrom": 170000,
                        "salaryTo": 300000,
                        "currency": "BTC",
                        "city": "Москва",
                        "cityId": 3,
                        "workSchedule": "FULL",
                        "workScheduleId": 4,
                        "url": null,
                        "date": null
                    },
                    {
                        "id": "gGsSgXQBfy6s0hUb5sK-",
                        "title": "FullStack Java Developer (Middle)",
                        "company": "Геоскан",
                        "shortDescription": null,
                        "description": null,
                        "experienceFrom": 1,
                        "experienceTo": 3,
                        "salaryFrom": 110000,
                        "salaryTo": 150000,
                        "currency": "BTC",
                        "city": "Москва",
                        "cityId": 3,
                        "workSchedule": "FULL",
                        "workScheduleId": 4,
                        "url": null,
                        "date": null
                    },
                    {
                        "id": "lGsSgXQBfy6s0hUb6MKC",
                        "title": "Vue.JS разработчик (Middle+/Senior)",
                        "company": "Ассоциация IPChain",
                        "shortDescription": null,
                        "description": null,
                        "experienceFrom": 3,
                        "experienceTo": 6,
                        "salaryFrom": 170000,
                        "salaryTo": 300000,
                        "currency": "BTC",
                        "city": "Москва",
                        "cityId": 3,
                        "workSchedule": "FULL",
                        "workScheduleId": 4,
                        "url": null,
                        "date": null
                    }
                ]
}
```
