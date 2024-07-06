<h1>Ecommerce Test Project</h1>

Considering the fact that the requirement is quite simple and hence doesn't 
require a big software infrastructure; But taking this as a great opportunity 
to reflect my knowledge on the domain, I have added some really interesting capabilities to the project as follows:
<br/>
<ul>
<li>Implemented SOA with API Gateway Architecture (Zuul).</li>
<li>Implemented Eureka Discovery for load balancing.</li>
<li>Created a highly configurable reusable security module.</li>
<li>Added JWT Authentication for incoming requests from external source.</li>
<li>Added Basic Authentication between all service calls.</li>
<li>Added CORS filter.</li>
<li>Configured load balanced RestTemplete bean to add basic authentication authorization header by default.</li>
<li>Added logging for all services, also configured slueth for marking traceId and spanId.</li>
<li>Added user role based access filter with spring security.</li>
<li>Configured to use derby database (not recommended for production use).</li>
<li>Added JPA Repositories for data persistence.</li>
<li>Added incoming request validations with javax.validation.</li>
<li>Written Mockito test cases (Only for service classes in Product service).</li>
<li>Added swagger documentation.</li>
<li>Add Parent POM to hold modules and dependency versions</li>
<li>Configured listener component to load admin details on fresh start of the app</li>
</ul>

**What more could be done**
<ul>
<li>Switch to a different db like MySQL, currently using derby</li>
<li>Add pagination</li>
<li>Add Redis for caching</li>
<li>Implement Controller Advice for proper error response.</li>
</ul>

**Features**
<ul>
<li>User registration, authentication, update and get functionality</li>
<li>Product add, edit, update and get by id, get all and search by name functionality</li>
<li>User Cart with add, update quantity, reduce/delete product from cart, clear whole cart, get cart and finally checkout</li>
</ul>

**Local Setup**
<ul>
<li>Make sure java 8 and maven is installed.</li>
<li>cd to project root directory and run <code>mvn clean install</code> </li>
<li>cd to discovery-service folder and run <code>mvn spring-boot:run</code></li>
<li>cd to user-service folder and run <code>mvn spring-boot:run</code></li>
<li>cd to product-service folder and run <code>mvn spring-boot:run</code></li>
<li>cd to api-gateway folder and run <code>mvn spring-boot:run</code></li>
</ul>

**Swagger Documentation**
<ul>
<li>User service: <a target="_blank">https://localhost:8083/swagger-ui/</a></li>
<li>Product service: <a target="_blank">https://localhost:8082/swagger-ui/</a></li>
</ul>

**Default admin detail**<br/>
<code>{
    "emailId":"admin@ecommerce.com",
    "password":"admin123"
}</code>