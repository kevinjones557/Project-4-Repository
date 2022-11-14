# Project-4-Repository
This repository contains the code for Project 4


Buyers -> Kevin -> KevinVinh.txt, hasBlocked.txt


Sellers-> Vinh -> VinhKevin.txt, hasBlocked.txt, statistics.txt        
        
        
*note: 
- Sellers credential file: Username \n StoreName \n Password
- Buyers credential file: Username \n Password
- statistics.txt contains a list of usernames and their number of messages sent to the store followed by words and their frequencies.

Sellers can create multiple stores.

LogIn: Must prompt for username, email, and password. If it is a seller, give them the option to add stores (as many as they want) any additional log in they can add stores
They must be able to change this info at any time

Seller can add stores at any time, can't get rid of stores

create a file under data/(buyers or sellers)/(username)/credentials to store email, username, and password

for sellers create a master file that has
Store1 : Seller1
Store2 : Seller1
Store3 : Seller2
Store4 : Seller3
Store5 : Seller3

all messaging will still be done through <buyername><sellername>.txt no direct communication through stores, just through seller

HUGE PROBLEM IF WE LET THEM EDIT USERNAME

In Menu:
Customers see a list of stores, then go throuhg file and find seller
Customeres also search for seller username directly
