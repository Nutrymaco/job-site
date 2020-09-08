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

| parameter name |    example      |       description        |
|----------------|-----------------|-------------             |
|text            |senior developer | text for search by title |
|                |                 | and description          |
| salary         | 10000           | min salary in vacancies  |      
