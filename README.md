<!-- JWT with Redis (Jedis) Spring Security Template -->
## About The Template


JWT-Redis Template using Spring Security new approach without WebSecurityConfigurerAdapter (Deprecated)


### Built With

* [![Spring][Spring.com]][Spring-url]
* [![Postgres][Postgre.com]][Postgre-url]


<!-- GETTING STARTED -->
## Getting Started

### Prerequisites

First thing first, install <a href="https://docs.spring.io/spring-boot/docs/1.0.2.RELEASE/reference/html/getting-started-installing-spring-boot.html">SpringBoot</a> and follow those instruction from that documentation

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/ingwerludwig/JWT-Redis-SpringSecurity-Template.git
   ```
   
2. Import Project
   File -> Import -> Existing Maven Project -> Navigate to the folder where you unzipped the zip

3. Create Database on your local with name 'oauth2'

4. Refresh dependencies
   Right Click pom.xml -> Maven -> Reload Project
   
5. Setup your env in application.properties

6. Choose Oauth2practiceApplication -> Right Click then Run


<!-- USAGE EXAMPLES -->
## Usage

For more examples, please look to the <a href="https://documenter.getpostman.com/view/26715144/2s93eVVYcp">documentation</a>



<!-- SPRING SECURITY FLOW -->
## Flow
![spring-boot-authentication-spring-security-architecture](https://user-images.githubusercontent.com/54592376/235856519-8af31b9c-cf20-49ff-aef4-3fe72e3ccbe9.png)

Defined Spring Security Mechanism for Web Security Configuration (JWT) <br />
    1. User want to access certain controller , but SecurityContextHolder needs Token to be verified <br />
        &nbsp;&nbsp;&nbsp;&nbsp;--SecurityContextHolder holds a bunch of Security Configuration, example : WebSecurityConfig--<br />
        &nbsp;&nbsp;&nbsp;&nbsp;--For WebSecurityConfig, we can define order or sequence of filter that must be checked--<br />
        &nbsp;&nbsp;&nbsp;&nbsp;--Each filter must be asking Detailed User Information to AuthenticationManager to create token--<br />
    2. JwtAuthFilter ask AuthenticationManager for UserDetails<br />
    3. JwtAuthFilter ask his provider (JwtAuthProvider) for UserDetails<br />
        &nbsp;&nbsp;&nbsp;&nbsp;--There are many of Authentication type, so certain Provider handle their own type--<br />
    4. Provider ask UserDetailsService to search if the requested user is existed, if yes it will build the UserDetails<br />
    5. When building the UserDetails, PasswordEncoder helps to checking the Password<br />
    6. Next, they return UserDetails to their own PIC to top until reach JwtAuthFilter<br />
    7. JwtAuthFilter receives UserDetails and create the valid token<br />
    8. Token handed to SecurityContextHolder<br />
    9. Filter chain (ApplicationFilterChain) do internalFilter and handle the request method as their<br />
       &nbsp;&nbsp;&nbsp;&nbsp;correspond privilege based on principal in the Token<br />
        <br /><br />
 NOTE : Every Request will be check if the token is valid AND check if token is in Redis Block (Logging Out user removes their token in Redis block)<br />
 EVERY INVALID TOKEN OR NON EXISTED TOKEN IN REDIS, WILL BE REDIRECT TO SIGNIN API<br />


<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE.txt` for more information.



<!-- CONTACT -->
## Contact

Ingwer Ludwig - [@your_twitter](https://twitter.com/your_username)

Project Link: <a href="https://github.com/ingwerludwig/JWT-Redis-SpringSecurity-Template">Click here</a>


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/othneildrew/Best-README-Template/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/othneildrew/Best-README-Template/network/members
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[product-screenshot]: images/screenshot.png
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
[Postgre.com]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white
[Postgre-url]: https://www.postgresql.org/
[Spring.com]: https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/
