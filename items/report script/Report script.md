# Report

## Team structure and roles


| UID      |     Name      |                                                                           Role |
|:---------|:-------------:|-------------------------------------------------------------------------------:|
| u7780366 |   Tianyi Xu   |                                              manager, backend,tester,presenter |
| u7776709 |   Ruize Luo   |                                                                backend, tester |
| u7705888 | Wenzhao Zheng |                                          front-end, tester, recorder,reporter  |
| u7727795 | Zhengyu Peng  |                                           front-end, tester, recorder,reporter |
| u7731262 |  Jiayu Jian   | Main programmer, backend programmer, and helping other members with the issues |

## Summary of individual contributions

1. **u7780366, Tianyi Xu**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
  - Feature A1, A2, A3 - class Dummy: 
  - XYZ Design Pattern -  class AnotherClass: 
  -  (any other contribution in the code, including UI and data files) 

- **Code and App Design**
  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
  - [Report Writing?] [Slides preparation?]*


2. **u7776709,  Ruize Luo**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
  - Feature A1, A2, A3 - class Dummy:
  - XYZ Design Pattern -  class AnotherClass:
  -  (any other contribution in the code, including UI and data files)

- **Code and App Design**
  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
  - [Report Writing?] [Slides preparation?]*

3. **u7705888, Wenzhao Zheng**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
  - Feature A1, A2, A3 - class Dummy:
  - XYZ Design Pattern -  class AnotherClass:
  -  (any other contribution in the code, including UI and data files)

- **Code and App Design**
  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
  - [Report Writing?] [Slides preparation?]*

4. **u7727795, Zhengyu Peng**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
  - Feature A1, A2, A3 - class Dummy:
  - XYZ Design Pattern -  class AnotherClass:
  -  (any other contribution in the code, including UI and data files)

- **Code and App Design**
  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
  - [Report Writing?] [Slides preparation?]*

5. **u7731262, Jiayu Jian**  I have 20% contribution, as follows: <br>
- **Code Contribution in the final App**
  - Feature A1, A2, A3 - class Dummy:
  - XYZ Design Pattern -  class AnotherClass:
  -  (any other contribution in the code, including UI and data files)

- **Code and App Design**
  - [What design patterns, data structures, did the involved member propose?]*
  - [UI Design. Specify what design did the involved member propose? What tools were used for the design?]* <br><br>

- **Others**: (only if significant and significantly different from an "average contribution")
  - [Report Writing?] [Slides preparation?]*


 

## Application Description

What we want to achieve is an APP for students to enjoy free educational resources. The goal is to bridge the gap of educational resources available to students from different races, countries and families.

The APP provides users with basic functions (such as login, view data, search, etc.) that can realize the purpose of the APP. In addition, some practical functions (such as load and share data, gps, etc.)  are added to improve the user experience.

The following screenshots will be helpful to understand what's going on in the app.


### Application Use Cases and or Examples

### Use Case 1: User registration and login

Description: Students can register an account through email and log in to the application.

Steps:

1. Open the APP and navigate to the main page.
2. Select the option to create an account.
3. Enter your username, account, and password.
4. Repeat password confirmation.
5. After making sure everything is correct, click the button to sign up.

### Use Case 2: Course search and viewing

Description: Students can search for courses or browse all courses to find the suitable one.

Steps:

1.  Students search for courses by entering keywords in the search bar on the main page.
2. The system returns a list of matched courses.
3. The student browses the total list of courses and selects a course of interest.

### Use Case 3: Course learning

Description: Students can select and begin the course.

Steps:

1. Students select a course and click to enter.
2. Students can view the introductory information of the course and decide whether to enter the course or not.
3. Entering the course, students can see all the resources needed to study the course, including assignments, and can see the discussion group for the course.

### Use Case 4: User interaction

Description: Students can discuss problems in the discussion board, and students can also send private messages to each other.

1. Students choose to join a discussion group.
2. They can see messages posted by others and respond.
3. Each person can also select a specific user and choose to send the private messages.

### Use case 5: Resource uploading and sharing

Description: Users can upload materials with permission and students can download and view them.

Steps:

1. The users select to upload the file and enters the resource description.
2. Students view and download resources on the course page.

### Application UML and ER diagram

![Untitled](Report%20d4b43c65a286492886332f3255a35983/Untitled.png)

![Untitled](Report%20d4b43c65a286492886332f3255a35983/Untitled%201.png)


## Code Design and Decisions

This is an important section of your report and should include all technical decisions made. Well-written justifications will increase your marks for both the report as well as for the relevant parts (e.g., data structure). This includes, for example,

- Details about the parser (describe the formal grammar and language used)

- Decisions made (e.g., explain why you chose one or another data structure, why you used a specific data model, etc.)

- Details about the design patterns used (where in the code, justification of the choice, etc)

*Please give clear and concise descriptions for each subsections of this part. It would be better to list all the concrete items for each subsection and give no more than `5` concise, crucial reasons of your design.

<hr>

### Data Structures

*I used the following data structures in my project:*

1. *LinkedList*
  * *Objective: used for storing xxxx for xxx feature.*
  * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
  * *Reasons:*
    * *It is more efficient than Arraylist for insertion with a time complexity O(1)*
    * *We don't need to access the item by index for xxx feature because...*
    * For the (part), the data ... (characteristics) ...

2. ...

3. ...

<hr>

### Design Patterns
*[What design patterns did your team utilise? Where and why?]*

1. *Adapter Pattern*
  * *Objective: used for storing xxxx for xxx feature.*
  * *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
  * *Reasons:*
    * ...
    * 
2. *Factory Pattern*
* *Objective: used for storing xxxx for xxx feature.*
* *Code Locations: defined in [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and [class AnotherClass, lines l1-l2](url); processed using [dataStructureHandlerMethod](url) and ...
* *Reasons:*
    * ...

<hr>

### Parser

### <u>Grammar(s)</u>
*[How do you design the grammar? What are the advantages of your designs?]*
*If there are several grammars, list them all under this section and what they relate to.*

Production Rules:

    <Non-Terminal> ::= <some output>
    <Non-Terminal> ::= <some output>


### <u>Tokenizers and Parsers</u>

*[Where do you use tokenisers and parsers? How are they built? What are the advantages of the designs?]*

<hr>

### Others

*[What other design decisions have you made which you feel are relevant? Feel free to separate these into their own subheadings.]*

<br>
<hr>


## Implemented Features

This app aims to provide free, high-quality educational resources to help students learn independently to achieve the project objectives: A better world, it's in our hands!. The core functions of the application include user management, course management, learning groups, interactive functions and resource sharing.

### Basic Features

1. [LogIn]. Description of the feature ... (easy)
  * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
  * Description of feature: ... <br>
  * Description of your implementation: ... <br>

2. [SignUp]. Description  ... ... (easy)
  * Code to the Data File [users_interaction.json](link-to-file), [search-queries.xml](link-to-file), ...
  * Link to the Firebase repo: ...

3. ...
   <br>

### Custom Features

Feature Category: Privacy <br>
1. [Privacy-Request]. Description of the feature  (easy)
  * Code: [Class X, methods Z, Y](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
  * Description of your implementation: ... <br>
    <br>

2. [Privacy-Block]. Description ... ... (medium)
   ... ...
   <br><br>

Feature Category: Firebase Integration <br>
3. [FB-Auth] Description of the feature (easy)
  * Code: [Class X, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43) and Class Y, ...
  * [Class B](../src/path/to/class/file.java#L30-85): methods A, B, C, lines of code: 30 to 85
  * Description of your implementation: ... <br>

<hr>

### Surprise Features

- If implemented, explain how your solution addresses the task (any detail requirements will be released with the surprise feature specifications).
- State that "Suprised feature is not implemented" otherwise.

<br> <hr>

## Summary of known errors/bugs

1. *Bug 1:*
  - *A space bar (' ') in the sign in email will crash the application.*
  - ...

2. *Bug 2:*
3. ...

<br> <hr>

## Testing summary

1. Tests for Search
  - Code: [TokenizerTest Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java) for the [Tokenizer Class, entire file](https://gitlab.cecs.anu.edu.au/comp2100/group-project/ga-23s2/-/blob/main/items/media/_examples/Dummy.java#L22-43)
  - *Number of test cases: ...*
  - *Code coverage: ...*
  - *Types of tests created and descriptions: ...*

2. Tests for Upload

...

<br> <hr>

## Team Management

### Meetings Records
* Link to the minutes of your meetings like above. There must be at least 4 team meetings.
  (each commited within 2 days aftre the meeting)
* Your meetings should also have a reasonable date spanning across Week 6 to 11.*


- *[Team Meeting 1](items/meeting01.md)*
- ...
- ...
- [Team Meeting 4](link_to_md_file.md)
- ... (Add any descriptions if needed) ...

<hr>

### Conflict Resolution Protocol
*[Write a well defined protocol your team can use to handle conflicts. That is, if your group has problems, what is the procedure for reaching consensus or solving a problem?
(If you choose to make this an external document, link to it here)]*

This shall include an agreed procedure for situations including (but not limited to):
- e.g., if a member fails to meet the initial plan and/or deadlines
- e.g., if your group has issues, how will your group reach consensus or solve the problem?
- e.g., if a member gets sick, what is the solution? Alternatively, what is your plan to mitigate the impact of unforeseen incidents for this 6-to-8-week project? 
