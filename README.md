# play_with_spring_auth
This project is created for me to play with spring-boot-starter-security and jjwt package. I may use them in my future projects.

## Init setup:
- Spring Boot 3.0.1 + Gradle
- Java 17

## Gradle Dependencies:
- implementation("org.springframework.boot:spring-boot-starter-web")
- implementation("org.springframework.boot:spring-boot-starter-security")
- implementation("io.jsonwebtoken:jjwt:0.9.1")


## Steps to use Spring Boot Security:
1. After import Spring Boot Security package to the project, create a folder(securityConfig) 
    to hold the configuration files. (Make sure you add the @Configuration & @EnableWebSecurity 
    to the class level.)
2. In the config class (SecurityConfig), you will create a function with @Bean on it, type is 
   public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{}
3. Create few user information in the configuration files, so in the initial run, we can use
    service without connect to database. (add @Bean to the creation method).
4. Created our CustomUserDetailsService class, and used it in the SecurityConfig class. 
   CustomUserDetailsService will connect to database and use the information in the database to build our 
   own user object. Setup AuthenticationManager and PasswordEncoder in SecurityConfig class too.
5. Add register() functions in Controller class and permit registration related actions, so users are 
   able to register their account.
6. Add login function in the controller. This function will use authenticationManager to create auth token.
   and then send it back to the success login user's browser. 