# Installation Guide
Hardcoded admin account: username: admin and password: admin

## Binary distribution
Download released jar file and run it with `java -jar cybersecuritybase-project-1.0-SNAPSHOT.jar`.
Site will be running in address http://localhost:8080/

## Source distribution
Clone repository and run `CyberSecurityBaseProjectApplication.main()` 
with maven-aware IDE or run `mvn package` to build a jar. 

# List of vulnerabilities

## A3: XSS
Table in `/admin/list` uses `th:utext` for user-supplied name. 
Anyone can inject stored XSS code with form and
attack executed by admin user when he opens `/admin/list`

**Attack:** 
1. Open http://localhost:8080
2. Use e.g. `<script >alert("pwned");</script>my name` as your name.
3. Enter something into address field and hit submit
4. Open http://localhost:8080/admin/list
5. Stored XSS success

**Fix:** 
1. Open `src/main/templates/list.html` 
2. change `th:utext` to `th:text`

More fixes: Add filtering and blacklist letters which are not used in names.


## A4: Insecure Direct Object References
Unsubcribe links use database IDs as personal identifiers. 
IDs are sequential and usually increased by one. 
It's easy to go through all users in database.

**Attack:**
1. Submit three different users with form
2. open http://localhost:8080/unsubscribe/2
3. Page is visible, easy to guess numbers

**Fix:**: 
* No simple fix. Use long and random UUIDs for links. Requires adding new UUID in
Signup. 
* How long UUIDs are considered to be safe for this kind of information?

## A5: Security misconfiguration
Multiple flaws present for this category. Some are trivial to fix other hard to fix

### Deployment
If application if deployed with HTTP admin passwords and auth tokens are
susceptible for snooping.

**Fix:** 
1. Deploy with HTTPS

### Hardcoded admin password
Default admin password is hardcoded in source code.

Fix: 
1. Application should generate random admin password at first run
2. OR ask user to supply password at first run. 

Hardcoded password should be only used for early prototypes and POC code, 
never in production environment.

Fix not implemented for this code. Fix includes adding user database, 
randomly generated default password. Note: when adding those features
remember to add proper hashing and salt.

## A6: data exposure & A7 Missing Function Level Access Control
`SecurityConfiguration` configures security for endpoints. Endpoints are 
listed explicitly. Later someone added REST endpoint but he forgot to add 
security configuration for `/admin/api` endpoint.

As a result API is visible for everyone who knows the URL. 
Attacker can list, add, delete and modify data in SignupRepository.

**Attack:** 
1. Open http://localhost:8080:/admin/api (Use e.g. curl to make sure that there is no
   cached credential in browser)
2. Spring data REST interface is fully visible and usable by anyone.

For example address http://localhost:8080/admin/api/signups/ list all information
about user who have signup with service.

## Bonus round:
`/h2-console/` is enabled by default in development enviroment.
If application is started from fat jar h2-console and other
development tools are automatically disabled. 

Ref: http://docs.spring.io/spring-boot/docs/current/reference/html/using-boot-devtools.html

Note: h2-console seem to be broken because of CSRF.