* Download or clone repository and run it in IntelliJ IDEA
* Go to  ```meal/src/main/resources/application.properties```
and in ```spring.datasource.url``` connect with your MySql database,
in ```spring.datasource.username and spring.datasource.password```
enter your username and password to database. Next in ```spring.mail.username and spring.mail.password``` enter valid
gmail email and password if you want api be able to send emails

# Register

* To make an account use client like Postman, go to:
```
http://localhost:{your_default_port}/registration
```
   and send a body in post request like example below:
```
{
    "login": "seba123",
    "email": "seba123@email.com",
    "password": "password123"
}

```
## Api map:

* Select current exchange rate by currency code and save this request (get request):
```
http://localhost:{your_default_port}/{code}
```
* Exchange currecy by typing what currency you want to change (codeFrom),it amount (amount), 
what currency you want to receive (codeTo). 
This endpoint will return the result and save it (get request):
```
http://localhost:{your_default_port}/{codeFrom}/{codeTo}/{amount}
```
* Send an email with performed operations(get request):
```
http://localhost:{your_default_port}/send
```
