# Lecture | Module Overview, Intro to Backend

## CONTENTS: 
1. What is Backend?
2. What is a Web application?

## I] What is Backend?
1. When we press enter for searching 'Iphone' on Amazon search bar, then the request goes to
   one of Amazon server which itself is a machine/computer. This server's task is to process 
   the code i.e. the business logic and provide the requested data to the client.
2. **Yes, exactly!** Server Holds the Business Logic

The server contains and executes the **core business logic** - the rules, processes, 
and intelligence that make the application work.

### (i) Why Keep Business Logic on Server?

- **Security**: Sensitive rules (pricing, discounts, inventory) stay protected
- **Consistency**: All users get the same business rules applied
- **Maintainability**: Update logic in one place instead of on all user devices
- **Performance**: Heavy computations happen on powerful servers, not user devices

### (ii) Client vs Server Responsibilities:

| **Client (Your Browser)** | **Server (Amazon's Machine)** |
|---------------------------|-----------------------------|
| Display UI/HTML pages | Business logic & rules      |
| Handle user interactions | Process complex calculations |
| Basic form validation | Database operations         |
| Temporary local storage | Authentication & authorization |
| | Payment processing          |

3. Now out of crores of products stored on amazon database, say a few thousands have Iphone 
   in their title like 'Iphone cable', 'Iphone charger', 'Iphone backcover', 'Iphone screenguard', etc.
   So, we are shown just around 20 most relevant products on the first page and then at the bottom
   there are more results in further pages. This process is Pagination. 
4. Benefits of Pagination:
   - Look, there are thousands of products with the title 'Iphone'. Now, if the server 
     sends all the ten thousand products, then the data might lose in the transmission.
   - Also, there are chances that the receiving client isn't capable of loading this much huge 
     list of products. For this to happen, we need very high-end products like a very good 
     smartphone or laptop.
   - Again, recall your shopping experience. How many times, do we go beyond the 2nd or 3rd page
     to look for our respective products because the amazon logic is strong enough to send us the 
     most relevant product on the first page itself.
   - Thus, pagination solves all of these requirements.
5. Once we place order at amazon, then we get a bunch of notifications like email notification, 
   sms notification, app notification, etc. This task is also done by backend application.
6. Now, for big tech companies, there are thousands of servers and many databases too, as
   a single machine/computer cannot take this much load. Even Scaler has hundreds of servers.
7. Each server machine is a replica of each other processing the same code. This avoids
   the probability of a single point of failure, which is:- even if one of the servers fails,
   then request for that server will be routed to some other server. 
8. So, the request from the client doesn't directly go to the server. Rather, it goes through
   the load balancer. It balances the load evenly among the backend servers.
9. On the other end, all the servers are also connected to the centralized database servers, 
   which are also having replicas.
10. NOTE: Please remember that internally in the system, all the machines are having their
    replicas for the very simple reason that the single machine cannot bear the load, and 
    it also helps to avoid the single point of failure.
11. Now, 'Iphone' might be in the title of while searching for 'Iphone', the query in the 
    backend would look something like this:
```sql
SELECT *
FROM products
WHERE title LIKE '%Iphone%'
LIMIT 10 offset 0;
```
Here, we are doing String matching, and we are well aware that there are around 100 million
products stored at amazon database. So, matching Strings for this much data is seriously 
costly (as Strings are matched character by character), hence Amazon uses something 
called 'Elastic search' which we will learn in probably HLD module.

---

## II] What is a Web application?
- A web application (or web app) is a software program that runs on a remote server and 
is accessed by users through a web browser over a network like the internet. 
- Unlike desktop software, you don't need to install a web app on your device; instead,
you use the browser to perform tasks, interact with complex functionality, and access 
data.
- This allows for universal accessibility from any device, eliminates the need for
local installation, and enables features like online shopping, email, and social media. 

### 2.1) How a Web Application Works

1. **User Request:** A user sends a request from their web browser to a web server where 
the application is hosted. 
2. **Server Processing:** The server processes this request, retrieves or updates any 
necessary data, and prepares the response. 
3. **Browser Response:** The processed data is then sent back to the user's browser, 
which displays the application's interface and content.

### 2.2) Key Characteristics and Benefits

- **Browser-Based Access:** Accessed through any common web browser, eliminating the need 
for device-specific installations. 
- **Remote Server Hosting:** All the application's code and data reside on a remote server,
not on the user's computer.   
- **Cross-Platform Compatibility:** Works on various operating systems and devices as long
as a web browser and internet connection are available.   
- **Simplified Updates:** Updates are managed on the server, ensuring all users have the
latest version without manual installation.   
- **No Local Storage Burden:** Web apps don't consume local hard drive space, as their 
files are stored on the remote server. 

### 2.3) Examples
Many common online services function as web applications, including: Email services
like Gmail, Online shopping sites, Social media platforms, and Online productivity 
tools like word processors. 

---

## Links:
1) http://github.com/jenv/jenv  setting up jEnv (java environment)
   This jEnv helps to switch between different java versions. This might be required 
   when we are working on different projects that require different java versions.
   To read more about it, kindly go through the readme file on above GitHub profile.


