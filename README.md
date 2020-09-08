# job-site

API DOCUMENTATION

Vacancies API

requests examples

request with curl without parameters
```sh
$ curl http://89.223.94.132/api/v1/vacancies
```
request with curl with parameters
```sh
$ curl http://89.223.94.132/api/v1/vacancies\?text=senior%20developer\&salary=100000\&experience=3\&cityId=3\&workScheduleId=4
```

parameters description
 
| parameter name |      example     |   type   |                                        description                                       |
|----------------|------------------|----------|------------------------------------------------------------------------------------------|
| text           | senior developer |  string  | text for search by title and description                                                 |
| salary         | 10000            |   int    | min salary in vacancy (salaryFrom in vacancy cant be lower than this value)              |      
| experience     | 3                |   int    | max experience required in vacancy (experienceTo cant be higher than this value)         |
| cityId         | 2                |   int[]  | city in vacancy must match with one city by cityId in cityId list                        |
