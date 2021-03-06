<h1>CORE CONCEPTS USED</h1>
The core concepts used are using File, FileInputStream, FileOutputSteam, ObjectInputStream, and ObjectOutputStream to create Seralized, Sortable ArrayLists built of classes of users and credentials.

Each user will have their own lockme login.

Each user will have their own lockme credential file. The lockme credential will not be delete-able in this credential file.

The Admin interface will allow users to delete files. In this project, this is not password protected, but before release to production this should be password protected.

To sort ArrayLists I used comparators on the User and Creds custom class objects to sort alphabetically by a chosen field, or the Arrays.sort() method where applicable.

To display stored credentials from files, I retrieved the credentials using FileInputStream and ObjectInputStream, then used for() loops to show all credentials saved.

Users can move between all interfaces (login, register, admin) until they login or register. Once they've successfully logged-in they will stay in the program until they quit. To acheive these 'active' interfaces I generally used while loops and used boolean fields to keep interfaces active until the choice to quit was made. 
Custom Exceptions are thrown when invalid characters are used in username or password, or when users are / are not found.

File not found expections are thrown when a file is not found. 


<h1>FLOW OF APP</h1>
To see the flow of the app, please view UML Diagrams here: <a href="https://lucid.app/lucidchart/invitations/accept/inv_072a29c7-37c4-4b13-b55d-96aa220d3ccf"> CLICK FOR DIAGRAMS </a>

Generally...

WelcomeScreen() => admin => admin portal => back to WelcomeScreen()

WelcomeScreen()	=> register => on success: User Portal => On quit: exit application

WelcomeScreen()	=> register => on failure: register => On quit: back to WelcomeScreen()

WelcomeScreen()	=> login => on success: User Portal => On quit: exit application

WelcomeScreen()	=> login => on failure: login => On quit: back to WelcomeScreen()


<h1>SPRINT PLAN</h1>

EACH SPRINT: 1 week.

VELOCITY: 10 Story Points


<h2>RELEASE 1, 3 SPRINTS:</h2>

<h3>SPRINT 1, WEEK 1: </h3>
<ul>
<li>User Story 1, 8 Story Points: As a developer, I want to use UML documentation style so that the flow of my application is easy to understand.</li>
<li>User Story 2, 2 Story Points: As a product manager, I want to plan my sprint so that my developers have a clear idea of the priority and order of work to be completed. </li>
</ul>
<h3>SPRINT 2, WEEK 2: </h3>
<ul>
<li>User Story 1, 2 Story Points: As a LockMe user, I want to see a welcome screen that displays application name, developer name, and user interface details like options I can choose so that I can login, register, or see the admin screen for lockme.com.</li>

<li>User Story 2, 3 Story Points: As a LockMe user WITH existing credentials, I want to log into my account successfully, so that I can manage my credentials (cred management in next sprint). </li>

<li>User Story 3, 3 Story Points: As a LockMe user WITHOUT existing credentials, I want to be able to register aka create a new account, so that I can manage my credentials with lockme.com  (cred management in next sprint). </li>

<li>User Story 4, 2 Story Points: As a LockMe user admin, I want to see all files saved, so that I can manage the files saved to the program (see files, delete files). </li>
</ul>
<h3>SPRINT 3, WEEK 3: </h3>
<ul>
<li>User Story 1, 6 Story Points: As a registered and logged in LockMe user, I want to be able to see (in sorted list), search, save new, and delete my credentials, so that I can manage my credentials myself. </li>

<li>User Story 2, 1 Story Points: As a LockMe user who has just registered, I want want my user and credential files to be created, so that I can login and save my credentials successfully at a later date. </li>

<li>User Story 3, 1 Story Points: As a LockMe user admin, I can see lists of and delete user files as well as see lists of or delete credential files, so that I can manage the files saved to the program. </li>

<li>User Story 4, 2 Story Points: QA and testing of all lockme features. </li>
</ul>
<h3>RELEASE 2 TBD</h3>
<ul>
<li>User Story 1, 2 Story Points: As a admin, I need an admin password to visit the admin portal.</li>
</ul>
