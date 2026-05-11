The project is created using jdk 11.
====================================================================================================================================

install.ps1 -> Run this script in power shell. This will do the following
a) build all the services.
b) use docker-compose to build images using project's dockerfile and bring the corresponding services up in individual containers.

=====================================================================================================================================

There are two main services:
=============================

1) account-service: This provide following APIs

a) add account details -> http://localhost:9001/accounts/
   {
    "accountId":987654321,
    "pin":4321,
    "openingBalance":1230,
    "overDraft":150
   }
   
b) check account balance -> http://localhost:9001/accounts/checkBalance/accountId/{accountId}/pin/{pin}

c) debit amount from account -> http://localhost:9001/accounts/debit

	{
	 "accountId": 0,
     "debitAmount": 0,
     "pin": 0
	}
	
	
2) atm-srvice: This provide the follwing APIs

a) Initialize ATM cash -> http://localhost:9002/atm/

	{
     "noOfFiveCurrency": 20,
     "noOfTenCurrency": 30,
     "noOfTwentyCurrency": 30,
     "noOfFiftyCurrency": 10
	}
	
b) Withdraw cash -> http://localhost:9002/atm/withdraw

    {
     "accountId":987654321,
     "pin":4321,
     "amount":60
	}

c) 	check balance -> http://localhost:9002/atm/checkBalance/accountId/{accountId}/pin/{pin}

======================================================================================================================================

Code coverge: 

Run the following command in each project to check test code coverage -> 
mvnw clean jacoco:prepare-agent test jacoco:report
	
