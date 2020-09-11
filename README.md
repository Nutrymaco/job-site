# job-site

# API DOCUMENTATION

## Vacancies API

### examples of requests

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

#### register user
```shell script
$ curl --location --request POST '89.223.94.132/api/v1/users'
```
Authorization: Bearer token (Google JWT)

## Autosearches API

### API methods

#### add autosearch
```shell script
$ curl --location --request GET '89.223.94.132/api/v1/users/1/autosearches/9/vacancies'
``` 