# micro-login
Spring Boot App for managing users

## Documentation
You can find the project requirements and UML diagrams [here](documentation/)

## Requirements
- Java 8
- Client to make POST requests

## Instructions
- Clone the repository and access into the project folder
- On a Terminal execute `./gradlew bootrun`
- To create a user make a POST request to `http://localhost:8080/sign-up` following the structure detailed in the next section. Along with the user's details you will get a token in the response.
- To login make a POST request to `http://localhost:8080/login` following the structure detailed in the next section (you will need to use the token obtained in the previous call)

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

## TODOs
- Password validation needs to be improved (not complaint with requirements)
- /login should require email and password and generate a token in response instead of asking for a token
