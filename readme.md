<h1>Book-Store Test Project</h1>

**Implemented features**
<ul>
<li>Implemented SOA with API Gateway Architecture with zuul.</li>
<li>Implemented Eureka Discovery for load balancing.</li>
<li>Created a highly configurable reusable security module (library project).</li>
<li>Added JWT Authentication for incoming requests from external source.</li>
<li>Added Basic Authentication between all service calls.</li>
<li>Added CORS filter.</li>
<li>Configured Resttemplete bean to add basic authentication authorization header by default.</li>
<li>Added logging for all services, also configured slueth for marking traceId and spanId.</li>
<li>Added user role based access filter with spring security.</li>
<li>Configured to use derby database (not recommended for production use).</li>
<li>Added JPA Repositories for data persistence.</li>
<li>Added incoming request validations with javax.validation.</li>
<li>Written Mockito test cases (Only for service classes in BookStore service).</li>
<li>Added swagger documentation.</li>
<li>Add Parent POM to hold modules and dependency versions</li>
</ul>

**Pending**
<ul>
<li>Switch to a different db like MySQL, currently using derby</li>
<li>Add pagination</li>
<li>Add Redis for caching</li>
<li>Implement Controller Advice for proper error response.</li>
</ul>

**Setup**
<ul>
<li>cd to project root directory and run <code>mvn clean install</code> </li>
<li>cd to discovery-service folder and run <code>mvn spring-boot:run</code></li>
<li>cd to user-service folder and run <code>mvn spring-boot:run</code></li>
<li>cd to bookstore-service folder and run <code>mvn spring-boot:run</code></li>
<li>cd to api-gateway folder and run <code>mvn spring-boot:run</code></li>
</ul>