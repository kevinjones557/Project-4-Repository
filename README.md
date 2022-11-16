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

There are two placeholder files initially created simply so that the directories do not disappear. There is one under 'data/buyers' and one under 'data/sellers'. The only reason these placeholders are here are because you cannot push an empty directory to brightspace. In the test cases the placeholders are deleted and then recreated. In the LogIn method these placeholders are automatically deleted; however, if they exist for some reason it will break the rest of the code.  


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
- Aden Riley submitted the Vocareum workplace
- Kevin Jones submitted Report on Brightspace


## 3. Classes, their functionalities and testing

**FileManager**

The FileManager class consists of static methods related to file I/O. It is called in the other classes.

Testing: Run ```MetricManagerTest.java``` first. Ensure that the data folder exists with sellers and buyers empty.

**MetricManager**

The MetricManager class consists of static methods related to Store metrics. It is called in the other classes.

Testing: Run ```FileManagerTest.java```. Ensure that the data folder exists with sellers and buyers empty.

**LogIn**

The LogIn class handles the Log-in portion of the main program, along with user and store creation. It calls the MarketUser and continues from there.

Testing: Run ```LogInTest.java```. Ensure that the users folder has no files other than storeNames, and that storeNames is empty.

**MarketUser**

The MarketUser class handles the messaging portion of the application. It is called from LogIn.

Testing: Run ```MarketUserTest.java``` and ```BlockingAndInvisibleTest.java```.

**User**

An interface that describes what methods a MarketUser should be able to do.

**UserNotFoundException**

Thrown when FileManager is unable to find a User associated with a Username.

**BlockingAndInvisibleTest**

A class to test the blocking and invisible features of the MarketUser class.

**FileManagerTest**

A class to test the static methods of the FileManager class.

**LogInTest**

A class to test the methods of the LogIn class.

**MarketUserTest**

A class to test the messaging, main method, and helper static methods of the MarketUserTest class.

**MetricManagerTest**

A class to test the static methods of the MetricManager class.

**Placeholder**

A class that hanldes the deleting and creating of the placeholder files. 
