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
| includeDescription | false (by default) | bool   | include or not description in response

## Filters API

### examples of requests

request with curl
```sh
$ curl http://89.223.94.132/api/v1/advanced_filters
```

### parameters of response description
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

