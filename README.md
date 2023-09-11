# micro-login
Spring Boot App for managing users

## Requirements
- Java 8

## Instructions
- Clone the repository and access into the project folder
- On a Terminal execute `./gradlew bootrun`

## Endpoints

### /sign-up (POST)
```JSON
{
  "name": String,
  "email": String,
  "password": String,
  "phones": [
    {
      "number": long,
      "citycode": int,
      "contrycode": String
    }
  ]
}
```
- "name" and "phones" are optional
- "email" must follow the OWASP definition of valid [OWASP Validation Regex Repository](https://owasp.org/www-community/OWASP_Validation_Regex_Repository)
- "password" must be from 8 to 12 character long (more validations to come)

### /login (POST)
``` JSON
{
    "token":"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyMUBleGFtcGxlLmNvbSIsImlhdCI6MTY5NDQyODQ3Nn0.9N-d3qK9dCI8CXWYyxJr69wk_-0cXcANEGIHiivAKJo"
}
```
