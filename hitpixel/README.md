- I have used Spring Boot to solve the problem.
- I have used H2 in memory database for this because otherwise you will have to install MySQL server locally. SO H2 is a good choice.
- I am providing the client and transaction data below to play with.
- You can import the project in your local IDE and run it.
- Lot of edge cases were not solved for this purpose.But for an actual production solution much more can be done, example having client id in the request.
- JMS could be used for concurrency design, we want to process lot of payments in a short period of time so using ActiveMQ or KafKa or RabbitMQ would be a right choice.
- I have only put two unit tests just to show that I am aware of the testing libraries, but for production we would want better coverage.
- Automation can be written too.
- Created the a Scheduler for Daily/Monthly Invoice Generation.
- Used cron job to process the bills.
- But in production I would write a seperate project all together to process the bills. In fact I would use AWS Lambda with Cloud watch to generate the Invoices.
- I have created an API to manually trigger the Invoice Generation. For both one client and all client. For all client all you need to provide is the billing-interval and the api will do the rest.
- The dataset given is really not optimized, there are lot of gaps, hence I had to work around it too.
- My solution is just a starting point, but there are many use cases needs to be implemented.
- Date formate was wrong in the dataset, for this testing purpose I have used "DD-MM-YYYY HH:mm".

##### HOW TO RUN #####
1. Import the project into an IDE
2. Fine "HitPixelPaymentSystemApplication" main application and run it.
3. Make sure to have MAVEN and JAVA 1.8 on your system.
4. Use PostMan to test the APIs. 

-----
I am happy to run the application with you and explain what was done and why.
Also what else can be improved.

---
For the purpose of an interview this is a massive project, it took me around 16 hours+. I hope you can appreciate the time I have put in it.
Its not a perfect solution but its a good solution for the technologies I have used here.
For production lot more can be brought up and used.

Did not do much Junit tests for the time sake.
----



#### API info below (Use Postman to test it)

 --- Add a new client: http://localhost:8080/api/client/add
Client name should be unique (as per your instructions, but in real world there should be an unique ID).
{
"client": "Pizza 8",
"status":"active",
"billing-interval":"daily",
"email":"c@c.com",
"fees-type": "flat-fee",
"fees": "1.00"
}

-- Add a Transaction : http://localhost:8080/api/transaction/add
{
"orderid":"9999",
"datetime":"31-05-2022 15:30",
"ordername":"chiekn Pizza",
"amount":"50.00",
"currency":"USD",
"cardtype": "visa",
"status":"approved",
"client":"Pizza 8"
}

--- Generate INVOICE API for ALL CLIENT
http://localhost:8080/api/invoice/byInterval?billingInterval=daily
---- Generate INVOICE API for ONE CLIENT
http://localhost:8080/api/invoice/byClientName?clientName=Pizza 8


#### There are add/delete/update/list APIs for Client and Transaction

Swagger integrated


