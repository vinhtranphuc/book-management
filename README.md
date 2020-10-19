Book Management

  Plugins:

    Spring Boot 2
    Spring Security With Bear Token
    MySQL
    Mybatis
    JPA/Hibernate
    Eclipse
  Install:

    Clone source
    Import Maven project at Eclipse
    Install maven
    Edit your database sever at line 71,72,37 in DatabaseConfig.java
    Run BookManagementApplication > Your database sever will be auto create database as name "book"

  Api:

    Swagger UI: http://localhost:8888/swagger-ui.html
    Login :
      - User Role:
        + email: user@gmail.com
        + password: 111111
      - Admin Role:
        + email: admin@gmail.com
        + password: 111111
      => Get access token response, then add to the header for requests to the authentication.
   
 Turorial Images :
 
  1. Login
  ![alt text](https://github.com/vinhtranphuc/book-management-api/blob/master/tutorial-image/login.png)
  
  2. Book List
  ![alt text](https://github.com/vinhtranphuc/book-management-api/blob/master/tutorial-image/all-books.png)
  
  3. Others
  https://github.com/vinhtranphuc/book-management-api/blob/master/tutorial-image
