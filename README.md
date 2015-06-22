# Project 4 : KissAir Web Application

##Description

A sample flight web application. Administrators can create and update flights, dispatchers can assign crews for the flights. Unauthorised users can visit the home page and see the list of the following flights.

## Tools used

### Back-end

- Jetty
- MySQL
- JDBC
- Hibernate
- HikariCP
- JSTL
- Log4j2

### Front-end

- Bootstrap
- jQuery
- l10n.js https://github.com/eligrey/l10n.js/
- Bootstrap-datetimepicker https://eonasdan.github.io/bootstrap-datetimepicker/
- jQuery-nicescroll https://github.com/inuyaksa/jquery.nicescroll

### Misc

- Maven
- SonarQube
- Apache
- phpMyAdmin

## Details

The web-site consists of a home page, admin and dispatcher pages, and pages for signing in and showing errors. The header and footer parts are separated into different jsp files and are included dynamically into other pages.

There is a sign in/sign out form in the header which uses the standard `j_security_check` servlet. Usernames, passwords and roles are stored in the database and are accessed via Jetty's LoginService configured to use realms. Thus, users with different roles cannot access each others pages, i. e. admin cannot use the dispatcherpage and vice versa.

Any unauthorised user can see the home page where there is a list of coming flights in the next 24 hours.

Admins have access to the full list of flights and can edit their parameters, like airports, aircrafts and dates, or they can add new flights to the list.

Dispatchers can also see the whole flights list and assign or dismiss crews for each flight.

Since reloading pages each time you edit some flights is not very convenient and it uses more bandwidth too, the functionality was implemented using a web-socket. That way only short messages are transfered between the clients and the server. 

In addition to that, the data is stored in a MySQL database, which can be accessed in two ways: using Hibernate ORM framework (the original approach) or with JDBC directly (this functionality was added later). It can be configured in the `src/main/resources/datasource.properties` file. Whichever connectivity is preferred, accessing the database would be made using Hikari Connection Pool.

And lastly, apart from the English version of the site there is a Ukrainian localisation, both for static jsp text and text generated with javascript.

## Things which could be improved

- First of all, the present functionality is very limited and it by no means can be viewed as a real flight control system. On the other hand, it is just a demo project.
- The data is taken from the database eagerly. It was kind of easier to implement it this way, but it is not an optimal solution, as for some tasks you only need flights info, but not the assigned crews info.
- Although the web-site design looks more or less fine, the front-end part could be much better. For example, there could be sorting and filtering functionality for flights and employees. It would be nice to have a search, too.
- Another great idea is to filter aircrafts and employees which cannot be assigned to a flight due to being assigned for a different one, so that there would be no overlaps in time. Plus, it is possible to perform checks if aircrafts flight range is sufficient to perform the selected flight. All the necessary info is present in the database, but there was not much time to add such functionality and it would require a different front-end implementation.
- If a user tries to save invalid or incomplete data, there should be some error messages explaining why the operation cannot be performed.
- `java.sql.Timestamp` should be favoured instead of `java.util.Date`, but it is not critical.
- By default dates on the home page are displayed with the en\_US locale (the default locale), but clicking on 'English' switches the locale to en\_GB. The latter should be used by default.
- Finally, some tests should be written.