# Community Compass README
This is the GitHub repository for the Community Compass service. 

#### Team members:
- Nicole Lin, nsl2126
- Preethi Prakash, pp2769

## Building and Running a Local Instance
In order to build and use our service you must install the following:

1. Maven 3.9.5: https://maven.apache.org/download.cgi Download and follow the installation instructions, be sure to set the bin as described in Maven's README as a new path variable by editing the system variables if you are on windows or by following the instructions for MacOS.
2. JDK 17: This project used JDK 17 for development so that is what we recommend you use: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
3. IntelliJ IDE: We recommend using IntelliJ but you are free to use any other IDE that you are comfortable with: https://www.jetbrains.com/idea/download/?section=windows
After you have everything installed, you an clone this repo using <code> git clone https://github.com/preethiprakash1/communitycompass.git </code>
4. If you wish to run the style checker, you can with <code>mvn checkstyle:check</code> or <code>mvn checkstyle:checkstyle</code> in the project directory if you wish to generate the report.
5. If you wish to run tests and generate a JaCoCo report, you can with the following commands in the project directory
```
mvn clean test
mvn jacoco:report
open target/site/jacoco/index.html
```
The endpoints are listed below in the "Endpoints" section, with brief descriptions of their parameters.

## Running a Cloud based Instance
You can reach this service in the cloud.
1. When running tests in Postman point them to: https://communitycompass-438103.ue.r.appspot.com/<endpoint>
2. To see if the cloud service is still operational please see if the following displays a welcome message: https://communitycompass-438103.ue.r.appspot.com/
3. If the above produced a welcome message "Hi, welcome to Community Compass" then that means the service is operational via the cloud.

## Running Tests
Our unit tests are located under the <code> project/src/test/java/com/example/javadb </code> directory. To run the tests using Java 17, you can run the following commands in the project directory:
```
mvn clean test
mvn jacoco:report
open target/site/jacoco/index.html
```

## API Endpoints
This section describes the endpoints that our service provides, along with their expected inputs and outputs.  It indicates what each endpoint will return upon both success and failure. If the API endpoint structure does not match what you are attempting to send you will receive HTTP 400 NOT FOUND in response.

#### GET /, /index, /home, /welcome
- Expected Input Parameters: N/A
- Expected Output: A welcome message (String)
- Description: Redirects to the homepage. This endpoint should be accessed to view a brief welcome message.
- Upon Success: N/A
- Upon Failure: N/A

#### GET /retrieveDept
- Expected Input Parameters: deptCode (String)
- Expected Output: A ResponseEntity object containing either the details of the Department and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Returns the details of the specified department.
- Upon Success: HTTP 200 Status Code is returned with the department details in the response body.
- Upon Failure: HTTP 404 Status Code with "Department Not Found" if the specified department does not exist.

#### GET /retrieveCourse
- Expected Input Parameters: deptCode (String), courseCode (int)
- Expected Output: A ResponseEntity object containing either the details of the course and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves the details of the requested course.
- Upon Success: HTTP 200 Status Code is returned with the course details.
- Upon Failure:
  - HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.
  - HTTP 404 Status Code with "Department Not Found" if the specified department does not exist.

#### GET /isCourseFull
- Expected Input Parameters: deptCode (String), courseCode (int)
- Expected Output: A ResponseEntity object containing either a boolean indicating course capacity status and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves whether the course has reached its enrollment capacity.
- Upon Success: HTTP 200 Status Code is returned with the capacity status, either "true" or "false".
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### GET /getMajorCountFromDept
- Expected Input Parameters: deptCode (String)
- Expected Output: A ResponseEntity object containing either number of majors for the specified department and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves the number of majors in the specified department.
- Upon Success: HTTP 200 Status Code is returned along with the number of majors.
- Upon Failure: HTTP 404 Status Code with "Department Not Found" if the specified department does not exist.

#### GET /idDeptChair
- Expected Input Parameters: deptCode (String)
- Expected Output: A ResponseEntity object containing either department chair of the specified department and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves the department chair for the specified department.
- Upon Success: HTTP 200 Status Code is returned with the department chair's name.
- Upon Failure: HTTP 404 Status Code with "Department Not Found" if the specified department does not exist.

#### GET /findCourseLocation
- Expected Input Parameters: deptCode (String), courseCode (int)
- Expected Output: A ResponseEntity object containing either the location of the course and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves the location for the specified course.
- Upon Success: HTTP 200 Status Code is returned with the course location.
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### GET /findCourseInstructor
- Expected Input Parameters: deptCode (String), courseCode (int)
- Expected Output: A ResponseEntity object containing either the course instructor and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves the instructor for the specified course.
- Upon Success: HTTP 200 Status Code is returned with the instructor's name.
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### GET /findCourseTime
- Expected Input Parameters: deptCode (String), courseCode (int)
- Expected Output: A ResponseEntity object containing either the details of the course timeslot and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Retrieves the time a specific course meets based on department and course codes.
- Upon Success: HTTP 200 Status Code is returned along with the course time.
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### GET /retrieveCourses
- Expected Input Parameters: courseCode (int)
- Expected Output: A ResponseEntity object containing a formatted representation of all courses with the specified course code and an HTTP 200 response or, an appropriate message indicating the proper response.
- Description: Lists all courses with the specified course code across all departments.
- Upon Success: HTTP 200 Status Code is returned along with the course details.
- Upon Failure: HTTP 404 Status Code with "No courses found with the code" if there are no courses that match the specific course code.

#### PATCH /addMajorToDept
- Expected Input Parameters: deptCode (String)
- Expected Output: A ResponseEntity object that provides a message indicating the successful addition of a major to the specified department, accompanied by an HTTP 200 response, or an appropriate message stating that the department was not found.
- Description: Adds a student to a specified department's major.
- Upon Success: HTTP 200 Status Code is returned along with the message "Attribute was updated successfully"
- Upon Failure: HTTP 404 Status Code with "Department Not Found" if the specified department does not exist.

#### PATCH /removeMajorFromDept
- Expected Input Parameters: deptCode (String)
- Expected Output: A ResponseEntity object that provides a message confirming the successful removal of a major from the specified department, along with an HTTP 200 response, or an appropriate message indicating that the department was not found.
- Description: Removes a student from a specified department's major.
- Upon Success: HTTP 200 Status Code is returned along with the message "Attribute was updated or is at minimum."
- Upon Failure: HTTP 404 Status Code with "Department Not Found" if the specified department does not exist.

#### PATCH /dropStudentFromCourse 
- Expected Input Parameters: deptCode (String), courseCode (int)
- Expected Output: A ResponseEntity object that provides a message confirming whether a student was successfully dropped from the specified course, along with an HTTP 200 response if the operation was successful, a HTTP 400 response if the student could not be dropped, or an appropriate message indicating that the course was not found.
- Description: Drops a student from a specified course.
- Upon Success: HTTP 200 Status Code is returned along with the message "Student has been dropped."
- Upon Failure:
  - HTTP 400 Status Code with "Course full. Student has not been enrolled." if the course is full.
  - HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### PATCH /setEnrollmentCount
- Expected Input Parameters: deptCode (String), courseCode (int), count (int, the new enrollment count)
- Expected Output: A ResponseEntity object that provides a message confirming the successful update of the enrollment count for the specified course, along with an HTTP 200 response, or an appropriate message indicating that the course was not found.
- Description: Updates the enrollment count for a specific course.
- Upon Success: HTTP 200 Status Code is returned along with the message "Attributed was updated successfully."
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### PATCH /changeCourseTime
- Expected Input Parameters: deptCode (String), courseCode (int), time (String)
- Expected Output: A ResponseEntity object that provides a message confirming the successful update of the course time for the specified course, along with an HTTP 200 response, or an appropriate message indicating that the course was not found.
- Description: Changes the meeting time of a course.
- Upon Success: HTTP 200 Status Code is returned along with the message "Attributed was updated successfully."
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### PATCH /changeCourseTeacher
- Expected Input Parameters: deptCode (String), courseCode (int), teacher (String, the new instructor name)
- Expected Output: A ResponseEntity object that provides a message confirming the successful update of the teacher for the specified course, along with an HTTP 200 response, or an appropriate message indicating that the course was not found.
- Description: Changes the instructor of a course.
- Upon Success: HTTP 200 Status Code is returned along with the message "Attributed was updated successfully."
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### PATCH /changeCourseLocation
- Expected Input Parameters: deptCode (String), courseCode (int), location (String)
- Expected Output: A ResponseEntity object that provides a message confirming the successful update of the location for the specified course, along with an HTTP 200 response, or an appropriate message indicating that the course was not found.
- Description: Updates the location for a specified course in a department.
- Upon Success: HTTP 200 Status Code is returned along with the message "Attributed was updated successfully."
- Upon Failure: HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

#### PATCH /enrollStudentInCourse
- Expected Input Parameters: deptCode (String), courseCode (int),
- Expected Output: A ResponseEntity object that provides a message confirming the successful enrollment of a student in the specified course, along with an HTTP 200 response, or an appropriate message indicating that the course is full or that the course was not found.
- Description: Attempts to enroll a student in the specified course.
- Upon Success: HTTP 200 Status Code is returned along with the message "Student has been enrolled."
- Upon Failure:
  - HTTP 400 Status Code with "Course full. Student has not been enrolled." if the course is full.
  - HTTP 404 Status Code with "Course Not Found" if the specified course does not exist.

## Style Checking Report
We used the tool "checkstyle" to check the style of our code and generate style checking reports. You can use the following command inside the project directory to see the report:
```
mvn checkstyle:checkstyle 
target/site/checkstyle.html
open target/site/checkstyle.html
```
You can also see the screenshot below for the most recent Checkstyle Report.
![add checkstyle image here]()

## Branch Coverage Reporting
We used JaCoCo to perform branch analysis in order to see the branch coverage of the relevant code within the code base. You can use the following command inside the project directory to see the report:
```
mvn clean test
mvn jacoco:report
open target/site/jacoco/index.html
```
You can also see the screenshot below for the most recent JaCoCo Report.
![add jacoco image here]()

## Static Code Analysis
We used PMD to perform static analysis on my codebase. Inside the project directory, do <code> pmd check -d project/src/main/java/com/example/javadb -R rulesets/java/quickstart.xml -f text </code> to see the PMD report. Or you can see below for the most recent output.
![add PMD report here]()

## Tools Used
This section includes notes on tools and technologies used in building this service.
- Maven Package Manager
- Checkstyle
  - I utilize Checkstyle for code quality reporting.
- PMD
  - PMD is employed for static analysis of my Java code.
- JUnit
  - JUnit tests get run automatically as part of the CI pipeline.
- JaCoCo
  - JaCoCo is used to generate code coverage reports.
- Postman and Curl
  - Postman and curl are used to test the functionality of my APIs.

