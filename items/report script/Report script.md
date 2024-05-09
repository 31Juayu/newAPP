# Report

## Team structure and roles

Tianyi Xu - manager, backend,tester,presenter

Ruizhe Luo - backend, tester

Zhengyu Peng - front-end, tester, recorder,reporter

Wenzhao Zheng - front back,helpdocument

Jiayu Jian - back-end

## Summary of individual contributions

Tianyi Xu - manage to teamwork, gps, data uploading and storage

Ruizhe Luo - Video loading and display, data uploading and storage

Zhengyu Peng - Page Design, Report, Firebase, record meeting

Wenzhao Zheng - Page Design

Jiayu Jian - Login, search, delete function

 

## APP summary

What we want to achieve is an APP for students to enjoy free educational resources. The goal is to bridge the gap of educational resources available to students from different races, countries and families.

我们要实现的是一个供学生享受免费的教育资源的APP，目标是弥合来自于不同的人种，国家，家庭的学生之间所能获取到的教育资源的差距。

The APP provides users with basic functions (such as login, view data, search, etc.) that can realize the purpose of the APP. In addition, some practical functions (such as load and share data, gps, etc.)  are added to improve the user experience.

APP给用户提供了能够实现APP用途的基础功能（如登录，查看数据，搜索等），此外增加了一些实用功能来提高用户的使用体验。

The following screenshots will be helpful to understand what's going on in the app.

## List of examples

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

## UML diagram and ER diagram

![Untitled](Report%20d4b43c65a286492886332f3255a35983/Untitled.png)

![Untitled](Report%20d4b43c65a286492886332f3255a35983/Untitled%201.png)

## Application Design and Decisions

### Summary of the basic App and implemented features

This app aims to provide free, high-quality educational resources to help students learn independently to achieve the project objectives: A better world, it's in our hands!. The core functions of the application include user management, course management, learning groups, interactive functions and resource sharing.

### List all items implemented

- User management
    
    User registration/login
    
    User profile
    
- Course management
    
    Course upload and publish
    
    Course search and browse
    
- Interactive features
    
    Discussion groups
    
    Private message chat
    
- Resource management
    
    Document upload/download
    
    Video upload/play
    
- Additional Features
    
    Offline Learning Group (GPS based)
    
    etc
    

## Summary of known errors/bugs

At present, it is found that uploading a large amount of video will fail, and the problem can only be solved by splitting the file temporarily.

## Testing summary

No systematic testing has begun.