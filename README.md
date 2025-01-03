# Project Overview
**Group 15**

We are developping a website for an online game store. The platform will enable customers to browse and purchase video games online, while providing store employees and managers with tools to efficiently manage inventory, customer orders, and promotions. Key features include:

- Customers: can create accounts, add games to their cart, update individual wishlists, complete purchases, track order history, and write reviews for purchased games.
- Employees: can view customer orders and assist with inventory management; can submit new games to be added to the store, with manager approval
- Managers: can oversee game approvals, employee management, and promotions; handles customer reviews, store policies, and game categories


**More info about team members:** 

| Team Member's Name | Major                    | Year        |
| ------------------ | ------------------------ | ------------|
| Jordan             | Mechanical Engineering   | U3          |
| Ben                | Software Engineering     | U3          |
| Jason              | Software Engineer        | U2          |
| Jacob              | Mechanical Engineering   | U4          |
| Yvehenry           | Computer Engineering     | U3          |


**Project Specification:**

| Team Member's Name | Team Member's Role |  
| ------------------ | ------------------ | 
| Jordan             | Product Owners     |      
| Ben                | Developers         | 
| Jason              | Product Owners     |  
| Jacob              | Scrum Master       | 
| Yvehenry           | Developers         |    
 
## Deliverable 1:
This first deliverable focuses on gathering and defining requirements, developing a domain model, designing a persistence layer, and ensuring that the project setup follows agile practices.<br/> 
[Key Design Decisions](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Class-Diagram-&-Design-Decision-Rationale)<br/>
[Meeting Minutes](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Project-Meeting-Minutes)<br/>

**Other Important Documents:**<br/>
[Use Case Diagram 1](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Accounts-Use-Case-Diagram)<br/>
[Use Case Diagram 2](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Purchasing-Menu-Use-Case-Diagram)<br/>

**Effort Distribution**
| Jordan | Ben | Jason | Jacob | Yvehenry |
| ------ | --- | ----- | ----- | -------- |
|   20%  | 20% |  20%  |  20%  |    20%   |  

## Deliverable 2:
The second deliverable focuses on implementing backend services using RESTful HTTP methods, modeling system behavior, establishing testing practices, and ensuring that the project setup follows agile practices.<br/> 
[Key Design Decisions](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Class-Diagram-&-Design-Decision-Rationale)<br/>
[Meeting Minutes](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Project-Meeting-Minutes)<br/>

**Other Important Documents:**<br/>
[Backend Services](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Backend-Services)<br/>
[Software Quality Assurance Plan](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Software-Quality-Assurance-Plan)<br/>
[Software Quality Assurance Report](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Software-Quality-Assurance-Report)<br/>
[Tests Case Locations](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Backend-Unit-and-Integration-Testing)<br/>

**To Build System and Run Test:**<br/>
-> cd gamestore ./gradlew clean build or ./gradlew build


**Effort Distribution**
| Jordan | Ben | Jason | Jacob | Yvehenry |
| ------ | --- | ----- | ----- | -------- |
|  30%   | 20% |  20%  |  15%  |    15%   |  


## Deliverable 3:
The third deliverable focuses on the implementation of the Web Frontend using HTML, CSS, and JS technology. There was also a focus on the integration of the Frontend with Backend services by issuing asynchronous calls via the systems RESTful API.<br/> 
[Key Design Decisions](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Class-Diagram-&-Design-Decision-Rationale)<br/>
[Meeting Minutes](https://github.com/McGill-ECSE321-Fall2024/project-group-15/wiki/Project-Meeting-Minutes)<br/>

**To Run and Deploy Backend:**<br/>
In terminal: <br/>
-> gameStore % ./gradlew bootRun <br/>

* This will start executing until it reaches 80% (Intended value)

In any browser (Chrome, Safari, etc..): <br/>
-> [localhost:8080](http://localhost:8080) <br/>

* Provides access to SpringBoot



**To Run and Deploy Frontend:**<br/>
In terminal: <br/>
->  GameStore-Frontend % npm run dev <br/>

* This will start the front end and provide a link (listed below) to where it is deployed too <br/>

In any browser (Chrome, Safari, etc..):<br/>
-> [localhost:5173](http://localhost:5173) <br/>

* Provides access to Web application

* Front end README.md
# GameStore-Frontend

This template should help get you started developing with Vue 3 in Vite.

## Recommended IDE Setup

[VSCode](https://code.visualstudio.com/) + [Volar](https://marketplace.visualstudio.com/items?itemName=Vue.volar) (and disable Vetur).

## Customize configuration

See [Vite Configuration Reference](https://vite.dev/config/).

## Project Setup

```sh
npm install
```

### Compile and Hot-Reload for Development

```sh
npm run dev
```

### Compile and Minify for Production

```sh
npm run build
```



**Effort Distribution**
| Jordan | Ben | Jason | Jacob | Yvehenry |
| ------ | --- | ----- | ----- | -------- |
|  20%   | 20% |  20%  |  20%  |    20%   |  
