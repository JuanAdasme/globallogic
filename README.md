
# GlobalLogic application project

This project consists of a users microservice, which is meant to be a test project 
using Spring-Boot and Gradle. It's built with a basic microservice structure, using controller, 
service and repository layers. It also includes an exception handler, a basic Spring Security 
configuration and JWT authentication.


## Run Locally

Clone the project

```bash
  git clone https://github.com/JuanAdasme/globallogic.git
```

Go to the project directory

```bash
  cd globallogic
```

Install dependencies

```bash
  gradle build
```

Run the application

```bash
  gradle run
```


## API Reference

#### Create a new user

```http
  POST /sign-up
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | User's name |
| `email` | `string` | **Required** User's email. Used as username. |
| `password` | `string` | **Required** User's password. |
| `phones` | `List<Phone>` | User's phones. |

#### Retrieve an existing user by its credentials

```http
  POST /login
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| `email` | `string` | **Required** User's email. Used as username. |
| `password` | `string` | **Required** User's password. |



## Authors

- [@JuanAdasme](https://github.com/JuanAdasme)

