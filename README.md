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

### Concepts
#### Thing
A "thing" is a concept in maintenance thing. Things can have many attributes associated with them such as documents,
comments, pictures, etc. Examples of things are:
* Batteries
* Generator
* Toilet
* Bunk bed
* Distilled water

Things may be something that you have and stay, such as a car, or they can be something like food, which you need to
acquire frequently.

Things can also link to other things, however there can be different types of links. Examples:
* "Far right cabinet" has an inventory link to "box of cheez-its"
  * This inventory link keeps track of how many boxes you have - "count=3"
* "Cabin" has a container link to "white bunk bed"
* "Rocky" has a type link to "dog"
* "Steve" has a type link to "stallion"
* "stallion" has a type link to "horse"
* "The Property" has a container link to "The Garage"
* "The Garage" has a container link to "Battery Room"
* "Battery Room" has a container link to "Battery Room Temperature"

... "Battery Room Temperature" (or the link) will somehow be able to keep track of the temperature
and the historical data for that. This historical data should have some sort of retention policy

### Maintenance Task
A "maintenance task" is a concept in maintenance thing. Tasks usually have instructions associated with them,
and can link to "things". Tasks can be:
* Add distilled water to the batteries
  * Requires "distilled water", acts upon "the batteries"
* Clean the bathroom
  * Requires "cleaning supplies", acts upon "the bathroom"
* Change the oil in the car
  * Requires "car tools", acts upon "the car"
* Do an equalize charge of the batteries using the generator

Maintenance tasks can also have results associated with them, which after all results are completed,
the scheduled task (described below) is "done".
For instance:
* fixing the car
  * status: "success" or "failed".
  * completed: date
* report how many bags of dog food we have in storage
  * count: non-negative integer
* take the car to shop
  * cost of repair: non-negative number

### Assign Method
An "assign method" is a concept in maintenance thing. Examples:
* Assign to Bob
* Assign to Bob and John
* Assign to Bob, Karen, or John. Pick randomly
* Assign to Bob unless Bob did $task last, then assign to Karen
* Assign to the "mechanical group" - A group of people. This group can then decide to assign it to a particular person or multiple people.
* Assign to the "cleaner" - A person. The cleaner can easily be changed. This is useful to assign someone many tasks, but the person with this role may change

### Maintenance Schedule
A "maintenance schedule" is a concept in maintenance thing. Schedules are a way to describe reoccurring tasks and
how often they reoccur. Schedules have a list of (task, assign method, due period) also known as a list of "schedule elements".
Examples:
* On the 1st of every month - "Monthly Schedule"
  * "Add distilled water to the batteries" assign to Ben, due in 7 days
  * "Clean the bathroom" assign to the "cleaner"
* Every two weeks on Thursday - "Biweekly Cleaning"
  * "Clean the house" assign to "people living in the house"
* Every day - "Daily Chores"
  * "Feed the dogs" assign to Greg
* On November 10th - "Prepare for winter"
  * "Move paint to warmer area" - assign to Ben
  * "Move coats from basement to first floor" - assign to John
* 10 days after "Add distilled water to the batteries" was completed
  * "Do an equalize charge"

"Triggers" is another possible concept
  
### Scheduled Task
A "scheduled task" is a concept in maintenance thing. A scheduled task is associated with a task and has a date.
Examples:
* "clean the house", assigned on 2021-07-01, due 7 days after
  * Was assigned to Ben
  * Completed on 2021-07-03
* "clean the house", assigning on 2029-07-01, due 7 days after

A "scheduled task" is not stored in the database until:
* Its assign date has passed/is today
* It is marked completed (earlier than the assign date)
* It is manually assigned to someone else

A "scheduled task" does not have to be stored in the database for it to "exist". The program can create it without
storing it in the database because it can create it from a "maintenance schedule". This is commonly how someone
can look at future scheduled tasks.

A "scheduled task" that is in the database for a reason other than its assign date passing can eaisly be removed, and may be removed automatically
if the "maintenance schedule" is altered or deleted. If you don't want this to happen, you should "confirm" the "scheduled task",
so that it stays even if the "maintenance schedule" is altered.

A "scheduled task" is optionally associated with a "maintenance schedule", so that we can tell if a non-confirmed
"scheduled task" is made from a stale (deleted or altered) "maintenance schedule".


### TODO
* Support viewing of PDF documents
  * https://www.npmjs.com/package/reactjs-pdf-reader
