# maintenance-thing
Code for a website that will help keep track of tasks that need to be done annually

### What I ran when setting up:
https://developer.okta.com/blog/2017/12/06/bootiful-development-with-spring-boot-and-react
```shell
npm install -g create-react-app@4.0.3
create-react-app client --template typescript
```
Then followed this tutorial a bit https://medium.com/@gkkomensi/packaging-react-and-springboot-applications-with-gradle-23b76523512c


### Running
We kind of hack our React application into the resources for the app module, so if you just try running with
IntelliJ, it may not work. So, you must either use `bootRun` or `bootJar`, which are set up to correctly depend on
the client module.
```shell
./gradlew app:bootRun
```
