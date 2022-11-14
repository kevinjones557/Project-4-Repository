# Project 4 Documentation


## 1. Instructions


**Compile and Run**


In the list of files and directories, you should see the following java files:
```
FileMananger.java
Filtering.java
LogIn.java
MarketUser.java
MetricManager.java
User.java (interface)
UserNotFoundException.java(Exception class)
```
Compile all the above files and run the LogIn.java file to run the program. From there you can log in, create new accounts, add stores if you are sellers and access all the user interaction options(message, block/unblock, become invisible, search, view statistics, etc...). *If you exit the program without using the program's exit option, your changes will not be saved.*


**Files and Directories**


You should also see directories which are used by the program to store all related data
```
data
>buyers
>sellers

users
```
*Editing these data files and directories without running the program will likely result in unexpected behavior when running the program again. Please do not try to modify the files.*


## 2. Submission
- Kevin Jones submitted the Vocareum workplace
- Vinh Pham Ngoc Thanh submitted Report on Brightspace


## 3. Classes, their functionalities and testing
**FileManager**
The FileManager class consists of static methods related to file I/O. It is called in the other classes.
Testing: Run this first. Ensure that the data folder exists with sellers and buyers empty.

**MetricManager**
The MetricManager class consists of static methods related to Store metrics. It is called in the other classes.
Testing: Run this next. Ensure that the data folder exists with sellers and buyers empty.

**LogIn**
The LogIn class handles the Log-in portion of the main program, along with user and store creation. It calls the MarketUser and continues from there.
Testing: Run this next. Ensure that the users folder has no files other than storeNames, and that storeNames is empty.

**MarketUser**
The MarketUser class handles the messaging portion of the application. It is called from LogIn.
Testing: Run this next.

**User**
An interface that describes what methods a MarketUser should be able to do.

**UserNotFoundException**
Thrown when FileManager is unable to find a User associated with a Username.
